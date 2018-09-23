package br.github.superteits.dores.commands;

import br.github.superteits.dores.DOres;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class DebugCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Debug command of dOres"))
            .permission("dores.command.debug")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    if (src instanceof Player) {
                        if(DOres.getInstance().getDebug().contains(((Player) src).getUniqueId()))
                            DOres.getInstance().getDebug().remove(((Player) src).getUniqueId());
                        else
                            DOres.getInstance().getDebug().add(((Player) src).getUniqueId());
                    }
                    return CommandResult.success();
                }})
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

}
