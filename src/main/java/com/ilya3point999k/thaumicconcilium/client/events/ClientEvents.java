package com.ilya3point999k.thaumicconcilium.client.events;

import com.ilya3point999k.thaumicconcilium.client.render.ShaderHelper;
import com.ilya3point999k.thaumicconcilium.client.render.projectile.ShardPowderEntityRenderer;
import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.items.ShardMill;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketChangeActiveShard;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketChangeEtherealRange;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.client.model.kami.ModelWings;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class ClientEvents {
    public int currentSlot = -1;
    static float radialHudScale = 0.0F;
    public static int astralHoleBlinkTimer = 60;
    boolean blinkFlag = false;
    DecimalFormat myFormatter2 = new DecimalFormat("#######.#");
    TreeMap<Integer, Integer> foci = new TreeMap<>();
    HashMap<Integer, Boolean> fociHover = new HashMap<>();
    HashMap<Integer, Float> fociScale = new HashMap<>();
    boolean lastState = false;
    long lastTime = 0L;

    public static boolean isEnslaved = false;

    public static int[] partyHealth = new int[4];
    public static int[] partyBlood = new int[4];
    public static int[] partyMana = new int[4];
    public static int[] partyRunes = new int[4];
    public static int[] brains = new int[4];

    final ResourceLocation HUD = new ResourceLocation("thaumcraft", "textures/gui/hud.png");
    final ResourceLocation astralHole = new ResourceLocation(ThaumicConcilium.MODID+":textures/gui/astral_hole.png");
    public static final ResourceLocation constellations = new ResourceLocation(ThaumicConcilium.MODID+":textures/gui/constellations.png");
    final ResourceLocation star = new ResourceLocation(ThaumicConcilium.MODID+":textures/gui/star.png");
    final ResourceLocation shackles = new ResourceLocation(ThaumicConcilium.MODID+":models/shackles.obj");
    final ResourceLocation chain = new ResourceLocation(ThaumicConcilium.MODID+":models/chain.obj");
    public static IModelCustom shacklesModel;
    public static IModelCustom chainModel;
    public static ResourceLocation shacklesTexture = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/shackles.png");
    public static ResourceLocation chainTexture = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/Chain.png");

    public static ModelBiped bratva = new ModelBiped();
    public static ResourceLocation skin = new ResourceLocation(ThaumicConcilium.MODID+":textures/entity/thaumaturge.png");
    public static ResourceLocation excsi = new ResourceLocation(ThaumicConcilium.MODID+":textures/entity/excsi.png");
    public static ResourceLocation loot = new ResourceLocation(ThaumicConcilium.MODID+":textures/entity/loot.png");
    public static ResourceLocation krump = new ResourceLocation(ThaumicConcilium.MODID+":textures/entity/krump.png");

    public static ResourceLocation ichor = new ResourceLocation(LibResources.MODEL_ARMOR_ICHOR_GEM_1);
    public static ResourceLocation ichor2 = new ResourceLocation(LibResources.MODEL_ARMOR_ICHOR_GEM_2);
    public static ModelBiped ichorChest = new ModelWings();
    public static ModelBiped ichorArmor = new ModelBiped(0.5F);

    RenderItem ri = new RenderItem();

    public ClientEvents(){
        shacklesModel = AdvancedModelLoader.loadModel(shackles);
        chainModel = AdvancedModelLoader.loadModel(chain);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void clientTick(MouseEvent event) {
        event.setCanceled(onMouse(event.dwheel));
        if (event.dwheel != 0 && currentSlot > -1) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            player.inventory.currentItem = this.currentSlot;
            TCPacketHandler.INSTANCE.sendToServer(new PacketChangeEtherealRange(player, event.dwheel));
        }
    }


    public boolean onMouse(int dwheel) {
        if (dwheel == 0) return false;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        ItemStack stack = player.getCurrentEquippedItem();
        if (stack == null) return false;

        boolean store = stack.getItem() instanceof ItemWandCasting && player.isSneaking() && player.isUsingItem();

        if (store) {
            currentSlot = player.inventory.currentItem;
            return true;
        } else {
            currentSlot = -1;
        }
        return false;
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        World world = mc.theWorld;
        if (event.phase != TickEvent.Phase.START && Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;
            long time = System.currentTimeMillis();


            if (player != null && mc.inGameHasFocus && Minecraft.isGuiEnabled()) {
                if(TCPlayerCapabilities.get(player) != null && !mc.gameSettings.showDebugInfo){
                    renderAstralHole(player);
                }

                if (player.inventory.getCurrentItem() != null) {
                    if (player.inventory.getCurrentItem().getItem() instanceof ShardMill) {
                        this.renderShardMillHUD(event.renderTickTime, player, time, player.inventory.getCurrentItem());
                    }
                }
            }
        }

    }

    @SideOnly(Side.CLIENT)
    private void renderAstralHole(EntityPlayer player){
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
        if (capabilities == null) return;
        if (!capabilities.lithographed) return;
        if(capabilities.relieved) return;

        Minecraft mc = Minecraft.getMinecraft();

        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), mc.displayWidth, mc.displayHeight);
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, sr.getScaledWidth_double(), sr.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        int k = sr.getScaledWidth();
        int l = sr.getScaledHeight();
        int dailLocation = Config.dialBottom ? l - 32 : 0;
        GL11.glTranslatef(0.0F, (float) dailLocation, -2001.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);

        mc.renderEngine.bindTexture(this.astralHole);
        double zLevel = -90.0;
        GL11.glPushMatrix();
            GL11.glScaled(20.0, 15.2, 1.0);
        UtilsFX.drawTexturedQuadFull(0, 0, zLevel);
        GL11.glPopMatrix();

        ShaderHelper.useShader(ShaderHelper.blinkShader);
        int progLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.blinkShader, "progress");
        GL11.glPushMatrix();
        mc.renderEngine.bindTexture(this.star);
            GL11.glTranslated(80.0, 80.0, 0.0);
            GL11.glRotated(astralHoleBlinkTimer / 30.0, 0.0F, 0.0F, 1.0F);
            GL11.glTranslated(-80.0, -80.0, -0.0);
            GL11.glScaled(7.0, 7.0, 1.0);

        ARBShaderObjects.glUniform1iARB(progLoc, 60 - astralHoleBlinkTimer);
        UtilsFX.drawTexturedQuadFull(0, 0, zLevel);
        GL11.glPopMatrix();

        mc.renderEngine.bindTexture(constellations);
        GL11.glPushMatrix();

        if (player.ticksExisted % 3 == 0) {
            astralHoleBlinkTimer += blinkFlag ? 1 : -1;
            if (astralHoleBlinkTimer == 60 || astralHoleBlinkTimer == 0) {
                blinkFlag = !blinkFlag;
            }
        }
        ARBShaderObjects.glUniform1iARB(progLoc, astralHoleBlinkTimer);

        if (partyHealth[0] != -1) {
            UtilsFX.drawTexturedQuad(0, 20, 16 * 3, 16 * partyHealth[0], 16, 16, zLevel);
        }
        if (partyHealth[1] != -1) {
            UtilsFX.drawTexturedQuad(20, 0, 16 * 3, 16 * partyHealth[1], 16, 16, zLevel);
        }
        if (partyHealth[2] != -1) {
            UtilsFX.drawTexturedQuad(100, 10, 16 * 3, 16 * partyHealth[2], 16, 16, zLevel);
        }
        if (partyHealth[3] != -1) {
            UtilsFX.drawTexturedQuad(15, 100, 16 * 3, 16 * partyHealth[3], 16, 16, zLevel);
        }


        if (partyBlood[0] != -1) {
            UtilsFX.drawTexturedQuad(14, 40, 16*2, 16 * partyBlood[0], 16, 16, zLevel);
        }
        if (partyBlood[1] != -1) {
            UtilsFX.drawTexturedQuad(37, 17, 16*2, 16 * partyBlood[1], 16, 16, zLevel);
        }
        if (partyBlood[2] != -1) {
            UtilsFX.drawTexturedQuad(90, 37, 16*2, 16 * partyBlood[2], 16, 16, zLevel);
        }
        if (partyBlood[3] != -1) {
            UtilsFX.drawTexturedQuad(37, 98, 16*2, 16 * partyBlood[3], 16, 16, zLevel);
        }

        if (partyMana[0] != -1) {
            UtilsFX.drawTexturedQuad(2, 55, 16, 16 * partyMana[0], 16, 16, zLevel);
        }
        if (partyMana[1] != -1) {
            UtilsFX.drawTexturedQuad(53, 12, 16, 16 * partyMana[1], 16, 16, zLevel);
        }
        if (partyMana[2] != -1) {
            UtilsFX.drawTexturedQuad(85, 56, 16, 16 * partyMana[2], 16, 16, zLevel);
        }
        if (partyMana[3] != -1) {
            UtilsFX.drawTexturedQuad(53, 90, 16, 16 * partyMana[3], 16, 16, zLevel);
        }

        if (partyRunes[0] != -1) {
            UtilsFX.drawTexturedQuad(5, 70, 0, 16 * partyRunes[0], 16, 16, zLevel);
        }
        if (partyRunes[1] != -1) {
            UtilsFX.drawTexturedQuad(73, 2, 0, 16 * partyRunes[1], 16, 16, zLevel);
        }
        if (partyRunes[2] != -1) {
            UtilsFX.drawTexturedQuad(95, 72, 0, 16 * partyRunes[2], 16, 16, zLevel);
        }
        if (partyRunes[3] != -1) {
            UtilsFX.drawTexturedQuad(73, 108, 0, 16 * partyRunes[3], 16, 16, zLevel);
        }
        GL11.glPushMatrix();
        GL11.glRotated(astralHoleBlinkTimer / 30.0, 0.0F, 0.0F, 1.0F);
        if (brains[0] != -1){
            for (int iter = 3; iter >= 0; iter--){
                if (((brains[0] & (1 << iter)) >> iter) == 1) {
                    UtilsFX.drawTexturedQuad(5, 25 + (36 - iter*12), 16 * 4, 16, 16, 16, zLevel);
                } else {
                    UtilsFX.drawTexturedQuad(5, 25 + (36 - iter*12), 16 * 4, 0, 16, 16, zLevel);
                }
            }
        }
        if (brains[1] != -1){
            for (int iter = 3; iter >= 0; iter--){
                if (((brains[1] & (1 << iter)) >> iter) == 1) {
                    UtilsFX.drawTexturedQuad(30 + (36 - iter*12), 3, 16 * 4, 16, 16, 16, zLevel);
                } else {
                    UtilsFX.drawTexturedQuad(30 + (36 - iter*12), 3, 16 * 4, 0, 16, 16, zLevel);
                }
            }
        }
        if (brains[2] != -1){
            for (int iter = 3; iter >= 0; iter--){
                if (((brains[2] & (1 << iter)) >> iter) == 1) {
                    UtilsFX.drawTexturedQuad(102 - iter*3, 20 + (36 - iter*12), 16 * 4, 16, 16, 16, zLevel);
                } else {
                    UtilsFX.drawTexturedQuad(102 - iter*3, 20 + (36 - iter*12), 16 * 4, 0, 16, 16, zLevel);
                }
            }
        }
        if (brains[3] != -1){
            for (int iter = 3; iter >= 0; iter--){
                if (((brains[3] & (1 << iter)) >> iter) == 1) {
                    UtilsFX.drawTexturedQuad(30 + (36 - iter*12), 100 - iter*3, 16 * 4, 16, 16, 16, zLevel);
                } else {
                    UtilsFX.drawTexturedQuad(30 + (36 - iter*12), 100 - iter*3, 16 * 4, 0, 16, 16, zLevel);
                }
            }
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        ShaderHelper.releaseShader();

        GL11.glPopMatrix();
    }

    @SideOnly(Side.CLIENT)
    private void renderShardMillHUD(Float partialTicks, EntityPlayer player, long time, ItemStack mill) {
        Minecraft mc = Minecraft.getMinecraft();


        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), mc.displayWidth, mc.displayHeight);
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, sr.getScaledWidth_double(), sr.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        int k = sr.getScaledWidth();
        int l = sr.getScaledHeight();
        int dailLocation = Config.dialBottom ? l - 32 : 0;
        GL11.glTranslatef(0.0F, (float) dailLocation, -2000.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        mc.renderEngine.bindTexture(this.HUD);
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        UtilsFX.drawTexturedQuad(0, 0, 0, 0, 64, 64, -90.0);
        GL11.glPopMatrix();
        GL11.glTranslatef(16.0F, 16.0F, 0.0F);

        NBTTagCompound tag = mill.getTagCompound();

        int amt = 0;

        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -32.0F, 0.0F);
        GL11.glScaled(0.5, 0.5, 0.5);
        if(tag != null) {
            if (tag.getInteger("Loaded") != -1) {
                amt = tag.getInteger("Amount");
                if (player.isUsingItem()) {
                    amt -= player.getItemInUseDuration();
                    amt = MathHelper.clamp_int(amt, 0, 20);
                }
                int loc = (int) (30.0F * (float) amt / (float) 20);
                GL11.glPushMatrix();
                Color ac = new Color(ShardPowderEntityRenderer.colors.get(tag.getInteger("Loaded")));
                GL11.glColor4f((float) ac.getRed() / 255.0F, (float) ac.getGreen() / 255.0F, (float) ac.getBlue() / 255.0F, 0.8F);
                UtilsFX.drawTexturedQuad(-4, 35 - loc, 104, 0, 8, loc, -90.0);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
        GL11.glPushMatrix();
        UtilsFX.drawTexturedQuad(-8, -3, 72, 0, 16, 42, -90.0);
        GL11.glPopMatrix();
        GL11.glPopMatrix();


        ItemStack shard;
        if (tag == null){
            shard = mill.getItemDamage() == 0 ? new ItemStack(ConfigItems.itemShard, 1, 0) : new ItemStack(ForbiddenItems.deadlyShards, 1, 0);
        } else {
            int type = tag.getInteger("Type");
            if (type == 14) {
                shard = new ItemStack(ForbiddenItems.gluttonyShard);
            } else {
                shard = mill.getItemDamage() == 0 ? new ItemStack(ConfigItems.itemShard, 1, type) : new ItemStack(ForbiddenItems.deadlyShards, 1, type - 7);
            }
        }


        GL11.glPushMatrix();
        GL11.glTranslatef(-24.0F, -24.0F, 90.0F);
        GL11.glEnable(2896);
        this.ri.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, shard, 16, 16);
        GL11.glDisable(2896);
        GL11.glPopMatrix();

        if(tag != null) {
            int f = tag.getInteger("Amount");
            if (player.isUsingItem()){
                f -= player.getItemInUseDuration();
            }
            if (f > 0 && player.isSneaking()) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, 0.0F, 150.0F);
                GL11.glScaled(0.5, 0.5, 0.5);
                String secs = Integer.toString(f);
                int w = mc.fontRenderer.getStringWidth(secs) / 2;
                mc.ingameGUI.drawString(mc.fontRenderer, secs, -w, -4, 16777215);
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderOverlay(final RenderGameOverlayEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final long time = System.nanoTime() / 1000000L;
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            this.handleFociRadial(mc, time, event);
        }
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderBlockOverlay(RenderBlockOverlayEvent event){
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(event.player);
        if (capabilities.ethereal){
            event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderChains(RenderHandEvent event){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
        if (capabilities.chainedTime != 0){
            event.setCanceled(true);
            renderArm(event.renderPass, event.partialTicks, Minecraft.getMinecraft(), capabilities.chainedTime);
        }
        //event.setCanceled(true);
        //renderArm(event.renderPass, event.partialTicks, Minecraft.getMinecraft());
    }

    public void handleFociRadial(Minecraft mc, long time, RenderGameOverlayEvent event) {
        ItemStack mill = mc.thePlayer.getCurrentEquippedItem();
        if (mill == null) return;
        if (!(mill.getItem() instanceof ShardMill)) return;
        if (TCKeyHandler.radialActive || radialHudScale > 0.0F) {
            long timeDiff = System.currentTimeMillis() - TCKeyHandler.lastPressF;
            if (!TCKeyHandler.radialActive) {
                if (mc.currentScreen == null && this.lastState) {
                    if (Display.isActive() && !mc.inGameHasFocus) {
                        mc.inGameHasFocus = true;
                        mc.mouseHelper.grabMouseCursor();
                    }

                    this.lastState = false;
                }
            } else {
                if (mc.currentScreen != null) {
                    TCKeyHandler.radialActive = false;
                    TCKeyHandler.radialLock = true;
                    mc.setIngameFocus();
                    mc.setIngameNotInFocus();
                    return;
                }

                if (radialHudScale == 0.0F) {
                    this.foci.clear();
                    this.fociHover.clear();
                    this.fociScale.clear();
                    int pouchcount = 0;
                    ItemStack item = null;
                    int a = 0;

                    while (true) {
                        ItemStack[] inv;
                        int q;
                        if (a >= 4) {
                            for (a = 0; a < 36; ++a) {
                                item = mc.thePlayer.inventory.mainInventory[a];
                                if (item != null) {
                                    switch (mill.getItemDamage()) {
                                        case 0: {
                                            if (item.getItem() == ConfigItems.itemShard) {
                                                //this.foci.put(0, item.getItemDamage());
                                                this.foci.put(item.getItemDamage(), a);
                                                this.fociScale.put(item.getItemDamage(), 1.0F);
                                                this.fociHover.put(item.getItemDamage(), false);
                                            }
                                            break;
                                        }
                                        case 1: {
                                            if (item.getItem() == ForbiddenItems.deadlyShards) {
                                                this.foci.put(item.getItemDamage(), a);
                                                this.fociScale.put(item.getItemDamage(), 1.0F);
                                                this.fociHover.put(item.getItemDamage(), false);
                                                //this.foci.put(1, item.getItemDamage());
                                            } else if (item.getItem() == ForbiddenItems.gluttonyShard) {
                                                this.foci.put(14, a);
                                                this.fociScale.put(14, 1.0F);
                                                this.fociHover.put(14, false);

                                            }
                                            break;
                                        }
                                        case 2: {

                                            break;
                                        }
                                    }
                                }

                            }

                            if (this.foci.size() > 0 && mc.inGameHasFocus) {
                                mc.inGameHasFocus = false;
                                mc.mouseHelper.ungrabMouseCursor();
                            }
                            break;
                        }

                        ++a;
                    }
                }
            }

            this.renderFocusRadialHUD(event.resolution.getScaledWidth_double(), event.resolution.getScaledHeight_double(), time, event.partialTicks);
            if (time > this.lastTime) {
                Iterator i$ = this.fociHover.keySet().iterator();

                while (i$.hasNext()) {
                    Integer key = (Integer) i$.next();
                    if ((Boolean) this.fociHover.get(key)) {
                        if (!TCKeyHandler.radialActive && !TCKeyHandler.radialLock) {
                            if (key == 14) {
                                TCPacketHandler.INSTANCE.sendToServer(new PacketChangeActiveShard(mc.thePlayer, 14));
                            } else {
                                TCPacketHandler.INSTANCE.sendToServer(new PacketChangeActiveShard(mc.thePlayer, key + (mill.getItemDamage() == 0 ? 0 : 7)));
                            }
                            TCKeyHandler.radialLock = true;
                        }

                        if ((Float) this.fociScale.get(key) < 1.3F) {
                            this.fociScale.put(key, (Float) this.fociScale.get(key) + 0.025F);
                        }
                    } else if ((Float) this.fociScale.get(key) > 1.0F) {
                        this.fociScale.put(key, (Float) this.fociScale.get(key) - 0.025F);
                    }
                }

                if (!TCKeyHandler.radialActive) {
                    radialHudScale -= 0.05F;
                } else if (radialHudScale < 1.0F) {
                    radialHudScale += 0.05F;
                }

                if (radialHudScale > 1.0F) {
                    radialHudScale = 1.0F;
                }

                if (radialHudScale < 0.0F) {
                    radialHudScale = 0.0F;
                    TCKeyHandler.radialLock = false;
                }

                this.lastTime = time + 5L;
                this.lastState = TCKeyHandler.radialActive;
            }
        }

    }

    @SideOnly(Side.CLIENT)
    private void renderFocusRadialHUD(double sw, double sh, long time, float partialTicks) {
        RenderItem ri = new RenderItem();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ShardMill) {
            ItemStack mill = mc.thePlayer.getCurrentEquippedItem();
            int i = (int) ((double) Mouse.getEventX() * sw / (double) mc.displayWidth);
            int j = (int) (sh - (double) Mouse.getEventY() * sh / (double) mc.displayHeight - 1.0);
            int k = Mouse.getEventButton();
            if (!this.foci.isEmpty()) {
                GL11.glPushMatrix();
                GL11.glClear(256);
                GL11.glMatrixMode(5889);
                GL11.glLoadIdentity();
                GL11.glOrtho(0.0, sw, sh, 0.0, 1000.0, 3000.0);
                GL11.glMatrixMode(5888);
                GL11.glLoadIdentity();
                GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glPushMatrix();
                GL11.glTranslated(sw / 2.0, sh / 2.0, 0.0);
                //ItemStack tt = null;
                float width = 16.0F + (float) this.foci.size() * 2.5F;
                UtilsFX.bindTexture("textures/misc/radial.png");
                GL11.glPushMatrix();
                GL11.glRotatef(partialTicks + (float) (mc.thePlayer.ticksExisted % 720) / 2.0F, 0.0F, 0.0F, 1.0F);
                GL11.glAlphaFunc(516, 0.003921569F);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                UtilsFX.renderQuadCenteredFromTexture(width * 2.75F * radialHudScale, 0.5F, 0.5F, 0.5F, 200, 771, 0.5F);
                GL11.glDisable(3042);
                GL11.glAlphaFunc(516, 0.1F);
                GL11.glPopMatrix();
                UtilsFX.bindTexture("textures/misc/radial2.png");
                GL11.glPushMatrix();
                GL11.glRotatef(-(partialTicks + (float) (mc.thePlayer.ticksExisted % 720) / 2.0F), 0.0F, 0.0F, 1.0F);
                GL11.glAlphaFunc(516, 0.003921569F);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                UtilsFX.renderQuadCenteredFromTexture(width * 2.55F * radialHudScale, 0.5F, 0.5F, 0.5F, 200, 771, 0.5F);
                GL11.glDisable(3042);
                GL11.glAlphaFunc(516, 0.1F);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GL11.glEnable(32826);
                RenderHelper.enableGUIStandardItemLighting();
                NBTTagCompound tag = mill.getTagCompound();
                ItemStack item = new ItemStack(ConfigItems.itemShard, 1, 0);
                if (tag != null) {
                    if (mill.getItemDamage() == 0) {
                        item = new ItemStack(ConfigItems.itemShard, 1, tag.getInteger("Type"));
                    } else if (mill.getItemDamage() == 1) {
                        int type = tag.getInteger("Type");
                        if (type == 14) {
                            item = new ItemStack(ForbiddenItems.gluttonyShard);
                        } else {
                            item = new ItemStack(ForbiddenItems.deadlyShards, 1, tag.getInteger("Type") - 7);
                        }
                    }
                }
                // item.stackTagCompound = null;
                ri.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, item, -8, -8);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable(32826);
                GL11.glPopMatrix();
                int mx = (int) ((double) i - sw / 2.0);
                int my = (int) ((double) j - sh / 2.0);
                /*if (mx >= -10 && mx <= 10 && my >= -10 && my <= 10) {
                    tt = wand.getFocusItem(mc.thePlayer.getCurrentEquippedItem());
                }

                 */

                GL11.glScaled((double) radialHudScale, (double) radialHudScale, (double) radialHudScale);
                float currentRot = -90.0F * radialHudScale;
                float pieSlice = 360.0F / (float) this.foci.size();
                Integer key = (Integer) this.foci.firstKey();
                for (int a = 0; a < this.foci.size(); ++a) {
                    double xx = (double) (MathHelper.cos(currentRot / 180.0F * 3.1415927F) * width);
                    double yy = (double) (MathHelper.sin(currentRot / 180.0F * 3.1415927F) * width);
                    currentRot += pieSlice;
                    GL11.glPushMatrix();
                    GL11.glTranslated(xx, yy, 100.0);
                    GL11.glScalef((Float) this.fociScale.get(key), (Float) this.fociScale.get(key), (Float) this.fociScale.get(key));
                    GL11.glEnable(32826);
                    RenderHelper.enableGUIStandardItemLighting();

                    ItemStack shard;
                    if (key == 14) {
                        shard = new ItemStack(ForbiddenItems.gluttonyShard);
                    } else {
                        shard = mill.getItemDamage() == 0 ? new ItemStack(ConfigItems.itemShard, 1, key) : new ItemStack(ForbiddenItems.deadlyShards, 1, key);
                    }
                    ri.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, shard, -8, -8);
                    RenderHelper.disableStandardItemLighting();
                    GL11.glDisable(32826);
                    GL11.glPopMatrix();
                    if (!TCKeyHandler.radialLock && TCKeyHandler.radialActive) {
                        int mmx = (int) ((double) i - sw / 2.0 - xx);
                        int mmy = (int) ((double) j - sh / 2.0 - yy);
                        if (mmx >= -10 && mmx <= 10 && mmy >= -10 && mmy <= 10) {
                            this.fociHover.put(key, true);
                            /*
                            if (k == 0) {
                                TCKeyHandler.radialActive = false;
                                TCKeyHandler.radialLock = true;
                                System.out.println(key);
                                if (key == 14) {
                                    TCPacketHandler.INSTANCE.sendToServer(new PacketChangeActiveShard(mc.thePlayer, key));
                                } else {
                                    TCPacketHandler.INSTANCE.sendToServer(new PacketChangeActiveShard(mc.thePlayer, key + mill.getItemDamage() == 0 ? 0 : 7));
                                }
                                break;
                            }

                             */
                        } else {
                            this.fociHover.put(key, false);
                        }
                    }

                    key = (Integer) this.foci.higherKey(key);
                }

                GL11.glPopMatrix();

                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
    }

    public void renderArm(int renderPass, float partialTicks, Minecraft mc, int chainedTime) {
        GL11.glClear(256);
        float farPlaneDistance = (float)(mc.gameSettings.renderDistanceChunks * 16);
        double cameraZoom = 1.0D;
        double cameraYaw = 0.0D;
        double cameraPitch = 0.0D;
        if(mc.entityRenderer.debugViewDirection <= 0) {
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            float f1 = 0.07F;
            if(mc.gameSettings.anaglyph) {
                GL11.glTranslatef((float)(-(renderPass * 2 - 1)) * f1, 0.0F, 0.0F);
            }

            if(cameraZoom != 1.0D) {
                GL11.glTranslatef((float)cameraYaw, (float)(-cameraPitch), 0.0F);
                GL11.glScaled(cameraZoom, cameraZoom, 1.0D);
            }
            Project.gluPerspective(this.getFOVModifier(partialTicks, mc.entityRenderer, mc), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
            if(mc.playerController.enableEverythingIsScrewedUpMode()) {
                float f2 = 0.6666667F;
                GL11.glScalef(1.0F, f2, 1.0F);
            }

            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            if(mc.gameSettings.anaglyph) {
                GL11.glTranslatef((float)(renderPass * 2 - 1) * 0.1F, 0.0F, 0.0F);
            }

            GL11.glPushMatrix();
            this.hurtCameraEffect(partialTicks, mc);
            if(mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(partialTicks, mc);
            }

            if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping() && !mc.gameSettings.hideGUI && !mc.playerController.enableEverythingIsScrewedUpMode()) {
                mc.entityRenderer.enableLightmap((double)partialTicks);
                this.renderEmptyHand(mc, partialTicks, chainedTime);
                mc.entityRenderer.disableLightmap((double)partialTicks);
            }

            GL11.glPopMatrix();
            if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping()) {
                mc.entityRenderer.itemRenderer.renderOverlays(partialTicks);
                this.hurtCameraEffect(partialTicks, mc);
            }

            if(mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(partialTicks, mc);
            }
        }

    }

    private void renderEmptyHand(Minecraft mc, float p_78440_1_, int chainedTime) {
        float f1 = 1.0F;
        EntityClientPlayerMP entityclientplayermp = mc.thePlayer;
        float f2 = entityclientplayermp.prevRotationPitch + (entityclientplayermp.rotationPitch - entityclientplayermp.prevRotationPitch) * p_78440_1_;
        GL11.glPushMatrix();
        GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(entityclientplayermp.prevRotationYaw + (entityclientplayermp.rotationYaw - entityclientplayermp.prevRotationYaw) * p_78440_1_, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        float f3 = entityclientplayermp.prevRenderArmPitch + (entityclientplayermp.renderArmPitch - entityclientplayermp.prevRenderArmPitch) * p_78440_1_;
        float f4 = entityclientplayermp.prevRenderArmYaw + (entityclientplayermp.renderArmYaw - entityclientplayermp.prevRenderArmYaw) * p_78440_1_;
        GL11.glRotatef((entityclientplayermp.rotationPitch - f3) * 0.1F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef((entityclientplayermp.rotationYaw - f4) * 0.1F, 0.0F, 1.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(!entityclientplayermp.isInvisible()) {
            Render render1 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render1;
            mc.getTextureManager().bindTexture(entityclientplayermp.getLocationSkin());
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            float f10 = 1.0F;
            GL11.glPushMatrix();
            GL11.glRotatef(1.0F, 0.0F, 0.0F, 1.0F);

            GL11.glPushMatrix();
            GL11.glTranslatef(0.53F, -0.8F, -0.8F);
            GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.5F, 0.0F, 0.1F);
            GL11.glScalef(f10, f10, f10);
            renderplayer.renderFirstPersonArm(mc.thePlayer);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(-0.05F, -0.8F, -0.8F);
            GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(f10, f10, f10);
            renderplayer.renderFirstPersonArm(mc.thePlayer);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, -1.2F);
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            float k = 0F;
            if (chainedTime < 10){
                k = ((float) (10 - chainedTime) / 30);
            }
            GL11.glScalef(1.05F + k, 1.05F, 1.05F + k);
            ShaderHelper.useShader(ShaderHelper.etherealShader);
            int progLoc = ARBShaderObjects.glGetUniformLocationARB(ShaderHelper.etherealShader, "progress");
            ARBShaderObjects.glUniform1iARB(progLoc, 100 - chainedTime);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA);
            mc.getTextureManager().bindTexture(shacklesTexture);
            shacklesModel.renderAll();
            mc.getTextureManager().bindTexture(chainTexture);
            chainModel.renderAll();
            GL11.glDisable(GL11.GL_BLEND);
            ShaderHelper.releaseShader();
            GL11.glPopMatrix();

            GL11.glPopMatrix();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
    }

    private void hurtCameraEffect(float p_78482_1_, Minecraft mc) {
        EntityLivingBase entitylivingbase = mc.renderViewEntity;
        float f1 = (float)entitylivingbase.hurtTime - p_78482_1_;
        float f2;
        if(entitylivingbase.getHealth() <= 0.0F) {
            f2 = (float)entitylivingbase.deathTime + p_78482_1_;
            GL11.glRotatef(40.0F - 8000.0F / (f2 + 200.0F), 0.0F, 0.0F, 1.0F);
        }

        if(f1 >= 0.0F) {
            f1 /= (float)entitylivingbase.maxHurtTime;
            f1 = MathHelper.sin(f1 * f1 * f1 * f1 * 3.1415927F);
            f2 = entitylivingbase.attackedAtYaw;
            GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f1 * 14.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
        }

    }

    private void setupViewBobbing(float p_78475_1_, Minecraft mc) {
        if(mc.renderViewEntity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)mc.renderViewEntity;
            float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            float f2 = -(entityplayer.distanceWalkedModified + f1 * p_78475_1_);
            float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * p_78475_1_;
            float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * p_78475_1_;
            GL11.glTranslatef(MathHelper.sin(f2 * 3.1415927F) * f3 * 0.5F, -Math.abs(MathHelper.cos(f2 * 3.1415927F) * f3), 0.0F);
            GL11.glRotatef(MathHelper.sin(f2 * 3.1415927F) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(Math.abs(MathHelper.cos(f2 * 3.1415927F - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
        }

    }

    private float getFOVModifier(float partialTicks, EntityRenderer er, Minecraft mc) {
        if(er.debugViewDirection > 0) {
            return 90.0F;
        } else {
            EntityLivingBase entityplayer = mc.renderViewEntity;
            float f1 = 70.0F;
            if(entityplayer.getHealth() <= 0.0F) {
                float block = (float)entityplayer.deathTime + partialTicks;
                f1 /= (1.0F - 500.0F / (block + 500.0F)) * 2.0F + 1.0F;
            }

            Block block1 = ActiveRenderInfo.getBlockAtEntityViewpoint(mc.theWorld, entityplayer, partialTicks);
            if(block1.getMaterial() == Material.water) {
                f1 = f1 * 60.0F / 70.0F;
            }

            return f1;
        }
    }


    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderPlayer(RenderPlayerEvent.Specials.Post event){
        Minecraft mc = Minecraft.getMinecraft();
        AbstractClientPlayer player = null;
        if (event.entityPlayer instanceof EntityClientPlayerMP) {
            player = (EntityClientPlayerMP) event.entityPlayer;
        } else if (event.entityPlayer instanceof EntityOtherPlayerMP){
            player = (EntityOtherPlayerMP) event.entityPlayer;
        }
        if (player == null) return;
        if (player.getCommandSenderName().equalsIgnoreCase("Ilya3000") ||
                player.getCommandSenderName().equals("RA3DUPLYATOR") ||
                player.getCommandSenderName().equals("BreadX22") ||
                player.getCommandSenderName().equals("Excsi") ||
                player.getCommandSenderName().equals("krumplerban")) {
            if (player.isInvisible() || player.isInvisibleToPlayer(mc.thePlayer) || player.isPotionActive(Potion.invisibility)) return;
            float move = player.limbSwing;
            if (player.prevPosX == player.posX && player.prevPosZ == player.posZ){
                move = 2.4F;
            }
            bratva.isChild = false;
            bratva.isSneak = player.isSneaking();
            if (player.getCommandSenderName().equals("Excsi")) {
                mc.getTextureManager().bindTexture(excsi);
            } else if(player.getCommandSenderName().equals("krumplerban")){
                mc.getTextureManager().bindTexture(krump);
            }
            else {
                mc.getTextureManager().bindTexture(skin);
            }

            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glTranslated( 1.0, 0,  1.0);
            bratva.render(player, move, 0.625F, player.ticksExisted, 0.0F, player.rotationPitch, 0.0625F);
            GL11.glTranslated( -2.0, 0,  0.0);
            bratva.render(player, move, 0.625F, player.ticksExisted, 0.0F, player.rotationPitch, 0.0625F);
            GL11.glPopMatrix();

        } else
        if (player.getCommandSenderName().equals("Lootram")){
            if (player.isInvisible() || player.isInvisibleToPlayer(mc.thePlayer) || player.isPotionActive(Potion.invisibility)) return;
            float move = player.limbSwing;
            if (player.moveForward == 0F && player.moveStrafing == 0F){
                move = 2.4F;
            }
            bratva.isChild = false;
            bratva.bipedHeadwear.isHidden = true;
            ichorChest.isChild = false;
            ichorArmor.isChild = false;
            bratva.isSneak = player.isSneaking();
            ichorChest.isSneak = bratva.isSneak;
            ichorArmor.isSneak = bratva.isSneak;
            mc.getTextureManager().bindTexture(loot);
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glTranslated( 1.0, 0,  1.0);
            bratva.render(player, move, 0.625F, player.ticksExisted, 0.0F, player.rotationPitch, 0.0625F);
            GL11.glTranslated( -2.0, 0,  0.0);
            bratva.render(player, move, 0.625F, player.ticksExisted, 0.0F, player.rotationPitch, 0.0625F);
            GL11.glPopMatrix();

            mc.getTextureManager().bindTexture(ichor);
            ichorArmor.setRotationAngles(move, 0.625F, player.ticksExisted, 0.0F, player.rotationPitch, 0.0625F, player);
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glTranslated( 1.0, 0,  1.0);
            ichorChest.render(player, move, 0.625F, player.ticksExisted, 0.0F, player.rotationPitch, 0.0625F);
            ichorArmor.bipedHead.render(0.0625F);
            ichorArmor.bipedLeftLeg.render(0.0625F);
            ichorArmor.bipedRightLeg.render(0.0625F);
            ichorArmor.bipedRightArm.render(0.0625F);
            ichorArmor.bipedLeftArm.render(0.0625F);
            GL11.glTranslated( -2.0, 0,  0.0);
            ichorChest.render(player, move, 0.625F, player.ticksExisted, 0.0F, player.rotationPitch, 0.0625F);
            ichorArmor.bipedHead.render(0.0625F);
            ichorArmor.bipedLeftLeg.render(0.0625F);
            ichorArmor.bipedRightLeg.render(0.0625F);
            ichorArmor.bipedRightArm.render(0.0625F);
            ichorArmor.bipedLeftArm.render(0.0625F);
            GL11.glPopMatrix();

            mc.getTextureManager().bindTexture(ichor2);
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glTranslated( 1.0, 0,  1.0);
            ichorArmor.bipedLeftLeg.render(0.0625F);
            ichorArmor.bipedRightLeg.render(0.0625F);
            GL11.glTranslated( -2.0, 0,  0.0);
            ichorArmor.bipedLeftLeg.render(0.0625F);
            ichorArmor.bipedRightLeg.render(0.0625F);
            GL11.glPopMatrix();
        }
    }
}
