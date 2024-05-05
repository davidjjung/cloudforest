package com.davigj.cloud_forest.common.block;

import com.davigj.cloud_forest.core.registry.CFBlocks;
import com.teamabnormals.blueprint.core.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FogMossBlock extends Block implements BonemealableBlock {
    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 4);

    public FogMossBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 4));
    }

    private static final VoxelShape[] LAYER_LOGISTICS = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity) {
            level.scheduleTick(pos, this, 4);
        }
    }

    public void deflate(Level level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos.above()).getBlock() instanceof FogMossBlock) {
            return;
        }
        RandomSource random = level.getRandom();
        double d0 = pos.getX() + random.nextDouble() * 0.8D + 0.1D;
        double d1 = pos.getY() + random.nextDouble() * 0.8D + 0.1D;
        double d2 = pos.getZ() + random.nextDouble() * 0.8D + 0.1D;
        double d3 = random.nextGaussian() * 0.04D;
        double d4 = random.nextGaussian() * 0.04D;
        double d5 = random.nextGaussian() * 0.04D;
        NetworkUtil.spawnParticle("cloud_forest:fog_moss_puff", d0, d1, d2, d3, d4, d5);
        level.playSound(null, pos, SoundEvents.HORSE_BREATHE, SoundSource.BLOCKS, 1.0F, 1.0F);
        int layers = state.getValue(LAYERS);
        if (layers > 1) {
            if (level.getBlockState(pos.above()).getBlock() instanceof FogMossCarpetBlock) {
                level.removeBlock(pos, false);
                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
            }
            level.setBlock(pos, state.setValue(LAYERS, layers - 1), 3);
            level.scheduleTick(pos, this, level.getRandom().nextInt(10));
        } else {
            if (level.getBlockState(pos.below()).getBlock() instanceof FogMossBlock || level.getBlockState(pos.below()).isAir()) {
                level.removeBlock(pos, false);
                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
            } else {
                level.setBlockAndUpdate(pos, CFBlocks.FOG_MOSS_CARPET.get().defaultBlockState());
            }
        }
    }

    public void checkNeighbors(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            Block block = level.getBlockState(blockpos).getBlock();
            int delay = 4;
            if (block instanceof FogMossBlock) {
                if (direction == Direction.UP) {
                    delay = 0;
                } else if (direction == Direction.DOWN) {
                    delay = 7;
                }
                level.scheduleTick(blockpos, block, delay + 3);
            }
        }
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(LAYERS) < 4;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return LAYER_LOGISTICS[state.getValue(LAYERS) - 1];
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.checkNeighbors(level, pos);
        this.deflate(level, pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        // TODO: place patch of bonemeal m8
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return (int) (double) blockState.getValue(LAYERS);
    }
}
