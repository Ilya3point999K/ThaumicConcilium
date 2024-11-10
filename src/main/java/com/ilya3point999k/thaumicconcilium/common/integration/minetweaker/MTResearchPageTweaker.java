package com.ilya3point999k.thaumicconcilium.common.integration.minetweaker;

import com.ilya3point999k.thaumicconcilium.api.ChainedRiftRecipe;
import com.ilya3point999k.thaumicconcilium.common.registry.Thaumonomicon;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchPage;

import java.util.ArrayList;
import java.util.Arrays;

@ZenClass("mods.thaumicconcilium.ResearchPageTweaker")
public class MTResearchPageTweaker {

    @ZenMethod
    public static void addRiftCraftingPage(String researchKey, String[] craftIDS, int pageNum) {
        MineTweakerAPI.apply(new AddRiftPage(researchKey,craftIDS,pageNum));
    }

    @ZenMethod
    public static void removePage(String researchKey,int pageNum) {
        MineTweakerAPI.apply(new RemoveResearchPage(researchKey,pageNum));
    }

    @ZenMethod
    public static void reloadRecipesInPage(String researchKey) {
        MineTweakerAPI.apply(new ReloadResearchRecipePage(researchKey));
    }

    public static class AddRiftPage implements IUndoableAction {

        public final String researchKey,tab;

        public final String[] craftIDs;

        public final int pageNumber;

        public final ResearchPage[] cachedPages;


        public AddRiftPage(String researchPage, String[] craftIDs, int pageNumber) {
            this.researchKey = researchPage;
            this.craftIDs = craftIDs;
            this.pageNumber = pageNumber;
            this.tab = TweakerHelper.getResearchTab(researchKey);
            this.cachedPages = tab!=null ? ResearchCategories.researchCategories.get(tab).research.get(researchKey).getPages() : null;
        }

        @Override
        public void apply() {
            if(tab == null)
                return;
            int num = pageNumber;
            ResearchPage[] newPages = new ResearchPage[cachedPages.length+1];
            if(pageNumber > newPages.length)
                num = newPages.length-1;
            if(craftIDs.length > 1)
                newPages[num] = Thaumonomicon.riftMultiPage(craftIDs);
            else
                newPages[num] = Thaumonomicon.riftPage(craftIDs[0]);
            int index = 0;
            for(int i = 0; i < cachedPages.length; i++) {
                if(newPages[index] != null)
                    index++;
                newPages[index]= cachedPages[i];
                index++;
            }
            ResearchCategories.researchCategories.get(tab).research.get(researchKey).setPages(newPages);
        }

        @Override
        public boolean canUndo() {
            return cachedPages !=null && tab!=null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.get(researchKey).setPages(cachedPages);
        }

        @Override
        public String describe() {
            return "Adding a page with a Chained Rift Recipe to "+ researchKey;
        }

        @Override
        public String describeUndo() {
            return "Restoring original pages to "+researchKey;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    public static class RemoveResearchPage implements IUndoableAction {

        public final String researchKey, tab;

        public final ResearchPage[] cachedPages;

        public final int pageNumber;

        public RemoveResearchPage(String researchKey, int pageNumber) {
            this.researchKey = researchKey;
            this.pageNumber = pageNumber;
            this.tab = TweakerHelper.getResearchTab(researchKey);
            this.cachedPages = tab!=null ? ResearchCategories.researchCategories.get(tab).research.get(researchKey).getPages() : null;
        }

        @Override
        public void apply() {
            if(tab == null)
                return;
            ResearchPage[] newPages = new ResearchPage[cachedPages.length-1];
            int index = 0;
            for(int i = 0; i < newPages.length; i++) {
                if (i == pageNumber)
                    index++;
                newPages[i] = cachedPages[index];
                index++;
            }
            ResearchCategories.researchCategories.get(tab).research.get(researchKey).setPages(newPages);
        }

        @Override
        public boolean canUndo() {
            return cachedPages !=null && tab!=null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.get(researchKey).setPages(cachedPages);
        }

        @Override
        public String describe() {
            return "Removing page number "+pageNumber+" in research " + researchKey;
        }

        @Override
        public String describeUndo() {
            return "Restoring page number "+pageNumber+" to "+researchKey;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    public static class ReloadResearchRecipePage implements IUndoableAction {

        public final String researchKey, tab;

        public final ResearchPage[] cachedPages;

        public ReloadResearchRecipePage(String researchKey) {
            this.researchKey = researchKey;
            this.tab = TweakerHelper.getResearchTab(researchKey);
            this.cachedPages = tab!=null ? ResearchCategories.researchCategories.get(tab).research.get(researchKey).getPages() : null;
        }

        @Override
        public void apply() {
            if(tab == null)
                return;
            ResearchPage[] newPages = Arrays.copyOf(cachedPages,cachedPages.length);
            ArrayList<ChainedRiftRecipe> recipes = TweakerHelper.getChainedRiftRecipes();
            for(ResearchPage page : newPages) {
                if(page.recipe==null)
                    continue;
                if(page.recipe instanceof ChainedRiftRecipe) {
                    ChainedRiftRecipe recipe = (ChainedRiftRecipe) page.recipe;
                    for(ChainedRiftRecipe r : recipes) {
                        if (ItemStack.areItemStacksEqual(r.getRecipeOutput(), recipe.getRecipeOutput())) {
                            page.recipe = r;
                            break;
                        }
                    }
                }
                if(page.recipe instanceof Object[] && ((Object[]) page.recipe)[0] instanceof ChainedRiftRecipe) {
                    Object[] arr = (Object[]) page.recipe;
                    ChainedRiftRecipe[] newArr = new ChainedRiftRecipe[arr.length];
                    for(int j = 0; j < arr.length; j++) {
                        ChainedRiftRecipe recipe = (ChainedRiftRecipe) arr[j];
                        for(int i = 0; i < recipes.size(); i++) {
                            if (ItemStack.areItemStacksEqual(recipes.get(i).getRecipeOutput(), recipe.getRecipeOutput())) {
                                newArr[j] = recipes.get(i);
                            }
                        }
                    }
                    page.recipe = newArr;
                }
            }
            ResearchCategories.researchCategories.get(tab).research.get(researchKey).setPages(newPages);
        }

        @Override
        public boolean canUndo() {
            return cachedPages !=null && tab!=null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.get(researchKey).setPages(cachedPages);
        }

        @Override
        public String describe() {
            return "Reloading recipes in "+researchKey;
        }

        @Override
        public String describeUndo() {
            return "Restoring old recipes to "+researchKey;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
