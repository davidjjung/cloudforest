package com.davigj.cloud_forest.core.registry;

import com.davigj.cloud_forest.common.block.*;
import com.davigj.cloud_forest.core.CloudForest;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.crafting.Ingredient.of;

import static net.minecraft.world.item.CreativeModeTabs.NATURAL_BLOCKS;

@Mod.EventBusSubscriber(modid = CloudForest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CFBlocks {
    public static final BlockSubRegistryHelper HELPER = CloudForest.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> FOG_MOSS_BLOCK = HELPER.createBlock("fog_moss_block", () -> new FogMossBlock(Block.Properties.copy(Blocks.MOSS_BLOCK)));
    public static final RegistryObject<Block> FOG_MOSS_CARPET = HELPER.createBlock("fog_moss_carpet", () -> new FogMossCarpetBlock(Block.Properties.copy(Blocks.MOSS_CARPET)));
    public static final RegistryObject<Block> GLISTEN_LICHEN = HELPER.createBlock("glisten_lichen", () -> new GlistenLichenBlock(Block.Properties.copy(Blocks.GLOW_LICHEN)));
    public static final RegistryObject<Block> HANGING_GLISTEN_LICHEN = HELPER.createBlock("hanging_glisten_lichen", () -> new HangingGlistenLichenBlock(Block.Properties.copy(Blocks.HANGING_ROOTS)));

    public static final RegistryObject<Block> DEWEY_SPRUCE_LEAVES = HELPER.createBlock("dewey_spruce_leaves", () -> new DeweySpruceLeavesBlock(Block.Properties.copy(Blocks.SPRUCE_LEAVES)));

    public static final RegistryObject<Block> NIMBUS_ORCHID = HELPER.createBlock("nimbus_orchid", () -> new NimbusOrchidBlock(() -> MobEffects.WEAKNESS, 12, CFBlockProperties.FLOWER));
    public static final RegistryObject<Block> POTTED_NIMBUS_ORCHID = HELPER.createBlockNoItem("potted_nimbus_orchid", () -> flowerPot(NIMBUS_ORCHID.get()));
    public static final RegistryObject<Block> RUBY_BROMELIAD = HELPER.createBlock("ruby_bromeliad", () -> new BromeliadBlock(() -> MobEffects.DAMAGE_RESISTANCE, 12, CFBlockProperties.FLOWER));
    public static final RegistryObject<Block> POTTED_RUBY_BROMELIAD = HELPER.createBlockNoItem("potted_ruby_bromeliad", () -> flowerPot(RUBY_BROMELIAD.get()));
    public static final RegistryObject<Block> HAZEBLOOM = HELPER.createBlock("hazebloom", () -> new HazebloomBlock(() -> MobEffects.LEVITATION, 8, Block.Properties.copy(Blocks.OXEYE_DAISY)));

    public static void buildCreativeTabContents() {
        CreativeModeTabContentsPopulator.mod(CloudForest.MOD_ID)
                .tab(NATURAL_BLOCKS).addItemsAfter(of(Blocks.MOSS_CARPET), FOG_MOSS_BLOCK, FOG_MOSS_CARPET)
                .tab(NATURAL_BLOCKS).addItemsAfter(of(Blocks.LILY_OF_THE_VALLEY), NIMBUS_ORCHID, RUBY_BROMELIAD, HAZEBLOOM)
                .tab(NATURAL_BLOCKS).addItemsAfter(of(Blocks.GLOW_LICHEN), GLISTEN_LICHEN, HANGING_GLISTEN_LICHEN)
        ;
    }

    public static class CFBlockProperties {
        public static final BlockBehaviour.Properties FLOWER = Block.Properties.of().mapColor(MapColor.PLANT).noOcclusion().noCollission()
                .instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    }

    private static FlowerPotBlock flowerPot(Block block, FeatureFlag... flags) {
        BlockBehaviour.Properties blockbehaviour$properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (flags.length > 0) {
            blockbehaviour$properties = blockbehaviour$properties.requiredFeatures(flags);
        }
        return new FlowerPotBlock(block, blockbehaviour$properties);
    }
}