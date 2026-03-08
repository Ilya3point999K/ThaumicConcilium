package com.ilya3point999k.thaumicconcilium.common.integration;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.infusion.Infusion;
import com.emoniph.witchery.infusion.infusions.symbols.EffectRegistry;
import com.emoniph.witchery.infusion.infusions.symbols.StrokeSet;
import com.emoniph.witchery.infusion.infusions.symbols.SymbolEffect;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.EtherealShacklesEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ShardPowderEntity;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import cpw.mods.fml.common.network.NetworkRegistry;
import fox.spiteful.forbidden.compat.Compat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.utils.EntityUtils;

public class WitcheryIntegration {
    public static void register() {
        try {
            Integration.witcheryClass = Class.forName("com.emoniph.witchery.Witchery");
            Integration.witcheryInfusionClass = Class.forName("com.emoniph.witchery.infusion.Infusion");
            Integration.earmuffs = Compat.getItem("witchery", "earmuffs");
            Integration.witchery_ingredient = Compat.getItem("witchery", "ingredient");
            Integration.tintPotionId = Witchery.Potions.COLORFUL.getId();
            EffectRegistry.instance().addEffect(new SymbolEffect(90, "tc.spell.incarcerous", 2, true, false, "incarcerous", 0) {
                @Override
                public void perform(World world, EntityPlayer player, int level) {
                    int inf = Infusion.getInfusionID(player);

                    if (inf == 4) {
                        EtherealShacklesEntity shackles = new EtherealShacklesEntity(world);
                        shackles.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
                        shackles.motionX = -MathHelper.sin(shackles.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(shackles.rotationPitch / 180.0F * (float) Math.PI);
                        shackles.motionZ = MathHelper.cos(shackles.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(shackles.rotationPitch / 180.0F * (float) Math.PI);
                        shackles.motionY = -MathHelper.sin(shackles.rotationPitch / 180.0F * (float) Math.PI);
                        for (int i = 0; i < 3; i++) shackles.moveEntity(shackles.motionX, shackles.motionY, shackles.motionZ);
                        shackles.setThrowableHeading(shackles.motionX, shackles.motionY, shackles.motionZ, 2.0F, 2.0F);
                        world.spawnEntityInWorld(shackles);
                        shackles.playSound(ThaumicConcilium.MODID + ":shackles_throw", 0.5F, 1.0F);
                    } else {
                        int orbs = 2 + player.worldObj.rand.nextInt(3);
                        Vec3 look = player.getLookVec();
                        world.playAuxSFXAtEntity(null, 1008, (int) player.posX, (int) player.posY, (int) player.posZ, 0);
                        for (int i = 0; i < orbs; i++) {
                            ShardPowderEntity orb = new ShardPowderEntity(player, player.posX + look.xCoord, player.posY + player.getEyeHeight() + look.yCoord, player.posZ + look.zCoord, 12);
                            orb.posX -= Math.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
                            orb.posY -= 0.5;
                            orb.posZ -= Math.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
                            orb.setPosition(orb.posX, orb.posY, orb.posZ);
                            orb.yOffset = 0.0F;
                            float f = 0.4F;
                            orb.motionX = -MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
                            orb.motionZ = MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * f;
                            orb.motionY = -MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI) * f;
                            float f2 = MathHelper.sqrt_double(orb.motionX * orb.motionX + orb.motionY * orb.motionY + orb.motionZ * orb.motionZ);
                            orb.motionX /= f2;
                            orb.motionY /= f2;
                            orb.motionZ /= f2;
                            orb.motionX += orb.worldObj.rand.nextGaussian() * 0.0075D * 12;
                            orb.motionY += orb.worldObj.rand.nextGaussian() * 0.0075D * 12;
                            orb.motionZ += orb.worldObj.rand.nextGaussian() * 0.0075D * 12;
                            player.worldObj.spawnEntityInWorld(orb);
                        }
                    }

                }
            }, new StrokeSet(new byte[]{2, 2, 2, 0, 3, 3, 1}));

            EffectRegistry.instance().addEffect(new SymbolEffect(91, "tc.spell.arrow", 2, false, false, "arrowspell", 0) {
                @Override
                public void perform(World world, EntityPlayer player, int i) {
                    int inf = Infusion.getInfusionID(player);
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
            }, new StrokeSet(new byte[]{1, 1, 1, 3}));

            EffectRegistry.instance().addEffect(new SymbolEffect(92, "tc.spell.spider", 2, false, false, "spiderspell", 0) {
                @Override
                public void perform(World world, EntityPlayer player, int level) {
                    int inf = Infusion.getInfusionID(player);
                    Entity e = EntityUtils.getPointedEntity(world, player, 1.0, 32.0, 1.0F);
                    if (e instanceof EntityMob) {
                        EntityMob mob = (EntityMob) e;
                        if (mob.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                            if (!e.isDead) {
                                player.worldObj.playSoundAtEntity(player, "thaumcraft:craftfail", 1.0F, 1.0F);
                                TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning(
                                        (float) player.posX, (float) (player.posY + player.height - 0.5),
                                        (float) player.posZ, (float) e.posX, (float) (e.posY + e.height / 2),
                                        (float) e.posZ, 0xFFFFFF, 0.02F
                                ), new NetworkRegistry.TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));

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
            }, new StrokeSet(new byte[]{0, 0, 0, 2, 1, 1, 0, 0}));

            EffectRegistry.instance().addEffect(new SymbolEffect(93, "tc.spell.undead", 2, false, false, "spellundead", 0) {
                @Override
                public void perform(World world, EntityPlayer player, int level) {
                    int inf = Infusion.getInfusionID(player);
                    Entity e = EntityUtils.getPointedEntity(world, player, 1.0, 32.0, 1.0F);
                    if (e instanceof EntityMob) {
                        EntityMob mob = (EntityMob) e;
                        if (mob.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
                            if (!e.isDead) {
                                player.worldObj.playSoundAtEntity(player, "thaumcraft:craftfail", 1.0F, 1.0F);
                                TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning(
                                        (float) player.posX, (float) (player.posY + player.height - 0.5),
                                        (float) player.posZ, (float) e.posX, (float) (e.posY + e.height / 2),
                                        (float) e.posZ, 0xFFFFFF, 0.02F
                                ), new NetworkRegistry.TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));

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
            }, new StrokeSet(1, new byte[]{1, 1, 1, 0, 3, 2, 2}), new StrokeSet(2, new byte[]{1, 1, 1, 1, 1, 0, 0, 3, 3, 2, 2, 2, 2}));

            EffectRegistry.instance().addEffect(new SymbolEffect(94, "tc.spell.blind", 2, false, false, "conjunctivitis", 0) {
                @Override
                public void perform(World world, EntityPlayer player, int i) {
                    int inf = Infusion.getInfusionID(player);
                    int dur = 200;
                    if (inf == 4) {
                        int darkness = Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(player.getCommandSenderName(), Aspect.DARKNESS);
                        dur += darkness;
                    }

                    Entity e = EntityUtils.getPointedEntity(world, player, 1.0, 32.0, 1.0F);
                    if (e instanceof EntityLiving) {
                        player.worldObj.playSoundAtEntity(player, "thaumcraft:craftfail", 1.0F, 1.0F);
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
            }, new StrokeSet(new byte[]{0, 0, 3, 3, 1, 1, 2, 2}));

        } catch (ClassNotFoundException | Compat.ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}