package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.entities.monster.EntityCultistCleric;
import thaumcraft.common.entities.monster.EntityCultistKnight;
import thaumcraft.common.lib.utils.EntityUtils;

import java.util.List;

public class LesserPortal extends EntityMob {
    int stage = 0;
    public int stagecounter = 200;
    public int pulse = 0;
    public boolean boss = false;

    public LesserPortal(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
        this.experienceValue = 30;
        this.setSize(1.5F, 3.0F);
    }

    public int getTotalArmorValue() {
        return 5;
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("stage", this.stage);
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.stage = nbt.getInteger("stage");
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0);
    }

    public float getShadowSize() {
        return 0.0F;
    }

    public boolean canBeCollidedWith() {
        return true;
    }
    public boolean canPickUpLoot() {
        return false;
    }

    public boolean canBePushed() {
        return false;
    }

    public void moveEntity(double par1, double par3, double par5) {
    }

    protected void updateEntityActionState() {
    }

    public boolean isInRangeToRenderDist(double par1) {
        return par1 < 4096.0;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {
        return 15728880;
    }

    public float getBrightness(float par1) {
        return 1.0F;
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            int a;
            if (this.stagecounter <= 0) {
                if (this.worldObj.getClosestPlayerToEntity(this, 48.0) != null) {
                    this.worldObj.setEntityState(this, (byte)16);
                    switch (this.stage) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            this.stagecounter = 15 + this.rand.nextInt(10 - this.stage) - this.stage;
                            this.spawnMinions();
                            break;
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        default:
                            a = this.getTiming();
                            this.stagecounter = a + this.rand.nextInt(5 + a / 3);
                            this.spawnMinions();
                            break;
                        case 12:
                            this.stagecounter = 50 + this.getTiming() * 2 + this.rand.nextInt(50);
                            this.spawnMinions();
                            break;
                        case 13:
                            if (boss)
                                this.spawnBoss();
                            break;
                    }

                    ++this.stage;
                } else {
                    this.stagecounter = 30 + this.rand.nextInt(30);
                }
            } else {
                --this.stagecounter;
            }

            if (this.stage < 12) {
                this.heal(1.0F);
            }
        }

        if (this.pulse > 0) {
            --this.pulse;
        }

    }

    int getTiming() {
        List<Entity> l = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityCultist.class, 32.0);
        return l.size() * 20;
    }

    void spawnBoss(){
        CrimsonPontifex boss = new CrimsonPontifex(worldObj);
        boss.setPosition(this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + 0.25, this.posZ + (double)this.rand.nextFloat() - (double)this.rand.nextFloat());
        this.worldObj.spawnEntityInWorld(boss);
        boss.addRandomArmor();
        boss.playSound("thaumcraft:wandfail", 1.0F, 1.0F);
        this.attackEntityFrom(DamageSource.outOfWorld, getHealth());
    }
    void spawnMinions() {
        EntityCultist cultist = null;
        int rand = this.rand.nextInt(5);
        switch (rand){
            case 0:{
                cultist = new EntityCultistKnight(this.worldObj);
                break;
            }
            case 1:{
                cultist = new EntityCultistCleric(this.worldObj);
                break;
            }
            case 2:{
                cultist = new CrimsonPaladin(this.worldObj);
                break;
            }
            case 3:{
                if(Integration.dyes){
                    cultist = new CrimsonArcher(this.worldObj);
                } else {
                    cultist = new EntityCultistKnight(this.worldObj);
                }
                break;
            }
            case 4:{
                if(Integration.dyes){
                    cultist = new CrimsonRanger(this.worldObj);
                } else {
                    cultist = new EntityCultistKnight(this.worldObj);
                }
                break;
            }
        }

        ((EntityCultist)cultist).setPosition(this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + 0.25, this.posZ + (double)this.rand.nextFloat() - (double)this.rand.nextFloat());
        ((EntityCultist)cultist).onSpawnWithEgg((IEntityLivingData)null);
        ((EntityCultist)cultist).spawnExplosionParticle();
        ((EntityCultist)cultist).setHomeArea((int)this.posX, (int)this.posY, (int)this.posZ, 32);
        this.worldObj.spawnEntityInWorld((Entity)cultist);
        ((EntityCultist)cultist).playSound("thaumcraft:wandfail", 1.0F, 1.0F);
        if (this.stage > 12) {
            this.attackEntityFrom(DamageSource.outOfWorld, (float)(5 + this.rand.nextInt(5)));
        }

    }


    public void onCollideWithPlayer(EntityPlayer p) {
        if (this.getDistanceSqToEntity(p) < 3.0 && p.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this), 8.0F)) {
            this.playSound("thaumcraft:zap", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F);
        }

    }

    protected float getSoundVolume() {
        return 0.75F;
    }

    public int getTalkInterval() {
        return 540;
    }

    protected String getLivingSound() {
        return "thaumcraft:monolith";
    }

    protected String getHurtSound() {
        return "thaumcraft:zap";
    }

    protected String getDeathSound() {
        return "thaumcraft:shock";
    }

    protected Item getDropItem() {
        return Item.getItemById(0);
    }


    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte msg) {
        if (msg == 16) {
            this.pulse = 10;
            this.spawnExplosionParticle();
        } else {
            super.handleHealthUpdate(msg);
        }

    }

    public void addPotionEffect(PotionEffect p_70690_1_) {
    }

    protected void fall(float p_70069_1_) {
    }

    public void onDeath(DamageSource p_70645_1_) {
        if (!this.worldObj.isRemote) {
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F, false, false);
        }

        super.onDeath(p_70645_1_);
    }
}