package com.ilya3point999k.thaumicconcilium.client.events;

import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.items.ShardMill;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketTogglePontifexRobe;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public class TCKeyHandler {
    public KeyBinding keyF = new KeyBinding("Choose Shard for Shard Mill", 36, "key.categories.misc");
    public KeyBinding keyH = new KeyBinding("Toggle Pontifex Robe", 38, "key.categories.misc");

    private boolean keyPressedF = false;
    private boolean keyPressedH = false;
    public static boolean radialActive = false;
    public static boolean radialLock = false;
    public static long lastPressF = 0L;

    public TCKeyHandler() {
        ClientRegistry.registerKeyBinding(this.keyF);
        if (Integration.taintedMagic) {
            ClientRegistry.registerKeyBinding(this.keyH);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.SERVER) {
            if (event.phase == TickEvent.Phase.START) {
                EntityPlayer player;
                if (this.keyF.getIsKeyPressed()) {
                    if (FMLClientHandler.instance().getClient().inGameHasFocus) {
                        player = event.player;
                        if (player != null) {
                            if (!this.keyPressedF) {
                                lastPressF = System.currentTimeMillis();
                                radialLock = false;
                            }

                            if (!radialLock && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ShardMill) {
                                radialActive = true;
                            }
                        }
                        this.keyPressedF = true;
                    }
                } else {
                    radialActive = false;
                    if (this.keyPressedF) {
                        lastPressF = System.currentTimeMillis();
                    }

                    this.keyPressedF = false;
                }

                if (Integration.taintedMagic) {
                    if (this.keyH.getIsKeyPressed()) {
                        if (FMLClientHandler.instance().getClient().inGameHasFocus) {
                            player = event.player;
                            if (player != null) {
                                if (!this.keyPressedH) {
                                    TCPacketHandler.INSTANCE.sendToServer(new PacketTogglePontifexRobe(player));
                                }
                            }
                            this.keyPressedH = true;
                        }
                    } else {
                        this.keyPressedH = false;
                    }
                }
            }
        }
    }
}
