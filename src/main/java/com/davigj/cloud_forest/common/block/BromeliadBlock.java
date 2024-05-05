package com.davigj.cloud_forest.common.block;

import com.davigj.cloud_forest.core.other.CFBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BromeliadBlock extends FlowerBlock {
    public BromeliadBlock(Supplier<MobEffect> effectSupplier, int p_53513_, Properties p_53514_) {
        super(effectSupplier, p_53513_, p_53514_);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState state2 = worldIn.getBlockState(pos.below());
        return state2.is(CFBlockTags.EPIPHYTE_PLACEABLE);
    }
}
