package com.ilya3point999k.thaumicconcilium.client.render.item;

import com.ilya3point999k.thaumicconcilium.client.events.ClientEvents;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderCallback;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderHelper;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.tiles.RedPoweredMindTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.Config;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

public class AstralMonitorRenderer implements IItemRenderer {
    private IModelCustom model;
    private IModelCustom glass;
    private static final ResourceLocation SCANNER = new ResourceLocation("thaumcraft", "textures/models/scanner.obj");
    private static final ResourceLocation ATSRAL_FRAME = new ResourceLocation(ThaumicConcilium.MODID + ":textures/models/atsral_frame.png");
    private static final ResourceLocation FLUC_OFF = new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/fluctuation_off.png");
    private static final ResourceLocation FLUC_ON = new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/fluctuation_on.png");
    public static final ResourceLocation SCREEN = new ResourceLocation("textures/entity/end_portal.png");
    private static final ResourceLocation GLASS = new ResourceLocation(ThaumicConcilium.MODID + ":models/screen.obj");

    private final ShaderCallback shaderCallback;
    private final ShaderCallback emptyCallback;
    //private int index, next;
    private FloatBuffer indexes;
    private FloatBuffer nexts;
    private int index, next;
    private float progress;

    public AstralMonitorRenderer() {
        Random random = new Random(System.currentTimeMillis());

        indexes = BufferUtils.createFloatBuffer(6);
        nexts = BufferUtils.createFloatBuffer(6);
        indexes.clear();
        nexts.clear();

        for (int i = 0; i < 6; i++) {
            index = random.nextInt(6);
            next = random.nextInt(6);
            while (next == index) {
                next = random.nextInt(6);
            }
            indexes.put(index);
            nexts.put(next);
        }

        progress = 0;
        int indexLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.atsralFrameShader, "indexes");
        ARBShaderObjects.glUniform1ARB(indexLoc, indexes);
        int nextLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.atsralFrameShader, "nexts");
        ARBShaderObjects.glUniform1ARB(nextLoc, nexts);
        int progLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.atsralFrameShader, "progress");
        ARBShaderObjects.glUniform1fARB(progLoc, progress);

        this.model = AdvancedModelLoader.loadModel(SCANNER);
        this.glass = AdvancedModelLoader.loadModel(GLASS);
        shaderCallback = new ShaderCallback() {
            @Override
            public void call(int shader) {
                Minecraft mc = Minecraft.getMinecraft();
                int x = ARBShaderObjects.glGetUniformLocationARB(shader, "yaw");
                ARBShaderObjects.glUniform1fARB(x, (float) (mc.thePlayer.rotationYaw * 2.0f * 3.141592653589793 / 360.0));
                int z = ARBShaderObjects.glGetUniformLocationARB(shader, "pitch");
                ARBShaderObjects.glUniform1fARB(z, -(float) (mc.thePlayer.rotationPitch * 2.0f * 3.141592653589793 / 360.0));
            }
        };
        emptyCallback = new ShaderCallback() {
            @Override
            public void call(int shader) {
            }
        };

    }

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }


    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();
        int rve_id = 0;
        int player_id = 0;
        if (type == ItemRenderType.EQUIPPED) {
            rve_id = mc.renderViewEntity.getEntityId();
            player_id = ((EntityLivingBase) data[1]).getEntityId();
        }
        EntityPlayer playermp = mc.thePlayer;
        float par1 = UtilsFX.getTimer(mc).renderPartialTicks;
        float var7 = 0.8F;
        EntityPlayerSP playersp = (EntityPlayerSP) playermp;
        GL11.glPushMatrix();
        int sw;
        int posY;
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON && player_id == rve_id && mc.gameSettings.thirdPersonView == 0) {
            GL11.glTranslatef(1.0F, 0.75F, -1.0F);
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

            for (sw = 0; sw < 2; ++sw) {
                posY = sw * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float) posY);
                GL11.glRotatef((float) (-45 * posY), 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef((float) (-65 * posY), 0.0F, 1.0F, 0.0F);
                Render var24 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
                RenderPlayer var26 = (RenderPlayer) var24;
                float var13 = 1.0F;
                GL11.glScalef(var13, var13, var13);
                var26.renderFirstPersonArm(mc.thePlayer);
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.4F, -0.4F, 0.0F);
            GL11.glEnable(32826);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
        } else {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            if (type == ItemRenderType.EQUIPPED) {
                GL11.glTranslatef(1.6F, 0.3F, 2.0F);
                GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
                GL11.glRotatef(30.0F, 0.0F, 0.0F, -1.0F);
            } else if (type == ItemRenderType.INVENTORY) {
                GL11.glRotatef(60.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(30.0F, 0.0F, 0.0F, -1.0F);
                GL11.glRotatef(248.0F, 0.0F, -1.0F, 0.0F);
            }
        }

        if(Config.shaders) {
            ShaderHelper.useShader(ShaderHelper.atsralFrameShader, emptyCallback);
            ++progress;
            if (progress == 60) {
                nexts.clear();
                indexes.clear();
                for (int i = 0; i < 6; i++) {
                    indexes.put(nexts.get());
                }
                indexes.clear();
                nexts.clear();
                for (int i = 0; i < 6; i++) {
                    index = next;
                    next = playermp.worldObj.rand.nextInt(6);
                    while (next == index) {
                        next = playermp.worldObj.rand.nextInt(6);
                    }
                    nexts.put(next);
                }
                indexes.clear();
                nexts.clear();
                int indexLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.atsralFrameShader, "indexes");
                ARBShaderObjects.glUniform1ARB(indexLoc, indexes);
                int nextLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.atsralFrameShader, "nexts");
                ARBShaderObjects.glUniform1ARB(nextLoc, nexts);
                progress = 0;
            }
            int progLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.atsralFrameShader, "progress");
            ARBShaderObjects.glUniform1fARB(progLoc, progress);

            mc.renderEngine.bindTexture(ATSRAL_FRAME);
            this.model.renderAll();
            ShaderHelper.releaseShader();
        } else {
            mc.renderEngine.bindTexture(ATSRAL_FRAME);
            this.model.renderAll();
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GL11.glTranslatef(0.0F, 0.11F, 0.0F);
        //GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        //GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        ShaderHelper.useShader(ShaderHelper.endScreenShader, shaderCallback);
        mc.renderEngine.bindTexture(SCREEN);
        this.glass.renderAll();
        //UtilsFX.renderQuadCenteredFromTexture(SCREEN, 2.5F, 1.0F, 1.0F, 1.0F, (int) (190.0F + MathHelper.sin((float) (playermp.ticksExisted - playermp.worldObj.rand.nextInt(2))) * 10.0F + 10.0F), 771, 1.0F);
        ShaderHelper.releaseShader();
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, 0.0F, -0.5F);
        if (playermp instanceof EntityPlayer && type == ItemRenderType.EQUIPPED_FIRST_PERSON && player_id == rve_id && mc.gameSettings.thirdPersonView == 0) {
            RenderHelper.disableStandardItemLighting();
            int j = (int) (190.0F + MathHelper.sin((float) (playermp.ticksExisted - playermp.worldObj.rand.nextInt(2))) * 10.0F + 10.0F);
            int k = j % 65536;
            int l = j / 65536;

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
            NBTTagCompound tag = item.getTagCompound();
            if (tag != null) {
                NBTTagCompound stacktag = tag.getCompoundTag("wand");
                if (stacktag != null) {
                    NBTTagCompound wandtag = stacktag.getCompoundTag("tag");
                    if (wandtag != null) {
                        String xyl = wandtag.getString("Xylography");
                        if (xyl != null && !xyl.isEmpty()) {
                            EntityPlayer player = playermp.worldObj.getPlayerEntityByName(xyl);
                            if (player != null) {
                                int[] vals = tag.getIntArray("vals");
                                if (vals != null && vals.length != 0) {
                                    sw = 0;
                                    mc.renderEngine.bindTexture(ClientEvents.constellations);
                                    GL11.glPushMatrix();
                                    GL11.glScalef(0.02F, 0.02F, 0.02F);
                                    j = (int) (190.0F + MathHelper.sin((float) (sw + playermp.ticksExisted - playermp.worldObj.rand.nextInt(2))) * 10.0F + 10.0F);
                                    k = j % 65536;
                                    l = j / 65536;
                                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);

                                    //ClientEvents.renderConstellations(player, -0.1, -32, -16, -10, 5, 0, -24, 16, 0);

                                    UtilsFX.drawTexturedQuad(-32, -16, 16 * 3, 16 * vals[0], 16, 16, -0.1);
                                    if (vals[1] != -1) {
                                        UtilsFX.drawTexturedQuad(-10, 5, 16 * 2, 16 * vals[1], 16, 16, -0.1);
                                    }
                                    if (vals[2] != -1) {
                                        UtilsFX.drawTexturedQuad(0, -24, 16, 16 * vals[2], 16, 16, -0.1);
                                    }
                                    UtilsFX.drawTexturedQuad(16, 0, 0, 16 * vals[3], 16, 16, -0.1);

                                    //drawTexturedQuadFull(-baseX + sw * 16, -8 + posY * 16, 0.01, progress / 60.0);
                                    GL11.glPopMatrix();
                                }
                            }
                        } else if (wandtag.hasKey("lookX")) {
                            int wx = wandtag.getInteger("lookX");
                            int wy = wandtag.getInteger("lookY");
                            int wz = wandtag.getInteger("lookZ");
                            int dim = wandtag.getInteger("lookDim");
                            if (playermp.dimension == dim) {
                                TileEntity tile = playermp.worldObj.getTileEntity(wx, wy, wz);
                                if (tile != null && tile instanceof RedPoweredMindTile) {
                                    sw = 0;
                                    posY = 0;
                                    int aa = 4;
                                    int baseX = aa * 8;
                                    mc.renderEngine.bindTexture(ClientEvents.constellations);

                                    for (int iter = 3; iter >= 0; iter--) {
                                        GL11.glPushMatrix();
                                        GL11.glScalef(0.02F, 0.02F, 0.02F);
                                        j = (int) (190.0F + MathHelper.sin((float) (sw + playermp.ticksExisted - playermp.worldObj.rand.nextInt(2))) * 10.0F + 10.0F);
                                        k = j % 65536;
                                        l = j / 65536;
                                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
                                        int power = ((RedPoweredMindTile) tile).power;

                                        if (((power & (1 << iter)) >> iter) == 1) {
                                            UtilsFX.drawTexturedQuad(-baseX + sw * 16, -8 + posY * 16, 16 * 4, 16, 16, 16, 0.01);
                                        } else {
                                            UtilsFX.drawTexturedQuad(-baseX + sw * 16, -8 + posY * 16, 16 * 4, 0, 16, 16, 0.01);
                                        }

                                        ++sw;
                                        if (sw >= 5 - posY) {
                                            sw = 0;
                                            ++posY;
                                            aa -= 5 - posY;
                                            baseX = Math.min(5 - posY, aa) * 8;
                                        }
                                        GL11.glPopMatrix();
                                    }
                                }
                            }
                        }
                    }
                }

            }
            RenderHelper.enableGUIStandardItemLighting();

        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public static void drawTexturedQuadFull(int par1, int par2, double zLevel, double offset) {
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double) (par1 + 0), (double) (par2 + 16), zLevel, 0.0 + offset, 1.0);
        var9.addVertexWithUV((double) (par1 + 16), (double) (par2 + 16), zLevel, 1.0 + offset, 1.0);
        var9.addVertexWithUV((double) (par1 + 16), (double) (par2 + 0), zLevel, 1.0 + offset, 0.0);
        var9.addVertexWithUV((double) (par1 + 0), (double) (par2 + 0), zLevel, 0.0 + offset, 0.0);
        var9.draw();
    }

}