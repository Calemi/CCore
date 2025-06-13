package com.calemi.ccore.api.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class SmithingTemplateHelper {

    public static SmithingTemplateItem createCustomUpgradeTemplate(String modId, String upgradeName, List<String> baseMaterialNames, List<String> additionMaterialNames) {
        return new SmithingTemplateItem(
                appliesToDescription(modId, upgradeName),
                ingredients(modId, upgradeName),
                upgradeDescription(modId, upgradeName),
                baseSlotDescription(modId, upgradeName),
                additionsSlotDescription(modId, upgradeName),
                toEmptySlotIconList(modId, baseMaterialNames),
                toEmptySlotIconList(modId, additionMaterialNames)
        );
    }

    private static Component appliesToDescription(String modId, String upgradeName) {
        return Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(modId, "smithing_template." + upgradeName + "_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE);
    }

    private static Component ingredients(String modId, String upgradeName) {
        return Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(modId, "smithing_template." + upgradeName + "_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE);
    }

    private static Component upgradeDescription(String modId, String upgradeName) {
        return Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.fromNamespaceAndPath(modId, "smithing_upgrade"))).withStyle(ChatFormatting.GRAY);
    }

    private static Component baseSlotDescription(String modId, String upgradeName) {
        return Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(modId, "smithing_template." + upgradeName + "_upgrade.base_slot_description")));
    }

    private static Component additionsSlotDescription(String modId, String upgradeName) {
        return Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(modId, "smithing_template." + upgradeName + "_upgrade.additions_slot_description")));
    }

    private static List<ResourceLocation> toEmptySlotIconList(String modId, List<String> materialNames) {
        return materialNames.stream().map(materialName -> ResourceLocation.fromNamespaceAndPath(modId, "item/empty_slot_" + materialName)).toList();
    }
}