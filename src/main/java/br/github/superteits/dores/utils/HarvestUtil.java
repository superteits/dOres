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

package br.github.superteits.dores.utils;

import com.pixelmonmod.pixelmon.items.tools.GenericPickaxe;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.HashSet;

public class HarvestUtil {

    /**
     * Handles pickaxe enchantments, needs {@link #getHarvestLevel(ItemType)} safety check.
     *
     * @param itemStack - {@link ItemStack} in {@link Player} hand.
     * @return int - Value to be used as multiplier in {@link ItemStack} creation.
     */
    public int handlePickaxeEnchantments(ItemStack itemStack) {
        if (itemStack.get(Keys.ITEM_ENCHANTMENTS).isPresent()) {
            HashSet<Enchantment> enchantmentSet = new HashSet<Enchantment>(itemStack.get(Keys.ITEM_ENCHANTMENTS).get());
            for (Enchantment enchantment : enchantmentSet) {
                if (enchantment.getType().equals(EnchantmentTypes.FORTUNE))
                    return enchantment.getLevel();
            }
        }
        return 1;
    }

    /**
     * Handles pickaxe custom harvest values.
     *
     * @param itemType - {@link ItemType} in {@link Player} hand.
     * @return int - Custom harvest value.
     */
    public int getHarvestLevel(ItemType itemType) {
        if (itemType.equals(ItemTypes.WOODEN_PICKAXE))
            return 1;
        if (itemType.equals(ItemTypes.STONE_PICKAXE))
            return 2;
        if (itemType.equals(ItemTypes.IRON_PICKAXE))
            return 3;
        if (itemType.equals(ItemTypes.GOLDEN_PICKAXE))
            return 4;
        if (itemType.equals(ItemTypes.DIAMOND_PICKAXE))
            return 5;
        if (itemType instanceof GenericPickaxe)
            return 6;
        return 0;
    }

    /**
     * Simple handler to get the layer of the block.
     * @param blockPosY - Postion of the block in Y axis.
     * @return int - The layer.
     */
    public int getLayer(int blockPosY) {
        if(blockPosY <= 12)
            return 1;
        else if(blockPosY <= 29)
            return 2;
        else if(blockPosY <= 63)
            return 3;
        return 0;
    }

}
