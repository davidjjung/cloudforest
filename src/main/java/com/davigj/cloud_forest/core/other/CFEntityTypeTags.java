package com.davigj.cloud_forest.core.other;

import com.davigj.cloud_forest.core.CloudForest;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class CFEntityTypeTags {
    public static final TagKey<EntityType<?>> FOG_SLAB_ADAPTED = entityTypeTag("fog_slab_adapted");

    private static TagKey<EntityType<?>> entityTypeTag(String name) {
        return TagUtil.entityTypeTag(CloudForest.MOD_ID, name);
    }
}
