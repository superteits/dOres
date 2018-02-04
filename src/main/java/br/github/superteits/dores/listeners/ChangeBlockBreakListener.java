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
import br.github.superteits.dores.utils.CustomOre;
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
import org.spongepowered.api.world.biome.BiomeTypes;

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
                    int chance = DOres.getInstance().getRNG().nextInt(10000);
                    for(CustomOre customOre : DOres.getInstance().getLayers().get(harvestUtil.getLayer(blockSnapshot.getPosition().getY()))) {
                        if(chance < customOre.getChance() * DOres.getInstance().getDropRate()) {
                            if(harvestUtil.getHarvestLevel(itemStack.getType()) >= customOre.getHarvestLevel()) {
                                if(customOre.getItemStack().getType().equals(ItemTypes.EMERALD) &&
                                        !blockSnapshot.getLocation().get().getBiome().equals(BiomeTypes.EXTREME_HILLS))
                                    return;
                                ItemStack itemStackToDrop = customOre.getItemStack();
                                itemStackToDrop.setQuantity(DOres.getInstance().getDropQuantity() * harvestUtil.handlePickaxeEnchantments(itemStack));
                                Item itemEntity = (Item) p.getWorld().createEntity(EntityTypes.ITEM, blockSnapshot.getPosition());
                                itemEntity.offer(Keys.REPRESENTED_ITEM, itemStackToDrop.createSnapshot());
                                p.getWorld().spawnEntity(itemEntity);
                                p.sendMessage(ChatTypes.ACTION_BAR, customOre.getText());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
