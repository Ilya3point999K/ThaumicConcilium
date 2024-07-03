package com.ilya3point999k.thaumicconcilium.client;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.containers.AstralMonitorContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class AstralMonitorGUI extends GuiContainer {
    ResourceLocation gui = new ResourceLocation(ThaumicConcilium.MODID +":textures/gui/astral_monitor.png");
    public AstralMonitorGUI(InventoryPlayer par1InventoryPlayer, World world) {
        super(new AstralMonitorContainer(par1InventoryPlayer, world));
    }

    protected void drawGuiContainerForegroundLayer() {
    }

    protected boolean checkHotbarKeys(int par1) {
        return false;
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        mc.renderEngine.bindTexture(gui);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
    }
}