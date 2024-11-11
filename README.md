# Thaumic Concilium
Thaumcraft 4 Addon

[CurseForge](https://www.curseforge.com/minecraft/mc-mods/thaumic-concilium)
[Modrinth](https://modrinth.com/mod/thaumic-concilium)

# CraftTweaker guide

## Chained Rift Recipes:

### Adding new Chained Rift Recipes

**Parameters:** String id(unique string id associated with the recipe), Research String, Output stack, Input stack, String of Aspects

`mods.thaumicconcilium.ChainedRiftRecipe.addRecipe(String,String,IItemStack,IItemStack,String);`

**Example:**

`mods.thaumicconcilium.ChainedRiftRecipe.addRecipe("TestCraft","NITOR",<Thaumcraft:ItemGoggles>,<minecraft:dirt>,"cognitio 32, aer 128");`

This will add a new rift recipe that crafts Goggles of Revealing from dirt and requires you to have the Nitor research, and to supply the hex with 32 cognitio and 128 aer.


### Removing Chained Rift Recipes

**Parameters:** String id(unique string id associated with the recipe)

`mods.thaumicconcilium.ChainedRiftRecipe.removeRecipe(String);`

**Example:**

`mods.thaumicconcilium.ChainedRiftRecipe.removeRecipe("TestCraft");`

This will remove the rift recipe from the example above. 
The ids of recipes that are already in the mod are: AstralMonitor, WarpDrum, RRiftGem, RTerraCastGem, PontifexRobeHead, PontifexRobeChest, 
						    PontifexRobeLegs, PontifexRobeFeet


## Polishment Recipes:


### Adding new Polishment Recipes

**Parameters:** OutputStack, String of Aspects

`mods.thaumicconcilium.PolishmentRecipe.addPolishmentRecipe(IItemStack,String);`

**Example:**

`mods.thaumicconcilium.PolishmentRecipe.addPolishmentRecipe(<Thaumcraft:ItemHelmetFortress>,"tutamen 256");`

This will add the polishment recipe to the infusion recipe of the Thaumic Fortress Helmet where 256 tutamen will be required.
Note: You are adding a polishemnt recipe as a new layer/addition on top of an already existing infusion recipe. Only one aspect can added.
      The recipe page in the thaumonomicon will be updated automatically to reflect the changes.


## Removing Polishment Recipes

**Parameters:** OutputStack

`mods.thaumicconcilium.PolishmentRecipe.removePolishmentRecipe(IItemStack);`

**Example:**

`mods.thaumicconcilium.PolishmentRecipe.removePolishmentRecipe(<ThaumicConcilium:DumpJackboots>);`

This will remove the polishment recipe from Dump Jackboots turning it into a regular infusion recipe.


## Page Tweaker:


### Adding Chained Rift Recipe Pages into research

**Parameters:** Research String, Array of String ids, Page Number(Starting from 0)

`mods.thaumicconcilium.ResearchPageTweaker.addRiftCraftingPage(String,String[],int);`

**Examples:**

`mods.thaumicconcilium.ResearchPageTweaker.addRiftCraftingPage("ROD_greatwood",["TestCraft"],1);`

`mods.thaumicconcilium.ResearchPageTweaker.addRiftCraftingPage("VOIDMETAL",["AstralMonitor","WarpDrum"],0);`

The first example will insert a rift crafting page with the recipe from the example craft into the Greatwood Wand research on the second page.
The second exmaple will insert a multi rift crafting page(one which displays multiple recipes) into the Voidmetal research on the first page.
When pages are inserted they move other pages to the right, so in the second example the crafting page will be displayed first and all the original pages will shift rightward.


### Removing pages from research

**Parameters:** Reseach String, Page Number(Starting from 0)

`mods.thaumicconcilium.ResearchPageTweaker.removePage(String,int);`

**Example:**

`mods.thaumicconcilium.ResearchPageTweaker.removePage("ASTRALMONITOR",1);`

This will remove the second page from the Astral Monitor. The intended use is to remove rift recipe pages if the recipes there are no longer relevant.
However using this you can remove any page from any research should you need to.


### Reloading Rift Recipes on a page

**Parameters:** Research String

`mods.thaumicconcilium.ResearchPageTweaker.reloadRecipesInPage(String);`

**Example:**

`mods.thaumicconcilium.ResearchPageTweaker.reloadRecipesInPage("ASTRALMONITOR");`

This can be used to update already existing pages with recipes in them. If for example you removed the original recipe of the astral monitor and
replaced it with your own, instead of removing the page and adding a new one you can use this method to reload the page so it can reflect the changes.
