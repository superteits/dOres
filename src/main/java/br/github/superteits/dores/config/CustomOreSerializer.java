package br.github.superteits.dores.config;

import br.github.superteits.dores.utils.CustomOre;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.AbstractMap;
import java.util.Map;

public class CustomOreSerializer {

    public static Map.Entry deserialize(ConfigurationNode node) throws ObjectMappingException {
        return new AbstractMap.SimpleEntry(
                node.getNode("layer").getInt(),
                new CustomOre(
                    (int)(node.getNode("chance").getDouble() * 100),
                    node.getNode("harvest-level").getInt(),
                    node.getNode("type").getValue(TypeToken.of(ItemStack.class)),
                    TextSerializers.FORMATTING_CODE.deserialize(node.getNode("message").getString())));
    }

}
