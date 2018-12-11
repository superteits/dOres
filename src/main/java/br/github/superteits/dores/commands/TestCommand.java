package br.github.superteits.dores.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class TestCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    Player player = (Player) src;
                    try {
                        System.out.println(DataFormats.JSON.write(player.getItemInHand(HandTypes.MAIN_HAND).get().toContainer()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return CommandResult.success();
                }})
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

}
