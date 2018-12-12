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
import br.github.superteits.dores.commands.TestCommand;
import br.github.superteits.dores.config.Config;
import br.github.superteits.dores.listeners.ChangeBlockBreakListener;
import br.github.superteits.dores.listeners.DropItemDestructListener;
import br.github.superteits.dores.listeners.SpawnEntityListener;
import com.google.inject.Inject;
import net.minecraft.launchwrapper.Launch;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.UUID;


@Plugin (id = DOres.ID,
		name = DOres.NAME,
		version = DOres.VERSION,
		authors = DOres.AUTHOR,
		description = "Simple solution for X-Rays.",
		dependencies = {@Dependency(id="pixelmon")})
public class DOres {

	public static final String ID = "dores";
	public static final String NAME = "dOres";
	public static final String VERSION = "1.1.3";
	public static final String AUTHOR = "Teits";

	@Inject
	private Logger logger;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private Path path;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	private	static DOres instance;
	private SecureRandom random = new SecureRandom();
	private HashSet<UUID> debug = new HashSet<>();
	private boolean ambienteDev = isDevEnv(Launch.blackboard.get("fml.deobfuscatedEnvironment"));
	
	@Listener
	public void onGameInit(GameInitializationEvent e) {
		instance = this;
		logger.info("Loading dOres...");
		logger.info("Registering event listeners...");
		Sponge.getEventManager().registerListeners(this, new ChangeBlockBreakListener());
		Sponge.getEventManager().registerListeners(this, new DropItemDestructListener());
		Sponge.getEventManager().registerListeners(this, new SpawnEntityListener());
	}

	@Listener
	public void onServerStarting(GameStartingServerEvent e) {
		logger.info("Loading configuration...");
		Config.install(path, configManager);
		logger.info("Registering commands...");
		Sponge.getCommandManager().register(this, new BaseCommand().getCommandSpec(), "dores", "do", "damno");
		if(ambienteDev)
			Sponge.getCommandManager().register(this, new TestCommand().getCommandSpec(), "test");
	}

	@Listener
	public void onGameReload(GameReloadEvent e) {
		Config.install(path, configManager);
	}

	public SecureRandom getRNG() {
		return random;
	}

	public static DOres getInstance() {
		return instance;
	}

	public HashSet<UUID> getDebug() {
		return debug;
	}

	public Logger getLogger() {
		return logger;
	}

	private boolean isDevEnv(Object object) {
		return java.lang.Boolean.valueOf(object.toString());
	}

}
