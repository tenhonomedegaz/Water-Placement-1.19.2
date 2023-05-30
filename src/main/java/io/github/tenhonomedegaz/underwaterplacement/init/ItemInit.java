package io.github.tenhonomedegaz.underwaterplacement.init;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.base.ModArmorMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    private static final Item.Properties props(){
        return new Item.Properties().tab(UnderwaterPlacement.TAB);
    }
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnderwaterPlacement.MODID);

    public static final RegistryObject<Item> AQUAMARINE = ITEMS.register("aquamarine",
            () -> new Item(props()));
    public static final RegistryObject<Item> AQUAMARINE_DROP = ITEMS.register("aquamarine_drop",
            () -> new Item(props()));
    public static final RegistryObject<Item> PRISMARINE_DUST = ITEMS.register("prismarine_dust",
            () -> new Item(props()));
    public static final RegistryObject<Item> HYDRANGEA_PETAL = ITEMS.register("hydrangea_petal",
            ()-> new Item(props()));
    public static final RegistryObject<Item> MARINE_INFUSED_IRON_INGOT = ITEMS.register("marine_infused_iron_ingot",
            () -> new Item(props()));
    public static final RegistryObject<Item> PRISMARSTEEL_INGOT = ITEMS.register("prismarsteel_ingot",
            () -> new Item(props()));
    public static  final  RegistryObject<Item> WATER_MELON = ITEMS.register("water_melon",
            () -> new Item(props().food(Foods.WATER_MELON)));
    public static  final  RegistryObject<Item> PRISMATIC_WATER_MELON = ITEMS.register("prismatic_water_melon",
            () -> new EnchantedGoldenAppleItem(props().food(Foods.PRISMATIC_WATER_MELON)));
    public static final RegistryObject<SwordItem> EXAMPLE_SWORD = ITEMS.register("example_sword",
            () -> new SwordItem(ToolTiers.PRISMARSTEEL, 6,-2.4f, props()));
    public static final RegistryObject<PickaxeItem> EXAMPLE_PICKAXE = ITEMS.register("example_pickaxe",
            () -> new PickaxeItem(ToolTiers.PRISMARSTEEL, 4,-2.8f, props()));
    public static final RegistryObject<ShovelItem> EXAMPLE_SHOVEL = ITEMS.register("example_shovel",
            () -> new ShovelItem(ToolTiers.PRISMARSTEEL, 3, -3.0f, props()));
    public static final RegistryObject<AxeItem> EXAMPLE_AXE = ITEMS.register("example_axe",
            () -> new AxeItem(ToolTiers.PRISMARSTEEL, 8,-3.0f, props()));
    public static final RegistryObject<HoeItem> EXAMPLE_HOE = ITEMS.register("example_hoe",
            () -> new HoeItem(ToolTiers.PRISMARSTEEL,1,0.0f, props()));

    public static final RegistryObject<ArmorItem> EXAMPLE_HELMET = ITEMS.register("example_helmet",
            () -> new ArmorItem(ArmorTiers.PRISMARSTEEL, EquipmentSlot.HEAD, props()));
    public static final RegistryObject<ArmorItem> EXAMPLE_CHESTPLATE = ITEMS.register("example_chestplate",
            () -> new ArmorItem(ArmorTiers.PRISMARSTEEL, EquipmentSlot.CHEST, props()));
    public static final RegistryObject<ArmorItem> EXAMPLE_LEGGINGS = ITEMS.register("example_leggings",
            () -> new ArmorItem(ArmorTiers.PRISMARSTEEL, EquipmentSlot.LEGS, props()));
    public static final RegistryObject<ArmorItem> EXAMPLE_BOOTS = ITEMS.register("example_boots",
            () -> new ArmorItem(ArmorTiers.PRISMARSTEEL, EquipmentSlot.FEET, props()));



    public static class Foods {
        public static final FoodProperties WATER_MELON = new FoodProperties.Builder()
                .nutrition(6)
                .saturationMod(0.6f)
                .alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 2400,1), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100,2), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400,1), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 2400,2), 1.0f)
                .build();

        public static final FoodProperties PRISMATIC_WATER_MELON = new FoodProperties.Builder()
                .nutrition(6)
                .saturationMod(0.6f)
                .alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.CONDUIT_POWER, 600,2), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 6000,4), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600,2), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400,4), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 2400,2), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 2400,2), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2400,2), 1.0f)
                .build();
    }

    public static class ToolTiers{
        public static final Tier PRISMARSTEEL = new ForgeTier(
                3,
                1236,
                7,
                0,
                30,
                BlockInit.Tags.NEEDS_PRISMARSTEEL_TOOL,
                () -> Ingredient.of(ItemInit.PRISMARSTEEL_INGOT.get()));
    }

    public static class ArmorTiers{
    public static final ArmorMaterial PRISMARSTEEL = new ModArmorMaterial(
            "prismarsteel",
            24,
            new int[] { 3, 6, 8, 3},
            30,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            1.0f,
            0.0f,
            () -> Ingredient.of(ItemInit.PRISMARSTEEL_INGOT.get()));
    }

    public static class Tags{
        public static final TagKey<Item> MARINE_ENCHANTING_FUEL = create("marine_enchanting_fuel");

        private static TagKey<Item> create(String location){
            return ItemTags.create(new ResourceLocation(UnderwaterPlacement.MODID, location));
        }
    }
}
