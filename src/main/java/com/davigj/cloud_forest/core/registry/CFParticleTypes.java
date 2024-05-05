package com.davigj.cloud_forest.core.registry;

import com.davigj.cloud_forest.client.particle.FogMossPuffParticle;
import com.davigj.cloud_forest.core.CloudForest;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CFParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CloudForest.MOD_ID);

    public static final RegistryObject<SimpleParticleType> FOG_MOSS_PUFF = PARTICLE_TYPES.register("fog_moss_puff", () -> new SimpleParticleType(true));

    // for when you need to look at more particles: https://github.com/davidjjung/just_dandy/blob/1.20.1/src/main/java/com/davigj/just_dandy/common/block/FluffyDandelionBlock.java


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FOG_MOSS_PUFF.get(), FogMossPuffParticle.Factory::new);
    }
}
