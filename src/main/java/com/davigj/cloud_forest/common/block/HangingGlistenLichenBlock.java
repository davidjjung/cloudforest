package com.davigj.cloud_forest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class HangingGlistenLichenBlock extends Block {
    public HangingGlistenLichenBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public boolean canSurvive(BlockState p_153347_, LevelReader p_153348_, BlockPos p_153349_) {
        BlockPos $$3 = p_153349_.above();
        BlockState $$4 = p_153348_.getBlockState($$3);
        return $$4.isFaceSturdy(p_153348_, $$3, Direction.DOWN);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double spread = 0.7;
        double $$4 = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * spread;
        double $$5 = (double) pos.getY() + 0.5 + (random.nextDouble() - 0.5) * spread;
        double $$6 = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * spread;
        level.addParticle(ParticleTypes.END_ROD, $$4, $$5, $$6, 0.0, 0.0, 0.0);
    }
}
