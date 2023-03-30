package io.github.tenhonomedegaz.underwaterplacement.blocks.entity;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnderwaterPlacement.MODID);

    public static final RegistryObject<BlockEntityType<WaterloggedEnchantingTableBlockEntity>> MARINE_ENCHANTING_TABLE =
            BLOCK_ENTITIES.register("marine_enchanting_table", ()->
                    BlockEntityType.Builder.of(WaterloggedEnchantingTableBlockEntity::new,
                            BlockInit.MARINE_ENCHANTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<WaterloggedBrewingStandBlockEntity>> MARINE_BREWING_STAND =
            BLOCK_ENTITIES.register("marine_brewing_stand", ()->
                    BlockEntityType.Builder.of(WaterloggedBrewingStandBlockEntity::new,
                            BlockInit.MARINE_BREWING_STAND.get()).build(null));


    public static void register(IEventBus bus){
        BLOCK_ENTITIES.register(bus);
    }
}
