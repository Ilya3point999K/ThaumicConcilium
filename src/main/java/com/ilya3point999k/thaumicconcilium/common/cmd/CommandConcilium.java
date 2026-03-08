package com.ilya3point999k.thaumicconcilium.common.cmd;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import thaumcraft.api.ThaumcraftApiHelper;

import java.lang.reflect.InvocationTargetException;

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
                if (Integration.witchery) {
                    icommandsender.addChatMessage(new ChatComponentTranslation("§3Use this to repair Witchery knowledge system.", new Object[0]));
                    icommandsender.addChatMessage(new ChatComponentTranslation("  /concilium repairWitchery <player>", new Object[0]));
                }
            } else if (astring.length >= 2 && astring[0].equalsIgnoreCase("restore")) {
                EntityPlayerMP player = getPlayer(icommandsender, astring[1]);
                if (player != null){
                    TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
                    capabilities.fleshAmount = 0;
                    capabilities.sync();
                    player.addChatMessage(new ChatComponentTranslation("§5Maximum health restored!"));
                }
            } else if (astring.length >= 2 && Integration.witchery && astring[0].equalsIgnoreCase("repairWitchery")) {
                EntityPlayerMP player = getPlayer(icommandsender, astring[1]);
                if (player != null){
                    if(!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "CRIMSONSPELLS")){
                        icommandsender.addChatMessage(new ChatComponentTranslation("§5This player doesn't have CRIMSONSPELLS research."));
                        return;
                    }
                    NBTTagCompound nbtPlayer = null;
                    try {
                        nbtPlayer = (NBTTagCompound) Integration.witcheryInfusionClass.getMethod("getNBT", Entity.class).invoke(null, player);
                    } catch (IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (nbtPlayer != null) {
                        if (!nbtPlayer.hasKey("WITCSpellBook")) {
                            nbtPlayer.setTag("WITCSpellBook", new NBTTagCompound());
                        }

                        NBTTagCompound nbtSpells = nbtPlayer.getCompoundTag("WITCSpellBook");
                        nbtSpells.setBoolean("incarcerous", true);
                        nbtSpells.setBoolean("arrowspell", true);
                        nbtSpells.setBoolean("spiderspell", true);
                        nbtSpells.setBoolean("spellundead", true);
                        nbtSpells.setBoolean("conjunctivitis", true);
                    }
                    player.addChatMessage(new ChatComponentTranslation("§5Witchery knowledge granted."));
                }
            } else {
                icommandsender.addChatMessage(new ChatComponentTranslation("§cInvalid arguments", new Object[0]));
                icommandsender.addChatMessage(new ChatComponentTranslation("§cUse /concilium help to get help", new Object[0]));
            }
        }
    }
}
