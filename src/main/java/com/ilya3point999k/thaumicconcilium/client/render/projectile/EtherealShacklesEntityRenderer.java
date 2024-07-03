package com.ilya3point999k.thaumicconcilium.client.render.projectile;

import com.ilya3point999k.thaumicconcilium.client.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EtherealShacklesEntityRenderer extends Render {

    public EtherealShacklesEntityRenderer(){
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 0.5F, (float)z);
        GL11.glRotatef(entity.ticksExisted * 10.0F, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(ClientEvents.shacklesTexture);
        ClientEvents.shacklesModel.renderAll();
        Minecraft.getMinecraft().renderEngine.bindTexture(ClientEvents.chainTexture);
        ClientEvents.chainModel.renderAll();
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ClientEvents.shacklesTexture;
    }

}
