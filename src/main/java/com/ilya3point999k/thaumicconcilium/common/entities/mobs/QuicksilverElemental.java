package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.QuicksilverCrucibleTile;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketResearchComplete;
import thaumcraft.common.tiles.TileCrucible;

import java.util.Collection;
import java.util.List;

public class QuicksilverElemental extends EntityMob {
    public QuicksilverElemental(World w) {
        super(w);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.6D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityCultist.class, 0, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.7D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void addRandomArmor() {
    }

    @Override
    public float getShadowSize() {
        return getHealth() / 30.0F;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public EntityLivingBase getAttackTarget() {
        return super.getAttackTarget();
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        return super.onSpawnWithEgg(par1EntityLivingData);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int r = this.rand.nextInt(2);
        r += i;
        this.entityDropItem(new ItemStack(ConfigItems.itemResource, r, 3), 1.5F);
        super.dropFewItems(flag, i);
    }
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float f) {
        if (!source.isFireDamage()) return false;
        int x = MathHelper.floor_double(posX);
        int y = MathHelper.floor_double(posY);
        int z = MathHelper.floor_double(posZ);
        TileEntity tile = this.worldObj.getTileEntity(x, y, z);
        if (tile instanceof TileCrucible) {
            worldObj.setBlockToAir(x, y, z);
            worldObj.removeTileEntity(x, y, z);
            QuicksilverCrucibleTile crucible = new QuicksilverCrucibleTile();
            worldObj.setBlock(x, y, z, TCBlockRegistry.QUICKSILVER_CRUCIBLE);
            worldObj.setTileEntity(x, y, z, crucible);
            QuicksilverCrucibleTile placed = (QuicksilverCrucibleTile) worldObj.getTileEntity(x, y, z);
            placed.aspects.add(Aspect.EXCHANGE, 20);
            placed.markDirty();
            worldObj.markBlockForUpdate(x, y, z);

            List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(16.0, 16.0, 16.0));
            if (!players.isEmpty()) {
                for (EntityPlayer player : players) {
                    if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "QUICKSILVERCRUCIBLE")) {
                        Thaumcraft.proxy.getResearchManager().completeResearch(player, "QUICKSILVERCRUCIBLE");
                        PacketHandler.INSTANCE.sendTo(new PacketResearchComplete("QUICKSILVERCRUCIBLE"), (EntityPlayerMP) player);
                        player.worldObj.playSoundAtEntity(player, "thaumcraft:heartbeat", 1.0F, 1.0F);
                    }
                }
            }

            this.setDead();
        }
        return super.attackEntityFrom(source, f);
    }

    @Override
    public boolean attackEntityAsMob(Entity e) {
        if (e instanceof EntityLivingBase) {
            if (((EntityLivingBase) e).getEntityAttribute(SharedMonsterAttributes.attackDamage) != null) {
                float f = (float) ((EntityLivingBase) e).getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                return e.attackEntityFrom(DamageSource.anvil, f);
            } else {
                return e.attackEntityFrom(DamageSource.anvil, (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
            }
        }
        this.swingItem();
        return super.attackEntityAsMob(e);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.setSize(0.6F * (getHealth() / 30.0F), 1.8F * (getHealth() / 30.0F));

    }

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID + ":melted";
    }

    @Override
    protected String getDeathSound() {
        return ThaumicConcilium.MODID + ":melted";
    }

    protected float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public int getTalkInterval() {
        return 65;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            if ((ticksExisted % 40 == 0) && (getAttackTarget() != null)) {
                EntityLivingBase target = getAttackTarget();
                PotionEffect effect = new PotionEffect(Config.potionInfVisExhaustID, 1000, 20);
                effect.getCurativeItems().clear();
                target.addPotionEffect(effect);

                Collection<PotionEffect> potions = target.getActivePotionEffects();
                if (!potions.isEmpty()) {

                    for (PotionEffect potion : potions) {
                        int id = potion.getPotionID();
                        boolean badEffect = ReflectionHelper.getPrivateValue(Potion.class, Potion.potionTypes[id], new String[]{"isBadEffect", "field_76418_K"});
                        if (!badEffect) {
                            target.removePotionEffect(id);
                            break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
    }
}