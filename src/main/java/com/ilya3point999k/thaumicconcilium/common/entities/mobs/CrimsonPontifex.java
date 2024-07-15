package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ConcentratedWarpChargeEntity;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketEnslave;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
import thaumcraft.common.entities.ai.combat.AICultistHurtByTarget;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.entities.monster.EntityCultistCleric;
import thaumcraft.common.entities.monster.EntityCultistKnight;
import thaumcraft.common.entities.monster.boss.EntityCultistLeader;
import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
import thaumcraft.common.entities.monster.mods.ChampionModifier;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockArc;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.utils.EntityUtils;

import java.util.Iterator;
import java.util.List;

public class CrimsonPontifex extends EntityThaumcraftBoss implements IBossDisplayData {

    String[] titles = new String[]{"Ivius", "Ufarihm", "Ihith", "Pemonar", "Shagron", "Ugimaex", "Qroleus", "Oxon", "Rheforn", "Zubras"};
    private int attackCounter = 0;
    private int aggroCooldown = 0;
    private int cultists;
    private Entity targetedEntity = null;
    public boolean continuousAttack = false;


    public CrimsonPontifex(World p_i1738_1_) {
        super(p_i1738_1_);
        this.setSize(0.75F, 2.25F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new AIAttackOnCollide(this, EntityLivingBase.class, 0.7, false));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.8));
        this.tasks.addTask(7, new EntityAIWander(this, 0.8));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new AICultistHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
        this.experienceValue = 40;
    }
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.32);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(25.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(16, (byte)0);
    }
    public void generateName() {
        int t = (int)this.getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue();
        if (t >= 0) {
            this.setCustomNameTag(String.format(StatCollector.translateToLocal("entity.ThaumicConcilium.CrimsonPontifex.name"), this.getTitle(), ChampionModifier.mods[t].getModNameLocalized()));
        }

    }
    private String getTitle() {
        return this.titles[this.getDataWatcher().getWatchableObjectByte(16)];
    }

    private void setTitle(int title) {
        this.dataWatcher.updateObject(16, (byte)title);
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("title", this.getDataWatcher().getWatchableObjectByte(16));
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setTitle(nbt.getByte("title"));
    }
    public boolean isOnSameTeam(EntityLivingBase el) {
        return el instanceof EntityCultist || el instanceof EntityCultistLeader || el instanceof CrimsonPontifex;
    }
    public boolean canPickUpLoot() {
        return false;
    }

    public boolean canAttackClass(Class clazz) {
        return clazz != EntityCultistCleric.class && clazz != EntityCultistLeader.class && clazz != EntityCultistKnight.class ? super.canAttackClass(clazz) : false;
    }

    protected Item getDropItem() {
        return Item.getItemById(0);
    }

    protected void dropFewItems(boolean flag, int i) {
        this.entityDropItem(new ItemStack(ConfigItems.itemLootbag, 1, 2), 1.5F);
        int r = this.rand.nextInt(10);
        if (r <= 3) {
            if (Integration.witchery) {
                EntityUtils.entityDropSpecialItem(this, ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "CRIMSONSPELLS", this.worldObj), this.height / 2.0F);
            } else {
                EntityUtils.entityDropSpecialItem(this, ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "MATERIALPEELER", this.worldObj), this.height / 2.0F);
            }
        }else if(r <= 5){
            EntityUtils.entityDropSpecialItem(this, ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "PONTIFEXHAMMER", this.worldObj), this.height / 2.0F);
        }
        else if(r <= 8){
            EntityUtils.entityDropSpecialItem(this, ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "MATERIALPEELER", this.worldObj), this.height / 2.0F);
        }
        else {
            EntityUtils.entityDropSpecialItem(this, ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "PONTIFEXROBE", this.worldObj), this.height / 2.0F);
        }
    }

    protected void dropRareDrop(int p_70600_1_) {
    }

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID+":chant";
    }

    @Override
    protected String getDeathSound() {
        return ThaumicConcilium.MODID+":growl";
    }

    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public int getTalkInterval() {
        return 450;
    }

    @Override
    protected void addRandomArmor() {
        this.setCurrentItemOrArmor(0, new ItemStack(TCItemRegistry.pontifexHammer));
        this.setCurrentItemOrArmor(4, new ItemStack(TCItemRegistry.pontifexHead));
        this.setCurrentItemOrArmor(3, new ItemStack(TCItemRegistry.pontifexChest));
        this.setCurrentItemOrArmor(2, new ItemStack(TCItemRegistry.pontifexLegs));
        this.setCurrentItemOrArmor(1, new ItemStack(TCItemRegistry.pontifexFeet));

    }
    protected void enchantEquipment() {
        float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        if (this.getHeldItem() != null && this.rand.nextFloat() < 0.5F * f) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(7.0F + f * (float)this.rand.nextInt(22)));
        }

    }
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        this.addRandomArmor();
        this.enchantEquipment();
        this.setTitle(this.rand.nextInt(this.titles.length));
        return super.onSpawnWithEgg(p_110161_1_);
    }

    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (damagesource.getSourceOfDamage() instanceof EntityLivingBase) {
            this.targetedEntity = (EntityLivingBase)damagesource.getSourceOfDamage();
            this.aggroCooldown = 200;
        }

        if (damagesource.getEntity() instanceof EntityLivingBase) {
            this.targetedEntity = (EntityLivingBase)damagesource.getEntity();
            this.aggroCooldown = 200;
        }
        i /= (cultists + 3);
        return super.attackEntityFrom(damagesource, i);
    }


    protected void updateAITasks() {
        super.updateAITasks();
        List<Entity> list = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityCultist.class, 8.0);
        if (!list.isEmpty()){
            cultists = list.size();
        }
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            Entity e = (Entity)i$.next();

            try {
                if (e instanceof EntityCultist && !((EntityCultist)e).isPotionActive(Potion.regeneration.id)) {
                    ((EntityCultist)e).addPotionEffect(new PotionEffect(Potion.regeneration.id, 60, 1));
                }
            } catch (Exception var5) {
            }
        }
        double attackrange = 16.0;
        if (this.targetedEntity != null && this.targetedEntity.isDead) {
            this.targetedEntity = null;
        }

        --this.aggroCooldown;
        if (this.worldObj.rand.nextInt(10) > 7 && (this.targetedEntity == null || this.aggroCooldown-- <= 0)) {
            this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 32.0);
            if (this.targetedEntity != null) {
                this.aggroCooldown = 50;
            }
        }

        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < attackrange * attackrange) {
            if (this.canEntityBeSeen(this.targetedEntity)) {
                ++this.attackCounter;
                if (continuousAttack && attackCounter < 10){
                } else if(attackCounter > 10){
                    if (!worldObj.isRemote) {
                        if (targetedEntity instanceof EntityPlayer) {
                            TCPacketHandler.INSTANCE.sendTo(new PacketEnslave(targetedEntity.getCommandSenderName(), false), (EntityPlayerMP)targetedEntity);
                        }
                    }
                    continuousAttack = false;
                }
                if (this.attackCounter == 20) {
                    if (targetedEntity instanceof EntityPlayer){
                        if (!worldObj.isRemote) {
                            int rand = worldObj.rand.nextInt(4);
                            switch (rand){
                                case 0:{
                                    boolean found = false;
                                    List<ConcentratedWarpChargeEntity> charges = worldObj.getEntitiesWithinAABB(ConcentratedWarpChargeEntity.class, targetedEntity.boundingBox.expand(7.0, 7.0, 7.0));
                                    if (!charges.isEmpty()) {
                                        for (ConcentratedWarpChargeEntity e : charges) {

                                            if (e.getOwner().getCommandSenderName().equals(targetedEntity.getCommandSenderName())) {
                                                found = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!found) {
                                        ConcentratedWarpChargeEntity charge = new ConcentratedWarpChargeEntity(targetedEntity.posX + (-0.5 + worldObj.rand.nextFloat()) * 4.0F, targetedEntity.posY, targetedEntity.posZ + (-0.5 + worldObj.rand.nextFloat()) * 4.0F, (EntityPlayer) targetedEntity);
                                        charge.setOwner(targetedEntity.getCommandSenderName());
                                        worldObj.spawnEntityInWorld(charge);
                                    }

                                    break;
                                }
                                case 1:{
                                    if (targetedEntity instanceof EntityPlayer) {
                                        String name = targetedEntity.getCommandSenderName();
                                        TCPacketHandler.INSTANCE.sendTo(new PacketEnslave(name, true), (EntityPlayerMP) targetedEntity);
                                        continuousAttack = true;
                                    }
                                    break;
                                }
                                case 2:{
                                    List<LesserPortal> portals = worldObj.getEntitiesWithinAABB(LesserPortal.class, this.boundingBox.expand(64.0, 64.0, 64.0));
                                    if (portals.size() < 3){
                                       if (!worldObj.isRemote){
                                           LesserPortal portal = new LesserPortal(worldObj);
                                           int a = (int)this.posX + this.rand.nextInt(5) - this.rand.nextInt(5);
                                           int b = (int)this.posZ + this.rand.nextInt(5) - this.rand.nextInt(5);
                                           if (a != (int)this.posX && b != (int)this.posZ && this.worldObj.isAirBlock(a, (int)this.posY, b)) {
                                               portal.setLocationAndAngles(a, posY, b, worldObj.rand.nextFloat(), worldObj.rand.nextFloat());
                                               worldObj.spawnEntityInWorld(portal);
                                               PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockArc(a, (int)this.posY, b, this.getEntityId()), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 32.0));
                                               this.playSound("thaumcraft:wandfail", 1.0F, 1.0F);
                                           }
                                       }
                                    }
                                    break;
                                }
                                case 3:{
                                    List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(6.0, 6.0, 6.0));
                                    list.remove(this);
                                    if (!list.isEmpty()) {
                                        for (EntityLivingBase e : entities) {
                                            if (!e.isDead && !(e instanceof EntityCultist)) {
                                                double x = e.posX;
                                                double y = e.posY;
                                                double z = e.posZ;
                                                if (!worldObj.isRemote) {
                                                    e.attackEntityFrom(DamageSource.magic, (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
                                                    this.heal(10.0F);
                                                } else {
                                                    for (int i = 0; i < 3; i++) {
                                                        ThaumicConcilium.proxy.lifedrain(this, x, y, z);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    this.attackCounter = -20 + this.worldObj.rand.nextInt(20);
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }

    }
}
