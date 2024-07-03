package com.ilya3point999k.thaumicconcilium.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class RedPoweredMindModel extends ModelBase {
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;

    public RedPoweredMindModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Shape1 = new ModelRenderer(this, 0, 0);
        this.Shape1.addBox(0.0F, 0.0F, 0.0F, 12, 10, 16);
        this.Shape1.setRotationPoint(-6.0F, 8.0F, -8.0F);
        this.Shape1.setTextureSize(128, 64);
        this.Shape1.mirror = true;
        this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
        this.Shape2 = new ModelRenderer(this, 64, 0);
        this.Shape2.addBox(0.0F, 0.0F, 0.0F, 8, 3, 7);
        this.Shape2.setRotationPoint(-4.0F, 18.0F, 0.0F);
        this.Shape2.setTextureSize(128, 64);
        this.Shape2.mirror = true;
        this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
        this.Shape3 = new ModelRenderer(this, 0, 32);
        this.Shape3.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
        this.Shape3.setRotationPoint(-1.0F, 18.0F, -2.0F);
        this.Shape3.setTextureSize(128, 64);
        this.Shape3.mirror = true;
        this.setRotation(this.Shape3, 0.4089647F, 0.0F, 0.0F);
        this.Shape4 = new ModelRenderer(this, 0, 32);
        this.Shape4.addBox(0.0F, 0.0F, 0.0F, 2, 16, 2);
        this.Shape4.setRotationPoint(-1.0F, 18.0F, -2.0F);
        this.Shape4.offsetY = 1.0F;
        this.Shape4.offsetZ = 0.12F;
        this.Shape4.setTextureSize(128, 64);
        this.Shape4.mirror = true;
        this.setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);

    }

    public void render(boolean cord) {
        this.Shape1.render(0.0625F);
        this.Shape2.render(0.0625F);
        this.Shape3.render(0.0625F);
        if (cord){
            this.Shape4.render(0.0625F);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}