package com.davigj.cloud_forest.core.other;

import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.registry.CFBiomes;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID)
public class CFEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderFog(RenderFog event) {
//        if (event.getCamera().getEntity() instanceof LocalPlayer player) {
//            Holder<Biome> holder = player.level().getBiome(player.blockPosition());
//            if (holder.is(CFBiomes.CLOUD_FOREST)) {
//                System.out.println("Far plane distance " + RenderSystem.getShaderFogEnd());
//                RenderSystem.setShaderFogEnd(interpolate(RenderSystem.getShaderFogEnd(), 48.0F));
//                event.setCanceled(true);
//            }
//        }
    }

    private static float interpolate(float start, float end) {
        if (start > end) {
            start -= 0.01F;
        } else if (start < end) {
            start += 0.01F;
        }
        System.out.println("Let's kick you down to size: " + start);
        return start;
    }
}