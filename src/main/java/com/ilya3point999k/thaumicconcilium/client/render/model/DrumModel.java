package com.ilya3point999k.thaumicconcilium.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class DrumModel extends ModelBase {
    ModelRenderer cube;

    public DrumModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.cube = new ModelRenderer(this, 0, 0);
        this.cube.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        this.cube.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    public void render(){
        GL11.glPushMatrix();
        this.cube.render(0.0624F);
        GL11.glPopMatrix();
    }

}
