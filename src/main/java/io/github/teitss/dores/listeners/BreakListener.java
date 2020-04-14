package io.github.teitss.dores.listeners;

import io.github.teitss.dores.config.Config;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BreakListener {

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent e) {
        if(Config.getBlocksBlacklist().contains(e.getState().getBlock().getRegistryName().toString()))
            e.setExpToDrop(0);
    }
}
