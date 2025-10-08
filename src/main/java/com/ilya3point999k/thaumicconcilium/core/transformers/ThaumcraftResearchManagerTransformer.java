package com.ilya3point999k.thaumicconcilium.core.transformers;

import com.ilya3point999k.thaumicconcilium.core.AsmHelper;
import com.ilya3point999k.thaumicconcilium.core.SubTransformer;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * {@link thaumcraft.common.lib.research.ResearchManager#completeResearch(EntityPlayer, String)}
 */
public class ThaumcraftResearchManagerTransformer extends SubTransformer {

    public ThaumcraftResearchManagerTransformer() {
        super("thaumcraft.common.lib.research.ResearchManager");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByDescriptor(node,
                "completeResearch",
                "completeResearch",
                "(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;)V");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingMethodInsNode(mn,
                "thaumcraft/api/ThaumcraftApi",
                "getWarp",
                "getWarp",
                "(Ljava/lang/Object;)I")
                .getNext();
        mn.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "com/ilya3point999k/thaumicconcilium/api/event/ThaumcraftResearchCompletedEvent",
                "fireResearchCompletedEvent",
                "(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;)V",
                false));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 2)); //String
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 1)); //EntityPlayer
    }
}
