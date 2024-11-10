package com.ilya3point999k.thaumicconcilium.common.integration;

import com.emoniph.witchery.infusion.infusions.symbols.StrokeSet;
import com.emoniph.witchery.infusion.infusions.symbols.SymbolEffect;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.DopeSquid;
import com.ilya3point999k.thaumicconcilium.common.entities.GolemBydlo;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.RedPoweredMind;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Thaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.EtherealShacklesEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ShardPowderEntity;
import com.ilya3point999k.thaumicconcilium.common.golems.AssistantGolemCore;
import com.ilya3point999k.thaumicconcilium.common.golems.ValetGolemCore;
import com.ilya3point999k.thaumicconcilium.common.integration.minetweaker.MineTweakerIntegration;
import com.ilya3point999k.thaumicconcilium.common.items.wands.Bracelets;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.forbidden.Config;
import fox.spiteful.forbidden.DarkAspects;
import fox.spiteful.forbidden.compat.Compat;
import fox.spiteful.forbidden.items.ForbiddenItems;
import makeo.gadomancy.api.AuraEffect;
import makeo.gadomancy.api.GadomancyApi;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import org.apache.logging.log4j.Level;
import tb.api.ITobacco;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Integration {
    public static boolean thaumicBases = false;
    public static boolean taintedMagic = false;
    public static boolean gadomancy = false;
    public static boolean horizons = false;
    public static boolean automagy = false;
    public static boolean witchery = false;

    public static boolean minetweaker = false;

    public static Class witcheryClass;
    public static Class witcheryInfusionClass;
    public static Class witcheryEffectRegistryClass;
    public static Class witcheryConstClass;
    public static Class witcheryTimeUtilClass;

    public static Class crystalEye = null;

    public static Item crimsonDagger = null;
    public static boolean allowTobacco = false;
    public static ITobacco tobacco = null;
    public static Item tobaccoitem = null;
    public static Item tobaccoleaves = null;
    public static Item tmMaterial = null;

    public static void init() throws Exception {
        thaumicBases = Loader.isModLoaded("thaumicbases");
        taintedMagic = Loader.isModLoaded("TaintedMagic");
        gadomancy = Loader.isModLoaded("gadomancy");
        horizons = Loader.isModLoaded("ThaumicHorizons");
        automagy = Loader.isModLoaded("Automagy");
        witchery = Loader.isModLoaded("witchery");
        minetweaker = Loader.isModLoaded("MineTweaker3");
        if (!ConfigHandler.enableKami){
            throw new Exception("Thaumic Concilium - turning off KAMI module of Thaumic Tinkerer is not supported.");
        }
        if (gadomancy) {
            if (horizons) {
                GadomancyApi.registerAdditionalGolemCore("assistantCore", new AssistantGolemCore());
            }
            GadomancyApi.registerAdditionalGolemCore("valetCore", new ValetGolemCore());
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

    }

    public static void postInit() {
        if (taintedMagic) {
            ItemStack is = new ItemStack(TCItemRegistry.resource, 1, 3);
            ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(is, 1, 1, 8));
            ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(is, 1, 1, 8));
            ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(is, 1, 1, 8));
            ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(is, 1, 1, 4));
            ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(is, 1, 1, 4));
            ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(is, 1, 1, 4));
            ChestGenHooks.addItem("strongholdLibrary", new WeightedRandomChestContent(is, 1, 1, 10));
            ThaumcraftApi.addLootBagItem(is, 30, 2);
            Item i = GameRegistry.findItem("TaintedMagic", "ItemCrystalDagger");
            if (i == null) {
                i = GameRegistry.findItem("TaintedMagic", "ItemHollowDagger");
            }
            if (i != null) {
                crimsonDagger = i;
            } else {
                ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Tainted Magic's crimson dagger, what a mess");
            }

            tmMaterial = GameRegistry.findItem("TaintedMagic", "ItemMaterial");
            if (tmMaterial == null){
                ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Tainted Magic's material, what a mess");
            }

        }

        if (thaumicBases && taintedMagic) {
            WandCap shadowmetalCap = WandCap.caps.get("shadowmetal");
            WandRod warpwoodRod = StaffRod.rods.get("warpwood");
            Bracelets.caps[6] = shadowmetalCap;
            Bracelets.rods[6] = warpwoodRod;
        }


        if (horizons) {
            if (thaumicBases){
                try {
                    if (Class.forName("tb.utils.TBConfig").getField("allowTobacco").getBoolean(null)) {
                        allowTobacco = true;
                        tobaccoitem = GameRegistry.findItem("thaumicbases", "tobaccoPowder");
                        if (tobaccoitem != null) {
                            tobacco = (ITobacco) tobaccoitem;
                        } else {
                            ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Thaumic Bases's tobacco, what a mess");
                        }
                        tobaccoleaves = GameRegistry.findItem("thaumicbases", "resource");
                        if (tobaccoleaves == null) {
                            ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Thaumic Bases's tobacco leaves, what a mess");
                        }
                    }
                } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }


            Class th = null;
            Class creatureInfusion = null;

            Object recipeList = null;
            try {
                th = Class.forName("com.kentington.thaumichorizons.common.ThaumicHorizons");
                creatureInfusion = Class.forName("com.kentington.thaumichorizons.common.lib.CreatureInfusionRecipe");
                recipeList = th.getDeclaredField("critterRecipes").get(null);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            try {
                EntityRegistry.registerModEntity(MadThaumaturge.class, "MadThaumaturge", 41, th.getField("instance").get(null), 64, 3, true);
                EntityRegistry.registerModEntity(GolemBydlo.class, "GolemBydlo", 42, th.getField("instance").get(null), 64, 3, true);
                EntityRegistry.registerModEntity(RedPoweredMind.class, "RedPoweredMind", 43, th.getField("instance").get(null), 64, 3, true);
                EntityRegistry.registerModEntity(DopeSquid.class, "DopeSquid", 44, th.getField("instance").get(null), 64, 3, true);

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

            Item golemPowder;
            try {
                golemPowder = Compat.getItem("ThaumicHorizons", "golemPowder");
            } catch (Compat.ItemNotFoundException e) {
                throw new RuntimeException(e);
            }


            Object buffGolemRecipe = null;
            Object brainLitteringRecipe = null;
            try {
                buffGolemRecipe = creatureInfusion.getConstructors()[0].newInstance("BUFFGOLEM", 42, (int) 4, new AspectList().add(Aspect.ENERGY, 64).add(Aspect.METAL, 128).add(DarkAspects.PRIDE, 32).add(Aspect.ENTROPY, 128),
                        MadThaumaturge.class, new ItemStack[]{
                                new ItemStack(ConfigItems.itemGolemPlacer, 1, 7),
                                new ItemStack(golemPowder),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 5),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                                new ItemStack(ConfigItems.itemGolemPlacer, 1, 7),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 9),
                                new ItemStack(ConfigItems.itemGolemPlacer, 1, 7),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 5),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                                new ItemStack(golemPowder)
                        }, (int) 0);
                brainLitteringRecipe = creatureInfusion.getConstructors()[0].newInstance("BRAINLITTERING", 41, 4, new AspectList().add(Aspect.ENTROPY, 64).add(Aspect.ELDRITCH, 64).add(Aspect.MIND, 64),
                        Thaumaturge.class, new ItemStack[]{
                                new ItemStack(ConfigItems.itemResource, 1, 9),
                                new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                                new ItemStack(ConfigItems.itemZombieBrain),
                                new ItemStack(ForbiddenItems.deadlyShards, 1, 2)
                        }, 0);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            ((ArrayList<Object>) recipeList).add(buffGolemRecipe);
            ((ArrayList<Object>) recipeList).add(brainLitteringRecipe);
            if (automagy) {
                Object redPoweredMindRecipe = null;
                try {
                    redPoweredMindRecipe = creatureInfusion.getConstructors()[0].newInstance("REDPOWEREDMIND", 43, 4, new AspectList().add(Aspect.CRYSTAL, 64).add(Aspect.MIND, 64).add(Aspect.SENSES, 32).add(Aspect.MECHANISM, 32),
                            MadThaumaturge.class, new ItemStack[]{
                                    new ItemStack(ConfigItems.itemBucketDeath),
                                    new ItemStack(Items.repeater),
                                    new ItemStack(Blocks.redstone_block),
                                    new ItemStack(Blocks.redstone_block),
                                    new ItemStack(Blocks.redstone_block),
                                    new ItemStack(ConfigItems.itemBucketDeath),
                                    new ItemStack(ConfigItems.itemResource, 1, 17),
                                    new ItemStack(ConfigItems.itemBucketDeath),
                                    new ItemStack(Blocks.redstone_block),
                                    new ItemStack(Blocks.redstone_block),
                                    new ItemStack(Blocks.redstone_block),
                                    new ItemStack(Items.comparator)
                            }, 0);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                ((ArrayList<Object>) recipeList).add(redPoweredMindRecipe);

                try {
                    crystalEye = Class.forName("tuhljin.automagy.items.ItemCrystalEye");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            if (thaumicBases && allowTobacco){
                Object dopeSquidRecipe = null;
                try {
                    dopeSquidRecipe = creatureInfusion.getConstructors()[0].newInstance("DOPESQUID", 44, 4, new AspectList().add(Aspect.PLANT, 64).add(Aspect.MAGIC, 64).add(Aspect.MAN, 32).add(Aspect.FLIGHT, 64),
                            EntitySquid.class, new ItemStack[]{
                                    new ItemStack(ConfigItems.itemShard, 1, 0),
                                    new ItemStack(tobaccoitem, 1, 1),
                                    new ItemStack(tobaccoitem, 1, 2),
                                    new ItemStack(tobaccoitem, 1, 3),
                                    new ItemStack(tobaccoitem, 1, 4),
                                    new ItemStack(ConfigItems.itemShard, 1, 1),
                                    new ItemStack(tobaccoitem, 1, 5),
                                    new ItemStack(tobaccoitem, 1, 6),
                                    new ItemStack(tobaccoitem, 1, 7),
                                    new ItemStack(tobaccoitem, 1, 8)
                            }, 0);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                ((ArrayList<Object>) recipeList).add(dopeSquidRecipe);

            }

        }

        if (witchery) {
            try {
                witcheryClass = Class.forName("com.emoniph.witchery.Witchery");
                witcheryInfusionClass = Class.forName("com.emoniph.witchery.infusion.Infusion");
                witcheryEffectRegistryClass = Class.forName("com.emoniph.witchery.infusion.infusions.symbols.EffectRegistry");
                witcheryConstClass = Class.forName("com.emoniph.witchery.util.Const");
                witcheryTimeUtilClass = Class.forName("com.emoniph.witchery.util.TimeUtil");

                final Object instance = witcheryEffectRegistryClass.getDeclaredMethod("instance").invoke(null);
                Method addEffect = instance.getClass().getMethod("addEffect", SymbolEffect.class, StrokeSet[].class);
                addEffect.invoke(instance, new SymbolEffect(90, "tc.spell.incarcerous", 2, true, false, "incarcerous", 0) {
                    @Override
                    public void perform(World world, EntityPlayer player, int level) {
                        int inf = -1;
                        try {
                            inf = (int) witcheryInfusionClass.getMethod("getInfusionID", EntityPlayer.class).invoke(instance, player);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                        if (inf == 4) {
                            EtherealShacklesEntity shackles = new EtherealShacklesEntity(world);
                            shackles.setLocationAndAngles(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
                            shackles.motionX = (double) (-MathHelper.sin(shackles.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(shackles.rotationPitch / 180.0F * (float) Math.PI));
                            shackles.motionZ = (double) (MathHelper.cos(shackles.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(shackles.rotationPitch / 180.0F * (float) Math.PI));
                            shackles.motionY = (double) (-MathHelper.sin(shackles.rotationPitch / 180.0F * (float) Math.PI));
                            for (int i = 0; i < 3; i++) {
                                shackles.moveEntity(shackles.motionX, shackles.motionY, shackles.motionZ);
                            }

                            shackles.setThrowableHeading(shackles.motionX, shackles.motionY, shackles.motionZ, 2.0F, 2.0F);
                            world.spawnEntityInWorld(shackles);
                            shackles.playSound(ThaumicConcilium.MODID+":shackles_throw", 0.5F, 1.0F);
                        } else {
                            int orbs = 2 + player.worldObj.rand.nextInt(3);
                            Vec3 look = player.getLookVec();
                            world.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)player.posX, (int)player.posY, (int)player.posZ, 0);
                            for (int i = 0; i < orbs; i++) {
                                ShardPowderEntity orb = new ShardPowderEntity(player, player.posX + (look.xCoord) , player.posY + player.getEyeHeight() + (look.yCoord), player.posZ + (look.zCoord), 12);
                                orb.posX -= (double) (Math.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                                orb.posY -= 0.5;
                                orb.posZ -= (double) (Math.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                                orb.setPosition(orb.posX, orb.posY, orb.posZ);
                                orb.yOffset = 0.0F;
                                float f = 0.4F;
                                orb.motionX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                                orb.motionZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                                orb.motionY = (double)(-MathHelper.sin((player.rotationPitch) / 180.0F * (float)Math.PI) * f);
                                float f2 = MathHelper.sqrt_double(orb.motionX * orb.motionX + orb.motionY * orb.motionY + orb.motionZ * orb.motionZ);
                                orb.motionX /= f2;
                                orb.motionY /= f2;
                                orb.motionZ /= f2;
                                orb.motionX += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                                orb.motionY += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                                orb.motionZ += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                                player.worldObj.spawnEntityInWorld(orb);
                            }
                        }

                    }
                }, new StrokeSet[]{new StrokeSet(new byte[]{2, 2, 2, 0, 3, 3, 1})});

                addEffect.invoke(instance, new SymbolEffect(91, "tc.spell.arrow", 2, false, false, "arrowspell", 0) {
                    @Override
                    public void perform(World world, EntityPlayer player, int level) {
                        int inf = -1;
                        try {
                            inf = (int) witcheryInfusionClass.getMethod("getInfusionID", EntityPlayer.class).invoke(instance, player);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                        if (inf == 2) {
                            int weapon = Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(player.getCommandSenderName(), Aspect.WEAPON);
                            EntityArrow entityarrow = new EntityArrow(world, player, 2.0F);
                            entityarrow.setIsCritical(true);
                            double dmg = Math.sqrt(weapon) * 1.5;
                            entityarrow.setDamage(1 + dmg);
                            entityarrow.canBePickedUp = 2;
                            world.spawnEntityInWorld(entityarrow);
                        } else {
                            EntityArrow entityarrow = new EntityArrow(world, player, 2.0F);
                            entityarrow.setIsCritical(true);
                            entityarrow.setDamage(entityarrow.getDamage() + 5.0D);
                            entityarrow.canBePickedUp = 2;
                            world.spawnEntityInWorld(entityarrow);
                        }
                    }
                }, new StrokeSet[]{new StrokeSet(new byte[]{1, 1, 1, 2})});

                addEffect.invoke(instance, new SymbolEffect(92, "tc.spell.spider", 2, false, false, "spiderspell", 0) {
                    @Override
                    public void perform(World world, EntityPlayer player, int level) {
                        int inf = -1;
                        try {
                            inf = (int) witcheryInfusionClass.getMethod("getInfusionID", EntityPlayer.class).invoke(instance, player);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                        Entity e = EntityUtils.getPointedEntity(world, player, 1.0, 32.0, 1.0F);
                        if (e instanceof EntityMob) {
                            EntityMob mob = (EntityMob) e;
                            if (mob.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                                if (!e.isDead) {
                                    player.worldObj.playSoundAtEntity(player,"thaumcraft:craftfail", 1.0F, 1.0F);
                                    TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) player.posX, (float) (player.posY + player.height - 0.5), (float) player.posZ, (float) e.posX, (float) (e.posY + e.height / 2), (float) e.posZ, 0xFFFFFF, 0.02F), new NetworkRegistry.TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));
                                    if (inf == 1) {
                                        int light = Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(player.getCommandSenderName(), Aspect.LIGHT);
                                        double dmg = Math.sqrt(light) * 2.5;
                                        e.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), (float) (1 + dmg));
                                    } else {
                                        e.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), world.rand.nextInt(30) + level * 2);
                                    }
                                }
                            }
                        }
                    }
                }, new StrokeSet[]{new StrokeSet(new byte[]{0, 0, 0, 2, 1, 1, 0, 0})});

                addEffect.invoke(instance, new SymbolEffect(93, "tc.spell.undead", 2, false, false, "spellundead", 0) {
                    @Override
                    public void perform(World world, EntityPlayer player, int level) {
                        int inf = -1;
                        try {
                            inf = (int) witcheryInfusionClass.getMethod("getInfusionID", EntityPlayer.class).invoke(instance, player);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }

                        Entity e = EntityUtils.getPointedEntity(world, player, 1.0, 32.0, 1.0F);
                        if (e instanceof EntityMob) {
                            EntityMob mob = (EntityMob) e;
                            if (mob.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
                                if (!e.isDead) {
                                    player.worldObj.playSoundAtEntity(player,"thaumcraft:craftfail", 1.0F, 1.0F);
                                    TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) player.posX, (float) (player.posY + player.height - 0.5), (float) player.posZ, (float) e.posX, (float) (e.posY + e.height / 2), (float) e.posZ, 0xFFFFFF, 0.02F), new NetworkRegistry.TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));
                                    if (inf == 1) {
                                        int light = Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(player.getCommandSenderName(), Aspect.LIGHT);
                                        double dmg = Math.sqrt(light) * 2.5;
                                        e.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), (float) (1 + dmg));
                                    } else {
                                        e.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), world.rand.nextInt(30) + level * 2);
                                    }
                                }
                            }
                        }
                    }
                }, new StrokeSet[]{new StrokeSet(1, new byte[]{1, 1, 1, 0, 3, 2, 2}), new StrokeSet(3, new byte[]{1, 1, 1, 1, 1, 0, 0, 3, 3, 2, 2, 2, 2})});

                addEffect.invoke(instance, new SymbolEffect(94, "tc.spell.blind", 2, false, false, "conjunctivitis", 0) {
                    @Override
                    public void perform(World world, EntityPlayer player, int level) {
                        int inf = -1;
                        try {
                            inf = (int) witcheryInfusionClass.getMethod("getInfusionID", EntityPlayer.class).invoke(instance, player);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                        int dur = 200;
                        if (inf == 4) {
                            int darkness = Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(player.getCommandSenderName(), Aspect.DARKNESS);
                            dur += darkness;
                        }
                        Entity e = EntityUtils.getPointedEntity(world, player, 1.0, 32.0, 1.0F);
                        if (e instanceof EntityLiving) {
                            player.worldObj.playSoundAtEntity(player,"thaumcraft:craftfail", 1.0F, 1.0F);
                            PotionEffect effect = new PotionEffect(thaumcraft.common.config.Config.potionBlurredID, dur + 200, 60);
                            effect.getCurativeItems().clear();
                            ((EntityLivingBase) e).addPotionEffect(effect);
                            if (inf == 4) {
                                PotionEffect effect1 = new PotionEffect(Potion.blindness.id, dur, 60);
                                effect1.getCurativeItems().clear();
                                ((EntityLivingBase) e).addPotionEffect(effect1);
                            }
                        }
                    }
                }, new StrokeSet[]{new StrokeSet(new byte[]{0, 0, 3, 3, 1, 1, 2, 2})});
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        if(minetweaker) {
            MineTweakerIntegration.register();
            ThaumicConcilium.logger.info("MineTweaker integration loaded");
        }
    }
}
