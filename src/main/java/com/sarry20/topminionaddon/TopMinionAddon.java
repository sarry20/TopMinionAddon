package com.sarry20.topminionaddon;

import com.sarry20.lib.data.ConfigHelper;
import com.sarry20.topminionaddon.listener.PlaceMinionListener;
import com.sarry20.topminionaddon.listener.PlayerJoinListener;
import com.sarry20.topminionaddon.manager.CPlayerManager;
import com.sarry20.topminionaddon.model.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class TopMinionAddon extends JavaPlugin {
    private ConfigHelper configUtil;
    private static TopMinionAddon instance;
    private CPlayerManager cPlayerManager;

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(CPlayer.class);
        getLogger().info("Plugin enabling");

        configUtil = new ConfigHelper("plugins//TopMinionAddon//config.yml");
        generateConfig();
        cPlayerManager.loadCPlayers();
        Bukkit.getPluginManager().registerEvents(new PlaceMinionListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(),this);
    }

    @Override
    public void onDisable() {
        cPlayerManager.saveCPlayers();
    }
    private void generateConfig(){
        if (configUtil.getYamlConfiguration().getInt("increaseLimit") == 0){
            configUtil.getYamlConfiguration().set("increaseLimit",5);
            configUtil.saveConfig();
        }
    }
    public static TopMinionAddon getInstance() {
        return instance;
    }
    public ConfigHelper getConfigUtil() {
        return configUtil;
    }

    public CPlayerManager getcPlayerManager() {
        return cPlayerManager;
    }
}
