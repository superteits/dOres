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

package io.github.teitss.dores.utils;

import com.pixelmonmod.pixelmon.items.tools.GenericPickaxe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
     * Handles pickaxe custom harvest values.
     *
     * @param item - {@link ItemType} in {@link Player} hand.
     * @return int - Custom harvest value.
     */
    public static int getHarvestLevel(Item item) {
        if (item.equals(Items.WOODEN_PICKAXE))
            return 1;
        if (item.equals(Items.STONE_PICKAXE))
            return 2;
        if (item.equals(Items.IRON_PICKAXE))
            return 3;
        if (item.equals(Items.GOLDEN_PICKAXE))
            return 4;
        if (item.equals(Items.DIAMOND_PICKAXE))
            return 5;
        if (item instanceof GenericPickaxe)
            return 6;
        return 0;
    }

    /**
     * Simple handler to get the layer of the block.
     * @param blockPosY - Postion of the block in Y axis.
     * @return int - The layer.
     */
    public static int getLayer(int blockPosY) {
        if(blockPosY <= 12)
            return 1;
        else if(blockPosY <= 29)
            return 2;
        else if(blockPosY <= 63)
            return 3;
        return 0;
    }

}
