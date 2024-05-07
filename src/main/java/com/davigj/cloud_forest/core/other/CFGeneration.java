package com.davigj.cloud_forest.core.other;

import com.davigj.cloud_forest.core.CloudForest;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.fml.common.Mod;

import static com.davigj.cloud_forest.core.registry.CFFeatures.CFPlacedFeatures.TREES_CLOUD_FOREST;

@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID)
public class CFGeneration {

    public static void cloudforest(BiomeGenerationSettings.Builder generation) {
        OverworldBiomes.globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addFerns(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addSavannaExtraGrass(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
        generation.addFeature(Decoration.VEGETAL_DECORATION, TREES_CLOUD_FOREST);
    }
}
