package com.davigj.cloud_forest.core.data.server.tags;

import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.other.CFEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class CFEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public CFEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, CloudForest.MOD_ID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(CFEntityTypeTags.FOG_SLAB_ADAPTED).add(EntityType.BEE, EntityType.FROG);
    }
}