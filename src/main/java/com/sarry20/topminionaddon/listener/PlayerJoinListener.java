package com.sarry20.topminionaddon.listener;

import com.sarry20.topminionaddon.TopMinionAddon;
import com.sarry20.topminionaddon.model.CPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private TopMinionAddon plugin = TopMinionAddon.getInstance();

    @EventHandler
    public void atJoin(PlayerJoinEvent event){
        CPlayer cPlayer = plugin.getcPlayerManager().getCPlayerByUUID(event.getPlayer().getUniqueId().toString());
        if (cPlayer == null){
            plugin.getcPlayerManager().createCPlayer(event.getPlayer());
        }

    }
}
