package io.github.teitss.dores.commands;

import com.google.common.collect.ImmutableMap;
import io.github.teitss.dores.DOres;
import io.github.teitss.dores.config.Config;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class ConfigCommand {

    private ImmutableMap<String, String> choices = ImmutableMap.of("drop-rate", "drop-rate", "drop-quantity", "drop-quantity");


    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Debug command of dOres"))
            .permission("dores.command.config")
            .arguments(GenericArguments.choices(Text.of("config-node"), choices),
            GenericArguments.string(Text.of("value")))
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    String configNode = args.<String>getOne(Text.of("config-node")).get();
                    String value = args.<String>getOne(Text.of("value")).get();

                    if (configNode.equals("drop-rate")) {
                        Config.setDropRate(Float.parseFloat(value));
                    } else {
                        Config.setDropQuantity(Integer.parseInt(value));
                    }
                    Config.save(DOres.getInstance().getConfigManager());
                    src.sendMessage(Text.of("Você alterou as configurações do plugin."));

                    return CommandResult.success();
                }})
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

}
