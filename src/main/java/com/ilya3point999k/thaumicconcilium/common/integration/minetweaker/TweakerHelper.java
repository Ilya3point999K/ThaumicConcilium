package com.ilya3point999k.thaumicconcilium.common.integration.minetweaker;

import com.ilya3point999k.thaumicconcilium.api.ChainedRiftRecipe;
import com.ilya3point999k.thaumicconcilium.common.registry.Thaumonomicon;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

import java.util.ArrayList;

public class TweakerHelper {

    public static ItemStack getStack(IItemStack iItemStack){
        return MineTweakerMC.getItemStack(iItemStack);
    }

    //Method taken from ModTweaker so the same aspect parsing system can be used here as is used for adding infusion recipes
    public static AspectList parseAspects(String str) {
        AspectList output=new AspectList();
        if (str == null || str.equals("")) return output;
        String[] aspects = str.split(",");
        for (String aspect : aspects) {
            if (aspect.startsWith(" ")) aspect = aspect.replaceFirst(" ", "");
            String[] aspct = aspect.split("\\s+");
            if (aspct.length == 2) output.add(Aspect.aspects.get(aspct[0]), Integer.parseInt(aspct[1]));
        }
        return output;
    }

    public static String aspectsToString(AspectList aspects) {
        String output="";
        for(Aspect aspect : aspects.getAspectsSortedAmount()) {
            if(aspect!=null)
                output+=aspect.getName()+" "+aspects.getAmount(aspect)+",";
        }
        return output;
    }

    public static String getResearchTab(String researchKey) {
        for (String tab : ResearchCategories.researchCategories.keySet()) {
            for (String research : ResearchCategories.researchCategories.get(tab).research.keySet()) {
                if (research.equals(researchKey)) return tab;
            }
        }
        return null;
    }

    public static ArrayList<ChainedRiftRecipe> getChainedRiftRecipes() {
        ArrayList<ChainedRiftRecipe> recipes = new ArrayList<ChainedRiftRecipe>();
        for (Object o : Thaumonomicon.recipes.values())
            if(o instanceof ChainedRiftRecipe)
                recipes.add((ChainedRiftRecipe) o);
        return recipes;
    }
}
