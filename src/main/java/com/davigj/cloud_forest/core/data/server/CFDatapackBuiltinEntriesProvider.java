package com.davigj.cloud_forest.core.data.server;

import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.other.CFBiomeSlices;
import com.davigj.cloud_forest.core.registry.CFBiomes;
import com.davigj.cloud_forest.core.registry.CFFeatures;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CFDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, CFFeatures.CFConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, CFFeatures.CFPlacedFeatures::bootstrap)
            .add(Registries.BIOME, CFBiomes::bootstrap)
            .add(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, CFBiomeSlices::bootstrap);

    public CFDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of(CloudForest.MOD_ID));
    }
}