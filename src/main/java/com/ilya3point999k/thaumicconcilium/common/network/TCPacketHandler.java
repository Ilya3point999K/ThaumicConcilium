package com.ilya3point999k.thaumicconcilium.common.network;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.network.packets.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class TCPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE;

    public static void init() {
        int id = 0;

        INSTANCE.registerMessage(PacketFXTaintsplosion.class, PacketFXTaintsplosion.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketFXBloodsplosion.class, PacketFXBloodsplosion.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketFXLithographerZap.class, PacketFXLithographerZap.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketFXLightning.class, PacketFXLightning.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketFXBurst.class, PacketFXBurst.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketMakeHole.class, PacketMakeHole.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketChangeEtherealRange.class, PacketChangeEtherealRange.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketChangeActiveShard.class, PacketChangeActiveShard.class, id++, Side.SERVER);
        //INSTANCE.registerMessage(PacketSendBurlakToggles.class, PacketSendBurlakToggles.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncPlayer.class, PacketSyncPlayer.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketUpdatePartyStatus.class, PacketUpdatePartyStatus.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketEnslave.class, PacketEnslave.class, id++, Side.CLIENT);

        if (Integration.taintedMagic) {
            INSTANCE.registerMessage(PacketTogglePontifexRobe.class, PacketTogglePontifexRobe.class, id++, Side.SERVER);
        }

    }
    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ThaumicConcilium.MODID);
    }

}
