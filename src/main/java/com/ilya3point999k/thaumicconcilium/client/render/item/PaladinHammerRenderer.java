package com.ilya3point999k.thaumicconcilium.client.render.item;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class PaladinHammerRenderer implements IItemRenderer {
    final IModelCustom model;
    final ResourceLocation modelRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/Molot.obj");
    final ResourceLocation textureRL = new ResourceLocation(ThaumicConcilium.MODID +":textures/models/Molot.png");
    public PaladinHammerRenderer() {
        model = AdvancedModelLoader.loadModel(modelRL);
    }

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return helper != IItemRenderer.ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();

        final EntityPlayer player = mc.thePlayer;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glPushMatrix();

        if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(0F, 1.5F, 0F);
        }
        else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {

            GL11.glTranslatef(0.5F, 1.0F, 0.3F);
            GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
        }
        else if (type == ItemRenderType.INVENTORY) {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glRotatef(-65, 1.0F, 0.0F, 0.0F);
            //GL11.glRotatef(-50, 0.0F, 1.0F, 0.0F);
            //GL11.glTranslatef(0F, 1F, 0.0F);
        }
        else {
            GL11.glRotatef(45, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 1.3F, 0.7F);
        }

        mc.renderEngine.bindTexture(textureRL);

        model.renderAll();

        GL11.glPopMatrix();

        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();

    }

}
