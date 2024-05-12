package com.davigj.cloud_forest.common.levelgen.feature.treedecorators;

import com.davigj.cloud_forest.core.registry.CFFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class NimbusOrchidsDecorator extends TreeDecorator {
    // TODO: look at TreeVineDecorator to see how glisten lichen may be placed idk
    public static final Codec<NimbusOrchidsDecorator> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(instance -> instance.probability),
            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(instance -> instance.blockProvider)
    ).apply(codec, NimbusOrchidsDecorator::new));


    private final float probability;
    private final BlockStateProvider blockProvider;

    public NimbusOrchidsDecorator(float probability, BlockStateProvider blockProvider) {
        this.probability = probability;
        this.blockProvider = blockProvider;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return CFFeatures.NIMBUS_ORCHIDS.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        for (BlockPos pos : context.leaves()) {
            if (random.nextFloat() < this.probability) {
                BlockPos candidate = pos.above();
                if (context.isAir(candidate)) {
                    context.setBlock(candidate, this.blockProvider.getState(random, candidate));
                }
            }
        }
    }
}
