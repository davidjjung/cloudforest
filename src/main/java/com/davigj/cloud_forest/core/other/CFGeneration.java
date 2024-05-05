package com.davigj.cloud_forest.core.other;

import com.davigj.cloud_forest.core.CloudForest;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.item.Items.COARSE_DIRT;

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
    }
}
