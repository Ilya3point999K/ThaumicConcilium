package com.ilya3point999k.thaumicconcilium.client.render.model;

import com.ilya3point999k.thaumicconcilium.common.entities.GolemBydlo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import thaumcraft.common.entities.golems.EntityGolemBase;

public class GolemBydloModel extends ModelBase {
    public ModelRenderer golemHead;
    public ModelRenderer golemBody;
    public ModelRenderer golemRightArm;
    public ModelRenderer golemLeftArm;
    public ModelRenderer golemRightLeg;
    public ModelRenderer golemLeftLeg;
    public int pass = 0;

    public GolemBydloModel(boolean p) {
        float f1 = 0.0F;
        float f2 = p ? -12.0F : 30.0F;
        short var3 = 128;
        short var4 = 128;
        this.golemHead = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.golemHead.setRotationPoint(0.0F, 0.0F + f2, -2.0F);
        this.golemHead.setTextureOffset(0, 0).addBox(-4.0F, -11.0F, -5.5F, 8, 9, 8, f1);
        this.golemBody = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.golemBody.setRotationPoint(0.0F, 0.0F + f2, 0.0F);
        this.golemBody.setTextureOffset(0, 40).addBox(-8.0F, -2.0F, -6.0F, 16, 12, 11, f1);
        this.golemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, f1 + 0.5F);
        this.golemRightArm = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.golemRightArm.setRotationPoint(0.0F, 0.0F + f2, 0.0F);
        this.golemRightArm.setTextureOffset(60, 21).addBox(-12.0F, -2.5F, -3.0F, 4, 25, 6, f1);
        this.golemLeftArm = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.golemLeftArm.mirror = true;
        this.golemLeftArm.setRotationPoint(0.0F, 0.0F + f2, 0.0F);
        this.golemLeftArm.setTextureOffset(60, 21).addBox(8.0F, -2.5F, -3.0F, 4, 25, 6, f1);
        this.golemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(var3, var4);
        this.golemRightLeg.setRotationPoint(-4.0F, 18.0F + f2, 0.0F);
        this.golemRightLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f1);
        this.golemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(var3, var4);
        this.golemLeftLeg.mirror = true;
        this.golemLeftLeg.setTextureOffset(37, 0).setRotationPoint(5.0F, 18.0F + f2, 0.0F);
        this.golemLeftLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f1);
    }

    public void render(Entity e, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(e, par2, par3, par4, par5, par6, par7);
        GL11.glPushMatrix();
        if (this.pass == 2) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glAlphaFunc(516, 0.003921569F);
        }

        GL11.glScaled(1.3, 1.3, 1.4);
        this.golemHead.render(par7);
        this.golemBody.render(par7);
        this.golemRightLeg.render(par7);
        this.golemLeftLeg.render(par7);
        this.golemRightArm.render(par7);
        this.golemLeftArm.render(par7);
        GL11.glScaled(1.0, 1.0, 1.0);
        if (this.pass == 2) {
            GL11.glAlphaFunc(516, 0.1F);
            GL11.glDisable(3042);
        }

        GL11.glPopMatrix();
    }

    public void setRotationAngles(Entity en, float par1, float par2, float par3, float par4, float par5, float par6) {
            if(en.riddenByEntity == null){
                this.golemHead.rotateAngleX = 57.295776F;
            } else this.golemHead.rotateAngleX = par5 / 57.295776F;
            this.golemHead.rotateAngleY = par4 / 57.295776F;
            this.golemRightLeg.rotateAngleX = -1.5F * this.func_78172_a(par1, 13.0F) * par2;
            this.golemLeftLeg.rotateAngleX = 1.5F * this.func_78172_a(par1, 13.0F) * par2;
            this.golemRightLeg.rotateAngleY = 0.0F;
            this.golemLeftLeg.rotateAngleY = 0.0F;
            this.golemLeftArm.rotateAngleZ = 0.0F;
            this.golemRightArm.rotateAngleZ = 0.0F;

    }

    public void setLivingAnimations(EntityLivingBase par1EntityLiving, float par2, float par3, float par4) {
        GolemBydlo var5 = null;
        int var6 = 0;
        int leftarm = 0;
        int rightarm = 0;
        if (par1EntityLiving instanceof GolemBydlo) {
            var5 = (GolemBydlo)par1EntityLiving;
            var6 = var5.getActionTimer();
            leftarm = var5.leftArm;
            rightarm = var5.rightArm;
        }

        if (var6 > 0) {
            this.golemRightArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a((float)var6 - par4, 5.0F);
            this.golemLeftArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a((float)var6 - par4, 5.0F);
        } else if (leftarm <= 0 && rightarm <= 0) {
                this.golemRightArm.rotateAngleX = (-0.2F + 1.5F * this.func_78172_a(par2, 13.0F)) * par3;
                this.golemLeftArm.rotateAngleX = (-0.2F - 1.5F * this.func_78172_a(par2, 13.0F)) * par3;
        } else {
            if (leftarm > 0) {
                this.golemLeftArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a((float)leftarm - par4, 20.0F);
            }

            if (rightarm > 0) {
                this.golemRightArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a((float)rightarm - par4, 20.0F);
            }
        }

    }

    private float func_78172_a(float par1, float par2) {
        return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
    }
}