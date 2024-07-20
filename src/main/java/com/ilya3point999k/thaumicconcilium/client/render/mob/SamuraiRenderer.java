package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.model.FakeFortressArmorModel;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Samurai;
import fox.spiteful.forbidden.compat.Compat;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.wands.WandCap;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;

public class SamuraiRenderer extends RenderBiped {

    private final ResourceLocation texture;

    private final ResourceLocation voidFortress = new ResourceLocation("taintedmagic", "textures/models/ModelVoidFortressArmor.png");
    private final ResourceLocation shadowFortress = new ResourceLocation("taintedmagic", "textures/models/ModelShadowFortressArmor.png");

    private final FakeFortressArmorModel armor = new FakeFortressArmorModel(1.0F);
    private final ResourceLocation thaumTexture = new ResourceLocation("taintedmagic", "textures/models/ModelKatanaThaumium.png");
    private final ResourceLocation voidTexture = new ResourceLocation("taintedmagic", "textures/models/ModelKatanaVoidmetal.png");
    private final ResourceLocation shadowTexture = new ResourceLocation("taintedmagic", "textures/models/ModelKatanaShadowmetal.png");


    private final ModelSaya saya = new ModelSaya();
    private final ModelKatana katana = new ModelKatana();
    private final ItemStack swordItem;
    private final ItemStack wand;

    {
        try {
            swordItem = new ItemStack(Compat.getItem("TaintedMagic", "ItemKatana"), 1, 0);
            wand = new ItemStack(ConfigItems.itemWandCasting);
            ((ItemWandCasting) wand.getItem()).setCap(wand, WandCap.caps.get("crimsoncloth"));
            ((ItemWandCasting) wand.getItem()).setRod(wand, ForbiddenItems.WAND_ROD_PROFANE);
            ItemStack focus = new ItemStack(ConfigItems.itemFocusShock);
            ((ItemWandCasting) wand.getItem()).setFocus(wand, focus);
        } catch (Compat.ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public SamuraiRenderer(ModelBiped model, ResourceLocation texture, float shadowSize) {
        super(model, shadowSize);
        this.texture = texture;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        super.doRender((EntityLiving) entity, x, y, z, yaw, pitch);
    }

    @Override
    protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        super.renderModel(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        byte type = ((Samurai) p_77036_1_).getType();
        switch (type) {
            case 0:
                UtilsFX.bindTexture("textures/models/fortress_armor.png");
                break;
            case 1:
                Minecraft.getMinecraft().renderEngine.bindTexture(voidFortress);
                break;
            case 2:
                Minecraft.getMinecraft().renderEngine.bindTexture(shadowFortress);
                break;
        }
        armor.isRiding = mainModel.isRiding;
        armor.isSneak = modelBipedMain.isSneak;
        armor.onGround = mainModel.onGround;
        armor.aimedBow = modelBipedMain.aimedBow;
        if (!p_77036_1_.isInvisible()) {
            armor.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        } else if (!p_77036_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            armor.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        } else {
            armor.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, p_77036_1_);
        }

        GL11.glPushMatrix();

        final int light = p_77036_1_.getBrightnessForRender(0);
        final int lightmapX = light % 65536;
        final int lightmapY = light / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glPushMatrix();

        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glRotatef(55, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);

        GL11.glTranslatef(-0.6F, 2.25F, 1.25F);
        switch (type) {
            case 0:
                Minecraft.getMinecraft().renderEngine.bindTexture(thaumTexture);
                break;
            case 1:
                Minecraft.getMinecraft().renderEngine.bindTexture(voidTexture);
                break;
            case 2:
                Minecraft.getMinecraft().renderEngine.bindTexture(shadowTexture);
                break;
        }
        saya.render(0.0625F);

        GL11.glPopMatrix();

        //System.out.println("AGG " + ((Samurai)p_77036_1_).getAnger());
        if (((Samurai) p_77036_1_).getAnger() == 0) {
            GL11.glPushMatrix();

            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glRotatef(55, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);

            GL11.glTranslatef(-0.6F, 2.25F, 1.25F);

            //Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack));
            katana.render(0.0625F);

            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();

    }

    protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        boolean low = p_77029_1_.getHealth() < p_77029_1_.getMaxHealth() / 3;
        ItemStack itemstack = null;
        if (low) {
            itemstack = wand;
        } else {
            itemstack = swordItem;
        }
        float f1;

        if (itemstack != null && itemstack.getItem() != null) {
            GL11.glPushMatrix();

            if (this.mainModel.isChild) {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(f1, f1, f1);
            }

            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(0.0F, 0.35F, -0.32F);


            f1 = 0.625F;

            this.func_82422_c();
            GL11.glScalef(f1, -f1, f1);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);


            float f2;
            int i;
            float f5;

            i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
            float f4 = (float) (i >> 16 & 255) / 255.0F;
            f5 = (float) (i >> 8 & 255) / 255.0F;
            f2 = (float) (i & 255) / 255.0F;
            GL11.glColor4f(f4, f5, f2, 1.0F);
            GL11.glPushMatrix();

            if (low) {
                GL11.glRotatef(-90F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslated(0.1, -0.5, -0.1);
                this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0);
            } else if (((Samurai) p_77029_1_).getAnger() != 0) {
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
                this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0);
            }
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return texture;
    }

    class ModelSaya extends ModelBase {
        public ModelRenderer saya;

        public ModelSaya() {
            textureWidth = 32;
            textureHeight = 64;

            saya = new ModelRenderer(this, 10, 0);
            saya.setRotationPoint(0.0F, -40.0F, 0.0F);
            saya.addBox(-1.0F, 0.5F, -2.0F, 2, 48, 4, 0.0F);
        }

        public void render(final float size) {
            saya.render(size);
        }

        /**
         * This is a helper function from Tabula to set the rotation of model parts
         */
        public void setRotation(final ModelRenderer m, final float x, final float y, final float z) {
            m.rotateAngleX = x;
            m.rotateAngleY = y;
            m.rotateAngleZ = z;
        }
    }

    class ModelKatana extends ModelBase {
        public ModelRenderer blade;
        public ModelRenderer grip1;
        public ModelRenderer grip2;

        public ModelKatana() {
            textureWidth = 32;
            textureHeight = 64;

            grip2 = new ModelRenderer(this, 22, 0);
            grip2.setRotationPoint(0.0F, 0.0F, 0.0F);
            grip2.addBox(-1.0F, -12.0F, -1.5F, 2, 12, 3, 0.0F);

            blade = new ModelRenderer(this, 0, 0);
            blade.setRotationPoint(0.0F, -40.0F, 0.0F);
            blade.addBox(-0.5F, 0.0F, -2.0F, 1, 48, 4, -0.75F);

            grip1 = new ModelRenderer(this, 0, 52);
            grip1.setRotationPoint(0.0F, -40.0F, 0.0F);
            grip1.addBox(-2.5F, 0.0F, -3.5F, 5, 1, 7, 0.0F);
            grip1.addChild(grip2);
        }

        public void render(final float size) {
            blade.render(size);
            grip1.render(size);
        }
    }

}