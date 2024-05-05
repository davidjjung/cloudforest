package com.davigj.cloud_forest.core.data.client;

import com.davigj.cloud_forest.core.registry.CFBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class CFLangProvider extends LanguageProvider {
    public CFLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(CFBlocks.FOG_MOSS_BLOCK.get());
    }

    private void add(Block... blocks) {
        List.of(blocks).forEach((block -> this.add(block, format(ForgeRegistries.BLOCKS.getKey(block)))));
    }

    private void add(Item... items) {
        List.of(items).forEach((item -> this.add(item, format(ForgeRegistries.ITEMS.getKey(item)))));
    }

    private String format(ResourceLocation registryName) {
        return WordUtils.capitalizeFully(registryName.getPath().replace("_", " "));
    }
}
