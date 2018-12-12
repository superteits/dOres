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

import br.github.superteits.dores.config.Config;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

public class SpawnEntityListener {

    @Listener
    public void onEntitySpawn(SpawnEntityEvent e) {
        //Handle Hacked clients drops
        e.getContext().get(EventContextKeys.SPAWN_TYPE).ifPresent(spawnType -> {
            if (spawnType.getId().equals("sponge:block_spawning") ||
                    spawnType.getId().equals("sponge:experience")) {
                if (e.getCause().root() instanceof EntityPlayerMP)
                    e.setCancelled(true);
            }
        });
        //Handle normal experience drop
        e.getContext().get(EventContextKeys.BLOCK_HIT).ifPresent(blockSnapshot -> {
            if(Config.getBlocksBlacklist().contains(blockSnapshot.getState().getType()))
                e.setCancelled(true);
        });
    }
}
