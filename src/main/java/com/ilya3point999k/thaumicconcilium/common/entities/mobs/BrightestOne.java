package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.Shadowbeam;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ShardPowderEntity;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketMakeHole;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumcraft.common.entities.projectile.EntityEmber;
import thaumcraft.common.entities.projectile.EntityGolemOrb;
import thaumcraft.common.items.ItemWispEssence;
import thaumcraft.common.items.wands.foci.ItemFocusPortableHole;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXWispZap;
import thaumcraft.common.lib.utils.EntityUtils;

import java.awt.*;
import java.util.List;

public class BrightestOne extends EntityFlying implements IBossDisplayData, IMob {
    public int courseChangeCooldown = 0;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity = null;
    private int aggroCooldown = 0;
    public int prevAttackCounter = 0;
    public int attackCounter = 0;
    public boolean continuousAttack = false;
    static final String[] tags = {"ignis", "aer", "vacuos", "tenebrae"};

    public BrightestOne(World world) {
        super(world);
        this.setSize(3.0F, 3.0F);
        this.experienceValue = 5;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(25.0);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public int decreaseAirSupply(int par1) {
        return par1;
    }

    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (damagesource.getSourceOfDamage() instanceof BrightestOne) return false;
        if (damagesource.getEntity() instanceof BrightestOne) return false;

        if (damagesource.getSourceOfDamage() instanceof EntityLivingBase) {
            this.targetedEntity = (EntityLivingBase)damagesource.getSourceOfDamage();
            this.aggroCooldown = 200;
        }

        if (damagesource.getEntity() instanceof EntityLivingBase) {
            this.targetedEntity = (EntityLivingBase)damagesource.getEntity();
            this.aggroCooldown = 200;
        }
        return false;
        //return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public void setHealth(float p_70606_1_) {
        this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(this.getMaxHealth(), 0.0F, this.getMaxHealth())));
    }


    public void shortCircuit(){
        this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(this.getHealth() - 1.0F, 0.0F, this.getMaxHealth())));
        if (this.getHealth() == 0){
            dropFewItems(true, 1);
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(22, String.valueOf(""));
    }

    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        if (this.worldObj.isRemote) {
            Thaumcraft.proxy.burst(this.worldObj, this.posX, this.posY + 0.44999998807907104, this.posZ, 4.0F);
        }
        if (!this.worldObj.isRemote) {
            dropFewItems(true, 0);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    public void handleHealthUpdate(byte p_70103_1_) {
        super.handleHealthUpdate(p_70103_1_);
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote && this.ticksExisted <= 1) {
            Thaumcraft.proxy.burst(this.worldObj, this.posX, this.posY + 0.44999998807907104, this.posZ, 1.0F);
        }

        if (this.worldObj.isRemote && this.worldObj.rand.nextBoolean() && Aspect.getAspect(this.getType()) != null) {
            Color color = new Color(Aspect.getAspect(this.getType()).getColor());
            Thaumcraft.proxy.wispFX(this.worldObj, this.posX + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F), this.posY + 0.44999998807907104 + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F), this.posZ + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F), 0.1F, (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F);
        }

    }

    public String getType() {
        return this.dataWatcher.getWatchableObjectString(22);
    }

    public void setType(String t) {
        this.dataWatcher.updateObject(22, String.valueOf(t));
    }
    public void changeType(){
        this.dataWatcher.updateObject(22, String.valueOf(tags[this.worldObj.rand.nextInt(tags.length)]));
    }

    @Override
    public void setFire(int p_70015_1_) {
    }

    protected void updateEntityActionState() {
        if (!worldObj.isRemote && Aspect.getAspect(this.getType()) == null){
            this.setType(Aspect.FIRE.getTag());
            //this.dataWatcher.updateObject(6, Float.valueOf(this.getMaxHealth()));
        }
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting.getDifficultyId() == 0) {
            this.setDead();
        }

        this.despawnEntity();
        this.prevAttackCounter = this.attackCounter;
        double attackrange = 16.0;
        double d = this.waypointX - this.posX;
        double d1 = this.waypointY - this.posY;
        double d2 = this.waypointZ - this.posZ;
        double d3 = d * d + d1 * d1 + d2 * d2;
        if (d3 < 1.0 || d3 > 3600.0) {
            this.waypointX = this.posX + (double)(this.rand.nextFloat() * 2.0F - 1.0F) * 16.0;
            this.waypointY = this.posY + (double)(this.rand.nextFloat() * 2.0F - 1.0F) * 16.0;
            this.waypointZ = this.posZ + (double)(this.rand.nextFloat() * 2.0F - 1.0F) * 16.0;
        }

        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
            d3 = (double)MathHelper.sqrt_double(d3);
            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                this.motionX += d / d3 * 0.1;
                this.motionY += d1 / d3 * 0.1;
                this.motionZ += d2 / d3 * 0.1;
            } else {
                this.waypointX = this.posX;
                this.waypointY = this.posY;
                this.waypointZ = this.posZ;
            }
        }

        if (this.targetedEntity != null && this.targetedEntity.isDead) {
            this.targetedEntity = null;
        }

        --this.aggroCooldown;
        if ((this.targetedEntity == null || this.aggroCooldown-- <= 0)) {
            this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0);
            if (this.targetedEntity != null) {
                this.aggroCooldown = 50;
            }
        }

        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < attackrange * attackrange) {
            double d5 = this.targetedEntity.posX - this.posX;
            double d6 = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
            double d7 = this.targetedEntity.posZ - this.posZ;
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(d5, d7)) * 180.0F / 3.141593F;
            if (this.canEntityBeSeen(this.targetedEntity)) {
                ++this.attackCounter;
                if (continuousAttack && attackCounter < 10){
                    switch (getType()){
                        case "aer":{
                            Vec3 v = this.getLook(1.0F);
                            Entity e = targetedEntity;
                            if (!worldObj.isRemote) {
                                    EntityGolemOrb blast = new EntityGolemOrb(this.worldObj, (EntityLivingBase) this, (EntityLivingBase) targetedEntity, false);
                                    blast.posX += v.xCoord + (-0.5F + worldObj.rand.nextFloat()) * 5.0F;
                                    blast.posY += 0.5f;
                                    blast.posZ += v.zCoord + (-0.5F + worldObj.rand.nextFloat()) * 5.0F;
                                    blast.setPosition(blast.posX, blast.posY, blast.posZ);
                                    double tx = e.posX + e.motionX - this.posX;
                                    double ty = e.posY - this.posY - (double) (e.height / 2.0F);
                                    double tz = e.posZ + e.motionZ - this.posZ;
                                    blast.setThrowableHeading(tx, ty, tz, 0.66F, 5.0F);
                                    this.playSound("thaumcraft:egattack", 1.0F, 1.0F + this.worldObj.rand.nextFloat() * 0.1F);
                                    this.worldObj.spawnEntityInWorld(blast);
                            }
                            break;
                        }
                        case "ignis":{
                            float scatter = 15.0F;
                            if (!this.worldObj.isRemote) {
                                for (int i = 0; i < 30; i++) {
                                    EntityEmber orb = new EntityEmber(this.worldObj, this, scatter);
                                    orb.damage = (float) 25;
                                    orb.motionX *= -0.5f + Math.random();
                                    orb.motionY *= Math.random();
                                    orb.motionZ *= -0.5f + Math.random();
                                    orb.firey = 0;
                                    orb.posX += orb.motionX * (8.0f + Math.random());
                                    orb.posY += orb.motionY * (8.0f + Math.random());
                                    orb.posZ += orb.motionZ * (8.0f + Math.random());
                                    this.worldObj.spawnEntityInWorld(orb);
                                }
                            }
                            break;
                        }
                        case "tenebrae":{
                            if (!worldObj.isRemote) {
                                this.faceEntity(targetedEntity, 100.0F, 100.0F);
                                Shadowbeam beam = new Shadowbeam(worldObj, this, 20);
                                beam.updateUntilDead();
                            }
                            break;
                        }
                    }
                } else if(attackCounter > 10){
                    continuousAttack = false;
                }
                if (this.attackCounter == 20) {
                    switch (getType()){
                        case "aer":{
                            if ((worldObj.rand.nextInt(10) % 2) == 0) {
                                this.worldObj.playSoundAtEntity(this, "thaumcraft:zap", 1.0F, 1.1F);
                                PacketHandler.INSTANCE.sendToAllAround(new PacketFXWispZap(this.getEntityId(), this.targetedEntity.getEntityId()), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 32.0));
                                float damage = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                                this.targetedEntity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
                            } else {
                                continuousAttack = true;
                            }
                            break;
                        }
                        case "ignis":{
                            int rand = worldObj.rand.nextInt(6);
                            Vec3 look = this.getLook(1.0F);
                            if (rand % 2 == 0){
                                if (!worldObj.isRemote) {
                                    for (int i = 0; i < 10; i++) {
                                        ShardPowderEntity orb = new ShardPowderEntity(this, this.posX + look.xCoord, this.posY + this.getEyeHeight(), this.posZ + look.zCoord, 1);
                                        orb.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.32F);
                                        orb.posY -= 0.3;
                                        orb.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.32F);
                                        orb.setPosition(orb.posX, orb.posY, orb.posZ);
                                        orb.motionX = look.xCoord * Math.random();
                                        orb.motionY = look.yCoord;
                                        orb.motionZ = look.zCoord * Math.random();
                                        this.worldObj.spawnEntityInWorld(orb);
                                    }
                                }
                            }
                            else {
                                continuousAttack = true;
                            }
                            break;
                        }
                        case "vacuos":{
                            int rand = worldObj.rand.nextInt(10);
                            if (rand % 2 == 0){
                                ItemFocusPortableHole.createHole(targetedEntity.worldObj, MathHelper.floor_double(targetedEntity.posX), MathHelper.floor_double(targetedEntity.posY)-1, MathHelper.floor_double(targetedEntity.posZ), 1, (byte) 33, 120);
                                TCPacketHandler.INSTANCE.sendToAllAround(new PacketMakeHole(targetedEntity.posX, targetedEntity.posY, targetedEntity.posZ), new NetworkRegistry.TargetPoint(targetedEntity.worldObj.provider.dimensionId, targetedEntity.posX, targetedEntity.posY, targetedEntity.posZ, 32.0F));
                            } else {
                                this.worldObj.playSoundAtEntity(this, "thaumcraft:zap", 1.0F, 1.1F);
                                PacketHandler.INSTANCE.sendToAllAround(new PacketFXWispZap(this.getEntityId(), this.targetedEntity.getEntityId()), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 32.0));
                                float damage = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                                this.targetedEntity.attackEntityFrom(DamageSource.outOfWorld, damage / 4);
                            }
                            break;
                        }
                        case "tenebrae":{
                            int rand = worldObj.rand.nextInt(10);
                            if (rand % 2 == 0){
                                continuousAttack = true;
                            } else {
                                List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(32.0F, 32.0F, 32.0F));
                                if (!players.isEmpty()){
                                    for (EntityPlayer p : players){
                                        PotionEffect effect = new PotionEffect(Potion.blindness.id, 600, 30);
                                        effect.getCurativeItems().clear();
                                        p.addPotionEffect(effect);
                                    }
                                }
                            }
                        }
                    }
                    List<EntityWisp> list = worldObj.getEntitiesWithinAABB(EntityWisp.class, this.boundingBox.expand(32.0, 32.0, 32.0));
                    if (list.isEmpty() || list.size() < 8) {
                        int wisps = worldObj.rand.nextInt(3) + 3;
                        if (!worldObj.isRemote && worldObj.rand.nextInt(3) == 2) {
                            for (int i = 0; i < wisps; i++) {
                                EntityWisp wisp = new EntityWisp(worldObj);
                                wisp.setPositionAndRotation(this.posX, this.posY, this.posZ, this.worldObj.rand.nextFloat(), this.worldObj.rand.nextFloat());
                                wisp.setType((String) tags[this.worldObj.rand.nextInt(tags.length)]);
                                this.worldObj.spawnEntityInWorld(wisp);
                            }
                        }
                    }
                    this.attackCounter = -20 + this.worldObj.rand.nextInt(20);
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        } else {
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / 3.141593F;
            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }

    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
        double d4 = (this.waypointX - this.posX) / d3;
        double d5 = (this.waypointY - this.posY) / d3;
        double d6 = (this.waypointZ - this.posZ) / d3;
        AxisAlignedBB axisalignedbb = this.boundingBox.copy();

        int x;
        for(x = 1; (double)x < d3; ++x) {
            axisalignedbb.offset(d4, d5, d6);
            if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        x = (int)this.waypointX;
        int y = (int)this.waypointY;
        int z = (int)this.waypointZ;
        if (this.worldObj.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        } else {
            for(int a = 0; a < 11; ++a) {
                if (!this.worldObj.isAirBlock(x, y - a, z)) {
                    return true;
                }
            }

            return false;
        }
    }

    protected String getLivingSound() {
        return "thaumcraft:wisplive";
    }

    protected String getHurtSound() {
        return "random.fizz";
    }

    protected String getDeathSound() {
        return "thaumcraft:wispdead";
    }

    protected Item getDropItem() {
        return Item.getItemById(0);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        if (Aspect.getAspect(this.getType()) != null) {
            ItemStack ess = new ItemStack(ConfigItems.itemWispEssence);
            new AspectList();
            ((ItemWispEssence)ess.getItem()).setAspects(ess, (new AspectList()).add(Aspect.getAspect(this.getType()), 2));
            this.entityDropItem(ess, 0.0F);
        }
        EntityUtils.entityDropSpecialItem(this, new ItemStack(TCItemRegistry.resource, 1, 1), this.height / 2.0F);

    }

    public void setInWeb() {
    }

    public boolean canPickUpLoot() {
        return false;
    }

    protected void addRandomArmor() {
    }

    protected void enchantEquipment() {
    }
    protected float getSoundVolume() {
        return 0.25F;
    }

    protected boolean canDespawn() {
        return false;
    }


    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setString("Type", this.getType());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.setType(nbttagcompound.getString("Type"));
    }




}
