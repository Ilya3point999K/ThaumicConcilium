package com.ilya3point999k.thaumicconcilium.common.cmd;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class CommandConcilium extends CommandBase {

    @Override
    public String getCommandName() {
        return "concilium";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/concilium <action> [<player> [<params>]]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 1;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        if (astring.length == 0) {
            icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
            icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /concilium help to get help", new Object[0]));
        } else {
            if (astring[0].equalsIgnoreCase("help")) {
                icommandsender.addChatMessage(new ChatComponentTranslation("§3Use this to reset health decrease from actions in noclip.", new Object[0]));
                icommandsender.addChatMessage(new ChatComponentTranslation("  /concilium restore <player>", new Object[0]));
            } else if (astring.length >= 2 && astring[0].equalsIgnoreCase("restore")) {
                EntityPlayerMP player = getPlayer(icommandsender, astring[1]);
                if (player != null){
                    TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
                    capabilities.fleshAmount = 0;
                    capabilities.sync();
                    player.addChatMessage(new ChatComponentTranslation("§5Maximum health restored!"));
                }
            } else {
                icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
                icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /concilium help to get help", new Object[0]));
            }
        }
    }
}
