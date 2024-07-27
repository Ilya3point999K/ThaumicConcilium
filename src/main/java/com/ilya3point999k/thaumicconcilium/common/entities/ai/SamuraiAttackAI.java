package com.ilya3point999k.thaumicconcilium.common.entities.ai;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Samurai;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SamuraiAttackAI extends EntityAIBase {
    World worldObj;
    EntityCreature attacker;
    /**
     * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
     */
    int attackTick;
    /**
     * The speed with which the mob will approach the target
     */
    double speedTowardsTarget;
    /**
     * When true, the mob will continue chasing its target, even if it can't find a path to them right now.
     */
    boolean longMemory;
    /**
     * The PathEntity of our entity.
     */
    PathEntity entityPathEntity;
    Class classTarget;
    private int field_75445_i;
    private double field_151497_i;
    private double field_151495_j;
    private double field_151496_k;

    private int failedPathFindingPenalty;

    public SamuraiAttackAI(EntityCreature p_i1635_1_, Class p_i1635_2_, double p_i1635_3_, boolean p_i1635_5_) {
        this(p_i1635_1_, p_i1635_3_, p_i1635_5_);
        this.classTarget = p_i1635_2_;
    }

    public SamuraiAttackAI(EntityCreature p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
        this.attacker = p_i1636_1_;
        this.worldObj = p_i1636_1_.worldObj;
        this.speedTowardsTarget = p_i1636_2_;
        this.longMemory = p_i1636_4_;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.isEntityAlive()) {
            return false;
        } else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
            return false;
        } else {
            if (--this.field_75445_i <= 0) {
                this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
                this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
                if (this.entityPathEntity != null) {
                    ((Samurai) this.attacker).setAnger(0);
                }
                return false;
            } else {
                ((Samurai) this.attacker).setAnger(0);
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        if (((Samurai) this.attacker).getAnger() <= 200 && this.attacker.getHealth() > this.attacker.getMaxHealth() / 3) {
            ((Samurai) this.attacker).setAnger(MathHelper.clamp_int(200 + this.attacker.worldObj.rand.nextInt(200), 0, 1000));
        }
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.field_75445_i = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
        double d1 = (double) (this.attacker.width * 2.0F * this.attacker.width * 2.0F + entitylivingbase.width);
        --this.field_75445_i;

        if (this.attacker.getHealth() > this.attacker.getMaxHealth() / 2 && d0 > 64F && worldObj.rand.nextInt(10) > 7) {
            this.attacker.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10, 3));
        }
        if (this.attacker.getHealth() > this.attacker.getMaxHealth() / 3) {
            if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D || entitylivingbase.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
                this.field_151497_i = entitylivingbase.posX;
                this.field_151495_j = entitylivingbase.boundingBox.minY;
                this.field_151496_k = entitylivingbase.posZ;
                this.field_75445_i = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);

                if (this.attacker.getNavigator().getPath() != null) {
                    PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                    if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1) {
                        failedPathFindingPenalty = 0;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                } else {
                    failedPathFindingPenalty += 10;
                }

                if (d0 > 1024.0D) {
                    this.field_75445_i += 10;
                } else if (d0 > 256.0D) {
                    this.field_75445_i += 5;
                }

                if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
                    this.field_75445_i += 15;
                }
            }

            this.attackTick = Math.max(this.attackTick - 1, 0);

            if (d0 <= d1 && this.attackTick <= 20) {
                this.attackTick = 20;
                this.attacker.swingItem();


                this.attacker.attackEntityAsMob(entitylivingbase);
            }
        } else {
            Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.attacker, 16, 7, Vec3.createVectorHelper(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ));

            if (vec3 != null && (entitylivingbase.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) > entitylivingbase.getDistanceSqToEntity(this.attacker)))
            {
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
                this.attacker.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speedTowardsTarget);
                if (this.attacker.ticksExisted % 20 == 0 && d0 < 256F) {
                    if (!worldObj.isRemote) {
                        if (this.attacker.getEntitySenses().canSee(entitylivingbase)) {
                            this.attacker.worldObj.playSoundEffect(this.attacker.posX, this.attacker.posY, this.attacker.posZ, "thaumcraft:shock", 0.25F, 1.0F);
                            entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.attacker), 2 * (((Samurai) this.attacker).getType() + 1));
                            TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) this.attacker.posX, (float) (this.attacker.posY + this.attacker.height - 0.5), (float) this.attacker.posZ, (float) entitylivingbase.posX, (float) (entitylivingbase.posY + entitylivingbase.height / 2), (float) entitylivingbase.posZ, 0x6666DD, 0.02F), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.attacker.posX, this.attacker.posY, this.attacker.posZ, 32.0));
                        }
                    }
                }
            }
        }
    }
}
