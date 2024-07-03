package com.emoniph.witchery.infusion.infusions.symbols;

import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public abstract class SymbolEffect {

    private final int effectID;
    private final String unlocalisedName;
    private final int chargeCost;
    private final boolean curse;
    private final boolean fallsToEarth;
    private final String knowledgeKey;
    private final boolean isVisible;
    private byte[] defaultStrokes;
    private final int cooldownTicks;


    public SymbolEffect(int effectID, String unlocalisedName) {
        this(effectID, unlocalisedName, 1, false, false, (String)null, 0, true);
    }

    public SymbolEffect(int effectID, String unlocalisedName, int spellCost, boolean curse, boolean fallsToEarth, String knowledgeKey, int cooldown) {
        this(effectID, unlocalisedName, spellCost, curse, fallsToEarth, knowledgeKey, cooldown, true);
    }

    public SymbolEffect(int effectID, String unlocalisedName, int spellCost, boolean curse, boolean fallsToEarth, String knowledgeKey, int cooldown, boolean isVisible) {
        this.effectID = effectID;
        this.unlocalisedName = unlocalisedName;
        this.chargeCost = spellCost;
        this.curse = curse;
        this.fallsToEarth = fallsToEarth;
        this.knowledgeKey = knowledgeKey;
        this.cooldownTicks = cooldown;
        this.isVisible = isVisible;
    }

    public int getEffectID() {
        return this.effectID;
    }

    public boolean isCurse() {
        return this.curse;
    }

    public boolean isUnforgivable() {
        return this.curse && this.knowledgeKey == null;
    }

    public String getLocalizedName() {
        try {
            return (String) Integration.witcheryClass.getMethod("resource", String.class).invoke(null, this.unlocalisedName);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void perform(World var1, EntityPlayer var2, int var3);

    public int getChargeCost(World world, EntityPlayer player, int level) {
        return MathHelper.floor_double(Math.pow(2.0D, (double)(level - 1)) * (double)this.chargeCost);
    }

    public boolean fallsToEarth() {
        return this.fallsToEarth;
    }

    public boolean hasValidInfusion(EntityPlayer player, int infusionID) {
        try {
            return player.capabilities.isCreativeMode?true:(infusionID <= 0?false:!this.isUnforgivable() || infusionID == Integration.witcheryClass.getField("Recipes").getClass().getField("infusionBeast").getInt("infusionID"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isVisible(EntityPlayer player) {
        return this.isVisible;
    }

    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append("§n");
        try {
            sb.append(Integration.witcheryClass.getMethod("resource", String.class).invoke(null, this.unlocalisedName));
            sb.append("§r");
            sb.append(Integration.witcheryConstClass.getField("BOOK_NEWLINE"));
            sb.append(Integration.witcheryConstClass.getField("BOOK_NEWLINE"));
            String descKey = this.unlocalisedName + ".info";
            String description = (String) Integration.witcheryClass.getMethod("resource", String.class).invoke(null, descKey);
            if(description != null && !description.isEmpty() && !description.equals(descKey)) {
                sb.append(description);
                sb.append(Integration.witcheryConstClass.getField("BOOK_NEWLINE"));
                sb.append(Integration.witcheryConstClass.getField("BOOK_NEWLINE"));
            }

            sb.append("§8");
            sb.append(Integration.witcheryClass.getMethod("resource", String.class).invoke(null, "witchery.book.wands.strokes"));
            sb.append("§0");
            sb.append(Integration.witcheryConstClass.getField("BOOK_NEWLINE"));
            int i = 1;
            byte[] arr$ = this.defaultStrokes;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                byte stroke = arr$[i$];
                sb.append(i++);
                sb.append(": ");
                sb.append(Integration.witcheryClass.getMethod("resource", String.class).invoke(null, "witchery.book.wands.stroke." + stroke));
                sb.append(Integration.witcheryConstClass.getField("BOOK_NEWLINE"));
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public void setDefaultStrokes(byte[] strokes) {
        this.defaultStrokes = strokes;
    }

    public boolean hasValidKnowledge(EntityPlayer player, NBTTagCompound nbtPlayer) {
        if(player.capabilities.isCreativeMode) {
            return true;
        } else if(this.knowledgeKey != null) {
            if(nbtPlayer.hasKey("WITCSpellBook")) {
                NBTTagCompound nbtSpells = nbtPlayer.getCompoundTag("WITCSpellBook");
                return nbtSpells.getBoolean(this.knowledgeKey);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void acquireKnowledge(EntityPlayer player) {
        if(this.knowledgeKey != null) {
            NBTTagCompound nbtPlayer = null;
            try {
                nbtPlayer = (NBTTagCompound) Integration.witcheryInfusionClass.getMethod("getNBT", Entity.class).invoke(null, player);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            if(nbtPlayer != null) {
                if(!nbtPlayer.hasKey("WITCSpellBook")) {
                    nbtPlayer.setTag("WITCSpellBook", new NBTTagCompound());
                }

                NBTTagCompound nbtSpells = nbtPlayer.getCompoundTag("WITCSpellBook");
                nbtSpells.setBoolean(this.knowledgeKey, true);
            }
        }

    }

    public static String getKnowledge(EntityPlayer player) {
        StringBuilder sb = new StringBuilder();
        NBTTagCompound nbtPlayer = null;
        try {
            nbtPlayer = (NBTTagCompound) Integration.witcheryInfusionClass.getMethod("getNBT", Entity.class).invoke(null, player);
            if(nbtPlayer != null && nbtPlayer.hasKey("WITCSpellBook")) {
                Iterator i$ = (Iterator) Integration.witcheryEffectRegistryClass.getDeclaredMethod("instance").invoke(null).getClass().getDeclaredMethod("getEffects").invoke(null).getClass().getDeclaredMethod("iterator").invoke(null);

                while(i$.hasNext()) {
                    SymbolEffect effect = (SymbolEffect)i$.next();
                    if(effect.knowledgeKey != null && effect.hasValidKnowledge(player, nbtPlayer)) {
                        if(sb.length() > 0) {
                            sb.append(", ");
                        }

                        sb.append(effect.getLocalizedName());
                    }
                }
            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public long cooldownRemaining(EntityPlayer player, NBTTagCompound nbtPlayer) {
        if(this.cooldownTicks > 0 && this.knowledgeKey != null && nbtPlayer.hasKey("WITCSpellBook")) {
            NBTTagCompound nbtSpells = nbtPlayer.getCompoundTag("WITCSpellBook");
            long lastUseTime = nbtSpells.getLong(this.knowledgeKey + "LastUse");
            long timeNow = 0;
            try {
                timeNow = (long) Integration.witcheryTimeUtilClass.getDeclaredMethod("getServerTimeInTicks").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            if(timeNow < lastUseTime + (long)this.cooldownTicks) {
                return lastUseTime + (long)this.cooldownTicks - timeNow;
            }
        }

        return 0L;
    }

    public void setOnCooldown(EntityPlayer player) {
        if(this.cooldownTicks > 0 && this.knowledgeKey != null && !player.capabilities.isCreativeMode) {
            NBTTagCompound nbtPlayer = null;
            try {
                nbtPlayer = (NBTTagCompound) Integration.witcheryInfusionClass.getMethod("getNBT", Entity.class).invoke(null, player);
                if(nbtPlayer != null && nbtPlayer.hasKey("WITCSpellBook")) {
                    NBTTagCompound nbtSpells = nbtPlayer.getCompoundTag("WITCSpellBook");
                    nbtSpells.setLong(this.knowledgeKey + "LastUse", (Long) Integration.witcheryTimeUtilClass.getDeclaredMethod("getServerTimeInTicks").invoke(null));
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean equals(Object obj) {
        if(obj != null && this.getClass() == obj.getClass()) {
            if(obj == this) {
                return true;
            } else {
                SymbolEffect other = (SymbolEffect)obj;
                return other.effectID == this.effectID;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        byte result = 17;
        int result1 = 37 * result + this.effectID;
        return result1;
    }
}