package com.davigj.cloud_forest.common.block;

import com.davigj.cloud_forest.core.registry.CFBlocks;
import com.teamabnormals.blueprint.core.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.function.Supplier;

public class HazebloomBlock extends FlowerBlock {
    public static final BooleanProperty FRUITING = BooleanProperty.create("fruiting");

    public HazebloomBlock(Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
        super(stewEffect, stewEffectDuration, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FRUITING, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FRUITING);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity living) {
            level.setBlockAndUpdate(pos, state.setValue(FRUITING, false));
            double $$4 = particlePos(pos.getX(), living.getRandom());
            double $$5 = particlePos(pos.getY(), living.getRandom());
            double $$6 = particlePos(pos.getZ(), living.getRandom());
            for (int i = 0; i < 4; i++) {
                if (level.isClientSide) {
                    level.addParticle(ParticleTypes.END_ROD, $$4, $$5, $$6, 0.0, 0.0, 0.0);
                } else {
                    double spread = living.getRandom().nextGaussian() * 0.04;
                    NetworkUtil.spawnParticle("minecraft:end_rod",
                            $$4, $$5, $$6, spread, spread, spread);
                }
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(FRUITING);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlockAndUpdate(pos, state.setValue(FRUITING, true));

        double $$4 = particlePos(pos.getX(), random);
        double $$5 = particlePos(pos.getY(), random);
        double $$6 = particlePos(pos.getZ(), random);
        for (int i = 0; i < 4; i++) {
            NetworkUtil.spawnParticle("minecraft:end_rod",
                    $$4, $$5, $$6, random.nextGaussian() * 0.2, random.nextGaussian() * 0.2,
                    random.nextGaussian() * 0.2);
        }
    }

    public double particlePos(double coord, RandomSource random) {
        return coord + 0.5 + (random.nextDouble() - 0.5) * 0.6;
    }
}
