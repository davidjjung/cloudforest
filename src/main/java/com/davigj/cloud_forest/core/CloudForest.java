package com.davigj.cloud_forest.core;

import com.davigj.cloud_forest.core.data.client.CFBlockStateProvider;
import com.davigj.cloud_forest.core.data.client.CFLangProvider;
import com.davigj.cloud_forest.core.data.server.CFBiomeTagsProvider;
import com.davigj.cloud_forest.core.data.server.CFDatapackBuiltinEntriesProvider;
import com.davigj.cloud_forest.core.data.server.tags.CFBlockTagsProvider;
import com.davigj.cloud_forest.core.data.server.tags.CFEntityTypeTagsProvider;
import com.davigj.cloud_forest.core.other.CFClientCompat;
import com.davigj.cloud_forest.core.registry.CFBlocks;
import com.davigj.cloud_forest.core.registry.CFFeatures;
import com.davigj.cloud_forest.core.registry.CFItems;
import com.davigj.cloud_forest.core.registry.CFParticleTypes;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(CloudForest.MOD_ID)
public class CloudForest {
    public static final String MOD_ID = "cloud_forest";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public CloudForest() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext context = ModLoadingContext.get();
        MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
        CFParticleTypes.PARTICLE_TYPES.register(bus);
        CFFeatures.FEATURES.register(bus);
        CFFeatures.TREE_DECORATOR_TYPES.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            CFItems.buildCreativeTabContents();
            CFBlocks.buildCreativeTabContents();
        });

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);
        context.registerConfig(ModConfig.Type.COMMON, CFConfig.COMMON_SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CFClientCompat.registerCompat();
        });
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        CFDatapackBuiltinEntriesProvider datapackEntries = new CFDatapackBuiltinEntriesProvider(output, provider);
        generator.addProvider(server, datapackEntries);

        CFBlockTagsProvider blockTags = new CFBlockTagsProvider(output, provider, helper);
        generator.addProvider(server, blockTags);

        provider = datapackEntries.getRegistryProvider();
        generator.addProvider(server, new CFEntityTypeTagsProvider(output, provider, helper));
        generator.addProvider(server, new CFBiomeTagsProvider(output, provider, helper));

        boolean client = event.includeClient();
        generator.addProvider(client, new CFBlockStateProvider(output, helper));


//        generator.addProvider(client, new CFLangProvider(generator));
    }
}