package com.sarry20.topminionaddon;

import com.sarry20.topminionaddon.config.ConfigUtil;
import com.sarry20.topminionaddon.listener.PlaceMinionListener;
import com.sarry20.topminionaddon.listener.PlayerJoinListener;
import com.sarry20.topminionaddon.manager.CPlayerManager;
import com.sarry20.topminionaddon.model.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class TopMinionAddon extends JavaPlugin {
    private ConfigUtil configUtil;
    private static TopMinionAddon instance;
    private CPlayerManager cPlayerManager;

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(CPlayer.class);
        getLogger().info("Plugin enabling");

        configUtil = new ConfigUtil("config",this,true);
        cPlayerManager = new CPlayerManager();
        Bukkit.getPluginManager().registerEvents(new PlaceMinionListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(),this);
    }

    @Override
    public void onDisable() {
        cPlayerManager.saveCPlayers();
    }

    public static TopMinionAddon getInstance() {
        return instance;
    }

    public ConfigUtil getConfigUtil() {
        return configUtil;
    }

    public CPlayerManager getcPlayerManager() {
        return cPlayerManager;
    }
}
