package com.ilya3point999k.thaumicconcilium.core.transformers;

import com.ilya3point999k.thaumicconcilium.core.AsmHelper;
import com.ilya3point999k.thaumicconcilium.core.SubTransformer;
import cpw.mods.fml.relauncher.Side;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ImpDealTransformer extends SubTransformer {
    public ImpDealTransformer() {
        super("com.emoniph.witchery.entity.EntityImp");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByDescriptor(
                node,
                "interact",
                "interact",
                "(Lnet/minecraft/entity/player/EntityPlayer;)Z"
        );

        MethodInsnNode activate =
                AsmHelper.getFirstMatchingMethodInsNode(
                        mn,
                        "com/emoniph/witchery/item/ItemGeneralContract",
                        "activate",
                        "activate",
                        "(Lnet/minecraft/item/ItemStack;"
                                + "Lnet/minecraft/entity/EntityLivingBase;)Z"
                );

        VarInsnNode affectionLoad =
                (VarInsnNode) activate.getPrevious();

        VarInsnNode stackLoad =
                (VarInsnNode) affectionLoad.getPrevious();

        int stackVar = stackLoad.var;
        int affectionVar = affectionLoad.var;

        for (AbstractInsnNode insn = mn.instructions.getFirst();
             insn != null;
             insn = insn.getNext()) {

            if (insn instanceof LdcInsnNode
                    && "entity.witchery.imp.spell.feelthefire"
                    .equals(((LdcInsnNode) insn).cst)) {

                AbstractInsnNode cursor = insn.getNext();
                while (cursor != null &&
                        !(cursor instanceof MethodInsnNode)) {
                    cursor = cursor.getNext();
                }

                if (cursor == null)
                    return;

                MethodInsnNode chatCall =
                        (MethodInsnNode) cursor;

                mn.instructions.insertBefore(
                        chatCall,
                        new VarInsnNode(Opcodes.ALOAD, 1)
                );

                mn.instructions.insertBefore(
                        chatCall,
                        new VarInsnNode(Opcodes.ALOAD, stackVar)
                );

                mn.instructions.insertBefore(
                        chatCall,
                        new VarInsnNode(Opcodes.ALOAD, affectionVar)
                );

                mn.instructions.insertBefore(
                        chatCall,
                        new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "com/ilya3point999k/thaumicconcilium/api/event/WitcheryImpDealEvent",
                                "fireImpDealEvent",
                                "(Lnet/minecraft/entity/player/EntityPlayer;"
                                        + "Lnet/minecraft/item/ItemStack;"
                                        + "Lnet/minecraft/entity/EntityLivingBase;)V",
                                false
                        )
                );

                return;
            }
        }
    }
}
