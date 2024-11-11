package github.snugbrick.miracleblock.items.inlayItemStack;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemLevel;
import github.snugbrick.miracleblock.items.MiraBlockItemStack;
import github.snugbrick.miracleblock.items.skill._Ability;
import github.snugbrick.miracleblock.tools.LoadLangFiles;
import github.snugbrick.miracleblock.tools.NSK;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.Objects;

public class InlayGemItemStack extends MiraBlockItemStack {
    private ItemLevel level;
    private ItemAttribute itemAttribute;
    private Class<? extends _Ability> ability;

    public InlayGemItemStack(ItemStack item, String key, String value, ItemAttribute itemAttribute, ItemLevel level, Class<? extends _Ability> ability) {
        super(item, key, value);
        this.setLevel(level)
                .setItemAttribute(itemAttribute)
                .setAbility(ability);
    }

    public InlayGemItemStack(MiraBlockItemStack item) {
        super(item);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(MiracleBlock.getInstance(), "miracle_inlay");

            if (container.has(key, PersistentDataType.STRING)) {
                this.level = (ItemLevel.getByString(getKeyValue("level")));
                this.itemAttribute = (ItemAttribute.getByString(getKeyValue("itemAttribute")));
                if (isInlaidGemItemStack(MiraBlockItemStack.getItem(getKeyValue("ability")))) {
                    InlayGemItemStack inlaidGemItemStack = (InlayGemItemStack) MiraBlockItemStack.getItem(getKeyValue("ability"));
                    this.ability = (inlaidGemItemStack.getAbility());
                }
            }
        }
    }

    public static Boolean isInlaidGemItemStack(MiraBlockItemStack item) {
        return NSK.hasNameSpacedKey(item, new NamespacedKey(MiracleBlock.getInstance(), "miracle_inlay"));
    }

    /**
     * 请在每个注册物品最后使用该方法 使物品得以完善
     *
     * @return 返回MiracleBlockItemStack 是这个类的终结链式方法
     */
    public MiraBlockItemStack buildItemLore() {
        for (Map<?, ?> theMap : LoadLangFiles.getMessageMap("ItemLore")) {
            if (itemAttribute != null && theMap.get("itemAttribute") != null) {
                this.setLore(false, theMap.get("itemAttribute").toString());
                this.setLore(false, this.itemAttribute.toString());
                continue;
            }
            if (level != null && theMap.get("level") != null) {
                this.setLore(false, theMap.get("level").toString());
                this.setLore(false, this.level.toString());
                continue;
            }
            if (ability != null && theMap.get("ability") != null) {
                this.setLore(false, theMap.get("ability").toString());
                this.setLore(false, this.ability.toString().split("@")[0]);
            }
        }
        return this;
    }

    public InlayGemItemStack setLevel(ItemLevel itemLevel) {
        this.level = itemLevel;
        super.removeNSK("level");
        this.setKeyValue("level", this.getLevel().toString());
        return this;
    }

    public InlayGemItemStack setItemAttribute(ItemAttribute itemAttribute) {
        this.itemAttribute = itemAttribute;
        super.removeNSK("itemAttribute");
        this.setKeyValue("itemAttribute", this.getItemAttribute().toString());
        return this;
    }

    public InlayGemItemStack setAbility(Class<? extends _Ability> ability) {
        if (ability == null) return this;
        this.ability = ability;
        super.removeNSK("ability");
        this.setKeyValue("ability", ability.getName());
        return this;
    }

    @Override
    public String toString() {
        return this.getDisplayName();
    }

    public String getDisplayName() {
        return Objects.requireNonNull(this.getItemMeta()).getDisplayName();
    }

    public ItemAttribute getItemAttribute() {
        return itemAttribute;
    }

    public ItemLevel getLevel() {
        return level;
    }

    public Class<? extends _Ability> getAbility() {
        return ability;
    }

    public static class GainPackage {
        GainPackage() {

        }
    }
}

