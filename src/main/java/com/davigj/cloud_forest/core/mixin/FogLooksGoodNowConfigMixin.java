package com.davigj.cloud_forest.core.mixin;

import net.minecraftforge.fml.common.Mod;
import net.shizotoaster.foglooksmodernnow.client.FogManager;
import net.shizotoaster.foglooksmodernnow.config.FogLooksGoodNowConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(FogLooksGoodNowConfig.class)
public class FogLooksGoodNowConfigMixin {
    @Inject(method = "getDensityConfigs", at = @At("HEAD"), remap = false, cancellable = true)
    private static void cooking(CallbackInfoReturnable<List<Pair<String, FogManager.BiomeFogDensity>>> cir) {
        List<Pair<String, FogManager.BiomeFogDensity>> list = new ArrayList<>();
        List<? extends String> densityConfigs = (List) FogLooksGoodNowConfig.CLIENT_CONFIG.biomeFogs.get();
        for (String densityConfig : densityConfigs) {
            String[] options = densityConfig.split(",");

            try {
                list.add(Pair.of(options[0], new FogManager.BiomeFogDensity(Float.parseFloat(options[1]), Float.parseFloat(options[2]), Integer.parseInt(options[3]))));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }

        cir.setReturnValue(list);
    }
}
