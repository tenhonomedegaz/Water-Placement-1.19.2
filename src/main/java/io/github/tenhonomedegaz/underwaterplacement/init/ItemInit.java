package io.github.tenhonomedegaz.underwaterplacement.init;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.base.ModArmorMaterial;
import io.github.tenhonomedegaz.underwaterplacement.items.AdvancedItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnderwaterPlacement.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new Item(props()));
    public static final RegistryObject<Item> EXAMPLE_ITEM2 = ITEMS.register("example_item2",
            () -> new Item(props()));
    public static  final  RegistryObject<Item> EXAMPLE_FOOD = ITEMS.register("example_food",
            () -> new Item(props().food(Foods.EXAMPLE_FOOD)));

    public static final RegistryObject<SwordItem> EXAMPLE_SWORD = ITEMS.register("example_sword",
            () -> new SwordItem(ToolTiers.EXAMPLE, 6,-2.4f, props()));
    public static final RegistryObject<PickaxeItem> EXAMPLE_PICKAXE = ITEMS.register("example_pickaxe",
            () -> new PickaxeItem(ToolTiers.EXAMPLE, 4,-2.8f, props()));
    public static final RegistryObject<ShovelItem> EXAMPLE_SHOVEL = ITEMS.register("example_shovel",
            () -> new ShovelItem(ToolTiers.EXAMPLE, 3, -3.0f, props()));
    public static final RegistryObject<AxeItem> EXAMPLE_AXE = ITEMS.register("example_axe",
            () -> new AxeItem(ToolTiers.EXAMPLE, 8,-3.0f, props()));
    public static final RegistryObject<HoeItem> EXAMPLE_HOE = ITEMS.register("example_hoe",
            () -> new HoeItem(ToolTiers.EXAMPLE,1,0.0f, props()));

    public static final RegistryObject<ArmorItem> EXAMPLE_HELMET = ITEMS.register("example_helmet",
            () -> new ArmorItem(ArmorTiers.EXAMPLE, EquipmentSlot.HEAD, props()));
    public static final RegistryObject<ArmorItem> EXAMPLE_CHESTPLATE = ITEMS.register("example_chestplate",
            () -> new ArmorItem(ArmorTiers.EXAMPLE, EquipmentSlot.CHEST, props()));
    public static final RegistryObject<ArmorItem> EXAMPLE_LEGGINGS = ITEMS.register("example_leggings",
            () -> new ArmorItem(ArmorTiers.EXAMPLE, EquipmentSlot.LEGS, props()));
    public static final RegistryObject<ArmorItem> EXAMPLE_BOOTS = ITEMS.register("example_boots",
            () -> new ArmorItem(ArmorTiers.EXAMPLE, EquipmentSlot.FEET, props()));

    private static final Item.Properties props(){
        return new Item.Properties().tab(UnderwaterPlacement.TAB);
    }

    public static class Foods {
        public static final FoodProperties EXAMPLE_FOOD = new FoodProperties.Builder()
                .nutrition(6)
                .saturationMod(0.6f)
                .alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 600,3), 1.0f)
                .build();
    }

    public static class ToolTiers{
        public static final Tier EXAMPLE = new ForgeTier(
                3,
                1236,
                7,
                0,
                30,
                BlockInit.Tags.NEEDS_EXAMPLE_TOOL,
                () -> Ingredient.of(ItemInit.EXAMPLE_ITEM.get()));
    }

    public static class ArmorTiers{
    public static final ArmorMaterial EXAMPLE = new ModArmorMaterial(
            "example",
            24,
            new int[] { 3, 6, 8, 3},
            30,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            1.0f,
            0.0f,
            () -> Ingredient.of(ItemInit.EXAMPLE_ITEM.get()));
    }
}
