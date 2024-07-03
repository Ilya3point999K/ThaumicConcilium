package com.ilya3point999k.thaumicconcilium.client.render.item;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TerraCastGemRenderer implements IItemRenderer {
    IModelCustom model;
    private static final ResourceLocation textureRL = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/gem_top.png");
    private static final ResourceLocation modelRL = new ResourceLocation(ThaumicConcilium.MODID+":models/gem_top.obj");


    public TerraCastGemRenderer(){
        this.model = AdvancedModelLoader.loadModel(modelRL);
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
            player_id = ((EntityLivingBase)data[1]).getEntityId();
        }

        EntityPlayer playermp = mc.thePlayer;
        float ticks = (float)Minecraft.getMinecraft().renderViewEntity.ticksExisted;
        float par1 = UtilsFX.getTimer(mc).renderPartialTicks;
        float var7 = 0.8F;
        EntityPlayerSP playersp = (EntityPlayerSP)playermp;
        GL11.glPushMatrix();
        int sw;
        int posY;
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON && player_id == rve_id && mc.gameSettings.thirdPersonView == 0) {
            GL11.glTranslatef(0.5F, 0.75F, -1.0F);
            GL11.glRotatef(135.0F, 0.0F, -1.0F, 0.0F);
            float f3 = playersp.prevRenderArmPitch + (playersp.renderArmPitch - playersp.prevRenderArmPitch) * par1;
            float f4 = playersp.prevRenderArmYaw + (playersp.renderArmYaw - playersp.prevRenderArmYaw) * par1;
            GL11.glRotatef((playermp.rotationPitch - f3) * 0.1F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef((playermp.rotationYaw - f4) * 0.1F, 0.0F, 1.0F, 0.0F);
            float var10000 = playermp.prevRotationPitch + (playermp.rotationPitch - playermp.prevRotationPitch) * par1;
            float f1 = UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer) + (UtilsFX.getEquippedProgress(mc.entityRenderer.itemRenderer) - UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer)) * par1;
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
            GL11.glTranslatef(-0.5F, -0.7F, 0.6F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);

            Render var24 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
            RenderPlayer var26 = (RenderPlayer)var24;
            float var13 = 1.0F;
            GL11.glScalef(var13, var13, var13);
            var26.renderFirstPersonArm(mc.thePlayer);
            GL11.glPopMatrix();

            GL11.glPopMatrix();
            GL11.glTranslatef(-1.2F, 0.2F, 2.0F);
            if (item.getItemDamage() == 1) {
                GL11.glRotatef(((ticks * (playermp.getItemInUse() != null ? 9.0F : 1.0F)) % 360.0F), 0.0F, 1.0F, 0.0F);
            }
            NBTTagCompound tag = item.getTagCompound();
            if(tag != null) {
                if(tag.hasKey("name")) {
                    ItemStack stack = new ItemStack(GameData.getItemRegistry().getObject(tag.getString("name")), 1, tag.getInteger("meta"));
                    EntityItem entityItem = new EntityItem(playermp.getEntityWorld(), 0.0, 0.0, 0.0, stack);
                    entityItem.hoverStart = 0.0F;
                    GL11.glTranslatef(0.0F, -0.2F, 0.0F);
                    GL11.glScalef(1.1F, 1.1F, 1.1F);
                    RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0, 0.0, 0.0, 0.0F, 0.0F);
                    if (!Minecraft.isFancyGraphicsEnabled()) {
                        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                        RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0, 0.0, 0.0, 0.0F, 0.0F);
                    }
                    GL11.glTranslatef(0.0F, 0.2F, 0.0F);
                    GL11.glScalef(0.9F, 0.9F, 0.9F);
                }
            }
            GL11.glEnable(32826);
            GL11.glScalef(0.4F, 0.4F, 0.4F);
        } else {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            if (type == ItemRenderType.EQUIPPED) {
                GL11.glTranslatef(0.0F, 2.5F, 1.4F);
                GL11.glScalef(0.8F, 0.8F, 0.8F);
                GL11.glRotatef(30.0F, 0.0F, 0.0F, -1.0F);
                if (item.getItemDamage() == 1) {
                    GL11.glRotatef(((ticks * (playermp.getItemInUse() != null ? 9.0F : 1.0F)) % 360.0F), 0.0F, 1.0F, 0.0F);
                }
            } else if (type == ItemRenderType.INVENTORY) {
                //GL11.glRotatef(60.0F, 1.0F, 0.0F, 0.0F);
                //GL11.glRotatef(30.0F, 0.0F, 0.0F, -1.0F);
                //GL11.glRotatef(248.0F, 0.0F, -1.0F, 0.0F);
                if (item.getItemDamage() == 1) {
                    GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
                }
                GL11.glScalef(1.0F, 1.0F, 1.0F);
            }
        }
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.renderEngine.bindTexture(textureRL);
        this.model.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.11F, 0.0F);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}