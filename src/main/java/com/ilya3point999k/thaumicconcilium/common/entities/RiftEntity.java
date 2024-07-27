package com.ilya3point999k.thaumicconcilium.common.entities;

import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import com.ilya3point999k.thaumicconcilium.common.dim.CausalBouillonTeleporter;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.BrightestOne;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.LesserPortal;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ChargedWispEntity;
import com.ilya3point999k.thaumicconcilium.common.items.RiftGem;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXBurst;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.HexOfPredictabilityTile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityMindSpider;
import thaumcraft.common.entities.projectile.EntityPrimalOrb;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockZap;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.tiles.TileNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RiftEntity extends Entity implements IEntityAdditionalSpawnData {
    private static final int SEED;
    private static final int SIZE;
    private static final int COLLAPSE;
    private static final int STATE;

    int maxSize;
    int lastSize;
    public ArrayList<Vec3> points;
    public ArrayList<Float> pointsWidth;
    public int collapsing;
    public boolean interdimensional;
    public boolean chained;
    public int hexX, hexY, hexZ;
    public int process;

    public RiftEntity(World par1World) {
        super(par1World);
        maxSize = 0;
        lastSize = -1;
        points = new ArrayList<Vec3>();
        pointsWidth = new ArrayList<Float>();
        collapsing = 0;
        interdimensional = false;
        chained = false;
        noClip = false;
        hexX = hexY = hexZ = 0;
        process = 0;
        setSize(2.0f, 2.0f);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    protected void entityInit() {
        getDataWatcher().addObject(RiftEntity.SIZE, new Integer(5));
        getDataWatcher().addObject(RiftEntity.SEED, new Integer(0));
        getDataWatcher().addObject(RiftEntity.COLLAPSE, new Integer(0));
        getDataWatcher().addObject(RiftEntity.STATE, new Integer(0));
    }

    public int getState() {
        return getDataWatcher().getWatchableObjectInt(RiftEntity.STATE);
    }

    public void setState(int s) {
        getDataWatcher().updateObject(RiftEntity.STATE, s);
    }

    public int getRiftSize() {
        return getDataWatcher().getWatchableObjectInt(RiftEntity.SIZE);
    }

    public void setRiftSize(int s) {
        if (s < 0) s = 0;
        getDataWatcher().updateObject(RiftEntity.SIZE, s);
        setSize();
    }

    public boolean isCollapsing() {
        return getDataWatcher().getWatchableObjectInt(RiftEntity.COLLAPSE) == 1;
    }

    public void setCollapsing(int size) {
        maxSize = size;
        getDataWatcher().updateObject(RiftEntity.COLLAPSE, 1);
    }

    public boolean isGrowing() {
        return getDataWatcher().getWatchableObjectInt(RiftEntity.COLLAPSE) == -1;
    }

    public void setGrowing() {
        getDataWatcher().updateObject(RiftEntity.COLLAPSE, -1);
    }

    public void setStable() {
        getDataWatcher().updateObject(RiftEntity.COLLAPSE, 0);
    }

    @Override
    public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_) {

    }

    public void setCollisionSize(float width, float height) {
        setSize(width, height);
    }

    public void setSize() {

        calcSteps(points, pointsWidth, new Random(getRiftSeed()));
        lastSize = getRiftSize();

        double x0 = Double.MAX_VALUE;
        double y0 = Double.MAX_VALUE;
        double z0 = Double.MAX_VALUE;
        double x2 = Double.MIN_VALUE;
        double y2 = Double.MIN_VALUE;
        double z2 = Double.MIN_VALUE;
        for (Vec3 v : points) {
            if (v.xCoord < x0) {
                x0 = v.xCoord;
            }
            if (v.xCoord > x2) {
                x2 = v.xCoord;
            }
            if (v.yCoord < y0) {
                y0 = v.yCoord;
            }
            if (v.yCoord > y2) {
                y2 = v.yCoord;
            }
            if (v.zCoord < z0) {
                z0 = v.zCoord;
            }
            if (v.zCoord > z2) {
                z2 = v.zCoord;
            }
        }
        width = Math.abs((float) Math.max(x2 - x0, z2 - z0));
        height = Math.abs((float) (y2 - y0));
        this.boundingBox.setBounds(posX + x0, posY + y0, posZ + z0, posX + x2, posY + y2, posZ + z2);
    }

    public void setPosition(double x, double y, double z) {
        /*posX = x;
        posY = y;
        posZ = z;
        if (getDataWatcher() != null) {
            setSize();
        } else {
            super.setPosition(x, y, z);
        }
         */
    }

    public int getRiftSeed() {
        return getDataWatcher().getWatchableObjectInt(RiftEntity.SEED);
    }

    public void setRiftSeed(int s) {
        getDataWatcher().updateObject(RiftEntity.SEED, s);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("MaxSize", maxSize);
        nbttagcompound.setInteger("RiftSize", getRiftSize());
        nbttagcompound.setInteger("RiftSeed", getRiftSeed());
        nbttagcompound.setInteger("Collapsing", collapsing);
        nbttagcompound.setBoolean("Interdimensional", interdimensional);
        nbttagcompound.setBoolean("Chained", chained);
        nbttagcompound.setIntArray("Hex", new int[]{hexX, hexY, hexZ});
        nbttagcompound.setInteger("Process", process);
        nbttagcompound.setInteger("State", getState());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        maxSize = nbttagcompound.getInteger("MaxSize");
        setRiftSize(nbttagcompound.getInteger("RiftSize"));
        setRiftSeed(nbttagcompound.getInteger("RiftSeed"));
        collapsing = nbttagcompound.getInteger("Collapsing");
        interdimensional = nbttagcompound.getBoolean("Interdimensional");
        chained = nbttagcompound.getBoolean("Chained");
        int[] hex = nbttagcompound.getIntArray("Hex");
        hexX = hex[0];
        hexY = hex[1];
        hexZ = hex[2];
        process = nbttagcompound.getInteger("Process");
        setState(nbttagcompound.getInteger("State"));
    }

    public void onUpdate() {
        super.onUpdate();
            if (lastSize != getRiftSize()) {
                setSize();
            }
        if (!worldObj.isRemote) {
            if (getRiftSeed() == 0) {
                setRiftSeed(rand.nextInt());
            }
        }

        if (!points.isEmpty()) {
            if (!worldObj.isRemote && !isDead && (ticksExisted % 300 == 0 || ticksExisted == 0)) {
                worldObj.playSoundAtEntity(this, "thaumcraft:evilportal", (float) (0.15000000596046448 + rand.nextGaussian() * 0.066), (float) (0.75 + rand.nextGaussian() * 0.1));
            }
            int pi = rand.nextInt(points.size() - 1);
            Vec3 copy = points.get(pi);
            Vec3 v1 = Vec3.createVectorHelper(copy.xCoord, copy.yCoord, copy.zCoord).addVector(posX, posY + height / 2.0, posZ);
            if (chained) {
                TileEntity tile = worldObj.getTileEntity(hexX, hexY, hexZ);
                if (!worldObj.isRemote && !(tile instanceof HexOfPredictabilityTile)) {
                    setCollapsing(0);
                }
                if (tile instanceof HexOfPredictabilityTile && ((HexOfPredictabilityTile) tile).heat > 0 && ((HexOfPredictabilityTile) tile).essentia.size() > 0) {
                    ArrayList<Entity> items = EntityUtils.getEntitiesInRange(this.worldObj, v1.xCoord, v1.yCoord, v1.zCoord, this, EntityItem.class, 1.4);
                    if (!worldObj.isRemote && !items.isEmpty()) {
                        worldObj.playSoundAtEntity(this, "thaumcraft:jacobs", 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                        ArrayList<Entity> shrinked = (ArrayList<Entity>) items.stream().limit(6).collect(Collectors.toList());
                        for (Entity i : shrinked) {
                            Aspect[] aspects = ((HexOfPredictabilityTile) tile).essentia.getAspects();
                            int rgb = aspects[worldObj.rand.nextInt(aspects.length)].getColor();
                            TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) v1.xCoord, (float) v1.yCoord, (float) v1.zCoord, (float) i.posX, (float) i.posY, (float) i.posZ, rgb, (((HexOfPredictabilityTile) tile).heat / 2400F) * 2), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 32.0));
                        }
                    }
                }
            }
            if (interdimensional) {
                if (worldObj.rand.nextInt() % 3 == 0) {
                    Thaumcraft.proxy.arcLightning(this.worldObj, (float) this.posX + (0.5f + (-0.5f + rand.nextFloat()) * 5.0f), (float) this.posY + (0.5f + (-0.5f + rand.nextFloat()) * 5.0f), (float) this.posZ + (0.5f + (-0.5f + rand.nextFloat()) * 5.0f), (float) v1.xCoord, (float) v1.yCoord, (float) v1.zCoord, 30.0F / 255.0F, 0.0F, 30.0F / 255.0F, 0.3f);
                }
            }
            if (!this.worldObj.isRemote) {
                if (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    copy = points.get(pi + 1);
                    Vec3 v2 = Vec3.createVectorHelper(copy.xCoord, copy.yCoord, copy.zCoord).addVector(posX, posY + height / 2.0, posZ);
                    MovingObjectPosition rt = this.worldObj.rayTraceBlocks(v1, v2, false);
                    if (rt != null) {
                        Block block = this.worldObj.getBlock(rt.blockX, rt.blockY, rt.blockZ);
                        if (!this.worldObj.isAirBlock(rt.blockX, rt.blockY, rt.blockZ) && EntityEnderman.getCarriable(block) && block.getBlockHardness(this.worldObj, rt.blockX, rt.blockY, rt.blockZ) >= 0.0f && block.canCollideCheck(block.getDamageValue(this.worldObj, rt.blockX, rt.blockY, rt.blockZ), false)) {
                            this.worldObj.setBlockToAir(rt.blockX, rt.blockY, rt.blockZ);
                        }
                    }
                }
                List<Entity> el = EntityUtils.getEntitiesInRange(this.worldObj, v1.xCoord, v1.yCoord, v1.zCoord, this, Entity.class, 0.5);
                for (Entity e : el) {
                    if (!e.isDead) {
                        if (e instanceof EntityPlayer) {
                            EntityPlayer player = (EntityPlayer) e;

                            if (this.interdimensional) {
                                EntityPlayerMP p = (EntityPlayerMP) player;
                                if (player.getHeldItem() != null) {
                                    if (player.getHeldItem().getItem() instanceof RiftGem) {
                                        ItemStack gem = player.getHeldItem();
                                        if (p.ridingEntity == null && p.riddenByEntity == null) {
                                            MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
                                            if (p.timeUntilPortal > 0) {
                                                p.timeUntilPortal = 100;
                                            } else if (p.dimension != TCConfig.causalBouillonID) {
                                                p.timeUntilPortal = 100;
                                                p.mcServer.getConfigurationManager().transferPlayerToDimension(p, TCConfig.causalBouillonID, new CausalBouillonTeleporter(mServer.worldServerForDimension(TCConfig.causalBouillonID)));
                                            } else {
                                                p.timeUntilPortal = 100;
                                                NBTTagCompound tag = gem.getTagCompound();
                                                if (tag == null) {
                                                    p.mcServer.getConfigurationManager().transferPlayerToDimension(p, 0, new CausalBouillonTeleporter(mServer.worldServerForDimension(0)));
                                                } else {
                                                    if (tag.hasKey("CURR")) {
                                                        if (tag.getInteger("CURR") != -1) {
                                                            p.mcServer.getConfigurationManager().transferPlayerToDimension(p, tag.getInteger("DIM" + tag.getInteger("CURR")), new CausalBouillonTeleporter(mServer.worldServerForDimension(0)));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (player.capabilities.isCreativeMode) {
                                continue;
                            }
                        }

                        if (e instanceof ChargedWispEntity && this.chained && ((ChargedWispEntity) e).caster != null) {
                            if (ThaumcraftApiHelper.isResearchComplete(((ChargedWispEntity) e).caster.getCommandSenderName(), "BRIGHTESTONE")) {
                                interdimensional = true;
                                e.setDead();
                                setState(1);
                                setCollapsing(-1);
                            }
                        }

                        if (e instanceof EntityItem && this.chained) {
                            EntityItem item = (EntityItem) e;
                            NBTTagCompound itemData = item.getEntityData();
                            String thrower = itemData.getString("thrower");
                            if (thrower != null) {
                                if (item.getEntityItem().getItem() == ConfigItems.itemEldritchObject && item.getEntityItem().getItemDamage() == 1) {
                                    if (ThaumcraftApiHelper.isResearchComplete(thrower, "CRIMSONPONTIFEX")) {
                                        interdimensional = true;
                                        e.setDead();
                                        setState(2);
                                        setCollapsing(-1);
                                    }
                                } else if (item.getEntityItem().getItem() == TCItemRegistry.resource && item.getEntityItem().getItemDamage() == 1) {
                                    if (ThaumcraftApiHelper.isResearchComplete(thrower, "MATERIALPEELER")) {
                                        interdimensional = true;
                                        e.setDead();
                                        setState(3);
                                        setCollapsing(-1);
                                    }
                                }

                            }
                        }
                        if (!(e instanceof EntityItem) && !chained) {
                            e.attackEntityFrom(DamageSource.outOfWorld, 2.0f);
                            continue;
                        }
                        if (!this.chained && ticksExisted > 100) e.setDead();

                    }
                }


                if (!interdimensional && !chained && ticksExisted % 300 == 0) {
                    fireEvent();
                }


                if (isCollapsing()) {
                    if (getRiftSize() > maxSize) {
                        setRiftSize(getRiftSize() - 1);
                        if (maxSize == 0) {
                            if (this.worldObj.rand.nextInt() % 3 == 0) {
                                int x = (int) (this.posX + this.worldObj.rand.nextGaussian() * 1.5);
                                int y = (int) this.posY;
                                int z = (int) (this.posZ + this.worldObj.rand.nextGaussian() * 1.5);

                                for (int a = 0; a < 10; ++a) {
                                    int xx = x + (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                                    int zz = z + (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                                    if (this.worldObj.rand.nextBoolean() && this.worldObj.getBiomeGenForCoords(xx, zz) != ThaumcraftWorldGenerator.biomeTaint) {
                                        Utils.setBiomeAt(this.worldObj, xx, zz, ThaumcraftWorldGenerator.biomeTaint);
                                        if (this.worldObj.isBlockNormalCubeDefault(xx, y - 1, zz, false) && this.worldObj.getBlock(xx, y, zz).isReplaceable(this.worldObj, xx, y, zz)) {
                                            this.worldObj.setBlock(xx, y, zz, ConfigBlocks.blockTaintFibres, 0, 3);
                                        }
                                        if (this.worldObj.isAirBlock(xx, y + 1, zz)) {
                                            if (this.worldObj.rand.nextBoolean()) {
                                                this.worldObj.setBlock(xx, y + 1, zz, ConfigBlocks.blockFluxGas, 0, 3);
                                            } else {
                                                this.worldObj.setBlock(xx, y + 1, zz, ConfigBlocks.blockFluxGoo, 0, 3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        setStable();
                    }
                }

                if (isGrowing()) {
                    if (getRiftSize() != maxSize) {
                        if (this.worldObj.rand.nextInt() % 3 == 0) {
                            setRiftSize(getRiftSize() + 1);
                        }
                    } else {
                        setStable();
                    }
                }

                if (interdimensional && (getRiftSize() >= maxSize || ticksExisted > 200)) {
                    setCollapsing(-1);
                }

                if (getRiftSize() > 300) {
                    setCollapsing(getRiftSize() / 2);
                }
                if (getRiftSize() == 0) {
                    if (this.chained) {
                        int s = getState();
                        if (s == 1) {
                            if (!worldObj.isRemote) {
                                BrightestOne brightestOne = new BrightestOne(worldObj);
                                brightestOne.setPositionAndRotation(posX, posY, posZ, this.worldObj.rand.nextFloat(), this.worldObj.rand.nextFloat());
                                worldObj.spawnEntityInWorld(brightestOne);
                            }
                        }
                        if (s == 2) {
                            if (!worldObj.isRemote) {
                                LesserPortal portal = new LesserPortal(worldObj);
                                portal.setPositionAndRotation(posX, posY, posZ, this.worldObj.rand.nextFloat(), this.worldObj.rand.nextFloat());
                                worldObj.spawnEntityInWorld(portal);
                                portal.boss = true;
                                this.playSound("thaumcraft:craftfail", 1.0F, 1.0F);
                            }
                        }
                        if (s == 3) {
                            if (!worldObj.isRemote) {
                                MaterialPeeler peeler = new MaterialPeeler(worldObj);
                                peeler.setPositionAndRotation(posX, posY, posZ, this.worldObj.rand.nextFloat(), this.worldObj.rand.nextFloat());
                                worldObj.spawnEntityInWorld(peeler);
                                TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXBurst(this.posX, this.posY, this.posZ), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 32.0));
                                this.playSound("thaumcraft:craftfail", 1.0F, 1.0F);
                            }
                        }

                    }
                    setDead();
                }
            }
        }
    }

    private void fireEvent() {
        int event = this.worldObj.rand.nextInt(10);
        //int event = 0;
        switch (event) {
            case 0: {
                ArrayList<EntityPlayer> players = (ArrayList<EntityPlayer>) this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(7, 7, 7));
                for (EntityPlayer p : players) {
                    final int totalWarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(p.getCommandSenderName());
                    double size = MathHelper.clamp_double(totalWarp, 2.0, 10.0);
                    this.maxSize += size;
                    this.setGrowing();
                    //this.setRiftSize(this.getRiftSize() + (int) size);
                }
                break;
            }
            case 1: {
                List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(7, 7, 7));
                if (!list.isEmpty()) {
                    int amount = MathHelper.clamp_int(this.getRiftSize() % 11, 3, 10);
                    for (int i = 0; i < amount; i++) {
                        Collections.shuffle(list);
                        EntityLivingBase e = list.get(0);
                        EntityPrimalOrb orb = new EntityPrimalOrb(this.worldObj);
                        orb.setLocationAndAngles(this.posX + (this.worldObj.rand.nextInt(6) - 3), this.posY + (this.worldObj.rand.nextInt(6) - 3), this.posZ + (this.worldObj.rand.nextInt(6) - 3), (float) this.worldObj.rand.nextGaussian(), (float) this.worldObj.rand.nextGaussian());
                        double d0 = e.posX - this.posX;
                        double d1 = e.posY - this.posY;
                        double d2 = e.posZ - this.posZ;

                        orb.setThrowableHeading(d0, d1, d2, 0.66F, 0.1F);
                        this.worldObj.spawnEntityInWorld(orb);
                    }
                }
                break;
            }
            case 2: {
                List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(7, 7, 7));
                if (!list.isEmpty()) {
                    for (EntityLivingBase e : list) {
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float) this.posX + 0.5F, (float) this.posY + 0.5F, (float) this.posZ + 0.5F, (float) e.posX, (float) e.posY + e.height / 2.0F, (float) e.posZ), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 32.0));
                        e.attackEntityFrom(DamageSource.magic, (float) (4 + this.worldObj.rand.nextInt(MathHelper.clamp_int(this.getRiftSize(), 5, 30))));
                    }
                }
                break;
            }
            case 3: {
                ArrayList<EntityPlayer> players = (ArrayList<EntityPlayer>) this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(7, 7, 7));
                for (EntityPlayer p : players) {
                    final int totalWarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(p.getCommandSenderName());
                    double size = MathHelper.clamp_double(totalWarp, 2.0, 10.0);
                    this.setRiftSize(this.getRiftSize() + (int) size);
                    PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float) this.posX + 0.5F, (float) this.posY + 0.5F, (float) this.posZ + 0.5F, (float) p.posX, (float) p.posY + p.height / 2.0F, (float) p.posZ), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 32.0));
                    p.attackEntityFrom(DamageSource.magic, (float) (4 + this.worldObj.rand.nextInt(MathHelper.clamp_int(this.getRiftSize(), 5, 30))));
                }
                break;
            }
            case 4: {
                int spawns = Math.min(50, this.getRiftSize());
                ArrayList<EntityPlayer> players = (ArrayList<EntityPlayer>) this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(7, 7, 7));
                for (int a = 0; a < spawns; ++a) {
                    EntityMindSpider spider = new EntityMindSpider(this.worldObj);
                    int i = MathHelper.floor_double(this.posX);
                    int j = MathHelper.floor_double(this.posY);
                    int k = MathHelper.floor_double(this.posZ);
                    boolean success = false;

                    for (int l = 0; l < 50; ++l) {
                        int i1 = i + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
                        int j1 = j + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
                        int k1 = k + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
                        if (World.doesBlockHaveSolidTopSurface(this.worldObj, i1, j1 - 1, k1)) {
                            spider.setPosition(i1, j1, k1);
                            if (this.worldObj.checkNoEntityCollision(spider.boundingBox) && this.worldObj.getCollidingBoundingBoxes(spider, spider.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(spider.boundingBox)) {
                                success = true;
                                break;
                            }
                        }
                    }

                    if (success) {
                        if (!players.isEmpty()) {
                            EntityPlayer player = players.get(this.worldObj.rand.nextInt(players.size()));
                            spider.setTarget(player);
                            spider.setAttackTarget(player);
                        }
                        this.worldObj.spawnEntityInWorld(spider);
                    }
                }
                break;
            }
            case 5: {
                List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(7, 7, 7));
                if (!list.isEmpty()) {
                    int amount = MathHelper.clamp_int(this.getRiftSize() % 11, 3, 10);
                    for (int i = 0; i < amount; i++) {
                        Collections.shuffle(list);
                        EntityLivingBase e = list.get(0);
                        double tx = e.posX + e.motionX - this.posX;
                        double ty = e.posY - this.posY - (double) (e.height / 2.0F);
                        double tz = e.posZ + e.motionZ - this.posZ;
                        ChargedWispEntity wisp = new ChargedWispEntity(this.worldObj, this.posX, this.posY, this.posZ, tx, ty, tz);
                        wisp.setType((String) Aspect.aspects.keySet().toArray()[this.worldObj.rand.nextInt(Aspect.aspects.keySet().size())]);
                    }
                }
                break;
            }
            case 6: {
                if (this.worldObj.rand.nextInt(100) > 60) {
                    this.setCollapsing(0);
                }
                break;
            }
            default:
                break;
        }
    }

    public static void createRift(World world, Vector3 pos, TileNode tile) {
        Vector3 p2 = new Vector3(pos.x, pos.y, pos.z);
        RiftEntity rift = new RiftEntity(world);
        rift.setRiftSeed(world.rand.nextInt());
        int amount = tile.getAspects().visSize() / 2;
        double size = MathHelper.clamp_double(amount, 5.0, 80.0);
        rift.setLocationAndAngles(p2.x, p2.y, p2.z, (float) world.rand.nextInt(360), 0.0f);
        if (world.spawnEntityInWorld(rift)) {
            rift.posX = p2.x;
            rift.posY = p2.y;
            rift.posZ = p2.z;
            rift.setRiftSize(5);
            rift.maxSize = ((int) size);
            rift.setGrowing();
        }
    }

    public static void summonRift(EntityPlayer player, Vector3 pos, boolean interdimensional) {
        RiftEntity rift = new RiftEntity(player.worldObj);
        rift.setRiftSeed(player.worldObj.rand.nextInt());
        rift.setLocationAndAngles(pos.x, pos.y, pos.z, (float) player.worldObj.rand.nextInt(360), 0.0f);
        rift.interdimensional = interdimensional;
        double size = 50.0;
        if (!player.worldObj.isRemote) {
            if (player.worldObj.spawnEntityInWorld(rift)) {
                rift.posX = pos.x;
                rift.posY = pos.y;
                rift.posZ = pos.z;
                rift.maxSize = ((int) size);
                rift.setGrowing();
            }
        }
    }

    public static void constructRift(World world, int x, int y, int z, int size) {
        Vector3 p2 = new Vector3(x, y, z);
        RiftEntity rift = new RiftEntity(world);
        rift.setRiftSeed(world.rand.nextInt());
        rift.setLocationAndAngles(p2.x, p2.y, p2.z, (float) world.rand.nextInt(360), 0.0f);
        size = MathHelper.clamp_int(size, 5, 150);
        rift.chained = true;
        rift.hexX = x;
        rift.hexY = y - 2;
        rift.hexZ = z;
        if (world.spawnEntityInWorld(rift)) {
            rift.posX = p2.x + 0.5;
            rift.posY = p2.y;
            rift.posZ = p2.z + 0.5;
            rift.setRiftSize(5);
            rift.maxSize = ((int) size);
            rift.setGrowing();
        }
    }

    private void calcSteps(ArrayList<Vec3> pp, ArrayList<Float> ww, Random rr) {
        pp.clear();
        ww.clear();
        Vec3 right = Vec3.createVectorHelper(rr.nextGaussian(), rr.nextGaussian(), rr.nextGaussian()).normalize();
        Vec3 left = Vec3.createVectorHelper(-right.xCoord, -right.yCoord, -right.zCoord);
        Vec3 lr = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        Vec3 ll = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        int steps = MathHelper.ceiling_float_int(getRiftSize() / 3.0f);
        float girth = getRiftSize() / 300.0f;
        double angle = 0.33;
        float dec = girth / steps;
        for (int a = 0; a < steps; ++a) {
            girth -= dec;
            right = rotatePitch(right, (float) (rr.nextGaussian() * angle));
            right = rotateYaw(right, (float) (rr.nextGaussian() * angle));
            lr = lr.addVector(right.xCoord * 0.2, right.yCoord * 0.2, right.zCoord * 0.2);
            pp.add(Vec3.createVectorHelper(lr.xCoord, lr.yCoord, lr.zCoord));
            ww.add(girth);
            left = rotatePitch(left, (float) (rr.nextGaussian() * angle));
            left = rotateYaw(left, (float) (rr.nextGaussian() * angle));
            ll = ll.addVector(left.xCoord * 0.2, left.yCoord * 0.2, left.zCoord * 0.2);
            pp.add(0, Vec3.createVectorHelper(ll.xCoord, ll.yCoord, ll.zCoord));
            ww.add(0, girth);
        }
        lr = lr.addVector(right.xCoord * 0.1, right.yCoord * 0.1, right.zCoord * 0.1);
        pp.add(Vec3.createVectorHelper(lr.xCoord, lr.yCoord, lr.zCoord));
        ww.add(0.0f);
        ll = ll.addVector(left.xCoord * 0.1, left.yCoord * 0.1, left.zCoord * 0.1);
        pp.add(0, Vec3.createVectorHelper(ll.xCoord, ll.yCoord, ll.zCoord));
        ww.add(0, 0.0f);

    }

    public void setFire(int seconds) {
    }

    public boolean isBurning() {
        return false;
    }

    public boolean canRenderOnFire() {
        return false;
    }

    private Vec3 rotatePitch(Vec3 v, float pitch) {
        float f = MathHelper.cos(pitch);
        float f1 = MathHelper.sin(pitch);
        double d0 = v.xCoord;
        double d1 = v.yCoord * (double) f + v.zCoord * (double) f1;
        double d2 = v.zCoord * (double) f - v.yCoord * (double) f1;
        return Vec3.createVectorHelper(d0, d1, d2);
    }

    private Vec3 rotateYaw(Vec3 v, float yaw) {
        float f = MathHelper.cos(yaw);
        float f1 = MathHelper.sin(yaw);
        double d0 = v.xCoord * (double) f + v.zCoord * (double) f1;
        double d1 = v.yCoord;
        double d2 = v.zCoord * (double) f - v.xCoord * (double) f1;
        return Vec3.createVectorHelper(d0, d1, d2);
    }

    static {
        SEED = 7;
        SIZE = 8;
        COLLAPSE = 9;
        STATE = 14;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeBoolean(this.interdimensional);
        buffer.writeBoolean(this.chained);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        try {
            this.interdimensional = additionalData.readBoolean();
            this.chained = additionalData.readBoolean();
        } catch (Exception e) {
        }
    }


}