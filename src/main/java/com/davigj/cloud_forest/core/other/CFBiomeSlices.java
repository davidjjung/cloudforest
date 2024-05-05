package com.davigj.cloud_forest.core.other;

import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.registry.CFBiomes;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSlice;
import com.teamabnormals.blueprint.core.registry.BlueprintBiomes;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import com.teamabnormals.blueprint.core.util.BiomeUtil;
import net.minecraft.SharedConstants;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.davigj.cloud_forest.core.registry.CFBiomes.CLOUD_FOREST;

public final class CFBiomeSlices {
    public static final ResourceKey<ModdedBiomeSlice> CLOUD_FOREST_SLICE = createKey("cloud_forest");

    public static final ResourceKey<Biome> CLOUD_FOREST_AREA = CFBiomes.createKey("cloud_forest_area");

    public static ResourceKey<ModdedBiomeSlice> createKey(String name) {
        return ResourceKey.create(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, new ResourceLocation(CloudForest.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ModdedBiomeSlice> context) {
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> entries = new ArrayList<>();
        new CFBiomeBuilder().addBiomes(entries::add);

        context.register(CLOUD_FOREST_SLICE, new ModdedBiomeSlice(25,
                BiomeUtil.MultiNoiseModdedBiomeProvider.builder().biomes(entries::forEach)
                .area(CLOUD_FOREST_AREA, CLOUD_FOREST)
                .build(), LevelStem.OVERWORLD));
    }

    @SuppressWarnings("unchecked")
    private static final class CFBiomeBuilder {
        private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
        private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
                Climate.Parameter.span(-1.0F, -0.45F),
                Climate.Parameter.span(-0.45F, -0.15F),
                Climate.Parameter.span(-0.15F, 0.2F),
                Climate.Parameter.span(0.2F, 0.55F),
                Climate.Parameter.span(0.55F, 1.0F)};

        private final Climate.Parameter[] humidities = new Climate.Parameter[]{
                Climate.Parameter.span(-1.0F, -0.35F),
                Climate.Parameter.span(-0.35F, -0.1F),
                Climate.Parameter.span(-0.1F, 0.1F),
                Climate.Parameter.span(0.1F, 0.3F),
                Climate.Parameter.span(0.3F, 1.0F)};

        private final Climate.Parameter[] erosions = new Climate.Parameter[]{
                Climate.Parameter.span(-1.0F, -0.78F),
                Climate.Parameter.span(-0.78F, -0.375F),
                Climate.Parameter.span(-0.375F, -0.2225F),
                Climate.Parameter.span(-0.2225F, 0.05F),
                Climate.Parameter.span(0.05F, 0.45F),
                Climate.Parameter.span(0.45F, 0.55F),
                Climate.Parameter.span(0.55F, 1.0F)};

        private final Climate.Parameter FROZEN_RANGE = this.temperatures[0];
        private final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(this.temperatures[1], this.temperatures[4]);

        private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
        private final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
        private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
        private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
        private final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);
        private final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
        private final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
        private final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);

        private final ResourceKey<Biome> VANILLA = BlueprintBiomes.ORIGINAL_SOURCE_MARKER;

        // Each progressive row represents a higher temperature level! Each column represents a humidity level!
        // Consult https://minecraft.wiki/w/World_generation for specifics!!
        private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, CLOUD_FOREST},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}
        };

        // Spawns at higher weirdness levels. Based on what parameter
        // combos actually have weirdness variants in vanilla's OverworldBiomeBuilder
        private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
                {VANILLA, null, VANILLA, null, null},
                {null, null, null, null, CLOUD_FOREST},
                {VANILLA, null, null, VANILLA, null},
                {null, null, VANILLA, VANILLA, VANILLA},
                {null, null, null, null, null}
        };

        private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}};

        private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
                {VANILLA, null, null, null, null},
                {VANILLA, null, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, null},
                {null, null, null, null, null},
                {VANILLA, VANILLA, null, null, null}};

        private final ResourceKey<Biome>[][] SHATTERED_BIOMES = new ResourceKey[][]{
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        protected void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187176_) {
            this.addOffCoastBiomes(p_187176_);
            this.addInlandBiomes(p_187176_);
            this.addUndergroundBiomes(p_187176_);
        }

        // The rest of the logic is taken straight from OverworldBiomeBuilder
        private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187196_) {
            this.addSurfaceBiome(p_187196_, this.FULL_RANGE, this.FULL_RANGE, this.mushroomFieldsContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);

            for (Climate.Parameter temperature : this.temperatures) {
                this.addSurfaceBiome(p_187196_, temperature, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
                this.addSurfaceBiome(p_187196_, temperature, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
            }
        }

        private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187216_) {
            this.addMidSlice(p_187216_, Climate.Parameter.span(-1.0F, -0.93333334F));
            this.addHighSlice(p_187216_, Climate.Parameter.span(-0.93333334F, -0.7666667F));
            this.addPeaks(p_187216_, Climate.Parameter.span(-0.7666667F, -0.56666666F));
            this.addHighSlice(p_187216_, Climate.Parameter.span(-0.56666666F, -0.4F));
            this.addMidSlice(p_187216_, Climate.Parameter.span(-0.4F, -0.26666668F));
            this.addLowSlice(p_187216_, Climate.Parameter.span(-0.26666668F, -0.05F));
            this.addValleys(p_187216_, Climate.Parameter.span(-0.05F, 0.05F));
            this.addLowSlice(p_187216_, Climate.Parameter.span(0.05F, 0.26666668F));
            this.addMidSlice(p_187216_, Climate.Parameter.span(0.26666668F, 0.4F));
            this.addHighSlice(p_187216_, Climate.Parameter.span(0.4F, 0.56666666F));
            this.addPeaks(p_187216_, Climate.Parameter.span(0.56666666F, 0.7666667F));
            this.addHighSlice(p_187216_, Climate.Parameter.span(0.7666667F, 0.93333334F));
            this.addMidSlice(p_187216_, Climate.Parameter.span(0.93333334F, 1.0F));
        }

        private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187178_, Climate.Parameter p_187179_) {
            for(int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter climate$parameter = this.temperatures[i];

                for(int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter climate$parameter1 = this.humidities[j];
                    ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, p_187179_);
                    ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, p_187179_);
                    ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, p_187179_);
                    ResourceKey<Biome> resourcekey3 = this.pickPlateauBiome(i, j, p_187179_);
                    ResourceKey<Biome> resourcekey4 = this.pickShatteredBiome(i, j, p_187179_);
                    ResourceKey<Biome> resourcekey5 = this.maybePickWindsweptSavannaBiome(i, j, p_187179_, resourcekey4);
                    ResourceKey<Biome> resourcekey6 = this.pickPeakBiome(i, j, p_187179_);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[0], p_187179_, 0.0F, resourcekey6);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[1], p_187179_, 0.0F, resourcekey2);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], p_187179_, 0.0F, resourcekey6);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), p_187179_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], p_187179_, 0.0F, resourcekey3);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, this.midInlandContinentalness, this.erosions[3], p_187179_, 0.0F, resourcekey1);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, this.farInlandContinentalness, this.erosions[3], p_187179_, 0.0F, resourcekey3);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], p_187179_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], p_187179_, 0.0F, resourcekey5);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], p_187179_, 0.0F, resourcekey4);
                    this.addSurfaceBiome(p_187178_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], p_187179_, 0.0F, resourcekey);
                }
            }

        }

        private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187198_, Climate.Parameter p_187199_) {
            for(int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter climate$parameter = this.temperatures[i];

                for(int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter climate$parameter1 = this.humidities[j];
                    ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, p_187199_);
                    ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, p_187199_);
                    ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, p_187199_);
                    ResourceKey<Biome> resourcekey3 = this.pickPlateauBiome(i, j, p_187199_);
                    ResourceKey<Biome> resourcekey4 = this.pickShatteredBiome(i, j, p_187199_);
                    ResourceKey<Biome> resourcekey5 = this.maybePickWindsweptSavannaBiome(i, j, p_187199_, resourcekey);
                    ResourceKey<Biome> resourcekey6 = this.pickSlopeBiome(i, j, p_187199_);
                    ResourceKey<Biome> resourcekey7 = this.pickPeakBiome(i, j, p_187199_);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187199_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[0], p_187199_, 0.0F, resourcekey6);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[0], p_187199_, 0.0F, resourcekey7);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[1], p_187199_, 0.0F, resourcekey2);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], p_187199_, 0.0F, resourcekey6);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), p_187199_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], p_187199_, 0.0F, resourcekey3);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, this.midInlandContinentalness, this.erosions[3], p_187199_, 0.0F, resourcekey1);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, this.farInlandContinentalness, this.erosions[3], p_187199_, 0.0F, resourcekey3);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], p_187199_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], p_187199_, 0.0F, resourcekey5);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], p_187199_, 0.0F, resourcekey4);
                    this.addSurfaceBiome(p_187198_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], p_187199_, 0.0F, resourcekey);
                }
            }

        }

        private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187218_, Climate.Parameter p_187219_) {
            this.addSurfaceBiome(p_187218_, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), p_187219_, 0.0F, Biomes.STONY_SHORE);
            this.addSurfaceBiome(p_187218_, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187219_, 0.0F, Biomes.SWAMP);
            this.addSurfaceBiome(p_187218_, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187219_, 0.0F, Biomes.MANGROVE_SWAMP);

            for(int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter climate$parameter = this.temperatures[i];

                for(int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter climate$parameter1 = this.humidities[j];
                    ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, p_187219_);
                    ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, p_187219_);
                    ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, p_187219_);
                    ResourceKey<Biome> resourcekey3 = this.pickShatteredBiome(i, j, p_187219_);
                    ResourceKey<Biome> resourcekey4 = this.pickPlateauBiome(i, j, p_187219_);
                    ResourceKey<Biome> resourcekey5 = this.pickBeachBiome(i, j);
                    ResourceKey<Biome> resourcekey6 = this.maybePickWindsweptSavannaBiome(i, j, p_187219_, resourcekey);
                    ResourceKey<Biome> resourcekey7 = this.pickShatteredCoastBiome(i, j, p_187219_);
                    ResourceKey<Biome> resourcekey8 = this.pickSlopeBiome(i, j, p_187219_);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[0], p_187219_, 0.0F, resourcekey8);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[1], p_187219_, 0.0F, resourcekey2);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.farInlandContinentalness, this.erosions[1], p_187219_, 0.0F, i == 0 ? resourcekey8 : resourcekey4);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[2], p_187219_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.midInlandContinentalness, this.erosions[2], p_187219_, 0.0F, resourcekey1);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.farInlandContinentalness, this.erosions[2], p_187219_, 0.0F, resourcekey4);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], p_187219_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[3], p_187219_, 0.0F, resourcekey1);
                    if (p_187219_.max() < 0L) {
                        this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.coastContinentalness, this.erosions[4], p_187219_, 0.0F, resourcekey5);
                        this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], p_187219_, 0.0F, resourcekey);
                    } else {
                        this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], p_187219_, 0.0F, resourcekey);
                    }

                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.coastContinentalness, this.erosions[5], p_187219_, 0.0F, resourcekey7);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[5], p_187219_, 0.0F, resourcekey6);
                    this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], p_187219_, 0.0F, resourcekey3);
                    if (p_187219_.max() < 0L) {
                        this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.coastContinentalness, this.erosions[6], p_187219_, 0.0F, resourcekey5);
                    } else {
                        this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, this.coastContinentalness, this.erosions[6], p_187219_, 0.0F, resourcekey);
                    }

                    if (i == 0) {
                        this.addSurfaceBiome(p_187218_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187219_, 0.0F, resourcekey);
                    }
                }
            }

        }

        private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187229_, Climate.Parameter p_187230_) {
            this.addSurfaceBiome(p_187229_, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), p_187230_, 0.0F, Biomes.STONY_SHORE);
            this.addSurfaceBiome(p_187229_, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187230_, 0.0F, Biomes.SWAMP);
            this.addSurfaceBiome(p_187229_, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187230_, 0.0F, Biomes.MANGROVE_SWAMP);

            for(int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter climate$parameter = this.temperatures[i];

                for(int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter climate$parameter1 = this.humidities[j];
                    ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, p_187230_);
                    ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, p_187230_);
                    ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, p_187230_);
                    ResourceKey<Biome> resourcekey3 = this.pickBeachBiome(i, j);
                    ResourceKey<Biome> resourcekey4 = this.maybePickWindsweptSavannaBiome(i, j, p_187230_, resourcekey);
                    ResourceKey<Biome> resourcekey5 = this.pickShatteredCoastBiome(i, j, p_187230_);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187230_, 0.0F, resourcekey1);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187230_, 0.0F, resourcekey2);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), p_187230_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), p_187230_, 0.0F, resourcekey1);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, this.coastContinentalness, Climate.Parameter.span(this.erosions[3], this.erosions[4]), p_187230_, 0.0F, resourcekey3);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], p_187230_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, this.coastContinentalness, this.erosions[5], p_187230_, 0.0F, resourcekey5);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, this.nearInlandContinentalness, this.erosions[5], p_187230_, 0.0F, resourcekey4);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], p_187230_, 0.0F, resourcekey);
                    this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, this.coastContinentalness, this.erosions[6], p_187230_, 0.0F, resourcekey3);
                    if (i == 0) {
                        this.addSurfaceBiome(p_187229_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187230_, 0.0F, resourcekey);
                    }
                }
            }

        }

        private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187238_, Climate.Parameter p_187239_) {
            this.addSurfaceBiome(p_187238_, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187239_, 0.0F, p_187239_.max() < 0L ? Biomes.STONY_SHORE : Biomes.FROZEN_RIVER);
            this.addSurfaceBiome(p_187238_, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187239_, 0.0F, p_187239_.max() < 0L ? Biomes.STONY_SHORE : Biomes.RIVER);
            this.addSurfaceBiome(p_187238_, this.FROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187239_, 0.0F, Biomes.FROZEN_RIVER);
            this.addSurfaceBiome(p_187238_, this.UNFROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187239_, 0.0F, Biomes.RIVER);
            this.addSurfaceBiome(p_187238_, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), p_187239_, 0.0F, Biomes.FROZEN_RIVER);
            this.addSurfaceBiome(p_187238_, this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), p_187239_, 0.0F, Biomes.RIVER);
            this.addSurfaceBiome(p_187238_, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], p_187239_, 0.0F, Biomes.FROZEN_RIVER);
            this.addSurfaceBiome(p_187238_, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], p_187239_, 0.0F, Biomes.RIVER);
            this.addSurfaceBiome(p_187238_, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187239_, 0.0F, Biomes.SWAMP);
            this.addSurfaceBiome(p_187238_, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187239_, 0.0F, Biomes.MANGROVE_SWAMP);
            this.addSurfaceBiome(p_187238_, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], p_187239_, 0.0F, Biomes.FROZEN_RIVER);

            for(int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter climate$parameter = this.temperatures[i];

                for(int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter climate$parameter1 = this.humidities[j];
                    ResourceKey<Biome> resourcekey = this.pickMiddleBiomeOrBadlandsIfHot(i, j, p_187239_);
                    this.addSurfaceBiome(p_187238_, climate$parameter, climate$parameter1, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), p_187239_, 0.0F, resourcekey);
                }
            }

        }

        private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187227_) {
            this.addUndergroundBiome(p_187227_, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.DRIPSTONE_CAVES);
            this.addUndergroundBiome(p_187227_, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.LUSH_CAVES);
        }

        private ResourceKey<Biome> pickMiddleBiome(int p_187164_, int p_187165_, Climate.Parameter p_187166_) {
            if (p_187166_.max() < 0L) {
                return this.MIDDLE_BIOMES[p_187164_][p_187165_];
            } else {
                ResourceKey<Biome> resourcekey = this.MIDDLE_BIOMES_VARIANT[p_187164_][p_187165_];
                return resourcekey == null ? this.MIDDLE_BIOMES[p_187164_][p_187165_] : resourcekey;
            }
        }

        private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(int p_187192_, int p_187193_, Climate.Parameter p_187194_) {
            return p_187192_ == 4 ? this.pickBadlandsBiome(p_187193_, p_187194_) : this.pickMiddleBiome(p_187192_, p_187193_, p_187194_);
        }

        private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int p_187212_, int p_187213_, Climate.Parameter p_187214_) {
            return p_187212_ == 0 ? this.pickSlopeBiome(p_187212_, p_187213_, p_187214_) : this.pickMiddleBiomeOrBadlandsIfHot(p_187212_, p_187213_, p_187214_);
        }

        private ResourceKey<Biome> maybePickWindsweptSavannaBiome(int p_201991_, int p_201992_, Climate.Parameter p_201993_, ResourceKey<Biome> p_201994_) {
            return p_201991_ > 1 && p_201992_ < 4 && p_201993_.max() >= 0L ? Biomes.WINDSWEPT_SAVANNA : p_201994_;
        }

        private ResourceKey<Biome> pickShatteredCoastBiome(int p_187223_, int p_187224_, Climate.Parameter p_187225_) {
            ResourceKey<Biome> resourcekey = p_187225_.max() >= 0L ? this.pickMiddleBiome(p_187223_, p_187224_, p_187225_) : this.pickBeachBiome(p_187223_, p_187224_);
            return this.maybePickWindsweptSavannaBiome(p_187223_, p_187224_, p_187225_, resourcekey);
        }

        private ResourceKey<Biome> pickBeachBiome(int p_187161_, int p_187162_) {
            if (p_187161_ == 0) {
                return Biomes.SNOWY_BEACH;
            } else {
                return p_187161_ == 4 ? Biomes.DESERT : Biomes.BEACH;
            }
        }

        private ResourceKey<Biome> pickBadlandsBiome(int p_187173_, Climate.Parameter p_187174_) {
            if (p_187173_ < 2) {
                return p_187174_.max() < 0L ? Biomes.BADLANDS : Biomes.ERODED_BADLANDS;
            } else {
                return p_187173_ < 3 ? Biomes.BADLANDS : Biomes.WOODED_BADLANDS;
            }
        }

        private ResourceKey<Biome> pickPlateauBiome(int p_187234_, int p_187235_, Climate.Parameter p_187236_) {
            if (p_187236_.max() >= 0L) {
                ResourceKey<Biome> resourcekey = this.PLATEAU_BIOMES_VARIANT[p_187234_][p_187235_];
                if (resourcekey != null) {
                    return resourcekey;
                }
            }

            return this.PLATEAU_BIOMES[p_187234_][p_187235_];
        }

        private ResourceKey<Biome> pickPeakBiome(int p_187241_, int p_187242_, Climate.Parameter p_187243_) {
            if (p_187241_ <= 2) {
                return p_187243_.max() < 0L ? Biomes.JAGGED_PEAKS : Biomes.FROZEN_PEAKS;
            } else {
                return p_187241_ == 3 ? Biomes.STONY_PEAKS : this.pickBadlandsBiome(p_187242_, p_187243_);
            }
        }

        private ResourceKey<Biome> pickSlopeBiome(int p_187245_, int p_187246_, Climate.Parameter p_187247_) {
            if (p_187245_ >= 3) {
                return this.pickPlateauBiome(p_187245_, p_187246_, p_187247_);
            } else {
                return p_187246_ <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
            }
        }

        private ResourceKey<Biome> pickShatteredBiome(int p_202002_, int p_202003_, Climate.Parameter p_202004_) {
            ResourceKey<Biome> resourcekey = this.SHATTERED_BIOMES[p_202002_][p_202003_];
            return resourcekey == null ? this.pickMiddleBiome(p_202002_, p_202003_, p_202004_) : resourcekey;
        }

        private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187181_, Climate.Parameter p_187182_, Climate.Parameter p_187183_, Climate.Parameter p_187184_, Climate.Parameter p_187185_, Climate.Parameter p_187186_, float p_187187_, ResourceKey<Biome> p_187188_) {
            p_187181_.accept(Pair.of(Climate.parameters(p_187182_, p_187183_, p_187184_, p_187185_, Climate.Parameter.point(0.0F), p_187186_, p_187187_), p_187188_));
            p_187181_.accept(Pair.of(Climate.parameters(p_187182_, p_187183_, p_187184_, p_187185_, Climate.Parameter.point(1.0F), p_187186_, p_187187_), p_187188_));
        }

        private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187201_, Climate.Parameter p_187202_, Climate.Parameter p_187203_, Climate.Parameter p_187204_, Climate.Parameter p_187205_, Climate.Parameter p_187206_, float p_187207_, ResourceKey<Biome> p_187208_) {
            p_187201_.accept(Pair.of(Climate.parameters(p_187202_, p_187203_, p_187204_, p_187205_, Climate.Parameter.span(0.2F, 0.9F), p_187206_, p_187207_), p_187208_));
        }
    }
}
