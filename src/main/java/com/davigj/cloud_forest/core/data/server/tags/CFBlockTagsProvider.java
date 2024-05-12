package com.davigj.cloud_forest.core.data.server.tags;

import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.other.CFBlockTags;
import com.davigj.cloud_forest.core.registry.CFBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.davigj.cloud_forest.core.registry.CFBlocks.*;
import static net.minecraft.tags.BlockTags.*;

public class CFBlockTagsProvider extends BlockTagsProvider {

    public CFBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, CloudForest.MOD_ID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                DEWEY_SPRUCE_LEAVES.get(), HAZEBLOOM.get(), NIMBUS_ORCHID.get(), RUBY_BROMELIAD.get(),
                FOG_MOSS_BLOCK.get()
        );

        this.tag(FLOWER_POTS).add(POTTED_NIMBUS_ORCHID.get(), POTTED_RUBY_BROMELIAD.get());
        this.tag(LEAVES).add(DEWEY_SPRUCE_LEAVES.get());
        this.tag(SMALL_FLOWERS).add(NIMBUS_ORCHID.get(),HAZEBLOOM.get(), RUBY_BROMELIAD.get());

        this.tag(CFBlockTags.EPIPHYTE_PLACEABLE).addTag(BlockTags.LEAVES).addTag(BlockTags.LOGS).addTag(BlockTags.DIRT);

        this.tag(MOSS_REPLACEABLE).add(FOG_MOSS_BLOCK.get());
        this.tag(SNIFFER_EGG_HATCH_BOOST).add(FOG_MOSS_BLOCK.get());
        this.tag(SCULK_REPLACEABLE).add(FOG_MOSS_BLOCK.get());
        this.tag(ENDERMAN_HOLDABLE).add(FOG_MOSS_BLOCK.get());
        this.tag(SMALL_DRIPLEAF_PLACEABLE).add(FOG_MOSS_BLOCK.get());
        this.tag(AZALEA_GROWS_ON).add(FOG_MOSS_BLOCK.get());
        this.tag(BAMBOO_PLANTABLE_ON).add(FOG_MOSS_BLOCK.get());
        this.tag(DIRT).add(FOG_MOSS_BLOCK.get());
        this.tag(SNIFFER_DIGGABLE_BLOCK).add(FOG_MOSS_BLOCK.get());
        this.tag(NETHER_CARVER_REPLACEABLES).add(FOG_MOSS_BLOCK.get());
        this.tag(DEAD_BUSH_MAY_PLACE_ON).add(FOG_MOSS_BLOCK.get());
        this.tag(BIG_DRIPLEAF_PLACEABLE).add(FOG_MOSS_BLOCK.get());
    }
}
