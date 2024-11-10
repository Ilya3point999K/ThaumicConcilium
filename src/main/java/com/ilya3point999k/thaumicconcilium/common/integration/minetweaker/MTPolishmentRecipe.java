package com.ilya3point999k.thaumicconcilium.common.integration.minetweaker;

import com.ilya3point999k.thaumicconcilium.api.ThaumicConciliumApi;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.aspects.AspectList;

@ZenClass("mods.thaumicconcilium.PolishmentRecipe")
public class MTPolishmentRecipe {

    @ZenMethod
    public static void addPolishmentRecipe(IItemStack stack, String aspectList) {
        MineTweakerAPI.apply(new Add(TweakerHelper.getStack(stack),TweakerHelper.parseAspects(aspectList)));
    }

    @ZenMethod
    public static void removePolishmentRecipe(IItemStack stack) {
        MineTweakerAPI.apply(new Remove(TweakerHelper.getStack(stack)));
    }

    public static class Add implements IUndoableAction {

        public final ItemStack stack;

        public final AspectList list;

        public Add(ItemStack stack, AspectList list) {
            this.stack = stack;
            this.list = list;
        }

        @Override
        public void apply() {
            ThaumicConciliumApi.addPolishmentRecipe(stack,list);
        }

        @Override
        public boolean canUndo() {
            return ThaumicConciliumApi.getPolishmentRecipe(stack)!=null;
        }

        @Override
        public void undo() {
            ThaumicConciliumApi.removePolismentRecipe(stack,list);
        }

        @Override
        public String describe() {
            return "Adding a Polishment Recipe to item: "+stack;
        }

        @Override
        public String describeUndo() {
            return "Removing a Polishment Recipe from item: "+stack;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    public static class Remove implements IUndoableAction {

        public final ItemStack stack;

        public final AspectList list;

        public Remove(ItemStack stack) {
            this.stack = stack;
            this.list = ThaumicConciliumApi.getPolishmentRecipe(stack);
        }

        @Override
        public void apply() {
            ThaumicConciliumApi.removePolismentRecipe(stack,list);
        }

        @Override
        public boolean canUndo() {
            return stack!=null && list!=null;
        }

        @Override
        public void undo() {
            ThaumicConciliumApi.addPolishmentRecipe(stack,list);
        }

        @Override
        public String describe() {
            return "Removing a Polishment Recipe from item: "+stack;
        }

        @Override
        public String describeUndo() {
            return "Restoring a Polishment Recipe to item: "+stack;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
