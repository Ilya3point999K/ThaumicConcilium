package com.ilya3point999k.thaumicconcilium.client.render.item;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.items.DrainageSyringe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.ConfigBlocks;

public class DrainageSyringeRenderer implements IItemRenderer {
    IModelCustom model;
    IModelCustom cap;
    private static final ResourceLocation textureRL = new ResourceLocation(ThaumicConcilium.MODID + ":textures/items/syringe.png");
    private static final ResourceLocation modelRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/syringe.obj");

    private static final ResourceLocation capRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/cap.obj");

    private static final ResourceLocation visRL = new ResourceLocation(ThaumicConcilium.MODID + ":textures/models/cap_vis.png");
    private static final ResourceLocation manaRL = new ResourceLocation(ThaumicConcilium.MODID + ":textures/models/cap_mana.png");
    private static final ResourceLocation bloodRL = new ResourceLocation(ThaumicConcilium.MODID + ":textures/models/cap_blood.png");


    public DrainageSyringeRenderer() {
        this.model = AdvancedModelLoader.loadModel(modelRL);
        this.cap = AdvancedModelLoader.loadModel(capRL);
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
        if (type == ItemRenderType.EQUIPPED) {
            rve_id = mc.renderViewEntity.getEntityId();
            player_id = ((EntityLivingBase) data[1]).getEntityId();
        }

        EntityPlayer playermp = mc.thePlayer;
        float ticks = (float) Minecraft.getMinecraft().renderViewEntity.ticksExisted;
        float par1 = UtilsFX.getTimer(mc).renderPartialTicks;
        float var7 = 0.8F;
        EntityPlayerSP playersp = (EntityPlayerSP) playermp;
        GL11.glPushMatrix();
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON && player_id == rve_id && mc.gameSettings.thirdPersonView == 0) {
            GL11.glTranslatef(0.5F, 0.75F, -1.0F);
            GL11.glRotatef(135.0F, 0.0F, -1.0F, 0.0F);
            float f1 = UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer) + (UtilsFX.getEquippedProgress(mc.entityRenderer.itemRenderer) - UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer)) * par1;
            GL11.glTranslatef(-0.7F * var7, -(-0.65F * var7) + (1.0F - f1) * 1.5F, 0.9F * var7);

            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.0F * var7, -0.9F * var7);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            mc.renderEngine.bindTexture(mc.thePlayer.getLocationSkin());
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.5F, -0.7F, 0.6F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);

            NBTTagCompound tag = item.getTagCompound();
            Render var24 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
            RenderPlayer var26 = (RenderPlayer) var24;
            float wave = (float) (Math.sin(playermp.getItemInUseDuration())) ;
            float var13 = 1.1F;
            GL11.glScalef(var13, var13, var13);
            GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, -0.5F, 0.5F);
            if(tag != null){
                if (!tag.getBoolean("IsOpen")){
                    GL11.glRotatef(wave, 1.0F, 0.0F, 0.0F);
                } else {
                    GL11.glRotatef(playermp.isUsingItem() ? -8.0F : 0.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(wave, 0.0F, 1.0F, 0.0F);
                }
            }

            if (playermp.isUsingItem()){
                GL11.glRotatef(MathHelper.clamp_float(-(playermp.getItemInUseDuration() * 5.0F), -20.0F, 0.0F), 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.1F, 0.0F);
            }
            var26.renderFirstPersonArm(mc.thePlayer);
            GL11.glPopMatrix();

            GL11.glPopMatrix();
            GL11.glTranslatef(-1.0F, 0.6F, 1.8F);
            GL11.glRotatef(35.0F, 0.0F, 0.0F, 1.0F);
            if (playermp.isUsingItem()){
                GL11.glRotatef(10.0F, 0.0F, 0.0f, -1.0F);
                GL11.glRotatef(MathHelper.clamp_float( 50.0F - (playermp.getItemInUseDuration() * 6.0F), 20.0F, 50.0F), 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(-0.1F, -0.2F, -1.0F);
            }
            if(tag != null){
                if (!tag.getBoolean("IsOpen")){
                    GL11.glRotatef(wave, 0.0F, 1.0F, 0.0F);
                } else{
                    GL11.glRotatef(playermp.isUsingItem() ? -10.0F : 0.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(wave, 0.0F, 0.0F, 1.0F);
                }
            }
            GL11.glEnable(32826);
            GL11.glScalef(0.9F, 0.9F, 0.9F);

            if (tag != null) {
                if (!tag.getBoolean("IsOpen")) {
                    GL11.glPushMatrix();
                    switch (tag.getInteger("Type")) {
                        case 0: {
                            mc.renderEngine.bindTexture(visRL);
                            cap.renderAll();
                            break;
                        }
                        case 1: {
                            mc.renderEngine.bindTexture(manaRL);
                            cap.renderAll();
                            break;
                        }
                        case 2: {
                            mc.renderEngine.bindTexture(bloodRL);
                            cap.renderAll();
                            break;
                        }
                    }
                    GL11.glPopMatrix();
                }
                if (tag.getInteger("Amount") > 0) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(-1.4F, 0.0F, 0.0F);
                    GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glScalef(0.7F, 0.7F, 0.7F);
                    renderLiquid(tag.getInteger("Type"), tag.getInteger("Amount"));
                    GL11.glPopMatrix();
                }
            }
        } else {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            if (type == ItemRenderType.EQUIPPED) {
                GL11.glTranslatef(0.0F, 2.5F, 1.4F);
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                GL11.glRotatef(-30.0F, 0.0F, 1.0F, 0.0F);
            } else if (type == ItemRenderType.INVENTORY) {
                //GL11.glRotatef(60.0F, 1.0F, 0.0F, 0.0F);
                //GL11.glRotatef(30.0F, 0.0F, 0.0F, -1.0F);
                //GL11.glRotatef(248.0F, 0.0F, -1.0F, 0.0F);
                //GL11.glScalef(1.0F, 1.0F, 1.0F);
            } else if (type == ItemRenderType.ENTITY){
                GL11.glTranslatef(0.0F, 0.5F, 0.0F);
            }
        }
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        mc.renderEngine.bindTexture(textureRL);
        this.model.renderAll();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.11F, 0.0F);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public void renderLiquid(int type, int amount) {
            int maxAmount = 1;
            int color = 0x9700c0;
            switch (type){
                case 0: {
                    maxAmount = DrainageSyringe.MAXVIS;
                    color = 0x9700c0;
                    break;
                }
                case 1:{
                    maxAmount = DrainageSyringe.MAXMANA;
                    color = 0x0fd9ff;
                    break;
                }
                case 2:{
                    maxAmount = DrainageSyringe.MAXBLOOD;
                    color = 16711680;
                    break;
                }
                default:
                    maxAmount = DrainageSyringe.MAXVIS;
                    color = 0x9700c0;
                    break;
            }
            GL11.glPushMatrix();
            //GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            RenderBlocks renderBlocks = new RenderBlocks();
            GL11.glDisable(2896);
            float level = (float)amount / (float)maxAmount * 0.625F;
            Tessellator t = Tessellator.instance;
            renderBlocks.setRenderBounds(0.25, 0.0625, 0.25, 0.75, 0.0625 + (double)level, 0.75);
            t.startDrawingQuads();
            t.setColorOpaque_I(color);

            int bright = 200;

            t.setBrightness(bright);
            IIcon icon = ((BlockJar)ConfigBlocks.blockJar).iconLiquid;
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            renderBlocks.renderFaceYNeg(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceYPos(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceZNeg(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceZPos(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceXNeg(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceXPos(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            t.draw();
            GL11.glEnable(2896);
            GL11.glPopMatrix();
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
}