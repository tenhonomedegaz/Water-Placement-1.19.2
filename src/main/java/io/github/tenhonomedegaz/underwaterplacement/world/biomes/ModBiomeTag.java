package io.github.tenhonomedegaz.underwaterplacement.world.biomes;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBiomeTag extends TagsProvider<Biome> {

    protected ModBiomeTag(DataGenerator generator, Registry<Biome> biomes, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, biomes, modId, existingFileHelper);
    }

    private static TagKey<Biome> create(String s) {
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(UnderwaterPlacement.MODID, s));
    }
    public static final TagKey<Biome> HAS_SHELL = create("has_shell");
    protected void addTags(){
        this.tag(ModBiomeTag.HAS_SHELL).addTag(BiomeTags.IS_OCEAN).addTag(BiomeTags.IS_RIVER).addTag(BiomeTags.IS_BEACH);
    }
}
