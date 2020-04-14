/*
 * Copyright 2017,2018 Teits <https://github.com/superteits>
 *
 * This file is part of dOres.
 *
 * dOres is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
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

package io.github.teitss.dores;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.api.text.Text;

import java.util.Objects;

public class CustomOre {

    private int chance;
    private ItemStack itemStack;
    private ITextComponent text;
    private int harvestLevel;

    public CustomOre(int chance, int harvestLevel, ItemStack itemStack, ITextComponent text) {
        this.chance = chance;
        this.harvestLevel = harvestLevel;
        this.itemStack = itemStack;
        this.text = text;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public ItemStack getItemStack() {
        return itemStack.copy();
    }

    public ITextComponent getText() {
        return text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chance, itemStack);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CustomOre) {
            CustomOre other = (CustomOre) obj;
            return other.getItemStack().isItemEqual(this.getItemStack()) &&
                    other.getChance() == this.getChance();
        }
        return false;
    }
}
