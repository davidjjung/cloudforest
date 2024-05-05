package com.davigj.cloud_forest.core.data.server;

import com.davigj.cloud_forest.core.CloudForest;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.davigj.cloud_forest.core.registry.CFBiomes.CLOUD_FOREST;

public class CFBiomeTagsProvider extends BiomeTagsProvider {
    public CFBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, CloudForest.MOD_ID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BiomeTags.IS_OVERWORLD).add(CLOUD_FOREST);
        this.tag(BiomeTags.IS_FOREST).add(CLOUD_FOREST);
        this.tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS).add(CLOUD_FOREST);
        this.tag(BiomeTags.HAS_RUINED_PORTAL_JUNGLE).add(CLOUD_FOREST);
        this.tag(BiomeTags.HAS_CLOSER_WATER_FOG).add(CLOUD_FOREST);
        this.tag(BiomeTags.INCREASED_FIRE_BURNOUT).add(CLOUD_FOREST);
        this.tag(BiomeTags.INCREASED_FIRE_BURNOUT).add(CLOUD_FOREST);
    }
}
