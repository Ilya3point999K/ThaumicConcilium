package com.ilya3point999k.thaumicconcilium.common.dim;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderCausalBouillonDimension extends WorldProvider {

    @Override
    public void registerWorldChunkManager() {
        WorldChunkManager manager = new WorldChunkManagerHell(BiomeGenBase.sky, BiomeGenBase.sky.rainfall);
        this.worldChunkMgr = manager;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderCausalBouillonDimension(worldObj, worldObj.getSeed(), false, "CausalBouillonDimension");
    }
    public boolean shouldMapSpin(String entity, double x, double y, double z) {
        return true;
    }
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
        return false;
    }

    public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
        return false;
    }

    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }
    @Override
    public String getDimensionName() {
        return "Causal Bouillon Dimension";
    }

    public String getWelcomeMessage() {
        return "Reassembling in Chaos";
    }

    public String getDepartMessage() {
        return "Reassembling in more sane reality";
    }

    public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_) {
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
        int i = 10518688;
        float f2 = MathHelper.cos(p_76562_1_ * 3.1415927F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        float f3 = (float)(i >> 16 & 255) / 255.0F;
        float f4 = (float)(i >> 8 & 255) / 255.0F;
        float f5 = (float)(i & 255) / 255.0F;
        f3 *= f2 * 0.0F + 0.15F;
        f4 *= f2 * 0.0F + 0.15F;
        f5 *= f2 * 0.0F + 0.15F;
        return Vec3.createVectorHelper((double)f3, (double)f4, (double)f5);
    }
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int p_76568_1_, int p_76568_2_) {
        return true;
    }
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    public boolean canRespawnHere() {
        return false;
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return true;
    }
}