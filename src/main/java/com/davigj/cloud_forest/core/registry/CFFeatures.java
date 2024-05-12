package com.davigj.cloud_forest.core.registry;

import com.davigj.cloud_forest.common.levelgen.feature.FogMossPatchFeature;
import com.davigj.cloud_forest.common.levelgen.feature.treedecorators.NimbusOrchidsDecorator;
import com.davigj.cloud_forest.core.CloudForest;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.OptionalInt;

@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CFFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CloudForest.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, CloudForest.MOD_ID);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, CloudForest.MOD_ID);

    public static final RegistryObject<Feature<TreeConfiguration>> DEWEY_SPRUCE_TREE = FEATURES.register("dewey_spruce_tree", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> FLOWERING_DEWEY_SPRUCE_TREE = FEATURES.register("flowering_dewey_spruce_tree", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> CLOUD_LAUREL_BRANCHING = FEATURES.register("cloud_laurel_branching", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> TALL_DARK_OAK = FEATURES.register("tall_dark_oak", () -> new TreeFeature(TreeConfiguration.CODEC));

    public static final RegistryObject<Feature<DiskConfiguration>> NOISY_FOG_MOSS_PATCH = FEATURES.register("noisy_fog_moss_patch", () -> new DiskFeature(DiskConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockStateConfiguration>> FOG_MOSS_BOULDER = FEATURES.register("fog_moss_boulder", () -> new FogMossPatchFeature(BlockStateConfiguration.CODEC));

    public static final RegistryObject<Feature<RandomPatchConfiguration>> HAZEBLOOM = FEATURES.register("hazebloom", () -> new RandomPatchFeature(RandomPatchConfiguration.CODEC));
    public static final RegistryObject<Feature<SimpleBlockConfiguration>> FOG_MOSS_VEGETATION = FEATURES.register("fog_moss_vegetation", () -> new SimpleBlockFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistryObject<Feature<VegetationPatchConfiguration>> FOG_MOSS_PATCH = FEATURES.register("fog_moss_patch", () -> new VegetationPatchFeature(VegetationPatchConfiguration.CODEC));

    public static final RegistryObject<TreeDecoratorType<?>> NIMBUS_ORCHIDS = TREE_DECORATOR_TYPES.register("nimbus_orchids", () -> new TreeDecoratorType<>(NimbusOrchidsDecorator.CODEC));
//    public static final RegistryObject<TrunkPlacerType<?>> CLOUD_LAUREL_TRUNK = TRUNK_PLACER_TYPES.register("cloud_laurel_trunk", () -> new TrunkPlacerType<>(CloudLaurelTrunkPlacer.CODEC));

    public static final class Configs {
        private static final NimbusOrchidsDecorator NIMBUS_ORCHIDS = new NimbusOrchidsDecorator(0.125F,
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(CFBlocks.NIMBUS_ORCHID.get().defaultBlockState(), 4)
                        .add(CFBlocks.RUBY_BROMELIAD.get().defaultBlockState(), 1).build()));

        public static final TreeConfiguration DEWEY_SPRUCE = createDeweySpruce().build();
        public static final TreeConfiguration FLOWERING_DEWEY_SPRUCE = createDeweySpruce().decorators(List.of(NIMBUS_ORCHIDS)).build();
        public static final TreeConfiguration CLOUD_LAUREL_BRANCHING = createCloudLaurelBranching().decorators(List.of(NIMBUS_ORCHIDS)).build();
        public static final TreeConfiguration TALL_DARK_OAK = createTallDarkOak().build();

    }

    private static TreeConfigurationBuilder createDeweySpruce() {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(5, 2, 1),
                BlockStateProvider.simple(CFBlocks.DEWEY_SPRUCE_LEAVES.get()),
                new SpruceFoliagePlacer(UniformInt.of(2, 3),
                        UniformInt.of(0, 2), UniformInt.of(1, 2)),
                new TwoLayersFeatureSize(2, 0, 2)).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createCloudLaurelBranching() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.STRIPPED_SPRUCE_LOG),
                new CherryTrunkPlacer(10, 4, 6,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1),
                                1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3),
                                3).build()), UniformInt.of(2, 4),
                        UniformInt.of(-7, -3), UniformInt.of(-4, -3)),
                BlockStateProvider.simple(Blocks.AZALEA_LEAVES), new CherryFoliagePlacer(UniformInt.of(3, 4),
                ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F,
                0.33333334F), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createTallDarkOak() {
        return (new TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                new DarkOakTrunkPlacer(12, 3, 1), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 1, 0, 1, 2,
                        OptionalInt.empty()))).ignoreVines();

    }

    public static final class CFConfiguredFeatures {
        public static final ResourceKey<ConfiguredFeature<?, ?>> DEWEY_SPRUCE = createKey("dewey_spruce");
        public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERING_DEWEY_SPRUCE = createKey("flowering_dewey_spruce");
        public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_LAUREL_BRANCHING = createKey("cloud_laurel_branching");
        public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_DARK_OAK = createKey("tall_dark_oak");
        public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_CLOUD_FOREST = createKey("trees_cloud_forest");

        public static final ResourceKey<ConfiguredFeature<?, ?>> NOISY_FOG_MOSS_PATCH = createKey("noisy_fog_moss_patch");

        public static final ResourceKey<ConfiguredFeature<?, ?>> FOG_MOSS_BOULDER = createKey("fog_moss_boulder");
        public static final ResourceKey<ConfiguredFeature<?, ?>> FOG_MOSS_VEGETATION = createKey("fog_moss_vegetation");
        public static final ResourceKey<ConfiguredFeature<?, ?>> FOG_MOSS_PATCH = createKey("fog_moss_patch");

        public static final ResourceKey<ConfiguredFeature<?, ?>> HAZEBLOOM = createKey("hazebloom");

        public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

            register(context, DEWEY_SPRUCE, CFFeatures.DEWEY_SPRUCE_TREE.get(), Configs.DEWEY_SPRUCE);
            register(context, FLOWERING_DEWEY_SPRUCE, CFFeatures.FLOWERING_DEWEY_SPRUCE_TREE.get(), Configs.FLOWERING_DEWEY_SPRUCE);
            register(context, CLOUD_LAUREL_BRANCHING, CFFeatures.CLOUD_LAUREL_BRANCHING.get(), Configs.CLOUD_LAUREL_BRANCHING);
            register(context, TALL_DARK_OAK, CFFeatures.TALL_DARK_OAK.get(), Configs.TALL_DARK_OAK);

            register(context, TREES_CLOUD_FOREST, Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(
                            new WeightedPlacedFeature(placedFeatures.getOrThrow(CFPlacedFeatures.FLOWERING_DEWEY_SPRUCE), 0.1F),
                            new WeightedPlacedFeature(placedFeatures.getOrThrow(CFPlacedFeatures.CLOUD_LAUREL_BRANCHING), 0.35F),
                            new WeightedPlacedFeature(placedFeatures.getOrThrow(CFPlacedFeatures.TALL_DARK_OAK), 0.35F)
                    ),
                            placedFeatures.getOrThrow(CFPlacedFeatures.DEWEY_SPRUCE)));

            register(context, NOISY_FOG_MOSS_PATCH, CFFeatures.NOISY_FOG_MOSS_PATCH.get(),
                    new DiskConfiguration(RuleBasedBlockStateProvider.simple(CFBlocks.FOG_MOSS_BLOCK.get()),
                            BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.CLAY)),
                            UniformInt.of(2, 3), 1));
            register(context, FOG_MOSS_BOULDER, CFFeatures.FOG_MOSS_BOULDER.get(),
                    new BlockStateConfiguration(CFBlocks.FOG_MOSS_BLOCK.get().defaultBlockState()));
            register(context, FOG_MOSS_VEGETATION, CFFeatures.FOG_MOSS_VEGETATION.get(), new SimpleBlockConfiguration(
                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 4)
                            .add(Blocks.GRASS.defaultBlockState(), 14)
                            .add(CFBlocks.HAZEBLOOM.get().defaultBlockState(), 7)
                            .add(CFBlocks.FOG_MOSS_CARPET.get().defaultBlockState(), 15)
                            .add(Blocks.AIR.defaultBlockState(), 50)
                    )));
            register(context, FOG_MOSS_PATCH, CFFeatures.FOG_MOSS_PATCH.get(), new VegetationPatchConfiguration(
                    BlockTags.MOSS_REPLACEABLE, new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
//                    .add(Blocks.GRASS_BLOCK.defaultBlockState(), 10)
                    .add(CFBlocks.FOG_MOSS_BLOCK.get().defaultBlockState(), 50)),
                    PlacementUtils.inlinePlaced(context.lookup(Registries.CONFIGURED_FEATURE)
                            .getOrThrow(CFConfiguredFeatures.FOG_MOSS_VEGETATION)), CaveSurface.FLOOR,
                    ConstantInt.of(3), 0.0F, 2, 0.8F,
                    UniformInt.of(3, 6), 0.75F));


            register(context, HAZEBLOOM, CFFeatures.HAZEBLOOM.get(), grassPatch(
                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(
                                    CFBlocks.HAZEBLOOM.get().defaultBlockState(), 2)
                            .add(Blocks.LARGE_FERN.defaultBlockState(), 1)), 64));
        }

        public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
            return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(CloudForest.MOD_ID, name));
        }

        public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
            context.register(key, new ConfiguredFeature<>(feature, config));
        }

        private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
            return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
        }
    }

    public static final class CFPlacedFeatures {
        public static final ResourceKey<PlacedFeature> DEWEY_SPRUCE = createKey("dewey_spruce");
        public static final ResourceKey<PlacedFeature> FLOWERING_DEWEY_SPRUCE = createKey("flowering_dewey_spruce");
        public static final ResourceKey<PlacedFeature> CLOUD_LAUREL_BRANCHING = createKey("cloud_laurel_branching");
        public static final ResourceKey<PlacedFeature> TALL_DARK_OAK = createKey("tall_dark_oak");
        public static final ResourceKey<PlacedFeature> TREES_CLOUD_FOREST = createKey("trees_cloud_forest");

        public static final ResourceKey<PlacedFeature> NOISY_FOG_MOSS_PATCH = createKey("noisy_fog_moss_patch");
        public static final ResourceKey<PlacedFeature> FOG_MOSS_BOULDER = createKey("fog_moss_boulder");

        public static final ResourceKey<PlacedFeature> FOG_MOSS_DECO_PATCH = createKey("fog_moss_deco_patch");

        public static final ResourceKey<PlacedFeature> HAZEBLOOM = createKey("hazebloom");

        public static void bootstrap(BootstapContext<PlacedFeature> context) {
            register(context, DEWEY_SPRUCE, CFConfiguredFeatures.DEWEY_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
            register(context, FLOWERING_DEWEY_SPRUCE, CFConfiguredFeatures.FLOWERING_DEWEY_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
            register(context, CLOUD_LAUREL_BRANCHING, CFConfiguredFeatures.CLOUD_LAUREL_BRANCHING, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
            register(context, TALL_DARK_OAK, CFConfiguredFeatures.TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));

            register(context, TREES_CLOUD_FOREST, CFConfiguredFeatures.TREES_CLOUD_FOREST,
                    treePlacement(PlacementUtils.countExtra(14, 0.1F, 1)));

            register(context, NOISY_FOG_MOSS_PATCH, CFConfiguredFeatures.NOISY_FOG_MOSS_PATCH,
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID,
                    PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

            register(context, FOG_MOSS_BOULDER, CFConfiguredFeatures.FOG_MOSS_BOULDER, CountPlacement.of(2),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

            register(context, FOG_MOSS_DECO_PATCH, CFConfiguredFeatures.FOG_MOSS_PATCH,
                    CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(4)), BiomeFilter.biome());

            register(context, HAZEBLOOM, CFConfiguredFeatures.HAZEBLOOM, RarityFilter.onAverageOnceEvery(16),
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
