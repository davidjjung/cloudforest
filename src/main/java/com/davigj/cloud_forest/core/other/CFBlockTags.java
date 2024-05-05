package com.davigj.cloud_forest.core.other;

import com.davigj.cloud_forest.core.CloudForest;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CFBlockTags {
    public static final TagKey<Block> EPIPHYTE_PLACEABLE = blockTag("epiphyte_placeable");

    private static TagKey<Block> blockTag(String name) {
        return TagUtil.blockTag(CloudForest.MOD_ID, name);
    }
}
