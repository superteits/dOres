package io.github.teitss.dores.config;

import com.google.common.reflect.TypeToken;
import io.github.teitss.dores.CustomOre;
import net.minecraft.util.text.TextComponentString;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;
import org.spongepowered.common.text.SpongeTexts;

import java.util.AbstractMap;
import java.util.Map;

public class CustomOreSerializer {

    public static Map.Entry deserialize(ConfigurationNode node) throws ObjectMappingException {
        return new AbstractMap.SimpleEntry(
                node.getNode("layer").getInt(),
                new CustomOre(
                    (int)(node.getNode("chance").getDouble() * 100),
                    node.getNode("harvest-level").getInt(),
                    ItemStackUtil.toNative(node.getNode("type").getValue(TypeToken.of(ItemStack.class))),
                    SpongeTexts.toComponent(TextSerializers.FORMATTING_CODE.deserialize(node.getNode("message").getString()))));
    }

}
