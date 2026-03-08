package com.ilya3point999k.thaumicconcilium.common.integration;

import WayofTime.alchemicalWizardry.common.entity.projectile.EntityMeteor;
import WayofTime.alchemicalWizardry.common.summoning.meteor.MeteorRegistry;
import am2.api.math.AMVector3;
import am2.entities.EntityThrownRock;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Overanimated;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.ThaumGib;
import com.ilya3point999k.thaumicconcilium.common.golems.AssistantGolemCore;
import com.ilya3point999k.thaumicconcilium.common.golems.ValetGolemCore;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TCFociUpgrades;
import fox.spiteful.forbidden.Config;
import fox.spiteful.forbidden.DarkAspects;
import fox.spiteful.forbidden.compat.Compat;
import makeo.gadomancy.api.AuraEffect;
import makeo.gadomancy.api.GadomancyApi;
import makeo.gadomancy.common.Gadomancy;
import makeo.gadomancy.common.aura.AuraResearchManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.entities.projectile.EntityExplosiveOrb;
import thaumic.tinkerer.common.core.helper.MiscHelper;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.Botania;

import java.util.*;
import java.util.function.BiConsumer;

public class GadomancyIntegration {

    public static ArrayList<BiConsumer<World, ChunkCoordinates>> meteors;

    public static void register() {
        if (Integration.horizons) {
            GadomancyApi.registerAdditionalGolemCore("assistantCore", new AssistantGolemCore());
        }
        GadomancyApi.registerAdditionalGolemCore("valetCore", new ValetGolemCore());
        //GadomancyApi.registerAdditionalGolemCore("burlakCore", new BurlakGolemCore());
        final AuraEffect INFERNUS = new AuraEffect.EntityAuraEffect() {
            @Override
            public boolean isEntityApplicable(Entity e) {
                return e instanceof EntityItem;
            }

            @Override
            public void doEntityEffect(ChunkCoordinates originTile, Entity e) {
                if (!e.worldObj.isRemote) {
                    EntityItem entityItem = (EntityItem) e;
                    if (FurnaceRecipes.smelting().getSmeltingResult(entityItem.getEntityItem()) != null) {
                        final ItemStack stacky = FurnaceRecipes.smelting().getSmeltingResult(entityItem.getEntityItem());
                        stacky.stackSize = entityItem.getEntityItem().stackSize;
                        entityItem.setEntityItemStack(stacky);
                    }
                }
            }

            @Override
            public int getTickInterval() {
                return 5;
            }
        }.register(DarkAspects.NETHER);

        if (!Config.noLust) {
            final AuraEffect LUXURIA = new AuraEffect.EntityAuraEffect() {
                @Override
                public boolean isEntityApplicable(Entity e) {
                    return e instanceof EntityAgeable;
                }

                @Override
                public void doEntityEffect(ChunkCoordinates originTile, Entity e) {
                    if (!e.worldObj.isRemote) {
                        EntityAgeable ageable = (EntityAgeable) e;
                        if (ageable.getGrowingAge() > 0) {
                            ageable.setGrowingAge(0);
                        }
                    }
                }

                @Override
                public int getTickInterval() {
                    return 40;
                }
            }.register(DarkAspects.LUST);
        }

        final AuraEffect GULA = new AuraEffect.EntityAuraEffect() {
            @Override
            public boolean isEntityApplicable(Entity e) {
                return e instanceof EntityPlayer;
            }

            @Override
            public void doEntityEffect(ChunkCoordinates originTile, Entity e) {
                if (!e.worldObj.isRemote) {
                    EntityPlayer player = (EntityPlayer) e;
                    player.getFoodStats().addStats(1, 1.0F);
                }
            }

            @Override
            public int getTickInterval() {
                return 20;
            }
        }.register(DarkAspects.GLUTTONY);

        final AuraEffect IRA = new AuraEffect.EntityAuraEffect() {
            @Override
            public boolean isEntityApplicable(Entity e) {
                return e instanceof EntityMob;
            }

            @Override
            public void doEntityEffect(ChunkCoordinates originTile, Entity e) {
                if (!e.worldObj.isRemote) {
                    List<EntityLiving> list = e.worldObj.getEntitiesWithinAABB(EntityLiving.class, e.boundingBox.expand(8, 8, 8));
                    list.remove(e);
                    if (!list.isEmpty()) {
                        Collections.shuffle(list);
                        ((EntityLivingBase) e).setRevengeTarget(list.get(0));
                    }
                }
            }

            @Override
            public int getTickInterval() {
                return 80;
            }
        }.register(DarkAspects.WRATH);

        final AuraEffect INVIDIA = new AuraEffect.EntityAuraEffect() {
            @Override
            public boolean isEntityApplicable(Entity e) {
                return e instanceof EntityLivingBase;
            }

            @Override
            public void doEntityEffect(ChunkCoordinates originTile, Entity e) {
                if (!e.worldObj.isRemote) {
                    if (e instanceof EntityPlayer) {
                        return;
                    }
                    EntityLivingBase living = (EntityLivingBase) e;
                    ItemStack stack = living.getHeldItem();
                    if (stack != null) {
                        living.entityDropItem(living.getHeldItem(), 0.5F);
                        living.setCurrentItemOrArmor(0, null);
                    }
                }
            }

            @Override
            public int getTickInterval() {
                return 80;
            }
        }.register(DarkAspects.ENVY);

        final AuraEffect DESIDIA = new AuraEffect.EntityAuraEffect() {
            @Override
            public boolean isEntityApplicable(Entity e) {
                return e instanceof EntityLivingBase;
            }

            @Override
            public void doEntityEffect(ChunkCoordinates originTile, Entity e) {
                if (!e.worldObj.isRemote) {

                    EntityLivingBase living = (EntityLivingBase) e;
                    Collection<PotionEffect> potions = living.getActivePotionEffects();
                    if (!potions.isEmpty()) {
                        for (PotionEffect potion : potions) {
                            living.addPotionEffect(new PotionEffect(potion.getPotionID(), potion.getDuration() + 20, potion.getAmplifier(), potion.getIsAmbient()));
                        }
                    }
                }
            }

            @Override
            public int getTickInterval() {
                return 4;
            }
        }.register(DarkAspects.SLOTH);

        final AuraEffect SUPERBIA = new AuraEffect.EntityAuraEffect() {
            @Override
            public boolean isEntityApplicable(Entity e) {
                return e instanceof EntityMob;
            }

            @Override
            public double getRange() {
                return 32;
            }

            @Override
            public void doEntityEffect(ChunkCoordinates originTile, Entity e) {
                if (!e.worldObj.isRemote) {
                    if (e.getDistanceSq(originTile.posX, originTile.posY, originTile.posZ) < 1024) {
                        Vector3 target = new Vector3(e.posX - originTile.posX, e.posY, e.posZ - originTile.posZ).multiply(2.0, 1.0, 2.0);
                        MiscHelper.setEntityMotionFromVector(e, target, 1.0F);
                    }
                }
            }
        }.register(DarkAspects.PRIDE);

    }

    public static void postRegister(){
        if (Integration.alfheim && Integration.tincturaAllowed){
            final AuraEffect TINCTURA = new AuraEffect() {
                @Override
                public EffectType getEffectType() {
                    return null;
                }

                @Override
                public boolean isEntityApplicable(Entity entity) {
                    return entity instanceof EntityLivingBase;
                }

                @Override
                public void doEntityEffect(ChunkCoordinates chunkCoordinates, Entity entity) {
                    if (Integration.tintPotionId != -1 && !entity.worldObj.isRemote){
                        EntityLivingBase living = (EntityLivingBase) entity;
                        living.addPotionEffect(new PotionEffect(Integration.tintPotionId, 1200, entity.worldObj.rand.nextInt(16), true));
                    }
                }

                @Override
                public int getBlockCount(Random random) {
                    return 10;
                }

                @Override
                public int getTickInterval() {
                    return 20;
                }

                @Override
                public void doBlockEffect(ChunkCoordinates originTile, ChunkCoordinates selectedBlock, World world) {
                    Block block = world.getBlock(selectedBlock.posX, selectedBlock.posY, selectedBlock.posZ);
                    if (BotaniaAPI.paintableBlocks.contains(block)) {
                        int meta = world.getBlockMetadata(selectedBlock.posX, selectedBlock.posY, selectedBlock.posZ);
                        int newColor = world.rand.nextInt(16);
                        if (meta != newColor) {
                            if (!world.isRemote) {
                                world.setBlockMetadataWithNotify(selectedBlock.posX, selectedBlock.posY, selectedBlock.posZ, newColor, 2);
                            }
                            float[] color = EntitySheep.fleeceColorTable[newColor];
                            float r = color[0];
                            float g = color[1];
                            float b = color[2];
                            for (int i = 0; i < 4; i++)
                                Botania.proxy.sparkleFX(world, selectedBlock.posX + (float) Math.random(), selectedBlock.posY + (float) Math.random(), selectedBlock.posZ + (float) Math.random(), r, g, b, 0.6F + (float) Math.random() * 0.3F, 5);
                        }
                    }
                }
            }.register(Integration.tinctura);
            new ResearchItem(String.format(AuraResearchManager.TC_AURA_RESEARCH_STR, Integration.tinctura.getTag()), Gadomancy.MODID).registerResearchItem();
        }

        if (Integration.avaritia){
            meteors = new ArrayList<>();
            meteors.add(GadomancyIntegration::spawnStoneShower);
            if (Compat.bm) {
                meteors.add(GadomancyIntegration::spawnBMMeteor);
            }
            if (Compat.am2){
                meteors.add(GadomancyIntegration::spawnAM2Meteor);
                meteors.add(GadomancyIntegration::spawnAM2Star);
            }
            if (Integration.horizons){
                meteors.add(GadomancyIntegration::spawnBrethrenMoon);
            }

            final AuraEffect TERMINUS = new AuraEffect.BlockAuraEffect() {
                @Override
                public int getBlockCount(Random random) {
                    return 1;
                }

                @Override
                public double getRange() {
                    return 32.0;
                }

                @Override
                public int getTickInterval() {
                    return 600;
                }

                @Override
                public void doBlockEffect(ChunkCoordinates chunkCoordinates, ChunkCoordinates selectedBlock, World world) {
                    if (!world.isRemote) {
                        meteors.get(world.rand.nextInt(meteors.size())).accept(world, selectedBlock);
                    }
                }
            }.register(Aspect.getAspect("terminus"));
            new ResearchItem(String.format(AuraResearchManager.TC_AURA_RESEARCH_STR, "terminus"), Gadomancy.MODID).registerResearchItem();
        }
    }

    public static void spawnBMMeteor(World world, ChunkCoordinates pos){
        int meteorID = MeteorRegistry.getParadigmIDForItem(new ItemStack(Blocks.stone));
        EntityMeteor meteor = new EntityMeteor(world, pos.posX + 0.5f, 257, pos.posZ + 0.5f, meteorID);
        meteor.motionY = -1.0f;
        world.spawnEntityInWorld(meteor);
    }

    public static void spawnAM2Meteor(World world, ChunkCoordinates pos){
        EntityThrownRock meteor = new EntityThrownRock(world);
        meteor.setPosition(pos.posX + (-8 + world.rand.nextInt(16)), world.getActualHeight(), pos.posZ + (-8 + world.rand.nextInt(16)));
        meteor.setMoonstoneMeteor();
        meteor.setMoonstoneMeteorTarget(new AMVector3(pos.posX, pos.posY, pos.posZ));
        world.spawnEntityInWorld(meteor);
    }
    public static void spawnAM2Star(World world, ChunkCoordinates pos){
        EntityThrownRock meteor = new EntityThrownRock(world);
        meteor.setPosition(pos.posX + (-8 + world.rand.nextInt(16)), world.getActualHeight(), pos.posZ + (-8 + world.rand.nextInt(16)));
        meteor.setShootingStar(50);
        world.spawnEntityInWorld(meteor);
    }

    public static void spawnBrethrenMoon(World world, ChunkCoordinates pos){
        for (int i = 0; i < 5; i++) {
            Overanimated overanimated = new Overanimated(world);
            overanimated.func_110163_bv();
            overanimated.setPositionAndRotation(pos.posX + (-2 + (world.rand.nextGaussian() * 4)), world.getActualHeight(), pos.posZ + (-2 + (world.rand.nextGaussian() * 4)), 0, 0);
            world.spawnEntityInWorld(overanimated);
        }
    }

    public static void spawnStoneShower(World world, ChunkCoordinates pos){
        for (int i = 0; i < 10; i++){
            EntityExplosiveOrb orb = new EntityExplosiveOrb(world);
            orb.setPosition(pos.posX, world.getActualHeight(), pos.posZ);
            orb.motionX += (-0.5 + world.rand.nextGaussian()) * 0.1;
            orb.motionY = -0.1;
            orb.motionZ += (-0.5 + world.rand.nextGaussian()) * 0.1;
            orb.strength = 20;
            orb.onFire = true;
            world.spawnEntityInWorld(orb);
        }
    }
}
