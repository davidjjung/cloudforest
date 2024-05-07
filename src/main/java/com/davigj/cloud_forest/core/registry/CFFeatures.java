package com.davigj.cloud_forest.core.registry;

import com.davigj.cloud_forest.common.levelgen.feature.FogMossPatchFeature;
import com.davigj.cloud_forest.common.levelgen.feature.NoisyFogMossPatchFeature;
import com.davigj.cloud_forest.core.CloudForest;
import com.google.common.collect.ImmutableList;
import com.teamabnormals.blueprint.common.levelgen.feature.BlueprintTreeFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CFFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CloudForest.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, CloudForest.MOD_ID);

    public static final RegistryObject<Feature<TreeConfiguration>> DEWEY_SPRUCE_TREE = FEATURES.register("dewey_spruce_tree", () -> new TreeFeature(TreeConfiguration.CODEC));

    public static final RegistryObject<Feature<DiskConfiguration>> NOISY_FOG_MOSS_PATCH = FEATURES.register("noisy_fog_moss_patch", () -> new DiskFeature(DiskConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockStateConfiguration>> FOG_MOSS_BOULDER = FEATURES.register("fog_moss_boulder", () -> new FogMossPatchFeature(BlockStateConfiguration.CODEC));

    // look at CaveFeatures.MOSS_PATCH to steal lush caves moss patches (and MOSS_VEGETATION)


    public static final class Configs {
        public static final TreeConfiguration DEWEY_SPRUCE = createDeweySpruce().build();
    }

    private static TreeConfigurationBuilder createDeweySpruce() {
        return  new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(5, 2, 1),
                BlockStateProvider.simple(CFBlocks.DEWEY_SPRUCE_LEAVES.get()),
                new SpruceFoliagePlacer(UniformInt.of(2, 3),
                        UniformInt.of(0, 2), UniformInt.of(1, 2)),
                new TwoLayersFeatureSize(2, 0, 2)).ignoreVines();
    }

    public static final class CFConfiguredFeatures {
        public static final ResourceKey<ConfiguredFeature<?, ?>> DEWEY_SPRUCE = createKey("dewey_spruce");
        public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_CLOUD_FOREST = createKey("trees_cloud_forest");

        public static final ResourceKey<ConfiguredFeature<?, ?>> NOISY_FOG_MOSS_PATCH = createKey("noisy_fog_moss_patch");

        public static final ResourceKey<ConfiguredFeature<?, ?>> FOG_MOSS_BOULDER = createKey("fog_moss_boulder");

        public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

            register(context, DEWEY_SPRUCE, CFFeatures.DEWEY_SPRUCE_TREE.get(), Configs.DEWEY_SPRUCE);
            register(context, TREES_CLOUD_FOREST, Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(
                            new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.SUPER_BIRCH_BEES), 0.2F),
                            new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.CHERRY_CHECKED), 0.4F)),
                            placedFeatures.getOrThrow(CFPlacedFeatures.DEWEY_SPRUCE)));
            register(context, NOISY_FOG_MOSS_PATCH, CFFeatures.NOISY_FOG_MOSS_PATCH.get(),
                    new DiskConfiguration(RuleBasedBlockStateProvider.simple(CFBlocks.FOG_MOSS_BLOCK.get()),
                            BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.CLAY)),
                            UniformInt.of(2, 3), 1));
            register(context, FOG_MOSS_BOULDER, CFFeatures.FOG_MOSS_BOULDER.get(),
                    new BlockStateConfiguration(CFBlocks.FOG_MOSS_BLOCK.get().defaultBlockState()));
        }

        public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
            return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(CloudForest.MOD_ID, name));
        }

        public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
            context.register(key, new ConfiguredFeature<>(feature, config));
        }
    }

    public static final class CFPlacedFeatures {
        public static final ResourceKey<PlacedFeature> DEWEY_SPRUCE = createKey("dewey_spruce");
        public static final ResourceKey<PlacedFeature> TREES_CLOUD_FOREST = createKey("trees_cloud_forest");

        public static final ResourceKey<PlacedFeature> NOISY_FOG_MOSS_PATCH = createKey("noisy_fog_moss_patch");
        public static final ResourceKey<PlacedFeature> FOG_MOSS_BOULDER = createKey("fog_moss_boulder");

        public static void bootstrap(BootstapContext<PlacedFeature> context) {
            register(context, DEWEY_SPRUCE, CFConfiguredFeatures.DEWEY_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
            register(context, TREES_CLOUD_FOREST, CFConfiguredFeatures.TREES_CLOUD_FOREST,
                    treePlacement(PlacementUtils.countExtra(14, 0.1F, 1)));

            register(context, NOISY_FOG_MOSS_PATCH, CFConfiguredFeatures.NOISY_FOG_MOSS_PATCH,
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID,
                    PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

            register(context, FOG_MOSS_BOULDER, CFConfiguredFeatures.FOG_MOSS_BOULDER, CountPlacement.of(2),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        }

        private static ImmutableList<PlacementModifier> treePlacement(PlacementModifier modifier, PlacementModifier... extraModifiers) {
            return ImmutableList.<PlacementModifier>builder().add(modifier).add(InSquarePlacement.spread()).add(SurfaceWaterDepthFilter.forMaxDepth(0)).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome()).add(extraModifiers).build();
        }

        public static ResourceKey<PlacedFeature> createKey(String name) {
            return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(CloudForest.MOD_ID, name));
        }

        public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
            context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(feature), modifiers));
        }

        public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
            register(context, key, feature, List.of(modifiers));
        }
    }
}
