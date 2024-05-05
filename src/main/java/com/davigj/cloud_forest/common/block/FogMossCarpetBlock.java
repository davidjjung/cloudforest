package com.davigj.cloud_forest.common.block;

import com.davigj.cloud_forest.core.registry.CFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.ForgeHooks;

public class FogMossCarpetBlock extends CarpetBlock {
    public static final BooleanProperty POWERED;

    public FogMossCarpetBlock(Properties p_152915_) {
        super(p_152915_);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double spread = 0.6;
        double $$4 = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * spread;
        double $$5 = (double) pos.getY() + 0.5 + (random.nextDouble() - 0.5) * spread;
        double $$6 = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * spread;
        level.addParticle(ParticleTypes.END_ROD, $$4, $$5, $$6, 0.0, 0.0, 0.0);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(POWERED);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, (BlockState) CFBlocks.FOG_MOSS_BLOCK.get().defaultBlockState(), 2);
        BlockPos above = pos.above();
        if (level.getBlockState(above).isAir() && random.nextBoolean()) {
            level.setBlockAndUpdate(above, state);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
