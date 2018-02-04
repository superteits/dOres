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

package br.github.superteits.dores;

import br.github.superteits.dores.commands.BaseCommand;
import br.github.superteits.dores.listeners.ChangeBlockBreakListener;
import br.github.superteits.dores.listeners.DropItemDestructListener;
import br.github.superteits.dores.listeners.SpawnEntityEventListener;
import br.github.superteits.dores.utils.CustomOre;
import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.config.PixelmonBlocks;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.items.PixelmonItem;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;


@Plugin (id = DOres.ID,
		name = DOres.NAME,
		version = DOres.VERSION,
		authors = DOres.AUTHOR,
		description = "Simple solution for X-Rays.",
		dependencies = {@Dependency(id="pixelmon")})
public class DOres {

	public static final String ID = "dores";
	public static final String NAME = "dOres";
	public static final String VERSION = "1.0.0";
	public static final String AUTHOR = "Teits";
	@Inject
	Logger logger;

	private HashSet<Long> test = new HashSet<>();
	private HashSet<BlockType> blockedTypes = new HashSet<BlockType>();
	private HashMap<Integer, LinkedHashSet<CustomOre>> layers = new HashMap<>();
	private	static DOres instance;
	private float dropRate = 1F;
	private int dropQuantity = 2;
	private SecureRandom random = new SecureRandom();
	
	@Listener
	public void onGameInit(GameInitializationEvent e) {
		instance = this;
		logger.info("Loading dOres...");
		logger.info("Registering event listeners...");
		Sponge.getEventManager().registerListeners(this, new ChangeBlockBreakListener());
		Sponge.getEventManager().registerListeners(this, new DropItemDestructListener());
		Sponge.getEventManager().registerListeners(this, new SpawnEntityEventListener());
	}

	@Listener
	public void onServerStarting(GameStartingServerEvent e) {
		logger.info("Registering commands...");
		Sponge.getCommandManager().register(this, new BaseCommand().getCommandSpec(), "dores", "do", "damno");
		registerBlockedTypes();
		registerCustomOres();
	}

	public SecureRandom getRNG() {
		return random;
	}
	public static DOres getInstance() {
		return instance;
	}

	public float getDropRate() {
		return dropRate;
	}

	public int getDropQuantity() {
		return dropQuantity;
	}

	public void setDropRate(float dropRate) {
		this.dropRate = dropRate;
	}

	public void setDropQuantity(int dropQuantity) {
		this.dropQuantity = dropQuantity;
	}

	public HashSet<BlockType> getBlockedTypes() {
		return blockedTypes;
	}

	public HashMap<Integer, LinkedHashSet<CustomOre>> getLayers() {
		return layers;
	}

	public HashSet<Long> getTest() {
		return test;
	}

	private void registerBlockedTypes() {
		logger.info("Registering blocked BlockTypes...");
		blockedTypes.add(BlockTypes.COAL_ORE);
		blockedTypes.add(BlockTypes.IRON_ORE);
		blockedTypes.add(BlockTypes.GOLD_ORE);
		blockedTypes.add(BlockTypes.DIAMOND_ORE);
		blockedTypes.add(BlockTypes.EMERALD_ORE);
		blockedTypes.add(BlockTypes.REDSTONE_ORE);
		blockedTypes.add(BlockTypes.LAPIS_ORE);
		blockedTypes.add((BlockType) PixelmonBlocks.amethystOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.crystalOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.fireStoneOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.dawnduskStoneOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.leafStoneOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.rubyOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.sapphireOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.siliconOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.sunStoneOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.thunderStoneOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.waterStoneOre.getDefaultState().getBlock());
		blockedTypes.add((BlockType) PixelmonBlocks.bauxite.getDefaultState().getBlock());
	}

	private void registerCustomOres() {
		logger.info("Registering chances and rewards...");
		layers.put(1, new LinkedHashSet<CustomOre>());
		layers.put(2, new LinkedHashSet<CustomOre>());
		layers.put(3, new LinkedHashSet<CustomOre>());
		layers.get(1).add(new CustomOre(110, 3, ItemStack.of(ItemTypes.REDSTONE, 1), Text.of(TextColors.RED, "Você achou Redstone!")));
		layers.get(1).add(new CustomOre(140, 3, ItemStack.of(ItemTypes.DIAMOND, 1), Text.of(TextColors.AQUA, "Você achou Diamante!")));
		layers.get(1).add(new CustomOre(170, 5, ItemStack.of((ItemType) PixelmonItems.siliconItem, 1), Text.of(TextColors.WHITE, "Você achou Silício!")));
		layers.get(1).add(new CustomOre(200, 5, ItemStack.of((ItemType) PixelmonItems.waterStoneShard, 1), Text.of(TextColors.BLUE, "Você achou um Pedaço de Pedra da Água!")));
		layers.get(1).add(new CustomOre(230, 5, ItemStack.of((ItemType) PixelmonItems.ruby, 1), Text.of(TextColors.RED, "Você achou Rubi!")));
		layers.get(1).add(new CustomOre(260, 5, ItemStack.of((ItemType) PixelmonItems.fireStoneShard, 1), Text.of(TextColors.RED, "Você achou um Pedaço de Pedra do Fogo!")));
		layers.get(2).add(new CustomOre(50, 3, ItemStack.of(ItemTypes.GOLD_ORE, 1), Text.of(TextColors.GOLD, "Você achou Ouro!")));
		layers.get(2).add(new CustomOre(100, 3, ItemStack.builder().itemType(ItemTypes.DYE).add(Keys.DYE_COLOR, DyeColors.BLUE).quantity(1).build(), Text.of(TextColors.BLUE, "Você achou Lapis Lazuli!")));
		layers.get(2).add(new CustomOre(130, 3, ItemStack.of(ItemTypes.EMERALD, 1), Text.of(TextColors.GREEN, "Você achou Esmeralda!")));
		layers.get(2).add(new CustomOre(160, 5, ItemStack.of((ItemType) PixelmonItems.sapphire, 1), Text.of(TextColors.DARK_BLUE, "Você achou Safira!")));
		layers.get(2).add(new CustomOre(190, 5, ItemStack.of((ItemType) PixelmonItems.amethyst, 1), Text.of(TextColors.DARK_PURPLE, "Você achou Ametista!")));
		layers.get(2).add(new CustomOre(220, 5, ItemStack.of((ItemType) PixelmonItems.dawnStoneShard, 1), Text.of(TextColors.AQUA, "Você achou um Pedaço de Pedra do Amanhecer!")));
		layers.get(2).add(new CustomOre(250, 5, ItemStack.of((ItemType) PixelmonItems.duskStoneShard, 1), Text.of(TextColors.BLUE, "Você achou um Pedaço de Pedra do Anoitecer!")));
		layers.get(3).add(new CustomOre(300, 1, ItemStack.of(ItemTypes.COAL, 1), Text.of(TextColors.BLACK, "Você achou Carvão!")));
		layers.get(3).add(new CustomOre(500, 2, ItemStack.of(ItemTypes.IRON_ORE, 1), Text.of(TextColors.GRAY, "Você achou Minério de Ferro!")));
		layers.get(3).add(new CustomOre(530, 5, ItemStack.of((ItemType) PixelmonItems.crystal, 1), Text.of(TextColors.AQUA, "Você achou Cristal!")));
		layers.get(3).add(new CustomOre(560, 5, ItemStack.of((ItemType) PixelmonItem.getItemFromBlock(PixelmonBlocks.bauxite), 1), Text.of(TextColors.GRAY, "Você achou Bauxita!")));
		layers.get(3).add(new CustomOre(590, 5, ItemStack.of((ItemType) PixelmonItems.thunderStoneShard, 1), Text.of(TextColors.GREEN, "Você achou um Pedaço de Pedra do Trovão!")));
		layers.get(3).add(new CustomOre(620, 5, ItemStack.of((ItemType) PixelmonItems.leafStoneShard, 1), Text.of(TextColors.DARK_GREEN, "Você achou um Pedaço de Pedra de Folha!")));
		layers.get(3).add(new CustomOre(650, 5, ItemStack.of((ItemType) PixelmonItems.sunStoneShard, 1), Text.of(TextColors.GOLD, "Você achou um Pedaço de Pedra do Sol!")));
	}
}
