package com.ilya3point999k.thaumicconcilium.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.research.ResearchManager;

import java.util.ArrayList;

public class ThaumicConciliumApi {
    static ArrayList<ChainedRiftRecipe> riftRecipes = new ArrayList();
    static ArrayList<Tuple> polishmentRecipes = new ArrayList<>();

    public static ChainedRiftRecipe addChainedRiftRecipe(String key, ItemStack result, Object catalyst, AspectList tags) {
        ChainedRiftRecipe recipe = new ChainedRiftRecipe(key, result, catalyst, tags);
        riftRecipes.add(recipe);
        return recipe;
    }

    public static void addPolishmentRecipe(ItemStack item, AspectList tags) {
        polishmentRecipes.add(new Tuple(item, tags));
    }

    public static ChainedRiftRecipe getRiftRecipe(ItemStack res, AspectList tags) {
        for (ChainedRiftRecipe r : riftRecipes) {
            if (r.matches(tags, res)) {
                return r;
            }
        }
        return null;
    }

    public static ChainedRiftRecipe findMatchingRiftRecipe(String username, AspectList aspects, ItemStack lastDrop) {
        int highest = 0;
        int index = -1;

        for (int a = 0; a < riftRecipes.size(); ++a) {
            ChainedRiftRecipe recipe = (ChainedRiftRecipe) riftRecipes.get(a);
            ItemStack temp = lastDrop.copy();
            temp.stackSize = 1;
            if (ResearchManager.isResearchComplete(username, recipe.key) && recipe.matches(aspects, temp)) {
                int result = recipe.aspects.size();
                if (result > highest) {
                    highest = result;
                    index = a;
                }
            }
        }
        if (index < 0) {
            return null;
        } else {
            new AspectList();
            return riftRecipes.get(index);
        }
    }


    public static AspectList getPolishmentRecipe(ItemStack res) {
        for (Tuple t : polishmentRecipes) {
            if (t.getFirst() instanceof ItemStack) {
                if (((ItemStack) t.getFirst()).isItemEqual(res)) {
                    return (AspectList) t.getSecond();
                }
            }
        }
        return null;
    }

}
