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

package br.github.superteits.dores.commands;

import br.github.superteits.dores.DOres;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SetDropRateMultiplierCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Set the drop rate of ores."))
            .permission("dores.command.setrate")
            .arguments(GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("multiplier"))))
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    Double multiplier = args.<Double>getOne("multiplier").get();
                    DOres.getInstance().setDropRate(Float.valueOf(multiplier.toString()));
                    src.sendMessage(Text.of(TextColors.GREEN, "[dOres] Você alterou a taxa de drop dos minérios com sucesso!"));
                    return CommandResult.success();
                }})
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

}
