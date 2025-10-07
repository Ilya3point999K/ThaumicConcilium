package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.RedPoweredMindTile;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class WitheredBotanist extends EntityLiving {
    public WitheredBotanist(World world) {
        super(world);
        setSize(2.0F, 0.3F);
    }

    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void addRandomArmor() {
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0);
    }


    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        return super.onSpawnWithEgg(par1EntityLivingData);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float f) {
        fertilize();
        this.setDead();
        return true;
    }

    @Override
    protected boolean interact(EntityPlayer p_70085_1_) {
        fertilize();
        this.setDead();
        return true;
    }

    public void fertilize(){
        final int range = 3;
        if(!this.worldObj.isRemote) {
            List<ChunkCoordinates> validCoords = new ArrayList();

            for(int i = -range - 1; i < range; i++)
                for(int j = -range - 1; j < range; j++) {
                    for(int k = 2; k >= -2; k--) {
                        int x = MathHelper.floor_double(posX) + i + 1;
                        int y = MathHelper.floor_double(posY) + k + 1;
                        int z = MathHelper.floor_double(posZ) + j + 1;
                        if(this.worldObj.isAirBlock(x, y, z) && (!this.worldObj.provider.hasNoSky || y < 255) && ModBlocks.flower.canBlockStay(this.worldObj, x, y, z))
                            validCoords.add(new ChunkCoordinates(x, y, z));
                    }
                }

            int flowerCount = Math.min(validCoords.size(), this.worldObj.rand.nextBoolean() ? 3 : 4);
            for(int i = 0; i < flowerCount; i++) {
                ChunkCoordinates coords = validCoords.get(this.worldObj.rand.nextInt(validCoords.size()));
                validCoords.remove(coords);
                this.worldObj.setBlock(coords.posX, coords.posY, coords.posZ, ModBlocks.flower, this.worldObj.rand.nextInt(16), 1 | 2);
            }
        } else {
            ThaumicConcilium.proxy.leaves(this);
            for(int i = 0; i < 15; i++) {
                double x = MathHelper.floor_double(posX) - range + this.worldObj.rand.nextInt(range * 2 + 1) + Math.random();
                double y = MathHelper.floor_double(posY) + 1;
                double z = MathHelper.floor_double(posZ) - range + this.worldObj.rand.nextInt(range * 2 + 1) + Math.random();
                float red = (float) Math.random();
                float green = (float) Math.random();
                float blue = (float) Math.random();
                Botania.proxy.wispFX(this.worldObj, x, y, z, red, green, blue, 0.15F + (float) Math.random() * 0.25F, -(float) Math.random() * 0.1F - 0.05F);
            }
        }
    }
}