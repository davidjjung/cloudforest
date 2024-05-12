package com.davigj.cloud_forest.common.levelgen.feature.trunkplacers;

import com.davigj.cloud_forest.core.registry.CFFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CloudLaurelTrunkPlacer extends TrunkPlacer {
    private static final Codec<UniformInt> BRANCH_START_CODEC = ExtraCodecs.validate(UniformInt.CODEC, (branchStart) -> {
        return branchStart.getMaxValue() - branchStart.getMinValue() < 1 ? DataResult.error(() -> {
            return "Need at least 2 blocks variation for the branch starts to fit both branches";
        }) : DataResult.success(branchStart);
    });
    public static final Codec<CloudLaurelTrunkPlacer> CODEC = RecordCodecBuilder.create((codec) -> {
        return trunkPlacerParts(codec).and(codec.group(IntProvider.codec(1, 3).fieldOf("branch_count").forGetter((branchCount) -> {
            return branchCount.branchCount;
        }), IntProvider.codec(2, 16).fieldOf("branch_horizontal_length").forGetter((branchHorizontalLength) -> {
            return branchHorizontalLength.branchHorizontalLength;
        }), IntProvider.codec(-16, 0, BRANCH_START_CODEC).fieldOf("branch_start_offset_from_top").forGetter((branchStartOffset) -> {
            return branchStartOffset.branchStartOffsetFromTop;
        }), IntProvider.codec(-16, 16).fieldOf("branch_end_offset_from_top").forGetter((branchEndOffset) -> {
            return branchEndOffset.branchEndOffsetFromTop;
        }))).apply(codec, CloudLaurelTrunkPlacer::new);
    });
    private final IntProvider branchCount;
    private final IntProvider branchHorizontalLength;
    private final UniformInt branchStartOffsetFromTop;
    private final UniformInt secondBranchStartOffsetFromTop;
    private final IntProvider branchEndOffsetFromTop;

    public CloudLaurelTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, IntProvider branchCount, IntProvider branchHorizontalLength, UniformInt branchStartOffsetFromTop, IntProvider branchEndOffsetFromTop) {
        super(baseHeight, heightRandA, heightRandB);
        this.branchCount = branchCount;
        this.branchHorizontalLength = branchHorizontalLength;
        this.branchStartOffsetFromTop = branchStartOffsetFromTop;
        this.secondBranchStartOffsetFromTop = UniformInt.of(branchStartOffsetFromTop.getMinValue(), branchStartOffsetFromTop.getMaxValue() - 1);
        this.branchEndOffsetFromTop = branchEndOffsetFromTop;
    }

    protected TrunkPlacerType<?> type() {
        return null;
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, int trunkHeight, BlockPos startPos, TreeConfiguration config) {
        setDirtAt(world, blockConsumer, random, startPos.below(), config);
        int firstBranchStart = Math.max(0, trunkHeight - 1 + this.branchStartOffsetFromTop.sample(random));
        int secondBranchStart = Math.max(0, trunkHeight - 1 + this.secondBranchStartOffsetFromTop.sample(random));
        if (secondBranchStart >= firstBranchStart) {
            ++secondBranchStart;
        }

        int branchCount = this.branchCount.sample(random);
        boolean hasThreeBranches = branchCount == 3;
        boolean hasTwoBranches = branchCount >= 2;
        int branchGenerationHeight;
        if (hasThreeBranches) {
            branchGenerationHeight = trunkHeight;
        } else if (hasTwoBranches) {
            branchGenerationHeight = Math.max(firstBranchStart, secondBranchStart) + 1;
        } else {
            branchGenerationHeight = firstBranchStart + 1;
        }

        for(int i = 0; i < branchGenerationHeight; ++i) {
            this.placeLog(world, blockConsumer, random, startPos.above(i), config);
        }

        List<FoliagePlacer.FoliageAttachment> foliageAttachments = new ArrayList<>();
        if (hasThreeBranches) {
            foliageAttachments.add(new FoliagePlacer.FoliageAttachment(startPos.above(branchGenerationHeight), 0, false));
        }

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Function<BlockState, BlockState> axisSetter = (state) -> {
            return state.trySetValue(RotatedPillarBlock.AXIS, direction.getAxis());
        };
        foliageAttachments.add(this.generateBranch(world, blockConsumer, random, trunkHeight, startPos, config, axisSetter, direction, firstBranchStart, firstBranchStart < branchGenerationHeight - 1, mutableBlockPos));
        if (hasTwoBranches) {
            foliageAttachments.add(this.generateBranch(world, blockConsumer, random, trunkHeight, startPos, config, axisSetter, direction.getOpposite(), secondBranchStart, secondBranchStart < branchGenerationHeight - 1, mutableBlockPos));
        }

        return foliageAttachments;
    }

    private FoliagePlacer.FoliageAttachment generateBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, int trunkHeight, BlockPos startPos, TreeConfiguration config, Function<BlockState, BlockState> axisSetter, Direction direction, int branchStart, boolean hasEnd, BlockPos.MutableBlockPos mutableBlockPos) {
        mutableBlockPos.set(startPos).move(Direction.UP, branchStart);
        int branchEndHeight = trunkHeight - 1 + this.branchEndOffsetFromTop.sample(random);
        boolean shouldGenerateExtraLayer = hasEnd || branchEndHeight < branchStart;
        int branchHorizontalLength = this.branchHorizontalLength.sample(random) + (shouldGenerateExtraLayer ? 1 : 0);
        BlockPos branchEndPos = startPos.relative(direction, branchHorizontalLength).above(branchEndHeight);
        int numLogsToPlace = shouldGenerateExtraLayer ? 2 : 1;

        for(int i = 0; i < numLogsToPlace; ++i) {
            this.placeLog(world, blockConsumer, random, mutableBlockPos.move(direction), config, axisSetter);
        }

        Direction upwardOrDownward = branchEndPos.getY() > mutableBlockPos.getY() ? Direction.UP : Direction.DOWN;

        while(true) {
            int distanceToBranchEnd = mutableBlockPos.distManhattan(branchEndPos);
            if (distanceToBranchEnd == 0) {
                return new FoliagePlacer.FoliageAttachment(branchEndPos.above(), 0, false);
            }

            float ratio = (float)Math.abs(branchEndPos.getY() - mutableBlockPos.getY()) / (float)distanceToBranchEnd;
            boolean shouldMoveUpward = random.nextFloat() < ratio;
            mutableBlockPos.move(shouldMoveUpward ? upwardOrDownward : direction);
            this.placeLog(world, blockConsumer, random, mutableBlockPos, config, shouldMoveUpward ? Function.identity() : axisSetter);
        }
    }
}
