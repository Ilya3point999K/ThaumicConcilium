package com.ilya3point999k.thaumicconcilium.client.render;

import com.ilya3point999k.thaumicconcilium.common.entities.WrathEffectEntity;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityLavaFX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import thaumcraft.codechicken.lib.vec.Vector3;

public class WrathEffectRender extends Render {
    public static final int[][] iraImage = {{0, 5}, {1, 4}, {2, 3}, {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {8, 3}, {9, 3}, {10, 3}, {11, 3}, {12, 4}, {13, 4}, {14, 1}, {14, 5},
            {14, 10}, {14, 12}, {15, 2}, {15, 12}, {16, 3}, {16, 13}, {17, 1}, {17, 4}, {17, 9}, {17, 14}, {18, 2}, {18, 5}, {18, 7}, {18, 9}, {18, 13}, {18, 16}, {19, 3}, {19, 11},
            {19, 14}, {20, 3}, {20, 12}, {20, 14}, {21, 3}, {21, 12}, {21, 14}, {22, 3}, {22, 12}, {22, 14}, {23, 3}, {25, 2}, {26, 1}, {24, 3}, {23, 2}, {22, 1}};

    public WrathEffectRender() {
        this.shadowSize = 0.0F;
    }

    public void renderEntityAt(WrathEffectEntity entity, double xx, double y, double zz, float fq, float pticks) {
        double x = entity.posX - Math.cos(3.141 / 180 * (entity.rotationYaw)) * (iraImage[entity.age%iraImage.length][1] * 0.3);
        double z = entity.posZ - Math.sin(3.141 / 180 * (entity.rotationYaw)) * (iraImage[entity.age%iraImage.length][1] * 0.3);
        double xn = entity.posX - Math.cos(3.141 / 180 * (entity.rotationYaw)) * (iraImage[entity.age%iraImage.length][1] * -0.3);
        double zn = entity.posZ - Math.sin(3.141 / 180 * (entity.rotationYaw)) * (iraImage[entity.age%iraImage.length][1] * -0.3);
        Vector3 end = new Vector3(x, entity.posY + (iraImage[entity.age%iraImage.length][0] * 0.2), z);
        Vector3 endn = new Vector3(xn, entity.posY + (iraImage[entity.age%iraImage.length][0] * 0.2), zn);
        double x1 = entity.posX - Math.cos(3.141 / 180 * (entity.rotationYaw)) * (iraImage[(entity.age + 1)%iraImage.length][1] * 0.3);
        double z1 = entity.posZ - Math.sin(3.141 / 180 * (entity.rotationYaw)) * (iraImage[(entity.age + 1)%iraImage.length][1] * 0.3);
        double xn1 = entity.posX - Math.cos(3.141 / 180 * (entity.rotationYaw)) * (iraImage[(entity.age + 1)%iraImage.length][1] * -0.3);
        double zn1 = entity.posZ - Math.sin(3.141 / 180 * (entity.rotationYaw)) * (iraImage[(entity.age + 1)%iraImage.length][1] * -0.3);
        Vector3 end1 = new Vector3(x1, entity.posY + (iraImage[(entity.age + 1)%iraImage.length][0] * 0.2), z1);
        Vector3 endn1 = new Vector3(xn1, entity.posY + (iraImage[(entity.age + 1)%iraImage.length][0] * 0.2), zn1);

        if(entity.age > 20 && entity.burnout){
            final EntityLavaFX lavaFX = new EntityLavaFX(entity.worldObj, end.x, end.y, end.z);
            final EntityLavaFX lavaFXn = new EntityLavaFX(entity.worldObj, endn.x, endn.y, endn.z);
            final EntityLavaFX lavaFX1 = new EntityLavaFX(entity.worldObj, end1.x, end1.y, end1.z);
            final EntityLavaFX lavaFXn1 = new EntityLavaFX(entity.worldObj, endn1.x, endn1.y, endn1.z);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(lavaFX);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(lavaFXn);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(lavaFX1);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(lavaFXn1);
        }
        final EntityFlameFX fx = new EntityFlameFX(entity.worldObj, end.x, end.y, end.z, 0.0, 0.0, 0.0);
        fx.noClip = true;
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
        final EntityFlameFX fxn = new EntityFlameFX(entity.worldObj, endn.x, endn.y, endn.z, 0.0, 0.0, 0.0);
        fxn.noClip = true;
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fxn);
        final EntityFlameFX fx1 = new EntityFlameFX(entity.worldObj, end1.x, end1.y, end1.z, 0.0, 0.0, 0.0);
        fx1.noClip = true;
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx1);
        final EntityFlameFX fxn1 = new EntityFlameFX(entity.worldObj, endn1.x, endn1.y, endn1.z, 0.0, 0.0, 0.0);
        fxn1.noClip = true;
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fxn1);

    }

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderEntityAt((WrathEffectEntity) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}