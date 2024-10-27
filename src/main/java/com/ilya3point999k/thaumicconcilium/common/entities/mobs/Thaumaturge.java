package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
import thaumcraft.common.entities.ai.combat.AILongRangeAttack;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.entities.projectile.EntityEmber;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.research.ResearchManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Thaumaturge extends EntityMob implements IRangedAttackMob {
	public boolean trading = false;
	public boolean updateAINextTick = false;
	private final EntityAIArrowAttack aiBlastAttack = new EntityAIArrowAttack(this, 1.0, 20, 40, 15.0F);
	private final AIAttackOnCollide aiMeleeAttack = new AIAttackOnCollide(this, EntityLivingBase.class, 0.6, false);
	static HashMap<Integer, Integer> valuedItems = new HashMap();
	public static ArrayList<List> tradeInventory = new ArrayList<>();

	public Thaumaturge(World world) {
		super(world);
		this.getNavigator().setBreakDoors(false);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new AILongRangeAttack(this, 3.0, 1.0, 20, 40, 24.0F));
		this.tasks.addTask(3, new AIAttackOnCollide(this, EntityLivingBase.class, 1.1, false));
		this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.5));
		this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0, false));
		this.tasks.addTask(9, new EntityAIWander(this, 0.6));
		this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		this.tasks.addTask(11, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCultist.class, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityBrainyZombie.class, 0, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, IEldritchMob.class, 0, true));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, ITaintedMob.class, 0, true));


		if (world != null && !world.isRemote) {
			this.setCombatTask();
		}
	}
	
	public Thaumaturge(World world, double x, double y, double z) {
		this(world);
		setPosition(x, y, z);
		//this.faceEntity();
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(14, new Short((short)0));
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	public String getCommandSenderName() {
		if (this.hasCustomNameTag()) {
			return this.getCustomNameTag();
		} else {
			return StatCollector.translateToLocal("entity.ThaumicConcilium.Thaumaturge.name");
			}
		}
	public boolean canPickUpLoot() {
		return false;
	}

	public void onUpdate() {
		if (this.getAnger() > 0) {
			this.setAnger(this.getAnger() - 1);
		}

		if (this.getAnger() > 0 && (this.entityToAttack == null || this.getAttackTarget() == null)) {
			this.findPlayerToAttack();
			this.setAttackTarget((EntityLivingBase)this.entityToAttack);
		}

		double d0;
		double d1;
		double d2;
		if (this.worldObj.isRemote && this.rand.nextInt(15) == 0 && this.getAnger() > 0) {
			d0 = this.rand.nextGaussian() * 0.02;
			d1 = this.rand.nextGaussian() * 0.02;
			d2 = this.rand.nextGaussian() * 0.02;
			this.worldObj.spawnParticle("angryVillager", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
		}
		super.onUpdate();
	}
	@Override
	protected void dropFewItems(boolean flag, int i) {
		int r = this.rand.nextInt(2);
		r += i;
		this.entityDropItem(new ItemStack(ConfigItems.itemResource, r, 9), 1.5F);
		super.dropFewItems(flag, i);
	}

	public int getAnger() {
		return this.dataWatcher.getWatchableObjectShort(14);
	}
	public void setAnger(int par1) {
		this.dataWatcher.updateObject(14, (short)par1);
	}
	@Override
	protected String getLivingSound() {
		return ThaumicConcilium.MODID+":think";
	}

	@Override
	public int getTalkInterval() {
		return 100;
	}

	protected Entity findPlayerToAttack() {
		return this.getAnger() == 0 ? null : super.findPlayerToAttack();
	}
	private void becomeAngryAt(Entity par1Entity) {
		this.entityToAttack = par1Entity;
		if (this.getAnger() <= 0) {
			 //this.worldObj.setEntityState(this, (byte)19);
			//this.playSound("thaumcraft:pech_charge", this.getSoundVolume(), this.getSoundPitch());
		}

		this.setAttackTarget((EntityLivingBase)par1Entity);
		this.setAnger(400 + this.rand.nextInt(400));
		this.updateAINextTick = true;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4);
	}

	public int getTotalArmorValue() {
		int i = super.getTotalArmorValue() + 2;
		if (i > 20) {
			i = 20;
		}

		return i;
	}

	public boolean getCanSpawnHere() {
		return true;
	}

	public boolean attackEntityFrom(DamageSource damSource, float par2) {
		if (this.isEntityInvulnerable()) {
			return false;
		} else {
			Entity entity = damSource.getEntity();
			if (entity instanceof EntityPlayer) {
				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0, 16.0, 32.0));

				for(int i = 0; i < list.size(); ++i) {
					Entity entity1 = (Entity)list.get(i);
					if (entity1 instanceof Thaumaturge) {
						Thaumaturge thaumaturge = (Thaumaturge) entity1;
						thaumaturge.becomeAngryAt(entity);
					}
				}
				this.becomeAngryAt(entity);
			}
			return super.attackEntityFrom(damSource, par2);
		}
	}

	@Override
	protected void addRandomArmor() {
		//super.addRandomArmor();
		ItemStack wand = new ItemStack(ConfigItems.itemWandCasting);
		ItemStack focus = new ItemStack(ConfigItems.itemFocusFire);
		((ItemWandCasting)wand.getItem()).setFocus(wand, focus);
		((ItemWandCasting)wand.getItem()).addVis(wand, Aspect.EARTH, 2 + this.rand.nextInt(6), true);
		((ItemWandCasting)wand.getItem()).addVis(wand, Aspect.ENTROPY, 2 + this.rand.nextInt(6), true);
		((ItemWandCasting)wand.getItem()).addVis(wand, Aspect.WATER, 2 + this.rand.nextInt(6), true);
		((ItemWandCasting)wand.getItem()).addVis(wand, Aspect.AIR, this.rand.nextInt(4), true);
		((ItemWandCasting)wand.getItem()).addVis(wand, Aspect.FIRE, 25, true);
		((ItemWandCasting)wand.getItem()).addVis(wand, Aspect.ORDER, this.rand.nextInt(4), true);
		this.setCurrentItemOrArmor(0, wand);
		this.equipmentDropChances[0] = 0.1F;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean interact(EntityPlayer player) {
		if (!player.isSneaking() && (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemNameTag))) {
			if (!this.worldObj.isRemote && this.getAnger() == 0) {
					if (ResearchManager.isResearchComplete(player.getCommandSenderName(), "THAUMATURGES")){
						player.openGui(ThaumicConcilium.instance, 0, this.worldObj, this.getEntityId(), 0, 0);
					} else {
						int r = rand.nextInt(4);
						player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.thaumaturge.taunt." + r)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
					}
					return true;
			} else {
				return super.interact(player);
			}
		} else {
			return false;
		}
	}
	public void updateAITasks() {
		if (this.updateAINextTick) {
			this.updateAINextTick = false;
			this.setCombatTask();
		}

		super.updateAITasks();
		if (this.ticksExisted % 40 == 0) {
			this.heal(1.0F);
		}

	}

	public void setCombatTask() {
		this.tasks.removeTask(this.aiBlastAttack);
		this.tasks.removeTask(this.aiMeleeAttack);
		ItemStack itemstack = this.getHeldItem();
		if (itemstack != null && itemstack.getItem() == ConfigItems.itemWandCasting) {
			this.tasks.addTask(1, this.aiBlastAttack);
		} else {
			this.tasks.addTask(2, this.aiMeleeAttack);
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float p_82196_2_) {
		for(int a = 0; a < 5; ++a) {
			EntityEmber orb = new EntityEmber(this.worldObj, this, 20.0f);
			orb.damage = 3.0f;
			orb.duration = 20;

			double d0 = entitylivingbase.posX + entitylivingbase.motionX - this.posX;
			double d1 = entitylivingbase.posY - this.posY;
			double d2 = entitylivingbase.posZ + entitylivingbase.motionZ - this.posZ;
			orb.setThrowableHeading(d0, d1, d2, 1.5F, 4.5F);
			orb.firey = 0;
			orb.posX += orb.motionX;
			orb.posY += orb.motionY;
			orb.posZ += orb.motionZ;
			this.worldObj.spawnEntityInWorld(orb);
		}
		this.playSound("thaumcraft:fireloop", 0.4F, 1.0F + this.rand.nextFloat() * 0.1F);
		this.swingItem();

	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
		this.addRandomArmor();
		this.tasks.addTask(1, this.aiBlastAttack);
		return super.onSpawnWithEgg(par1EntityLivingData);
	}

	public int getValue(ItemStack item) {
		if (item == null) {
			return 0;
		} else {
			int value = valuedItems.containsKey(Item.getIdFromItem(item.getItem())) ? valuedItems.get(Item.getIdFromItem(item.getItem())) : 0;
			return value;
		}
	}

	public boolean isValued(ItemStack item) {
		if (item == null) {
			return false;
		} else {
			boolean value = valuedItems.containsKey(Item.getIdFromItem(item.getItem()));
			return value;
		}
	}

	static {
		valuedItems.put(Item.getIdFromItem(ConfigItems.itemManaBean), 1);
		valuedItems.put(Item.getIdFromItem(Items.skull), 3);
		valuedItems.put(Item.getIdFromItem(ConfigItems.itemWandCasting), 3);
		valuedItems.put(Item.getIdFromItem(Items.experience_bottle), 3);
		valuedItems.put(Item.getIdFromItem(Items.enchanted_book), 5);
		valuedItems.put(Item.getIdFromItem(ConfigItems.itemResearchNotes), -1);

		tradeInventory.add(Arrays.asList(9, new ItemStack(ConfigItems.itemManaBean)));
		for(int a = 0; a < 6; ++a) {
			tradeInventory.add(Arrays.asList(10, new ItemStack(ConfigItems.itemShard, 1, a)));
		}
		tradeInventory.add(Arrays.asList(7, new ItemStack(ConfigItems.itemResource, 1, 9)));
		tradeInventory.add(Arrays.asList(7, new ItemStack(ConfigItems.itemResource, 1, 9)));
		for(int a = 0; a < 7; ++a) {
			tradeInventory.add(Arrays.asList(2, new ItemStack(ConfigBlocks.blockCrystal, 1, a)));
		}
		tradeInventory.add(Arrays.asList(1, new ItemStack(ConfigItems.itemFocusPouch)));
		tradeInventory.add(Arrays.asList(1, new ItemStack(ConfigItems.itemFocusFire)));
		tradeInventory.add(Arrays.asList(4, new ItemStack(ConfigItems.itemAmuletVis, 1, 0)));
		tradeInventory.add(Arrays.asList(1, new ItemStack(ForbiddenItems.wandCore, 1, 5)));
	}
}
