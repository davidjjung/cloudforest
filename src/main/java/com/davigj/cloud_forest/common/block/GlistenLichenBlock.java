package com.davigj.cloud_forest.common.block;

import com.davigj.cloud_forest.core.registry.CFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class GlistenLichenBlock extends GlowLichenBlock {
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);
    public GlistenLichenBlock(Properties p_153282_) {
        super(p_153282_);
    }

    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(getFaceProperty(Direction.UP))) {
            level.setBlockAndUpdate(pos, CFBlocks.HANGING_GLISTEN_LICHEN.get().defaultBlockState());
        } else {
            this.spreader.spreadFromRandomFaceTowardRandomDirection(state, level, pos, random);
        }
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double spread = 0.6;
        double $$4 = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * spread;
        double $$5 = (double) pos.getY() + 0.5 + (random.nextDouble() - 0.5) * spread;
        double $$6 = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * spread;
        level.addParticle(ParticleTypes.END_ROD, $$4, $$5, $$6, 0.0, 0.0, 0.0);
    }
}
