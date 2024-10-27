package github.snugbrick.miracleblock.recipes;

import github.snugbrick.miracleblock.items.MiraBlockItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Recipes {
    private static final Map<MiraBlockItemStack, MiraBlockItemStack[]> allRecipes = new HashMap<>();

    public static void register(MiraBlockItemStack craftingItem, MiraBlockItemStack[] recipe) {
        allRecipes.put(craftingItem, recipe);
    }

    public static MiraBlockItemStack[] getRecipes(MiraBlockItemStack craftingItem) {
        return allRecipes.get(craftingItem);
    }

    public static Map<MiraBlockItemStack, MiraBlockItemStack[]> getAllRecipes() {
        return allRecipes;
    }

    public static MiraBlockItemStack getCraft(MiraBlockItemStack[] recipes) {
        Map<MiraBlockItemStack[], MiraBlockItemStack> collect =
                allRecipes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        if (collect.get(recipes) != null) {
            return collect.get(recipes);
        }
        return null;
    }
}
