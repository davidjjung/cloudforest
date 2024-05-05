package com.davigj.cloud_forest.core.other;


import com.davigj.cloud_forest.core.CloudForest;
import com.davigj.cloud_forest.core.registry.CFBlocks;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID, value = Dist.CLIENT)
public class CFClientCompat {

    public static void registerCompat() {
        registerRenderLayers();
        registerBlockColors();
    }

    private static void registerRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(CFBlocks.GLISTEN_LICHEN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CFBlocks.HANGING_GLISTEN_LICHEN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CFBlocks.NIMBUS_ORCHID.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CFBlocks.POTTED_NIMBUS_ORCHID.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CFBlocks.RUBY_BROMELIAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CFBlocks.POTTED_RUBY_BROMELIAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CFBlocks.HAZEBLOOM.get(), RenderType.cutout());
    }

    private static void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();

        List<RegistryObject<Block>> foliageColors = Arrays.asList(
                CFBlocks.DEWEY_SPRUCE_LEAVES
        );

        DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.get(0.5D, 1.0D), foliageColors);
        DataUtil.registerBlockItemColor(itemColors, (color, items) -> FoliageColor.get(0.5D, 1.0D), foliageColors);
    }
}
