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

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;

public class HelpCommand {

    private PaginationList helpPagination = PaginationList.builder()
            .title(TextSerializers.formattingCode('&').deserialize("&6[&l&fdOres Help&6]"))
            .header(TextSerializers.formattingCode('&').deserialize("&7Aliases - dores, do, damno"))
            .padding(Text.of(TextColors.GOLD, "-"))
            .contents(Arrays.asList(
                    TextSerializers.formattingCode('&').deserialize("&l&f/do help &r&7- Mostra essa lista de ajuda."),
                    TextSerializers.formattingCode('&').deserialize("&l&f/do rate <número decimal> &r&7- Altera a taxa de drop"),
                    TextSerializers.formattingCode('&').deserialize("&l&f/do quantity &r&7- Altera a quantidade de drop"),
                    TextSerializers.formattingCode('&').deserialize("&l&f/do info &r&7- Mostra as informações atuais do plugin.")))
            .build();

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Help command of dOres"))
            .permission("dores.command.help")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                helpPagination.sendTo(src);
                return CommandResult.success();
                }})
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
