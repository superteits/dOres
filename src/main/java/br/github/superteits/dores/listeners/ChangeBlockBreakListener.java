/*
 * Copyright 2017,2018 Teits <https://github.com/superteits>
 *
 * This file is part of dOres.
 *
 * dOres is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * dOres is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with dOres  If not, see <http://www.gnu.org/licenses/>.
 */

package br.github.superteits.dores.listeners;

import br.github.superteits.dores.DOres;
import br.github.superteits.dores.utils.HarvestUtil;
import com.pixelmonmod.pixelmon.config.PixelmonBlocks;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.items.PixelmonItem;
import net.minecraft.init.Biomes;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class ChangeBlockBreakListener {

    HarvestUtil harvestUtil = new HarvestUtil();

    @Listener
    public void onBreak(ChangeBlockEvent.Break event, @First Player p) {
        if (event.getTransactions().get(0).getOriginal().getState().getType().equals(BlockTypes.STONE)) {
            if (p.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
                ItemStack itemStack = p.getItemInHand(HandTypes.MAIN_HAND).get();
                if (harvestUtil.getHarvestLevel(itemStack.getType()) != 0) {
                    BlockSnapshot blockSnapshot = event.getTransactions().get(0).getOriginal();
                    float chance = DOres.getInstance().getRNG().nextInt(10001);
                    Optional<ItemStack> itemStackToDrop = null;
                    if (blockSnapshot.getLocation().get().getBlockY() <= 12) {
                        if (chance <= 110 * DOres.getInstance().getDropRate()) { //Redstone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 3) {
                                itemStackToDrop = Optional.of(ItemStack.of(ItemTypes.REDSTONE, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.RED, "Você achou Redstone!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 140 * DOres.getInstance().getDropRate()) { //Diamond
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 3) {
                                itemStackToDrop = Optional.of(ItemStack.of(ItemTypes.DIAMOND, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.AQUA, "Você achou Diamante!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 170 * DOres.getInstance().getDropRate()) { //Silicon
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.siliconItem, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.WHITE, "Você achou Silício!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 200 * DOres.getInstance().getDropRate()) { //Water Stone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.waterStoneShard, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.BLUE, "Você achou um Pedaço de Pedra da Água!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 230 * DOres.getInstance().getDropRate()) { //Ruby
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.ruby, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.RED, "Você achou Rubi!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 260 * DOres.getInstance().getDropRate()) { //Fire Stone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.fireStoneShard, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.RED, "Você achou um Pedaço de Pedra do Fogo!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else {
                            itemStackToDrop = Optional.of(ItemStack.empty());
                        }
                    } else if (p.getLocation().getY() <= 29) {
                        if (chance <= 50 * DOres.getInstance().getDropRate()) { //Gold
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 3) {
                                itemStackToDrop = Optional.of(ItemStack.of(ItemTypes.GOLD_ORE, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.GOLD, "Você achou Ouro!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 100 * DOres.getInstance().getDropRate()) { //Lapis Lazuli
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 3) {
                                itemStackToDrop = Optional.of(ItemStack.builder().itemType(ItemTypes.DYE).add(Keys.DYE_COLOR, DyeColors.BLUE).quantity(DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)).build());//(ItemTypes.DYE, 2 * checks.toolCheckIron(p));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.BLUE, "Você achou Lapis Lazuli!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 130 * DOres.getInstance().getDropRate()) { //Emerald
                            if (p.getLocation().getBiome().equals(Biomes.EXTREME_HILLS)) {
                                if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 3) {
                                    itemStackToDrop = Optional.of(ItemStack.of(ItemTypes.EMERALD, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                    p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.GREEN, "Você achou Esmeralda!"));
                                } else {
                                    itemStackToDrop = Optional.empty();
                                }
                            } else {
                                itemStackToDrop = Optional.of(ItemStack.empty());
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.GREEN, "Você quase achou Esmeralda!"));
                            }
                        } else if (chance <= 160 * DOres.getInstance().getDropRate()) { //Sapphire
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.sapphire, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_BLUE, "Você achou Safira!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 190 * DOres.getInstance().getDropRate()) { //Amthyst
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.amethyst, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_PURPLE, "Você achou Ametista!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 220 * DOres.getInstance().getDropRate()) { //Dawn Stone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.dawnStoneShard, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.AQUA, "Você achou um Pedaço de Pedra do Amanhecer!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 250 * DOres.getInstance().getDropRate()) { //Dusk Stone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.duskStoneShard, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.BLUE, "Você achou um Pedaço de Pedra do Anoitecer!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else {
                            itemStackToDrop = Optional.of(ItemStack.empty());
                        }
                    } else if (blockSnapshot.getLocation().get().getBlockY() <= 63) {
                        if (chance <= 300 * DOres.getInstance().getDropRate()) { //Coal
                            itemStackToDrop = Optional.of(ItemStack.of(ItemTypes.COAL, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                            p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.BLACK, "Você achou Carvão!"));
                        } else if (chance <= 500 * DOres.getInstance().getDropRate()) { //Iron
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 2) {
                                itemStackToDrop = Optional.of(ItemStack.of(ItemTypes.IRON_ORE, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.GRAY, "Você achou Minério de Ferro!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 530 * DOres.getInstance().getDropRate()) { //Crystal
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.crystal, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.AQUA, "Você achou Cristal!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 560 * DOres.getInstance().getDropRate()) { //Bauxite
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItem.getItemFromBlock(PixelmonBlocks.bauxite), DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.GRAY, "Você achou Bauxita!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 590 * DOres.getInstance().getDropRate()) { //Thunder Stone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.thunderStoneShard, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.GREEN, "Você achou um Pedaço de Pedra do Trovão!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 620 * DOres.getInstance().getDropRate()) { //Leaf Stone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.leafStoneShard, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_GREEN, "Você achou um Pedaço de Pedra de Folha!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else if (chance <= 650 * DOres.getInstance().getDropRate()) { //Sun Stone
                            if (harvestUtil.getHarvestLevel(itemStack.getType()) >= 5) {
                                itemStackToDrop = Optional.of(ItemStack.of((ItemType) PixelmonItems.sunStoneShard, DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack)));
                                p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.GOLD, "Você achou um Pedaço de Pedra do Sol!"));
                            } else {
                                itemStackToDrop = Optional.empty();
                            }
                        } else {
                            itemStackToDrop = Optional.of(ItemStack.empty());
                        }
                    } else {
                        itemStackToDrop = Optional.of(ItemStack.empty());
                    }
                    if (itemStackToDrop == null) {
                        p.sendMessage(Text.of(TextColors.RED, "Ocorreu um erro, por favor contate um administrador imediatamente."));
                        return;
                    }
                    if (!itemStackToDrop.isPresent()) {
                        p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_RED, "Você achou um minério mas não conseguiu quebrá-lo"));
                        return;
                    }
                    if (itemStackToDrop.get().isEmpty())
                        return;
                    Item itemEntity = (Item) p.getWorld().createEntity(EntityTypes.ITEM, blockSnapshot.getPosition());
                    itemEntity.offer(Keys.REPRESENTED_ITEM, itemStackToDrop.get().createSnapshot());
                    p.getWorld().spawnEntity(itemEntity);
                }
            }
        }
    }
}
