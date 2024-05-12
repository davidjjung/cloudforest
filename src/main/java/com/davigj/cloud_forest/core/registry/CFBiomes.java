package com.davigj.cloud_forest.core.registry;

import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.other.CFGeneration;
import com.teamabnormals.blueprint.common.world.modification.chunk.modifiers.ChunkGeneratorModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

import java.util.List;


@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CFBiomes {
    public static final ResourceKey<Biome> CLOUD_FOREST = createKey("cloud_forest");

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(CLOUD_FOREST, cloudforest(features, carvers));
    }

    public static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(CloudForest.MOD_ID, name));
    }

    private static Biome cloudforest(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
        CFGeneration.cloudforest(generation);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
        BiomeDefaultFeatures.commonSpawns(spawns);
        return biome(false, 0.26F, 0.82F, 13293534, 7578511, 6525570,
                5403055, 1054750, 13293534, spawns, generation, null);
    }

    private static Biome biome(boolean precipitation, float temperature, float downfall, int skyColor, int grassColor, int foliageColor, int waterColor, int waterFogColor, int fogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(grassColor).foliageColorOverride(foliageColor)
                        .waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor)
                        .skyColor(skyColor).ambientMoodSound(
                                AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music)
                        .build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
    }
}
