
package pl.vertty.arivi.loader;

import io.netty.util.collection.CharObjectHashMap;
import pl.vertty.arivi.utils.ChatUtil;
import cn.nukkit.inventory.Recipe;

import java.util.*;

import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.Server;
import cn.nukkit.item.Item;

public class CraftingLoader
{
    public static void onCraftingLoad() {
        onKox();
        onStoniarka();
    }
    
    public static void onKox() {
        final Map<Character, Item> ingredients = new CharObjectHashMap<Item>();
        ingredients.put('G', Item.get(41));
        ingredients.put('A', Item.get(260));
        Server.getInstance().getCraftingManager().registerRecipe(419, new ShapedRecipe(Item.get(466), new String[] { "AAA", "AGA", "AAA" }, ingredients, new ArrayList()));


//        final Map<Character, Item> ingredients = new CharObjectHashMap<Item>();
//        final Item applae = Item.get(466);
//        final String[] shapeaaaaa = { "GGG", "GAG", "GGG" };
//        ingredients.put('G', Item.get(41));
//        ingredients.put('A', Item.get(260));
//        Server.getInstance().getCraftingManager().registerRecipe(419 ,new ShapedRecipe(applae, shapeaaaaa, ingredients, new ArrayList()));
//        ingredients.clear();
    }
    
    public static void onStoniarka() {
        final Map<Character, Item> ingredients = new CharObjectHashMap<Item>();
        final Item applae = Item.get(121);
        applae.setCustomName(ChatUtil.fixColor("&9Stoniarka"));
        applae.setLore(ChatUtil.fixColor("&8>> &7Postaw na ziemi!"), ChatUtil.fixColor("&8>> &7nad blokiem pojawi sie stone!"));
        final String[] shapeaaaaa = { "GGG", "GAG", "GGG" };
        ingredients.put('G', Item.get(1));
        ingredients.put('A', Item.get(264));
        Server.getInstance().getCraftingManager().registerRecipe(419 ,new ShapedRecipe(applae, shapeaaaaa, ingredients, new ArrayList()));
        ingredients.clear();
    }
}
