package io.github.tenhonomedegaz.underwaterplacement.menus;

import io.github.tenhonomedegaz.underwaterplacement.blocks.WaterloggedEnchantingTable;
import io.github.tenhonomedegaz.underwaterplacement.init.ItemInit;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.List;

import static io.github.tenhonomedegaz.underwaterplacement.init.BlockInit.MARINE_ENCHANTING_TABLE;

public class MarineEnchantmentMenu extends AbstractContainerMenu {
    private final Container enchantSlots = new SimpleContainer(2) {
        public void setChanged() {
            super.setChanged();
            MarineEnchantmentMenu.this.slotsChanged(this);
        }
    };

    private final ContainerLevelAccess access;
    private final RandomSource random = RandomSource.create();
    private final DataSlot enchantmentSeed = DataSlot.standalone();
    public final int[] costs = new int[3];
    public final int[] enchantClue = new int[]{-1, -1, -1};
    public final int[] levelClue = new int[]{-1, -1, -1};

    public MarineEnchantmentMenu(int i1, Inventory inventory, ContainerLevelAccess levelAccess) {
        super(MenuType.ENCHANTMENT, i1);
        this.access = levelAccess;
        this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) {
            public boolean mayPlace(ItemStack itemStack) {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) {
            public boolean mayPlace(ItemStack itemStack) {
                return (itemStack.is(net.minecraftforge.common.Tags.Items.ENCHANTING_FUELS) || itemStack.is(ItemInit.Tags.MARINE_ENCHANTING_FUEL));
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(DataSlot.shared(this.costs, 0));
        this.addDataSlot(DataSlot.shared(this.costs, 1));
        this.addDataSlot(DataSlot.shared(this.costs, 2));
        this.addDataSlot(this.enchantmentSeed).set(inventory.player.getEnchantmentSeed());
        this.addDataSlot(DataSlot.shared(this.enchantClue, 0));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 1));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 2));
        this.addDataSlot(DataSlot.shared(this.levelClue, 0));
        this.addDataSlot(DataSlot.shared(this.levelClue, 1));
        this.addDataSlot(DataSlot.shared(this.levelClue, 2));
    }

    private List<EnchantmentInstance> getEnchantmentList(ItemStack p_39472_, int p_39473_, int p_39474_) {
        this.random.setSeed((long)(this.enchantmentSeed.get() + p_39473_));
        List<EnchantmentInstance> list = EnchantmentHelper.selectEnchantment(this.random, p_39472_, p_39474_, false);
        if (p_39472_.is(Items.BOOK) && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }

        return list;
    }

    public int getGoldCount() {
        ItemStack itemstack = this.enchantSlots.getItem(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    public int getEnchantmentSeed() {
        return this.enchantmentSeed.get();
    }

    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (i == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (i == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.is(net.minecraftforge.common.Tags.Items.ENCHANTING_FUELS)  || itemstack1.is(ItemInit.Tags.MARINE_ENCHANTING_FUEL)) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1.copy();
                itemstack2.setCount(1);
                itemstack1.shrink(1);
                this.slots.get(0).set(itemstack2);
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }


    public void removed(Player p_39488_) {
        super.removed(p_39488_);
        this.access.execute((p_39469_, p_39470_) -> {
            this.clearContainer(p_39488_, this.enchantSlots);
        });
    }

    public void slotsChanged(Container container) {
        if (container == this.enchantSlots) {
            ItemStack itemstack = container.getItem(0);
            if (!itemstack.isEmpty() && itemstack.isEnchantable()) {
                this.access.execute((p_39485_, p_39486_) -> {
                    float j = 0;

                    for(BlockPos blockpos : WaterloggedEnchantingTable.BOOKSHELF_OFFSETS) {
                        if (WaterloggedEnchantingTable.isValidBookShelf(p_39485_, p_39486_, blockpos)) {
                            j += p_39485_.getBlockState(p_39486_.offset(blockpos)).getEnchantPowerBonus(p_39485_, p_39486_.offset(blockpos));
                        }
                    }

                    this.random.setSeed((long)this.enchantmentSeed.get());

                    for(int k = 0; k < 3; ++k) {
                        this.costs[k] = EnchantmentHelper.getEnchantmentCost(this.random, k, (int)j, itemstack);
                        this.enchantClue[k] = -1;
                        this.levelClue[k] = -1;
                        if (this.costs[k] < k + 1) {
                            this.costs[k] = 0;
                        }
                        this.costs[k] = net.minecraftforge.event.ForgeEventFactory.onEnchantmentLevelSet(p_39485_, p_39486_, k, (int)j, itemstack, costs[k]);
                    }

                    for(int l = 0; l < 3; ++l) {
                        if (this.costs[l] > 0) {
                            List<EnchantmentInstance> list = this.getEnchantmentList(itemstack, l, this.costs[l]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentInstance enchantmentinstance = list.get(this.random.nextInt(list.size()));
                                this.enchantClue[l] = Registry.ENCHANTMENT.getId(enchantmentinstance.enchantment);
                                this.levelClue[l] = enchantmentinstance.level;
                            }
                        }
                    }

                    this.broadcastChanges();
                });
            } else {
                for(int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }

    }

    public boolean clickMenuButton(Player player, int i1) {
        if (i1 >= 0 && i1 < this.costs.length) {
            ItemStack itemstack = this.enchantSlots.getItem(0);
            ItemStack itemstack1 = this.enchantSlots.getItem(1);
            int i = i1 + 1;
            if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !player.getAbilities().instabuild) {
                return false;
            } else if (this.costs[i1] <= 0 || itemstack.isEmpty() || (player.experienceLevel < i || player.experienceLevel < this.costs[i1]) && !player.getAbilities().instabuild) {
                return false;
            } else {
                this.access.execute((level, blockPos) -> {
                    ItemStack itemstack2 = itemstack;
                    List<EnchantmentInstance> list = this.getEnchantmentList(itemstack, i1, this.costs[i1]);
                    if (!list.isEmpty()) {
                        player.onEnchantmentPerformed(itemstack, i);
                        boolean flag = itemstack.is(Items.BOOK);
                        if (flag) {
                            itemstack2 = new ItemStack(Items.ENCHANTED_BOOK);
                            CompoundTag compoundtag = itemstack.getTag();
                            if (compoundtag != null) {
                                itemstack2.setTag(compoundtag.copy());
                            }

                            this.enchantSlots.setItem(0, itemstack2);
                        }

                        for(int j = 0; j < list.size(); ++j) {
                            EnchantmentInstance enchantmentinstance = list.get(j);
                            if (flag) {
                                EnchantedBookItem.addEnchantment(itemstack2, enchantmentinstance);
                            } else {
                                itemstack2.enchant(enchantmentinstance.enchantment, enchantmentinstance.level);
                            }
                        }

                        if (!player.getAbilities().instabuild) {
                            itemstack1.shrink(i);
                            if (itemstack1.isEmpty()) {
                                this.enchantSlots.setItem(1, ItemStack.EMPTY);
                            }
                        }

                        player.awardStat(Stats.ENCHANT_ITEM);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, itemstack2, i);
                        }

                        this.enchantSlots.setChanged();
                        this.enchantmentSeed.set(player.getEnchantmentSeed());
                        this.slotsChanged(this.enchantSlots);
                        level.playSound((Player)null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
                    }

                });
                return true;
            }
        } else {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + i1);
            return false;
        }
    }



    @Override
    public boolean stillValid(Player player){
        return stillValid(this.access, player, MARINE_ENCHANTING_TABLE.get());
    }
}


