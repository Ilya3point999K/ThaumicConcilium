package com.ilya3point999k.thaumicconcilium.client.render.item;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class ThaumaturgeDrumRenderer implements IItemRenderer {
    //DrumModel auraModel;
    IModelCustom auraModel;
    IModelCustom spiderModel;

    private static final ResourceLocation auraRl = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/auram_thaum_drum.png");
    private static final ResourceLocation spiderRl = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/warped_thaum_drum.png");

    private static final ResourceLocation auraModelRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/auram_thaum_drum.obj");
    private static final ResourceLocation spiderModelRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/warped_thaum_drum.obj");

    public ThaumaturgeDrumRenderer(){
        this.auraModel = AdvancedModelLoader.loadModel(auraModelRL);
        this.spiderModel = AdvancedModelLoader.loadModel(spiderModelRL);
    }
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.BLOCK_3D;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();
        int rve_id = 0;
        int player_id = 0;
        float pt = UtilsFX.getTimer(mc).renderPartialTicks;
        float var7 = 0.8F;
        if (type == ItemRenderType.EQUIPPED) {
            rve_id = mc.renderViewEntity.getEntityId();
            player_id = ((EntityLivingBase)data[1]).getEntityId();
        }

        EntityPlayer playermp = mc.thePlayer;
        EntityPlayerSP playersp = (EntityPlayerSP)playermp;
        GL11.glPushMatrix();

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON && player_id == rve_id && mc.gameSettings.thirdPersonView == 0) {
            GL11.glTranslatef(0.5F, 0.75F, -1.0F);
            GL11.glRotatef(135.0F, 0.0F, -1.0F, 0.0F);
            float f3 = playersp.prevRenderArmPitch + (playersp.renderArmPitch - playersp.prevRenderArmPitch) * pt;
            float f4 = playersp.prevRenderArmYaw + (playersp.renderArmYaw - playersp.prevRenderArmYaw) * pt;
            GL11.glRotatef((playermp.rotationPitch - f3) * 0.1F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef((playermp.rotationYaw - f4) * 0.1F, 0.0F, 1.0F, 0.0F);
            float f1 = UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer) + (UtilsFX.getEquippedProgress(mc.entityRenderer.itemRenderer) - UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer)) * pt;
            GL11.glTranslatef(-0.7F * var7, -(-0.65F * var7) + (1.0F - f1) * 1.5F, 0.9F * var7);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.0F * var7, -0.9F * var7);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glScalef(5.0F, 5.0F, 5.0F);
            mc.renderEngine.bindTexture(mc.thePlayer.getLocationSkin());

            GL11.glPushMatrix();
            GL11.glTranslatef(-0.0F, -1.0F, -1.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);

            Render var24 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
            RenderPlayer var26 = (RenderPlayer)var24;
            float var13 = 1.0F;
            GL11.glScalef(var13, var13, var13);
            var26.renderFirstPersonArm(mc.thePlayer);
            GL11.glPopMatrix();

            //right
            if(playermp.getItemInUse() != null){
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.4F, 0.0F, 0.6F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
                float wave = (float) (Math.sin(playermp.getItemInUseDuration()) * 60.0F);
                GL11.glRotatef(wave, 0.0F, 0.0F, 1.0F);
                wave = (float) (Math.sin(playermp.getItemInUseDuration()) * 60.0F);
                GL11.glRotatef(wave, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(var13, var13, var13);
                var26.renderFirstPersonArm(mc.thePlayer);
                GL11.glPopMatrix();
            } else {
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0F, -0.6F, 1.1F);
                GL11.glRotatef(-45.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-65.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var13, var13, var13);

                var26.renderFirstPersonArm(mc.thePlayer);
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
            GL11.glRotatef(0.0F, 0.0F, 0.0F, -1.0F);
            GL11.glTranslatef(0.5F, -5.5F, -0.7F);
            GL11.glEnable(32826);
            GL11.glScalef(3.0F, 3.0F, 3.0F);
        } else {
            if (type == ItemRenderType.EQUIPPED) {
                GL11.glRotatef(40.0F, -0.5F, 0.0F, 1.0F);
                GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            } else if (type == ItemRenderType.INVENTORY) {
                GL11.glTranslatef(0.0F, -0.3F, 0.0F);
                GL11.glScalef(0.6F, 0.6F, 0.6F);
            } else if (type == ItemRenderType.ENTITY){
                GL11.glTranslatef(0.0F, 0.5F, 0.0F);
            }
        }
        if (item.getItemDamage() == 0) {
            mc.renderEngine.bindTexture(auraRl);
            this.auraModel.renderAll();
        } else {
            mc.renderEngine.bindTexture(spiderRl);
            this.spiderModel.renderAll();
        }
        GL11.glPopMatrix();

    }
}
