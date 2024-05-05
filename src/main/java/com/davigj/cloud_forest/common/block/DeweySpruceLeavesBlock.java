package com.davigj.cloud_forest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DeweySpruceLeavesBlock extends LeavesBlock {
    public DeweySpruceLeavesBlock(Properties p_54422_) {
        super(p_54422_);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction $$4 = Direction.getRandom(random);
            if ($$4 != Direction.UP) {
                BlockPos $$5 = pos.relative($$4);
                BlockState $$6 = level.getBlockState($$5);
                if (!state.canOcclude() || !$$6.isFaceSturdy(level, $$5, $$4.getOpposite())) {
                    double $$7 = $$4.getStepX() == 0 ? random.nextDouble() : 0.5 + (double)$$4.getStepX() * 0.6;
                    double $$8 = $$4.getStepY() == 0 ? random.nextDouble() : 0.5 + (double)$$4.getStepY() * 0.6;
                    double $$9 = $$4.getStepZ() == 0 ? random.nextDouble() : 0.5 + (double)$$4.getStepZ() * 0.6;
                    level.addParticle(ParticleTypes.DRIPPING_HONEY, (double)pos.getX() + $$7,
                            (double)pos.getY() + $$8, (double)pos.getZ() + $$9, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
