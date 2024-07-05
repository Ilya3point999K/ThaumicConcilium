package com.ilya3point999k.thaumicconcilium.client.render.item;

import com.ilya3point999k.thaumicconcilium.client.render.projectile.ShardPowderEntityRenderer;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.ModelCrystal;

import java.awt.*;

public class ShardMillRenderer implements IItemRenderer {
    IModelCustom body;
    IModelCustom cap;
    IModelCustom handle;
    private ModelCrystal crystal = new ModelCrystal();

    private static final ResourceLocation textureGRL = new ResourceLocation(ThaumicConcilium.MODID + ":textures/models/greatwood_shard_mill.png");
    private static final ResourceLocation textureSRL = new ResourceLocation(ThaumicConcilium.MODID + ":textures/models/silverwood_shard_mill.png");

    private static final ResourceLocation bodyRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/body_shard_mill.obj");

    private static final ResourceLocation capRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/cap_shard_mill.obj");
    private static final ResourceLocation handleRL = new ResourceLocation(ThaumicConcilium.MODID + ":models/handle_shard_mill.obj");


    public ShardMillRenderer() {
        this.body = AdvancedModelLoader.loadModel(bodyRL);
        this.cap = AdvancedModelLoader.loadModel(capRL);
        this.handle = AdvancedModelLoader.loadModel(handleRL);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();

        final EntityPlayer player = mc.thePlayer;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glPushMatrix();

        if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(0F, 0.2F, 0F);
            GL11.glScalef(0.7F, 0.7F, 0.7F);
            mc.renderEngine.bindTexture(item.getItemDamage() == 0 ? textureGRL : textureSRL);
            body.renderAll();
            cap.renderAll();
            handle.renderAll();
        }
        else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {

            Render var24 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
            RenderPlayer var26 = (RenderPlayer)var24;
            NBTTagCompound tag = item.getTagCompound();
            boolean open = false;
            boolean using = false;
            if(tag != null){
                open = tag.getBoolean("IsOpen");
                if (open){
                    using = player.isUsingItem();
                    if (using) {
                        GL11.glTranslatef(1.0F, -0.2F, 0.0F);
                        GL11.glRotatef(18.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glRotatef(12.0F, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(8.0F, 1.0F, 0.0F, 0.0F);

                        int ticks = -player.getItemInUseCount();
                        GL11.glPushMatrix();
                        mc.renderEngine.bindTexture(mc.thePlayer.getLocationSkin());
                        GL11.glTranslatef(-1.5F, 1.3F, -0.1F);
                        GL11.glRotatef(100.0F + (ticks > -15 ? ticks * 1.1F : 0.0F), 0.0F, 1.0F ,0.0F);
                        GL11.glRotatef(45.0F + (1 - (ticks/-20.0F)) * 100.0F, 1.0F, 0.0F ,0.0F);
                        GL11.glScalef(1.0F, 1.0F, 1.0F);
                        var26.renderFirstPersonArm(mc.thePlayer);
                        GL11.glPopMatrix();

                        GL11.glPushMatrix();
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(770, 771);
                        UtilsFX.bindTexture("textures/models/crystal.png");
                        GL11.glTranslatef(-0.5F, 1.5F - ( 1.0F - (ticks)/-20.0F) * (ticks > -15 ? 2.0F : 1.5F), 0.3F);
                        GL11.glScalef((0.15F + 0.075F) * 0.8F, (0.5F + 0.1F) * 0.8F, (0.15F + 0.05F) * 0.8F);
                        Color ac = new Color(ShardPowderEntityRenderer.colors.get(tag.getInteger("Type")));
                        GL11.glColor4f((float) ac.getRed() / 255.0F, (float) ac.getGreen() / 255.0F, (float) ac.getBlue() / 255.0F, 0.8F);
                        crystal.render();
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                    }

                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -1.5F, 0.0F);
                } else {
                    using = player.isUsingItem();
                    if (using) {
                        GL11.glTranslatef(1.0F, -0.2F, 0.0F);
                        GL11.glRotatef(18.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glRotatef(12.0F, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(8.0F, 1.0F, 0.0F, 0.0F);
                    }

                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(1.0F, -2.2F, -0.5F);
                    if (using){
                        float ticks = (float)Minecraft.getMinecraft().renderViewEntity.ticksExisted;
                        GL11.glPushMatrix();
                        GL11.glTranslatef(0.0F, 1.8F, 0.0F);
                        GL11.glRotatef(-((ticks * 9.0F) % 360.0F) , 1.0F, 0.0F, 0.0F);
                        GL11.glTranslatef(0.0F, -1.8F, 0.0F);
                        //GL11.glScalef(2.0F, 1.0F, 1.0F);
                        mc.renderEngine.bindTexture(item.getItemDamage() == 0 ? textureGRL : textureSRL);
                        handle.renderAll();
                        GL11.glPopMatrix();

                        GL11.glPushMatrix();
                        mc.renderEngine.bindTexture(mc.thePlayer.getLocationSkin());
                        GL11.glTranslatef(-1.7F, 2.5F, 0.0F);
                        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                        //GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glRotatef(-((ticks * 9.0F) % 360.0F) , 1.0F, 0.0F, 0.0F);
                        GL11.glScalef(2.0F, 2.0F, 2.0F);
                        var26.renderFirstPersonArm(mc.thePlayer);
                        GL11.glPopMatrix();
                    }
                }
            } else {
                open = true;
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(0.0F, -1.5F, 0.0F);
            }
            mc.renderEngine.bindTexture(item.getItemDamage() == 0 ? textureGRL : textureSRL);
            body.renderAll();
            if (!open){
                cap.renderAll();
            }
            if (!using && !open) {
                handle.renderAll();
                GL11.glPushMatrix();
                mc.renderEngine.bindTexture(mc.thePlayer.getLocationSkin());
                GL11.glTranslatef(-1.7F, 3.0F, 0.0F);
                GL11.glRotatef(190.0F, 1.0F, 0.0F, 0.0F);
                //GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
                GL11.glScalef(2.0F, 3.0F, 2.0F);
                var26.renderFirstPersonArm(mc.thePlayer);
                GL11.glPopMatrix();
            }
        }
        else if (type == ItemRenderType.INVENTORY) {
            GL11.glScalef(0.4F, 0.4F, 0.4F);
            GL11.glRotatef(-65, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0F, 0.0F);
            mc.renderEngine.bindTexture(item.getItemDamage() == 0 ? textureGRL : textureSRL);
            body.renderAll();
            cap.renderAll();
            handle.renderAll();
        }
        else {
            GL11.glRotatef(50.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.5F, -1.0F, -1.0F);
            if (player.isUsingItem()){
                GL11.glTranslatef(0.0F, -1.5F, 0.5F);
            }
            mc.renderEngine.bindTexture(item.getItemDamage() == 0 ? textureGRL : textureSRL);
            body.renderAll();
            cap.renderAll();
            handle.renderAll();
        }



        GL11.glPopMatrix();

        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();

    }
}