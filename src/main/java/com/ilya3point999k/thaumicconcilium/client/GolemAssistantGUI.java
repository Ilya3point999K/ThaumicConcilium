package com.ilya3point999k.thaumicconcilium.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.entity.RenderGolemBase;
import thaumcraft.client.renderers.models.entities.ModelGolem;
import thaumcraft.common.entities.golems.ContainerGolem;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.golems.ItemGolemCore;

public class GolemAssistantGUI extends GuiContainer {
    private float xSize_lo;
    private float ySize_lo;
    private EntityGolemBase golem;
    private int threat = -1;
    RenderLiving rgb = new RenderGolemBase(new ModelGolem(false));
    private static ModelGolem model = new ModelGolem(true);
    private Slot theSlot;

    public GolemAssistantGUI(EntityPlayer player, EntityGolemBase e) {
        super(new ContainerGolem(player.inventory, e.inventory));
        this.golem = e;
        if (this.golem.advanced && this.golem.worldObj.rand.nextInt(4) == 0) {
            this.threat = this.golem.worldObj.rand.nextInt(9);
        }

    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        if (this.threat >= 0) {
            this.fontRendererObj.drawSplitString(StatCollector.translateToLocal("golemthreat." + this.threat + ".text"), 80, 22, 110, 14540253);
        } else {
            this.fontRendererObj.drawSplitString(StatCollector.translateToLocal("tc.assistant.gui.text"), 80, 22, 110, 14540253);
        }

        if (((ContainerGolem) this.inventorySlots).maxScroll > 0) {
            this.fontRendererObj.drawString(((ContainerGolem) this.inventorySlots).currentScroll + 1 + "/" + (((ContainerGolem) this.inventorySlots).maxScroll + 1), 323, 140, 14540253);
        }

        GL11.glPopMatrix();
    }

    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        this.xSize_lo = (float) par1;
        this.ySize_lo = (float) par2;
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        int baseX = this.guiLeft;
        int baseY = this.guiTop;
        int var10000 = par2 - (baseX + 139);
        var10000 = par3 - (baseY + 10);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        UtilsFX.bindTexture("textures/gui/guigolem.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(baseX, baseY, 0, 0, this.xSize, this.ySize);
        int slots = this.golem.inventory.slotCount;
        int typeLoc = this.golem.getGolemType().ordinal() * 24;
        IIcon icon = null;
        int shiftx;
        int shifty;
        if (this.golem.getCore() > -1 && ItemGolemCore.hasInventory(this.golem.getCore())) {
            for (shiftx = 0; shiftx < Math.min(6, slots); ++shiftx) {
                this.drawTexturedModalRect(baseX + 96 + shiftx / 2 * 28, baseY + 12 + shiftx % 2 * 31, 184, typeLoc, 24, 24);
            }

            /*
            if (slots > 6) {
                if (((ContainerGolem)this.inventorySlots).currentScroll > 0) {
                    this.drawTexturedModalRect(baseX + 111, baseY + 68, 0, 200, 24, 8);
                } else {
                    this.drawTexturedModalRect(baseX + 111, baseY + 68, 0, 208, 24, 8);
                }

                if (((ContainerGolem)this.inventorySlots).currentScroll < ((ContainerGolem)this.inventorySlots).maxScroll) {
                    this.drawTexturedModalRect(baseX + 135, baseY + 68, 24, 200, 24, 8);
                } else {
                    this.drawTexturedModalRect(baseX + 135, baseY + 68, 24, 208, 24, 8);
                }
            }
             */
        }

        GL11.glDisable(3042);
        GL11.glPopMatrix();
        this.drawGolem(this.mc, baseX + 51, baseY + 75, 30, (float) (baseX + 51) - this.xSize_lo, (float) (baseY + 75 - 50) - this.ySize_lo);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void drawGolem(Minecraft mc, int par1, int par2, int par3, float par4, float par5) {
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution var7 = new ScaledResolution(Minecraft.getMinecraft(), this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(), (var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(), 240 * var7.getScaleFactor());
        GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
        GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        float var8 = 1.0F;
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(-1.5F, -1.0F, -12.0F);
        GL11.glScalef(var8, var8, var8);
        float var9 = 5.0F;
        GL11.glScalef(var9, var9, var9);
        float f2 = this.golem.renderYawOffset;
        float f3 = this.golem.rotationYaw;
        float f4 = this.golem.rotationPitch;
        float f5 = this.golem.prevRotationYawHead;
        float f6 = this.golem.rotationYawHead;
        this.golem.renderYawOffset = -20.0F;
        this.golem.rotationYaw = 0.0F;
        this.golem.rotationPitch = 0.0F;
        this.golem.prevRotationYawHead = -5.0F;
        this.golem.rotationYawHead = -5.0F;
        RenderManager.instance.renderEntityWithPosYaw(this.golem, 0.0, 0.0, 0.0, 0.0F, 1.0F);
        this.golem.renderYawOffset = f2;
        this.golem.rotationYaw = f3;
        this.golem.rotationPitch = f4;
        this.golem.prevRotationYawHead = f5;
        this.golem.rotationYawHead = f6;
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(5889);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glDisable(32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        int baseX = (this.width - this.xSize) / 2;
        int baseY = (this.height - this.ySize) / 2;
        int slots = this.golem.inventory.slotCount;
        int var7;
        int var8;
        int shiftx;
        if (ItemGolemCore.hasInventory(this.golem.getCore())) {

            if (slots > 6) {
                var7 = par1 - (baseX + 111);
                var8 = par2 - (baseY + 68);
                if (var7 >= 0 && var8 >= 0 && var7 < 24 && var8 < 8 && ((ContainerGolem)this.inventorySlots).currentScroll > 0) {
                    this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 66);
                    --((ContainerGolem)this.inventorySlots).currentScroll;
                    ((ContainerGolem)this.inventorySlots).refreshInventory();
                    return;
                }

                var7 = par1 - (baseX + 135);
                var8 = par2 - (baseY + 68);
                if (var7 >= 0 && var8 >= 0 && var7 < 24 && var8 < 8 && ((ContainerGolem)this.inventorySlots).currentScroll < ((ContainerGolem)this.inventorySlots).maxScroll) {
                    this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 67);
                    ++((ContainerGolem)this.inventorySlots).currentScroll;
                    ((ContainerGolem)this.inventorySlots).refreshInventory();
                    return;
                }
            }

        }

    }

    protected void renderToolTip(ItemStack par1ItemStack, int par2, int par3) {
        super.renderToolTip(par1ItemStack, par2, par3);
    }
}