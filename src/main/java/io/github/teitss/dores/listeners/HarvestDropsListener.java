package io.github.teitss.dores.listeners;

import io.github.teitss.dores.CustomOre;
import io.github.teitss.dores.DOres;
import io.github.teitss.dores.config.Config;
import io.github.teitss.dores.utils.HarvestUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HarvestDropsListener {

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent e) {
        //Removing drops from blocks in blacklist
        if (Config.getBlocksBlacklist().contains(e.getState().getBlock().getRegistryName().toString())) {
            e.getDrops().clear();
            return;
        }

        if (Blocks.STONE.equals(e.getState().getBlock())) {

            if (e.getHarvester() == null) return;
            EntityPlayerMP player = (EntityPlayerMP) e.getHarvester();


            if (player.getHeldItemMainhand().equals(ItemStack.EMPTY)) return;
            ItemStack itemStack = player.getHeldItemMainhand();

            if (HarvestUtil.getHarvestLevel(itemStack.getItem()) == 0) return;
            if (HarvestUtil.getLayer(e.getPos().getY()) == 0) return;

            int chance = DOres.getInstance().getRNG().nextInt(10000);

            for(CustomOre customOre : Config.getChances().get(HarvestUtil.getLayer(e.getPos().getY()))) {
                if(chance >= customOre.getChance() * Config.getDropRate()) continue;

                if(HarvestUtil.getHarvestLevel(itemStack.getItem()) < customOre.getHarvestLevel()) continue;

                if(customOre.getItemStack().getItem().equals(Items.EMERALD) &&
                        !e.getWorld().getBiome(e.getPos()).equals(Biomes.EXTREME_HILLS)) continue;

                ItemStack drop = customOre.getItemStack();
                drop.setCount(Config.getDropQuantity() + e.getFortuneLevel());
                e.getDrops().add(drop);
                player.connection.sendPacket(new SPacketTitle(SPacketTitle.Type.ACTIONBAR, customOre.getText()));
                break;
            }

        }
    }

}
