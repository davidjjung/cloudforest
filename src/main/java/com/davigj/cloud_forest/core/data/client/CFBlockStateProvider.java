package com.davigj.cloud_forest.core.data.client;

import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.registry.CFBlocks;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CFBlockStateProvider extends BlueprintBlockStateProvider {
    public CFBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, CloudForest.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
//        this.block(CFBlocks.fog_moss_BLOCK);
    }
}
