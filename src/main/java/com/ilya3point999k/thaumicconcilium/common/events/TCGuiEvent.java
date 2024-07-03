package com.ilya3point999k.thaumicconcilium.common.events;

import com.ilya3point999k.thaumicconcilium.api.ChainedRiftRecipe;
import com.ilya3point999k.thaumicconcilium.api.ThaumicConciliumApi;
import com.ilya3point999k.thaumicconcilium.client.PolishedInfusionGUI;
import com.ilya3point999k.thaumicconcilium.common.Injector;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.client.gui.GuiResearchRecipe;

public class TCGuiEvent {
    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event) {
        if (event.gui != null && event.gui instanceof GuiResearchRecipe) {
            GuiResearchRecipe gui = (GuiResearchRecipe) event.gui;
            Injector inj = new Injector(gui, GuiResearchRecipe.class);
            ResearchItem research = inj.getField("research");
            int found = 0;
            for (ResearchPage p : research.getPages()) {
                if (p.type.equals(ResearchPage.PageType.INFUSION_CRAFTING) && p.recipeOutput != null) {
                    if (ThaumicConciliumApi.getPolishmentRecipe(p.recipeOutput) != null) {
                        found = 1;
                    }
                }
                if (p.type.equals(ResearchPage.PageType.CRUCIBLE_CRAFTING)){
                    if (p.recipe instanceof Object[]){
                        for (Object o : (Object[])p.recipe){
                            if (o instanceof ChainedRiftRecipe){
                                found = 1;
                            }
                        }
                    }
                    if (p.recipe instanceof ChainedRiftRecipe){
                        found = 1;
                    }
                    //if (TCConfig.dematRecipes.get(GameData.getItemRegistry().getNameForObject(p.recipe)))
                }
            }
            switch (found){
                case 0:
                    break;
                case 1:{
                    double guiX = inj.getField("guiMapX");
                    double guiY = inj.getField("guiMapY");
                    int page = inj.getField("page");
                    event.gui = new PolishedInfusionGUI(research, page, guiX, guiY);
                    break;
                }
            }
        }
    }
}
