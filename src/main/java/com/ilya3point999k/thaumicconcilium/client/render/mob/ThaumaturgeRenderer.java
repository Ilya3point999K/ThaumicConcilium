package com.ilya3point999k.thaumicconcilium.client.render.mob;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class ThaumaturgeRenderer extends RenderBiped {
	
	private final ResourceLocation texture;
	
	public ThaumaturgeRenderer(ModelBiped model, ResourceLocation texture, float shadowSize) {
		super(model, shadowSize);
		this.texture = texture;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
		super.doRender((EntityLiving)entity, x, y, z, yaw, pitch);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return texture;
	}
}
