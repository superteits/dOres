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
import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.config.PixelmonBlocks;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.security.SecureRandom;
import java.util.HashSet;


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

	private HashSet<BlockType> affectedBlockList = new HashSet<BlockType>();
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
		logger.info("Registering affected blocks...");
		affectedBlockList.add(BlockTypes.COAL_ORE);
		affectedBlockList.add(BlockTypes.IRON_ORE);
		affectedBlockList.add(BlockTypes.GOLD_ORE);
		affectedBlockList.add(BlockTypes.DIAMOND_ORE);
		affectedBlockList.add(BlockTypes.EMERALD_ORE);
		affectedBlockList.add(BlockTypes.REDSTONE_ORE);
		affectedBlockList.add(BlockTypes.LAPIS_ORE);
		affectedBlockList.add((BlockType) PixelmonBlocks.amethystOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.crystalOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.fireStoneOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.dawnduskStoneOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.leafStoneOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.rubyOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.sapphireOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.siliconOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.sunStoneOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.thunderStoneOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.waterStoneOre.getDefaultState().getBlock());
		affectedBlockList.add((BlockType) PixelmonBlocks.bauxite.getDefaultState().getBlock());
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

	public HashSet<BlockType> getAffectedBlockList() {
		return affectedBlockList;
	}
}
