package io.github.teitss.dores.commands;

import io.github.teitss.dores.DOres;
import io.github.teitss.dores.config.Config;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class SwitchSmeltCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Debug command of dOres"))
            .permission("dores.command.smelted")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    if (src instanceof Player) {
                        if(DOres.getInstance().getIsSmelting().contains(((Player) src).getUniqueId())) {
                            DOres.getInstance().getIsSmelting().remove(((Player) src).getUniqueId());
                            src.sendMessage(Text.of(Config.getSmeltOffMessage()));
                        }

                        else {
                            DOres.getInstance().getIsSmelting().add(((Player) src).getUniqueId());
                            src.sendMessage(Text.of(Config.getSmeltOnMessage()));
                        }

                    }
                    return CommandResult.success();
                }})
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

}
