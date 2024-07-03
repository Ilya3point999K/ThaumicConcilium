package com.ilya3point999k.thaumicconcilium.common.events;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import baubles.common.lib.PlayerHandler;
import com.ilya3point999k.thaumicconcilium.api.ThaumicConciliumApi;
import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.RiftEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.CrimsonPaladin;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Dissolved;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.golems.ValetGolemCore;
import com.ilya3point999k.thaumicconcilium.common.golems.ai.GolemChatHandler;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.items.AstralMonitor;
import com.ilya3point999k.thaumicconcilium.common.items.RiftGem;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.PontifexRobe;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TCFociUpgrades;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TaintAnimationFoci;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.VisConductorFoci;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketUpdatePartyStatus;
import com.ilya3point999k.thaumicconcilium.common.tiles.DestabilizedCrystalTile;
import com.ilya3point999k.thaumicconcilium.common.tiles.LithographerTile;
import com.ilya3point999k.thaumicconcilium.common.tiles.RedPoweredMindTile;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import fox.spiteful.forbidden.compat.Compat;
import makeo.gadomancy.api.GadomancyApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.entities.monster.EntityCultistKnight;
import thaumcraft.common.entities.monster.EntityGiantBrainyZombie;
import thaumcraft.common.items.ItemWispEssence;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.FakeThaumcraftPlayer;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.research.ResearchNoteData;
import thaumcraft.common.lib.research.ScanManager;
import thaumcraft.common.tiles.TileInfusionMatrix;
import thaumcraft.common.tiles.TileNode;
import thaumcraft.common.tiles.TilePedestal;
import vazkii.botania.api.mana.IManaItem;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;

public class TCEntityEventHandler {
    public static final HashSet<String> ethereals = new HashSet<>();

    @SubscribeEvent
    public void onEntityConstruction(EntityEvent.EntityConstructing event) {
        if ((event.entity instanceof EntityPlayer && !(event.entity instanceof FakePlayer) && !(event.entity instanceof FakeThaumcraftPlayer))) {
            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get((EntityPlayer) event.entity);
            if (capabilities == null) {
                TCPlayerCapabilities.register((EntityPlayer) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onClonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone e) {
        EntityPlayer player = e.entityPlayer;
        if (!e.wasDeath || player instanceof FakePlayer || player instanceof FakeThaumcraftPlayer)
            return;
        NBTTagCompound compound = new NBTTagCompound();
        TCPlayerCapabilities original = TCPlayerCapabilities.get(e.original);
        original.saveNBTData(compound);
        TCPlayerCapabilities clone = TCPlayerCapabilities.get(e.entityPlayer);
        clone.loadNBTData(compound);
    }


    @SubscribeEvent
    public void on(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.player.worldObj.isRemote) {
            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(event.player);
            capabilities.sync(event.player);
        }
    }

    @SubscribeEvent
    public void on(LivingSetAttackTargetEvent event) {
        if (event.entityLiving instanceof ITaintedMob && !(event.entity instanceof IBossDisplayData)) {
            if (event.target instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.target;
                ItemStack stack = player.getHeldItem();
                if (stack != null) {
                    if (stack.getItem() instanceof ItemWandCasting) {
                        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
                        if (wand.getFocus(stack) instanceof TaintAnimationFoci) {
                            ((EntityLiving) event.entityLiving).setAttackTarget(null);
                        }
                    }
                }
            }
        }
        if (event.entityLiving instanceof EntityCultist && event.target instanceof EntityPlayer) {
            if (PontifexRobe.isFullSet((EntityPlayer) event.target)) {
                ((EntityLiving) event.entityLiving).setAttackTarget(null);
            }
        }
    }

    @SubscribeEvent
    public void on(ItemTooltipEvent event) {
        if (event.itemStack.getItem() instanceof ItemWandCasting) {
            NBTTagCompound tag = event.itemStack.getTagCompound();
            if (tag != null) {
                String name = tag.getString("Xylography");
                if (name != null && !name.isEmpty()) {
                    event.toolTip.add(StatCollector.translateToLocal("tc.tooltip.xylography") + " " + name);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() == null) return;
        TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
        if (tile instanceof TileNode) {
            if (!event.world.isRemote) {
                RiftEntity.createRift(event.world, new Vector3(event.x, event.y, event.z), event.getPlayer());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void on(PlayerInteractEvent e) {
        if (!e.world.isRemote) {
            if (Integration.taintedMagic && Integration.witchery) {
                if ((e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) && e.entityPlayer.getHeldItem() != null) {

                    ItemStack stack = e.entityPlayer.getHeldItem();
                    if (stack.getItem() == ConfigItems.itemResearchNotes) {
                        ResearchNoteData data = ResearchManager.getData(stack);
                        if (data.key.equals("CRIMSONSPELLS")) {
                            NBTTagCompound nbtPlayer = null;
                            try {
                                nbtPlayer = (NBTTagCompound) Integration.witcheryInfusionClass.getMethod("getNBT", Entity.class).invoke(null, e.entityPlayer);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                                throw new RuntimeException(ex);
                            }
                            if (nbtPlayer != null) {
                                if (!nbtPlayer.hasKey("WITCSpellBook")) {
                                    nbtPlayer.setTag("WITCSpellBook", new NBTTagCompound());
                                }

                                NBTTagCompound nbtSpells = nbtPlayer.getCompoundTag("WITCSpellBook");
                                nbtSpells.setBoolean("incarcerous", true);
                                nbtSpells.setBoolean("arrowspell", true);
                                nbtSpells.setBoolean("spiderspell", true);
                                nbtSpells.setBoolean("spellundead", true);
                                nbtSpells.setBoolean("conjunctivitis", true);

                            }
                        }
                    }
                }
            }


            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(e.entityPlayer);
            if (capabilities != null) {
                if (capabilities.chainedTime != 0) e.setCanceled(true);
            }
            ItemStack i = e.entityPlayer.getHeldItem();
            boolean wand = false;

            if (i != null) {
                if ((i.getItem() instanceof ItemWandCasting)) {
                    if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        WandHandler.handleWandInteract(e.world, e.x, e.y, e.z, e.entityPlayer, i);
                    }
                    wand = true;
                }
                if (i.getItem() instanceof RiftGem) {
                    wand = true;
                }
            }
            if (capabilities != null && capabilities.ethereal && !wand) {
                capabilities.fleshAmount++;
                capabilities.sync();
            }
        }
    }

    @SubscribeEvent
    public void on(PlayerSleepInBedEvent event) {
        TileEntity tile = event.entityPlayer.worldObj.getTileEntity(event.x, event.y + 1, event.z);
        if (tile instanceof LithographerTile) {
            LithographerTile lithographerTile = (LithographerTile) tile;
            if (lithographerTile.deploy == 0) {
                lithographerTile.project();
            }
        }
    }


    @SubscribeEvent
    public void on(LivingHealEvent event) {
        EntityLivingBase ent = event.entityLiving;
        if (ent instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) ent;
            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
            if (capabilities != null) {
                if (capabilities.ethereal) {
                    event.setCanceled(true);
                }
            }

        }
    }


    @SubscribeEvent
    public void on(PlayerUseItemEvent.Start event) {
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(event.entityPlayer);
        if (capabilities != null) {
            if (capabilities.chainedTime != 0) event.setCanceled(true);
            if (!event.entityPlayer.worldObj.isRemote) {
                if (capabilities.ethereal) {
                    if (event.item == null) {
                        capabilities.fleshAmount++;
                        capabilities.sync();
                    } else if (!(event.item.getItem() instanceof ItemWandCasting)) {
                        capabilities.fleshAmount++;
                        capabilities.sync();
                    }
                }
            }
        }


    }

    @SubscribeEvent
    public void on(AttackEntityEvent event) {
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(event.entityPlayer);
        if (capabilities != null) {
            if (capabilities.chainedTime != 0) event.setCanceled(true);
            if (!event.entityPlayer.worldObj.isRemote) {
                if (capabilities.ethereal) {
                    ItemStack stack = event.entityPlayer.getHeldItem();
                    if (stack == null) {
                        capabilities.fleshAmount++;
                        capabilities.sync();
                    } else if (!(stack.getItem() instanceof ItemWandCasting)) {
                        capabilities.fleshAmount++;
                        capabilities.sync();
                    }
                }
            }
        }
        if (Integration.taintedMagic) {
            if (!event.entityPlayer.worldObj.isRemote && PontifexRobe.isFullSet(event.entityPlayer) && event.target != null) {
                List<EntityCultist> list = event.entityPlayer.worldObj.getEntitiesWithinAABB(EntityCultist.class, event.entityPlayer.boundingBox.expand(32, 32, 32));
                if (!list.isEmpty()) {
                    int life = Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(event.entityPlayer.getCommandSenderName(), Aspect.HUNGER);
                    int heal = Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(event.entityPlayer.getCommandSenderName(), Aspect.HEAL);
                    int potency = MathHelper.clamp_int((life / 100), 1, 200);
                    int regen = MathHelper.clamp_int((heal / 100), 1, 200);
                    for (EntityCultist cultist : list) {
                        if (event.target instanceof EntityLivingBase && !(cultist instanceof IBossDisplayData) && !(event.target instanceof EntityCultist)) {
                            cultist.setAttackTarget((EntityLivingBase) event.target);
                            cultist.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 100, potency));
                            cultist.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, regen));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void on(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote) {
            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(event.player);
            if (capabilities.ethereal) {
                if (capabilities.fleshAmount >= event.player.getMaxHealth()) {
                    capabilities.fleshAmount = MathHelper.floor_float(event.player.getMaxHealth() - 2);
                }
                capabilities.ethereal = false;
            }
            capabilities.sync(event.player);
        }
    }

    @SubscribeEvent
    public void on(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityBrainyZombie || event.entity instanceof EntityGiantBrainyZombie) {
            if (event.world.rand.nextInt(100) > 80) {
                if (!event.world.isRemote) {
                    MadThaumaturge madThaumaturge = new MadThaumaturge(event.world);
                    madThaumaturge.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.world.rand.nextFloat() * 360.0F, 0.0F);
                    event.entity.setDead();
                    event.world.spawnEntityInWorld(madThaumaturge);
                    madThaumaturge.addEquipment();
                }
            }
        }
        if (event.entity instanceof EntityCultistKnight){
            if (event.world.rand.nextInt(100) > 80) {
                if (!event.world.isRemote) {
                    CrimsonPaladin paladin = new CrimsonPaladin(event.world);
                    paladin.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.world.rand.nextFloat() * 360.0F, 0.0F);
                    event.entity.setDead();
                    event.world.spawnEntityInWorld(paladin);
                    paladin.addEquipment();
                }
            }
        }
    }

    @SubscribeEvent
    public void on(PlayerEvent.PlayerLoggedInEvent event) {
        TCPlayerCapabilities.get(event.player).sync(event.player);
    }

    @SubscribeEvent
    public void on(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        ThaumicConcilium.proxy.sendLocalMovementData(player);
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
        if (capabilities != null) {
            if (!player.worldObj.isRemote) {
                if (capabilities.pontifexRobeToggle && !PontifexRobe.isFullSet(player)) {
                    capabilities.ethereal = false;
                    capabilities.pontifexRobeToggle = false;
                    capabilities.sync();
                }
                if (capabilities.chainedTime > 0) {
                    capabilities.chainedTime--;
                    capabilities.sync();
                }
                float maxHealth = player.getMaxHealth() - capabilities.fleshAmount;
                if (player.getHealth() > maxHealth) {
                    player.setHealth(maxHealth);
                }
                if (player.ticksExisted % 10 == 0) {
                    ItemStack monitor = player.getHeldItem();
                    if (monitor != null && monitor.getItem() instanceof AstralMonitor) {
                        NBTTagCompound tag = monitor.getTagCompound();
                        if (tag != null) {
                            NBTTagCompound stacktag = tag.getCompoundTag("wand");
                            if (stacktag != null) {
                                NBTTagCompound wandtag = stacktag.getCompoundTag("tag");
                                if (wandtag != null) {
                                    String xyl = wandtag.getString("Xylography");
                                    if (xyl != null && !xyl.isEmpty()) {
                                        EntityPlayer monitored = player.worldObj.getPlayerEntityByName(xyl);
                                        if (monitored != null) {

                                            int health = Math.round((monitored.getHealth() / monitored.getMaxHealth()) * 10);
                                            int finBlood = -1;
                                            int finMana = -1;
                                            int finRunes = -1;
                                            if (Compat.bm) {
                                                int blood = SoulNetworkHandler.getCurrentEssence(monitored.getCommandSenderName());
                                                int maxBlood = SoulNetworkHandler.getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(monitored.getCommandSenderName()));
                                                if (maxBlood != 1) {
                                                    finBlood = MathHelper.clamp_int(Math.round(((float) blood / (float) maxBlood) * 10) - 1, 0, 9);
                                                }
                                            }

                                            if (Compat.botan) {
                                                int totalMana = 0;
                                                int totalMaxMana = 0;

                                                IInventory mainInv = monitored.inventory;
                                                IInventory baublesInv = PlayerHandler.getPlayerBaubles(monitored);

                                                int invSize = mainInv.getSizeInventory();
                                                int size = invSize;
                                                if (baublesInv != null)
                                                    size += baublesInv.getSizeInventory();

                                                for (int inc = 0; inc < size; inc++) {
                                                    boolean useBaubles = inc >= invSize;
                                                    IInventory inv = useBaubles ? baublesInv : mainInv;
                                                    ItemStack stack = inv.getStackInSlot(inc - (useBaubles ? invSize : 0));

                                                    if (stack != null) {
                                                        Item mitem = stack.getItem();

                                                        if (mitem instanceof IManaItem) {
                                                            if (!((IManaItem) mitem).isNoExport(stack)) {
                                                                totalMana += ((IManaItem) mitem).getMana(stack);
                                                                totalMaxMana += ((IManaItem) mitem).getMaxMana(stack);
                                                            }
                                                        }
                                                    }
                                                }
                                                finMana = Math.round(((float) totalMana / (float) totalMaxMana) * 10) - 1;
                                            }

                                            if (Thaumcraft.instance.runicEventHandler != null && Thaumcraft.instance.runicEventHandler.runicInfo != null && Thaumcraft.instance.runicEventHandler.runicCharge != null) {
                                                Integer[] ret = Thaumcraft.instance.runicEventHandler.runicInfo.get(monitored.getEntityId());
                                                int maxRunes = 0;
                                                if (ret != null) {
                                                    maxRunes = ret[0];
                                                }
                                                if (maxRunes != 0) {
                                                    int runes = Thaumcraft.instance.runicEventHandler.runicCharge.get(monitored.getEntityId());
                                                    finRunes = Math.round(((float) runes / (float) maxRunes) * 10) - 1;
                                                }
                                            }
                                            int[] vals = new int[]{health, finBlood, finMana, finRunes};
                                            tag.setIntArray("vals", vals);
                                            monitor.setTagCompound(tag);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (capabilities.lithographed && !capabilities.relieved) {
                        NBTTagCompound party = new NBTTagCompound();
                        int[] partyHealth = new int[4];
                        for (int i = 0; i < 4; i++) {
                            EntityPlayer monitored = player.worldObj.getPlayerEntityByName(capabilities.monitored[i]);
                            if (monitored == null) {
                                partyHealth[i] = -1;
                            } else {
                                partyHealth[i] = Math.round((monitored.getHealth() / monitored.getMaxHealth()) * 10);
                            }
                        }
                        int[] partyBlood = new int[4];
                        if (Compat.bm) {
                            for (int i = 0; i < 4; i++) {
                                EntityPlayer monitored = player.worldObj.getPlayerEntityByName(capabilities.monitored[i]);
                                if (monitored == null) {
                                    partyBlood[i] = -1;
                                } else {
                                    int blood = SoulNetworkHandler.getCurrentEssence(monitored.getCommandSenderName());
                                    int maxBlood = SoulNetworkHandler.getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(monitored.getCommandSenderName()));
                                    if (maxBlood != 1) {
                                        partyBlood[i] = MathHelper.clamp_int(Math.round(((float) blood / (float) maxBlood) * 10) - 1, 0, 9);
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < 4; i++) {
                                partyBlood[i] = -1;
                            }
                        }

                        int[] partyMana = new int[4];
                        if (Compat.botan) {
                            for (int i = 0; i < 4; i++) {
                                EntityPlayer monitored = player.worldObj.getPlayerEntityByName(capabilities.monitored[i]);
                                if (monitored == null) {
                                    partyMana[i] = -1;
                                } else {
                                    int totalMana = 0;
                                    int totalMaxMana = 0;

                                    IInventory mainInv = monitored.inventory;
                                    IInventory baublesInv = PlayerHandler.getPlayerBaubles(monitored);

                                    int invSize = mainInv.getSizeInventory();
                                    int size = invSize;
                                    if (baublesInv != null)
                                        size += baublesInv.getSizeInventory();

                                    for (int j = 0; j < size; j++) {
                                        boolean useBaubles = j >= invSize;
                                        IInventory inv = useBaubles ? baublesInv : mainInv;
                                        ItemStack stack = inv.getStackInSlot(j - (useBaubles ? invSize : 0));

                                        if (stack != null) {
                                            Item item = stack.getItem();

                                            if (item instanceof IManaItem) {
                                                if (!((IManaItem) item).isNoExport(stack)) {
                                                    totalMana += ((IManaItem) item).getMana(stack);
                                                    totalMaxMana += ((IManaItem) item).getMaxMana(stack);
                                                }
                                            }
                                        }
                                    }
                                    partyMana[i] = Math.round(((float) totalMana / (float) totalMaxMana) * 10) - 1;
                                }
                            }
                        } else {
                            for (int i = 0; i < 4; i++) {
                                partyMana[i] = -1;
                            }
                        }

                        int[] partyRunes = new int[4];
                        for (int i = 0; i < 4; i++) {
                            EntityPlayer monitored = player.worldObj.getPlayerEntityByName(capabilities.monitored[i]);
                            if (monitored == null) {
                                partyRunes[i] = -1;
                            } else {
                                if (Thaumcraft.instance.runicEventHandler != null && Thaumcraft.instance.runicEventHandler.runicInfo != null && Thaumcraft.instance.runicEventHandler.runicCharge != null) {
                                    Integer[] ret = Thaumcraft.instance.runicEventHandler.runicInfo.get(monitored.getEntityId());
                                    if (ret != null) {
                                        int maxRunes = ret[0];
                                        if (maxRunes != 0) {
                                            int runes = Thaumcraft.instance.runicEventHandler.runicCharge.get(monitored.getEntityId());
                                            partyRunes[i] = Math.round(((float) runes / (float) maxRunes) * 10) - 1;
                                        }
                                    }
                                }
                            }
                        }

                        int[] brains = new int[4];
                        for (int i = 0; i < 4; i++) {
                            brains[i] = -1;
                            if (capabilities.monitored[i].contains(";")) {
                                String[] s = capabilities.monitored[i].split(";");
                                World w = DimensionManager.getWorld(Integer.parseInt(s[0]));
                                int x = Integer.parseInt(s[1]);
                                int y = Integer.parseInt(s[2]);
                                int z = Integer.parseInt(s[3]);
                                TileEntity tile = w.getTileEntity(x, y, z);
                                if (tile instanceof RedPoweredMindTile) {
                                    brains[i] = ((RedPoweredMindTile) tile).power;
                                }
                            }
                        }

                        party.setIntArray("partyHealth", partyHealth);
                        party.setIntArray("partyBlood", partyBlood);
                        party.setIntArray("partyMana", partyMana);
                        party.setIntArray("partyRunes", partyRunes);
                        party.setIntArray("brains", brains);
                        TCPacketHandler.INSTANCE.sendTo(new PacketUpdatePartyStatus(party), (EntityPlayerMP) player);
                    }
                }
            }
            if (capabilities.ethereal) {
                player.noClip = true;
                if (!player.isSneaking() || (!player.isSneaking() && !player.capabilities.allowFlying)) {
                    player.motionY = 0;
                }
            } else if (player.noClip) {
                player.noClip = false;
            }
        }
    }

    @SubscribeEvent
    public void on(LivingAttackEvent event) {
        if (event.entityLiving instanceof Dissolved) {
            if (!event.source.damageType.equals("outOfWorld") && !event.source.damageType.equals("inWall")) {
                event.setCanceled(true);
            }
        }
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;

            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
            if (capabilities != null) {
                if (capabilities.ethereal) {
                    if (event.source.isMagicDamage() || event.source.damageType.equals("outOfWorld")) {
                        capabilities.fleshAmount++;
                        capabilities.sync();
                    }
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void on(LivingDeathEvent event) {
        if (event.source.isMagicDamage()) {
            if (event.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
                ItemStack stack = player.getHeldItem();
                if (stack != null) {
                    if (stack.getItem() instanceof ItemWandCasting) {
                        if (((ItemWandCasting) stack.getItem()).getFocus(stack) instanceof VisConductorFoci) {
                            if (!((ItemWandCasting) stack.getItem()).getFocus(stack).isUpgradedWith(((ItemWandCasting) stack.getItem()).getFocusItem(stack), TCFociUpgrades.wispLauncher)) {
                                ItemWispEssence itemEssence = (ItemWispEssence) ConfigItems.itemWispEssence;
                                ItemStack wispyEssence = new ItemStack(itemEssence, 1, 0);
                                AspectList aspectsCompound = ScanManager.generateEntityAspects(event.entity);
                                itemEssence.setAspects(wispyEssence, new AspectList().add(aspectsCompound.getAspects()[player.worldObj.rand.nextInt(aspectsCompound.size())], 2));
                                event.entity.entityDropItem(wispyEssence, 0.2f);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void on(final PlayerEvent.ItemCraftedEvent event) {
        System.out.println("CRAFTED");
        if (event.crafting.getItem() instanceof ItemWandCasting && !(event.player instanceof FakePlayer)) {
            NBTTagCompound tag = event.crafting.getTagCompound();
            if (tag != null) {
                String xyl = tag.getString("Xylography");
                if (xyl != null && xyl.equals(" ")) {
                    tag.setString("Xylography", event.player.getCommandSenderName());
                    event.crafting.setTagCompound(tag);
                    System.out.println(event.crafting.getTagCompound().getString("Xylography"));
                }
            }
        }

        AspectList aspectList = ThaumicConciliumApi.getPolishmentRecipe(event.crafting);
        if (aspectList != null) {
            EntityPlayer player = event.player;
            double iX = event.player.posX;
            double iY = event.player.posY + 1;
            double iZ = event.player.posZ;
            boolean found = false;
            for (int yy = -16; yy <= 16; yy++)
                for (int zz = -16; zz <= 16; zz++)
                    for (int xx = -16; xx <= 16; xx++)
                        if (event.player.worldObj.getTileEntity((int) event.player.posX + xx, (int) event.player.posY + yy, (int) event.player.posZ + zz) instanceof TileInfusionMatrix) {
                            iX = event.player.posX + xx;
                            iY = event.player.posY + yy;
                            iZ = event.player.posZ + zz;
                            found = true;
                        }
            if (player.getHeldItem() != null && found) {
                if (player.getHeldItem().getItem() instanceof ItemWandCasting) {
                    ItemStack wand = player.getHeldItem();
                    NBTTagCompound fociTag = wand.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
                    if (fociTag != null) {
                        if (fociTag.hasKey("blockX")) {
                            int x = fociTag.getInteger("blockX");
                            int y = fociTag.getInteger("blockY");
                            int z = fociTag.getInteger("blockZ");
                            int amount = fociTag.getInteger("amount");
                            if (player.worldObj.getTileEntity(x, y, z) instanceof DestabilizedCrystalTile) {
                                DestabilizedCrystalTile crystal = (DestabilizedCrystalTile) player.worldObj.getTileEntity(x, y, z);
                                if (crystal.capacity == 0 && amount >= aspectList.getAmount(aspectList.getAspects()[0]) && crystal.aspect.equals(aspectList.getAspects()[0].getTag())) {
                                    if (!player.worldObj.isRemote) {
                                        player.worldObj.setBlock(x, y, z, Blocks.air, 0, 7);
                                        player.worldObj.removeTileEntity(x, y, z);
                                        player.worldObj.playSoundAtEntity(player, "thaumcraft:shock", 0.8F, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                                        player.stopUsingItem();
                                        int rgb = Aspect.aspects.get(crystal.aspect).getColor();
                                        TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) player.posX, (float) (player.posY + 1F), (float) player.posZ, (float) iX, (float) iY, (float) iZ, rgb, 1.0F), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));
                                        return;
                                    } else {
                                        Thaumcraft.proxy.burst(player.worldObj, x, y, z, 2.0F);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!event.player.worldObj.isRemote) {
                event.crafting.stackSize -= 1;

                TileEntity te = event.player.worldObj.getTileEntity((int) iX, (int) iY - 2, (int) iZ);
                if (te instanceof TilePedestal) {
                    ((TilePedestal) te).decrStackSize(0, 1);
                    player.stopUsingItem();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ServerChatEvent event) {
        if (Integration.gadomancy) {
            EntityPlayer player = event.player;
            if (!player.worldObj.isRemote) {
                List<EntityGolemBase> list = player.worldObj.getEntitiesWithinAABB(EntityGolemBase.class, player.boundingBox.expand(32.0, 32.0, 32.0));
                for (EntityGolemBase g : list) {
                    if (GadomancyApi.getAdditionalGolemCore(g) instanceof ValetGolemCore) {
                        if (g.getOwnerName().equals(player.getCommandSenderName())) {
                            GolemChatHandler.messages.put(event.player.getCommandSenderName(), event.message);
                            g.setToggle(5, true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        GolemChatHandler.messages.remove(event.player.getCommandSenderName());
    }
}
