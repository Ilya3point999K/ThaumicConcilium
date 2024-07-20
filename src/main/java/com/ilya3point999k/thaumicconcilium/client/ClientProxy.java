package com.ilya3point999k.thaumicconcilium.client;

import com.ilya3point999k.thaumicconcilium.client.events.ClientEvents;
import com.ilya3point999k.thaumicconcilium.client.events.TCKeyHandler;
import com.ilya3point999k.thaumicconcilium.client.render.*;
import com.ilya3point999k.thaumicconcilium.client.render.block.*;
import com.ilya3point999k.thaumicconcilium.client.render.fx.ChainFX;
import com.ilya3point999k.thaumicconcilium.client.render.fx.RuneFlowFX;
import com.ilya3point999k.thaumicconcilium.client.render.item.*;
import com.ilya3point999k.thaumicconcilium.client.render.mob.*;
import com.ilya3point999k.thaumicconcilium.client.render.model.GolemBydloModel;
import com.ilya3point999k.thaumicconcilium.client.render.model.VengefulGolemModel;
import com.ilya3point999k.thaumicconcilium.client.render.projectile.*;
import com.ilya3point999k.thaumicconcilium.common.CommonProxy;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.*;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.*;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.*;
import com.ilya3point999k.thaumicconcilium.common.events.TCGuiEvent;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.*;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXBreaking;
import thaumcraft.client.fx.particles.FXBubble;
import thaumcraft.client.fx.particles.FXEssentiaTrail;
import thaumcraft.client.renderers.entity.RenderTaintSpider;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.golems.EntityGolemBase;

import java.util.List;
import java.util.Random;

public class ClientProxy extends CommonProxy {
    final static Random random = new Random();
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ShaderHelper.initShaders();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        registerRenderers();
        MinecraftForge.EVENT_BUS.register(new TCGuiEvent());
        ClientEvents events = new ClientEvents();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }

    @Override
    public void registerKeyBindings() {
        FMLCommonHandler.instance().bus().register(new TCKeyHandler());
    }

    @Override
    public void registerRenderers() {
        TransparentItemRenderer transparentItemRenderer = new TransparentItemRenderer();
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.tightBelt, transparentItemRenderer);
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.impulseFoci, transparentItemRenderer);
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.ringOfBlusteringLight, transparentItemRenderer);
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.burdeningAmulet, transparentItemRenderer);
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.spotlightFoci, transparentItemRenderer);
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.thaumaturgeDrum, new ThaumaturgeDrumRenderer());
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.pontifexHammer, new PaladinHammerRenderer());
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.shardMill, new ShardMillRenderer());
        MinecraftForgeClient.registerItemRenderer(TCItemRegistry.astralMonitor, new AstralMonitorRenderer());

        RenderingRegistry.registerEntityRenderingHandler(WrathEffectEntity.class, new WrathEffectRender());
        RenderingRegistry.registerEntityRenderingHandler(DistortionEffectEntity.class, new DistortionEffectRenderer());
        RenderingRegistry.registerEntityRenderingHandler(UpcomingHoleEntity.class, new UpcomingHoleRender());

        RenderingRegistry.registerEntityRenderingHandler(Thaumaturge.class, new ThaumaturgeRenderer(new ModelBiped(),
                new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/thaumaturge.png"), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(MadThaumaturge.class, new MadThaumaturgeRenderer(new ModelBiped(),
                new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/mad_thaumaturge.png"), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(TaintSpiderSmart.class, new RenderTaintSpider());

        if (Integration.taintedMagic) {
           RenderingRegistry.registerEntityRenderingHandler(Samurai.class, new SamuraiRenderer(new ModelBiped(),
                   new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/thaumaturge.png"), 0.5f));
           RenderingRegistry.registerEntityRenderingHandler(CrimsonPaladin.class, new CrimsonPaladinRenderer(new ModelBiped(),
                   new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/crimson_paladin.png"), 0.5f));
           RenderingRegistry.registerEntityRenderingHandler(CrimsonPontifex.class, new CrimsonPontifexRenderer(new ModelBiped(), new ResourceLocation("thaumcraft", "textures/models/cultist.png"), 1.0F));

           RenderingRegistry.registerEntityRenderingHandler(LesserPortal.class, new LesserPortalRenderer());
           RenderingRegistry.registerEntityRenderingHandler(CrimsonOrbEntity.class, new CrimsonOrbEntityRenderer());
           RenderingRegistry.registerEntityRenderingHandler(EtherealShacklesEntity.class, new EtherealShacklesEntityRenderer());
           RenderingRegistry.registerEntityRenderingHandler(MaterialPeeler.class, new MaterialPeelerRenderer());

        }
        if (Integration.horizons) {
            RenderingRegistry.registerEntityRenderingHandler(Overanimated.class, new ThaumaturgeRenderer(new ModelBiped(),
                    new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/overanimated.png"), 0.5f));
            RenderingRegistry.registerEntityRenderingHandler(ThaumGib.class, new ThaumGibRenderer());

            if (Integration.thaumicBases) {
                RenderingRegistry.registerEntityRenderingHandler(DopeSquid.class, new DopeSquidRenderer(new ModelSquid(), 0.5F));
            }

        }
        if (Integration.automagy) {         
            RenderingRegistry.registerEntityRenderingHandler(VengefulGolem.class, new VengefulGolemRenderer(new VengefulGolemModel(),
                    new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/vengeful_golem.png"), 0.5f));
        }
        RenderingRegistry.registerEntityRenderingHandler(Dissolved.class, new DissolvedRenderer(new ModelBiped(),
                new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/thaumaturge.png"), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(QuicksilverElemental.class, new QuicksilverElementalRenderer(new ModelBiped(),
                new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/quicksilver_elemental.png"), 0.5f));

        //RenderingRegistry.registerEntityRenderingHandler(VisCapsuleEntity.class, new RenderSnowball(TCItemRegistry.visCapsule));
        RenderingRegistry.registerEntityRenderingHandler(BottleOfThickTaintEntity.class, new RenderSnowball(TCItemRegistry.bottleOfThickTaint));
        RenderingRegistry.registerEntityRenderingHandler(BottleOfClearWaterEntity.class, new RenderSnowball(TCItemRegistry.bottleOfClearWater));
        RenderingRegistry.registerEntityRenderingHandler(PositiveBurstOrbEntity.class, new PositiveBurstOrbRenderer());
        RenderingRegistry.registerEntityRenderingHandler(CompressedBlastEntity.class, new CompressedBlastEntityRenderer());
        RenderingRegistry.registerEntityRenderingHandler(ChargedWispEntity.class, new ChargedWispRenderer());
        RenderingRegistry.registerEntityRenderingHandler(ConcentratedWarpChargeEntity.class, new ConcentratedWarpChargeEntityRenderer());
        RenderingRegistry.registerEntityRenderingHandler(RiftEntity.class, new RiftRenderer());
        RenderingRegistry.registerEntityRenderingHandler(ShardPowderEntity.class, new ShardPowderEntityRenderer());
        RenderingRegistry.registerEntityRenderingHandler(BrightestOne.class, new BrightestOneRenderer());

        if (Integration.automagy){
            RenderingRegistry.registerEntityRenderingHandler(RedPoweredMind.class, new RedPoweredMindRender());
        }

        ClientRegistry.bindTileEntitySpecialRenderer(DestabilizedCrystalTile.class, new DestabilizedCrystalTileRenderer());
        TCBlockRegistry.destabilizedCrystalBlockID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new DestabilizedCrystalBlockRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(VisCondenserTile.class, new VisCondenserTileRenderer());
        TCBlockRegistry.visCondenserBlockID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new VisCondenserBlockRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(HexOfPredictabilityTile.class, new HexOfPredictabilityTileRenderer());
        TCBlockRegistry.hexOfPredictabilityID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new HexOfPredictabilityBlockRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(QuicksilverCrucibleTile.class, new QuicksilverCrucibleTileRenderer());
        TCBlockRegistry.quicksilverCrucibleID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new QuicksilverCrucibleBlockRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(LithographerTile.class, new LithographerTileRenderer());
        TCBlockRegistry.lithographerID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new LithographerBlockRenderer());
        if (Integration.taintedMagic) {
            ClientRegistry.bindTileEntitySpecialRenderer(FleshCrucibleTile.class, new FleshCrucibleTileRenderer());
            TCBlockRegistry.fleshCrucibleID = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(new FleshCrucibleBlockRenderer());
        }

        if(Integration.thaumicBases){
            MinecraftForgeClient.registerItemRenderer(TCItemRegistry.castingBracelet, new CastingBraceletRenderer());
        }
        if(Integration.gadomancy){
            MinecraftForgeClient.registerItemRenderer(TCItemRegistry.riftGem, new RiftGemRenderer());
            MinecraftForgeClient.registerItemRenderer(TCItemRegistry.terraCastGem, new TerraCastGemRenderer());
        }

        if(Integration.horizons){
            MinecraftForgeClient.registerItemRenderer(TCItemRegistry.drainageSyringe, new DrainageSyringeRenderer());
            RenderingRegistry.registerEntityRenderingHandler(GolemBydlo.class, new GolemBydloRenderer(new GolemBydloModel(true)));
            if(Integration.automagy){
                ClientRegistry.bindTileEntitySpecialRenderer(RedPoweredMindTile.class, new RedPoweredMindTileRenderer());
                TCBlockRegistry.redPoweredMindBlockID = RenderingRegistry.getNextAvailableRenderId();
                RenderingRegistry.registerBlockHandler(new RedPoweredMindBlockRenderer());
            }
        }
    }

    @Override
    public void sendLocalMovementData(EntityLivingBase ent){
        if (ent == Minecraft.getMinecraft().thePlayer){
            if (ent.worldObj.isRemote) {
                if(ent.ridingEntity instanceof GolemBydlo){
                    EntityClientPlayerMP player = (EntityClientPlayerMP)ent;
                    player.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(player.rotationYaw, player.rotationPitch, player.onGround));
                    player.sendQueue.addToSendQueue(new C0CPacketInput(player.moveStrafing, player.moveForward, player.movementInput.jump, player.movementInput.sneak));
                }
                if (ClientEvents.isEnslaved){
                    EntityClientPlayerMP player = (EntityClientPlayerMP)ent;
                    List<VengefulGolem> list = player.worldObj.getEntitiesWithinAABB(VengefulGolem.class, player.boundingBox.expand(32.0, 32.0, 32.0));
                    List<CrimsonPontifex> list1 = player.worldObj.getEntitiesWithinAABB(CrimsonPontifex.class, player.boundingBox.expand(32.0, 32.0, 32.0));
                    if ((list.isEmpty() && list1.isEmpty()) && random.nextInt(10) > 5){
                        ClientEvents.isEnslaved = false;
                    }

                    if(player.ticksExisted % 60 == 0) {
                        player.rotationYaw = 360.0F * (-0.5F + player.worldObj.rand.nextFloat());
                    }
                    player.moveEntityWithHeading((-0.5F + player.worldObj.rand.nextFloat()), 1.0F);
                    player.movementInput.jump = false;
                    player.movementInput.sneak = false;
                    player.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(player.rotationYaw, MathHelper.cos(player.ticksExisted), player.onGround));
                    player.sendQueue.addToSendQueue(new C0CPacketInput(0.0F, player.moveForward, false, false));
                }
            }

        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case 0:{
                return new ThaumaturgeGUI(player.inventory, world, (Thaumaturge) (world).getEntityByID(x));
            }
            case 1:{
                return new GolemAssistantGUI(player, (EntityGolemBase) player.worldObj.getEntityByID(x));
            }
            case 2:{
                return new GolemValetGUI(player, (EntityGolemBase) player.worldObj.getEntityByID(x));
            }
            case 4:{
                return new AstralMonitorGUI(player.inventory, world);
            }
            case 5:{
                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile instanceof LithographerTile) {
                    return new LithographerGUI(player.inventory, (LithographerTile) tile);
                }
                break;
            }
        }
        return null;
    }

    @Override
    public void registerHandlers() {

    }

    @Override
    public void crucibleBoil(World world, int x, int y, int z) {
        FXBubble fb = new FXBubble(world, (double)x, (double)y, (double)z, 0.0, 0.0, 0.0, 1);
        fb.setRBGColorF(0.6F, 0.6F, 0.6F);
        ParticleEngine.instance.addEffect(world, fb);

    }

    @Override
    public void quicksilverFlow(World w, double x, double y, double z, double tx, double ty, double tz) {
        for (int i = 0; i < 5; i++) {
            ParticleEngine.instance.addEffect(w, new FXEssentiaTrail(w, tx + (random.nextDouble()), ty, tz + (random.nextDouble()), x + 0.5, y + 0.5, z + 0.5, 3, 0xAAAAAA, 1.3F));
        }
    }

    @Override
    public void lightbeam(EntityPlayer player, double tx, double ty, double tz, float red, float green, float blue,
                          int age) {
        Thaumcraft.proxy.beamCont(player.worldObj, player, tx, ty, tz, 2, 0xffff00, false, 0.0F, null, 5);
    }

    @Override
    public void warpchain(EntityPlayer player, double tx, double ty, double tz) {
        ParticleEngine.instance.addEffect(player.worldObj, new FXEssentiaTrail(player.worldObj, player.posX,
                player.posY + (random.nextDouble() - 1), player.posZ, tx, ty, tz, 1, 0x9929BD, 0.5F));
    }
    @Override
    public void lifedrain(Entity player, double tx, double ty, double tz) {
        ParticleEngine.instance.addEffect(player.worldObj, new FXEssentiaTrail(player.worldObj, tx, ty, tz, player.posX, player.posY + 0.5, player.posZ,1, 0x7A1A1A, 0.5F));
    }

    @Override
    public void bloodinitiation(Entity player, Entity madman) {
        ParticleEngine.instance.addEffect(player.worldObj, new FXEssentiaTrail(player.worldObj, player.posX, player.posY + 0.5, player.posZ, madman.posX, madman.posY + 1.8, madman.posZ, 5, 0x7A1A1A, 0.5F));
    }

    @Override
    public void runeFlow(EntityPlayer player, Entity e){
        RuneFlowFX rune = new RuneFlowFX(player.worldObj, e, player.posX, player.posY, player.posZ, 1.0f, 0.6f, 0.0f, 5);
        rune.multipleParticleScaleBy(2.0f);
        //MiscHelper.setEntityMotionFromVector(rune, new Vector3(e.posX, e.posY + 1.0f, e.posZ), 0.5f);
        ParticleEngine.instance.addEffect(player.worldObj, rune);
    }

    @Override
    public void chain(World world, double sx, double sy, double sz, double tx, double ty, double tz) {
        ChainFX chainFX = new ChainFX(world, sx, sy, sz, tx, ty, tz, 1.0F, 1.0F, 1.0F, 8);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(chainFX);
    }

    @Override
    public void smeltFX(final double blockX, final double blockY, final double blockZ, final World w, final int howMany) {

        for (int x = -1; x < 2; ++x) {
            for (int y = -1; y < 2; ++y) {
                for (int z = -1; z < 2; ++z) {
                    for (int i = 0; i < howMany; ++i) {
                        final EntityFlameFX fx = new EntityFlameFX(w, blockX + 0.5 + x, blockY + 0.5 + y, blockZ + 0.5 + z, (w.rand.nextDouble() - 0.5) * 0.25, (w.rand.nextDouble() - 0.5) * 0.25, (w.rand.nextDouble() - 0.5) * 0.25);
                        fx.noClip = true;
                        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
                    }
                }
            }
        }
    }

    public void bloodsplosion(World world, double x, double y, double z){
        FXBreaking fx = new FXBreaking(world, x, y + (double)(world.rand.nextFloat() * 2), z, Items.slime_ball);
        if (world.rand.nextBoolean()) {
            fx.setRBGColorF(0.8F, 0.0F, 0.0F);
            fx.setAlphaF(0.4F);
        } else {
            fx.setRBGColorF(0.6F, 0.0F, 0.0F);
            fx.setAlphaF(0.6F);
        }

        fx.motionX = (float)(Math.random() * 2.0 - 1.0);
        fx.motionY = (float)(Math.random() * 2.0 - 1.0);
        fx.motionZ = (float)(Math.random() * 2.0 - 1.0);
        float f = (float)(Math.random() + Math.random() + 1.0) * 0.15F;
        float f1 = MathHelper.sqrt_double(fx.motionX * fx.motionX + fx.motionY * fx.motionY + fx.motionZ * fx.motionZ);
        fx.motionX = fx.motionX / (double)f1 * (double)f * 0.9640000000596046;
        fx.motionY = fx.motionY / (double)f1 * (double)f * 0.9640000000596046 + 0.10000000149011612;
        fx.motionZ = fx.motionZ / (double)f1 * (double)f * 0.9640000000596046;
        fx.setParticleMaxAge((int)(66.0F / (world.rand.nextFloat() * 0.9F + 0.1F)));
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
    }


    public void taintsplosion(World world, double x, double y, double z){
        FXBreaking fx = new FXBreaking(world, x, y + (double)(world.rand.nextFloat()), z, Items.slime_ball);
        if (world.rand.nextBoolean()) {
            fx.setRBGColorF(0.6F, 0.0F, 0.3F);
            fx.setAlphaF(0.4F);
        } else {
            fx.setRBGColorF(0.3F, 0.0F, 0.3F);
            fx.setAlphaF(0.6F);
        }

        fx.motionX = (float)(Math.random() * 2.0 - 1.0);
        fx.motionY = (float)(Math.random() * 2.0 - 1.0);
        fx.motionZ = (float)(Math.random() * 2.0 - 1.0);
        float f = (float)(Math.random() + Math.random() + 1.0) * 0.15F;
        float f1 = MathHelper.sqrt_double(fx.motionX * fx.motionX + fx.motionY * fx.motionY + fx.motionZ * fx.motionZ);
        fx.motionX = fx.motionX / (double)f1 * (double)f * 0.9640000000596046;
        fx.motionY = fx.motionY / (double)f1 * (double)f * 0.9640000000596046 + 0.10000000149011612;
        fx.motionZ = fx.motionZ / (double)f1 * (double)f * 0.9640000000596046;
        fx.setParticleMaxAge((int)(66.0F / (world.rand.nextFloat() * 0.9F + 0.1F)));
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
    }

    /*
    @Override
    public void visCapsuleFX(World world, double x, double y, double z) {
        String s = "iconcrack_" + Item.getIdFromItem(TCItemRegistry.visCapsule) + "_" + 0;

        for (int k1 = 0; k1 < 8; ++k1) {
            Minecraft.getMinecraft().renderGlobal.spawnParticle(s, x, y, z, world.rand.nextGaussian() * 0.15, world.rand.nextDouble() * 0.2, world.rand.nextGaussian() * 0.15);
        }

        world.playSound(x, y, z, "game.potion.smash", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F, false);

    }

     */

    @Override
    public void sparkles(World world, double x, double y, double z) {
        float r = 0.33F + world.rand.nextFloat() * 0.67F;
        float g = 0.33F + world.rand.nextFloat() * 0.67F;
        float b = 0.33F + world.rand.nextFloat() * 0.67F;
        Thaumcraft.proxy.drawGenericParticles(world, (double)((float)x - 0.1F + world.rand.nextFloat() * 1.2F), (double)((float)y - 0.1F + world.rand.nextFloat() * 1.2F), (double)((float)z - 0.1F + world.rand.nextFloat() * 1.2F), 0.0, (double)world.rand.nextFloat() * 0.02, 0.0, r - 0.2F + world.rand.nextFloat() * 0.4F, g - 0.2F + world.rand.nextFloat() * 0.4F, b - 0.2F + world.rand.nextFloat() * 0.4F, 0.9F, false, 112, 9, 1, 5 + world.rand.nextInt(8), world.rand.nextInt(10), 0.7F + world.rand.nextFloat() * 0.4F);
    }
}