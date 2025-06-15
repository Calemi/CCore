package com.calemi.ccore.api.datagen;

import com.calemi.ccore.api.string.StringHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class CLanguageProvider extends LanguageProvider {

    protected final String CONFIG;

    protected final String modId;

    public CLanguageProvider(String modId, PackOutput output, String locale) {
        super(output, modId, locale);
        this.modId = modId;
        this.CONFIG = getPrefixedKey("config", "");
    }

    protected void addAutoBlock(Block block) {
        add(block, autoString(BuiltInRegistries.BLOCK.getKey(block).getPath(), "block"));
    }

    protected void addAutoItem(Item item) {
        add(item, autoString(BuiltInRegistries.ITEM.getKey(item).getPath(), "item"));
    }

    protected void addAutoConfig(String camelCaseName) {
        add(CONFIG + StringHelper.camelToSnake(camelCaseName), StringHelper.camelToTitle(camelCaseName));
    }

    protected String getPrefixedKey(String prefix, String name) {
        return prefix + "." + modId + "." + name;
    }

    protected String autoString(String descriptionId, String prefixToRemove) {
        String[] words = descriptionId.replace(getPrefixedKey(prefixToRemove, ""), "").split("_");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return result.toString().trim();
    }
}