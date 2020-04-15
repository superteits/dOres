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

package io.github.teitss.dores.commands;

import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class BaseCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Base command of dOres"))
            .child(new InfoCommand().getCommandSpec(), "info")
            .child(new DebugCommand().getCommandSpec(), "debug")
            .child(new SwitchSmeltCommand().getCommandSpec(), "smelt")
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

}
