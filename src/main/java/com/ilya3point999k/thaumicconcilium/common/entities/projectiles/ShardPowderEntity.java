package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import com.ilya3point999k.thaumicconcilium.common.Util;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.entities.monster.EntityTaintacleSmall;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.util.Iterator;
import java.util.List;

public class ShardPowderEntity extends Entity implements IEntityAdditionalSpawnData {
    public int orbAge = 0;
    public int orbMaxAge = 40;
    public int orbCooldown;
    private int orbHealth = 5;
    public int type;
    private EntityPlayer closestPlayer;
    private EntityLivingBase caster;

    public boolean isInRangeToRenderDist(double par1) {
        double d1 = 0.5;
        d1 *= 64.0 * this.renderDistanceWeight;
        return par1 < d1 * d1;
    }

    public ShardPowderEntity(World par1World) {
        super(par1World);
        this.setSize(0.125F, 0.125F);
        this.yOffset = this.height / 2.0F;
    }

    public ShardPowderEntity(EntityLivingBase caster, double x, double y, double z, int type) {
        super(caster.worldObj);
        this.setSize(0.125F, 0.125F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(x, y, z);
        this.caster = caster;
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.type = type;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
    }


    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {
        float f1 = 0.5F;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(par1);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.orbCooldown > 0) {
            --this.orbCooldown;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746;
        if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava) {
            this.motionY = 0.20000000298023224;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        double d0 = 8.0;
        if (type == 9) {
            if (this.ticksExisted % 5 == 0 && this.closestPlayer == null) {
                List<Entity> targets = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(d0, d0, d0));
                if (targets.size() > 0) {
                    targets.remove(caster);
                    double distance = Double.MAX_VALUE;
                    Iterator i$ = targets.iterator();

                    while (i$.hasNext()) {
                        Entity t = (Entity) i$.next();
                        if (!t.equals(caster)) {
                            double d = t.getDistanceSqToEntity(this);
                            if (d < distance) {
                                distance = d;
                                this.closestPlayer = (EntityPlayer) t;
                            }
                        }
                    }
                }
            }

            if (this.closestPlayer != null) {
                double d1 = (this.closestPlayer.posX - this.posX) / d0;
                double d2 = (this.closestPlayer.posY + (double) this.closestPlayer.getEyeHeight() - this.posY) / d0;
                double d3 = (this.closestPlayer.posZ - this.posZ) / d0;
                double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                double d5 = 1.0 - d4;
                if (d5 > 0.0) {
                    d5 *= d5;
                    this.motionX += d1 / d4 * d5 * 0.1;
                    this.motionY += d2 / d4 * d5 * 0.1;
                    this.motionZ += d3 / d4 * d5 * 0.1;
                }
            }
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;
        if (this.onGround) {
            f = 0.58800006F;
            Block i = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (!i.isAir(this.worldObj, MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ))) {
                f = i.slipperiness * 0.98F;
            }
        }

        this.motionX *= f;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= f;
        if (this.onGround) {
            this.motionY *= -0.8999999761581421;
        }

        ++this.orbAge;
        if (this.orbAge >= this.orbMaxAge) {
            performEffect();
            this.setDead();
        }

    }

    public void performEffect(){
        switch (type){
            case 0:{
                if (!worldObj.isRemote) {
                    if (this.worldObj.rand.nextInt(10) > 8) {
                        worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, posX, posY, posZ));
                    }
                }
                break;
            }
            case 1:{
                if (!worldObj.isRemote) {
                    if (this.worldObj.rand.nextInt(10) > 8) {
                        this.worldObj.newExplosion((Entity) null, this.posX, this.posY, this.posZ, 1.5F, true, false);
                    }
                }
                break;
            }
            case 2:{
                if (!worldObj.isRemote){
                    int x = MathHelper.floor_double(posX);
                    int y = MathHelper.floor_double(posY) - 1;
                    int z = MathHelper.floor_double(posZ);
                    ItemDye.applyBonemeal(new ItemStack(Items.dye, 1, 15), worldObj, x, y, z, (EntityPlayer) caster);
                    ItemDye.applyBonemeal(new ItemStack(Items.dye, 1, 15), worldObj, x, y + 1, z, (EntityPlayer) caster);
                }
                break;
            }
            case 3:{
                if (!worldObj.isRemote) {
                    int x = MathHelper.floor_double(posX);
                    int y = MathHelper.floor_double(posY);
                    int z = MathHelper.floor_double(posZ);
                    Block block = worldObj.getBlock(x, y, z);
                    if (block.isReplaceable(worldObj, x, y, z)){
                        worldObj.setBlock(x, y, z, Blocks.dirt);
                    }
                }
                break;
            }
            case 4:
            {
                if (!worldObj.isRemote) {
                    List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.5F, 1.5F, 1.5F));
                    if (!list.isEmpty()) {
                        if (list.get(0).getEntityAttribute(SharedMonsterAttributes.attackDamage) != null) {
                            float f = (float) list.get(0).getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                            list.get(0).attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) caster).setMagicDamage(), f);
                        }
                    }
                }
                break;
            }
            case 5:{
                if (!worldObj.isRemote) {
                    List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.5F, 1.5F, 1.5F));
                    if (!list.isEmpty()) {
                        list.get(0).addPotionEffect(new PotionEffect(Potion.wither.id, 90, 50));
                    }
                }
                break;
            }
            case 7:{
                if (!worldObj.isRemote) {
                    List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.5F, 1.5F, 1.5F));
                    if (!list.isEmpty()) {
                        EntityLivingBase e = list.get(0);
                        e.setFire(50000);
                        e.addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 5));
                        e.addPotionEffect(new PotionEffect(Config.potionInfVisExhaustID, 200, 10));
                    }
                }
                break;
            }
            case 8:{
                List<Entity> projs = worldObj.getEntitiesWithinAABB(Entity.class, this.boundingBox.expand(10.0, 10.0, 10.0));
                if (!worldObj.isRemote) {
                    for (Entity e : projs) {
                        if (e instanceof IProjectile) {
                            worldObj.spawnEntityInWorld(new ShardPowderEntity(caster, e.posX, e.posY, e.posZ, 8));
                            e.setDead();
                        }
                    }
                }
                break;
            }
            case 9:{
                if (!worldObj.isRemote) {
                    List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.5F, 1.5F, 1.5F));
                    if (!list.isEmpty()) {
                        EntityLivingBase e = list.get(0);
                        if (e instanceof EntityPlayer){
                            Thaumcraft.proxy.getPlayerKnowledge().addWarpTemp(e.getCommandSenderName(), 20);
                        } else {
                            ((EntityLivingBase) e).addPotionEffect(new PotionEffect(Config.potionTaintPoisonID, 80, 50));
                        }
                    }
                }
                break;
            }
            case 10:{
                if (!worldObj.isRemote) {

                    List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.5F, 1.5F, 1.5F));
                    if (!list.isEmpty()) {
                        EntityLivingBase e = list.get(0);
                        Vector3 target = new Vector3(e.posX - this.posX, e.posY, e.posZ - this.posZ).multiply(2.0, 1.0, 2.0);
                        MiscHelper.setEntityMotionFromVector(e, target, 3.0F);
                    }
                }
                break;
            }
            case 11:{
                if (!worldObj.isRemote){
                    if (worldObj.rand.nextInt(10) > 8) {
                        EntityTaintacleSmall taintacle = new EntityTaintacleSmall(worldObj);
                        taintacle.setPosition(posX, posY, posZ);
                        worldObj.spawnEntityInWorld(taintacle);
                        List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, taintacle.boundingBox.expand(20.5F, 2.5F, 20.5F));
                        list.remove(taintacle);
                        if (!list.isEmpty()) {
                            int i = -1;
                            for (EntityLivingBase e : list){
                                if (!(e instanceof ITaintedMob)){
                                    i = list.indexOf(e);
                                    break;
                                }
                            }
                            if (i != -1) {
                                taintacle.setTarget(list.get(i));
                            }
                        }
                    }
                }
                break;
            }
            case 12:{
                if (!worldObj.isRemote) {

                    List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.5F, 1.5F, 1.5F));
                    if (!list.isEmpty()) {
                        EntityLivingBase e = list.get(0);

                        ((EntityLivingBase) e).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 15));
                        ((EntityLivingBase) e).addPotionEffect(new PotionEffect(Config.potionInfVisExhaustID, 60, 20));
                    }
                }
                break;
            }
            case 13:{
                if (!worldObj.isRemote) {

                    List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.5F, 1.5F, 1.5F));
                    if (!list.isEmpty()) {
                        EntityLivingBase e = list.get(0);
                        MiscHelper.setEntityMotionFromVector(e, new Vector3(this.posX, this.posY, this.posZ), 3.0F);
                    }
                }
                break;
            }
        }
    }


    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    protected void dealFireDamage(int par1) {
        this.attackEntityFrom(DamageSource.inFire, (float)par1);
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else {
            this.setBeenAttacked();
            this.orbHealth = (int)((float)this.orbHealth - par2);
            if (this.orbHealth <= 0) {
                this.setDead();
            }

            return false;
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("Health", (byte)this.orbHealth);
        par1NBTTagCompound.setShort("Age", (short)this.orbAge);
        par1NBTTagCompound.setInteger("Type", this.type);

    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.orbHealth = par1NBTTagCompound.getShort("Health") & 255;
        this.orbAge = par1NBTTagCompound.getShort("Age");
        this.type = par1NBTTagCompound.getInteger("Type");
    }

    public void onCollideWithPlayer(EntityPlayer player) {
        if (!this.worldObj.isRemote) {
            //if (!par1EntityPlayer.equals(caster)) {
            if (this.orbCooldown == 0 && player.xpCooldown == 0 && !player.isEntityInvulnerable()) {
                switch (type){
                    case 9:{
                        ThaumcraftApiHelper.consumeVisFromInventory(player, Util.getPrimals(10000));
                        break;
                    }
                    case 14:{
                        player.getFoodStats().addStats(1, 1.0F);
                        break;
                    }
                }
                player.xpCooldown = 2;
                //this.playSound("random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
                this.setDead();
            }
            // }
        }

    }
    public boolean canAttackWithItem() {
        return false;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.type);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        try {
            this.type = additionalData.readInt();
        } catch (Exception e){}
    }
}