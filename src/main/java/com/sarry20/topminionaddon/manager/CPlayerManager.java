package com.sarry20.topminionaddon.manager;

import com.sarry20.topminionaddon.TopMinionAddon;
import com.sarry20.topminionaddon.config.ConfigUtil;
import com.sarry20.topminionaddon.model.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class CPlayerManager {
    private final HashMap<String,CPlayer> CPLAYERS = new HashMap<>();

    public CPlayerManager(){
        loadCPlayers();
    }
    public void loadCPlayers() {
        try {
            File folder = new File("plugins//TopMinionAddon//data");
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles == null) return;
            CPLAYERS.clear();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    CPlayer cPlayer = (CPlayer) config.get("CPlayer");
                    CPLAYERS.put(cPlayer.getUuid(),cPlayer);
                }
            }
        } catch (Exception ex) {
            Bukkit.getLogger().info(ChatColor.RED + "[TopMinionsAddon] An error has occurred loadCPlayers()");
        }
    }

    public void saveCPlayers() {
        try {
            for (CPlayer cPlayer : CPLAYERS.values()) {
                File folder = new File("plugins//TopMinionAddon//data");
                ConfigUtil configUtil = new ConfigUtil(cPlayer.getUuid(), TopMinionAddon.getInstance(),folder,false);
                FileConfiguration config = configUtil.getConfig();
                config.set("CPlayer", cPlayer);
                configUtil.saveSync();
            }
        } catch (Exception ex) {
            Bukkit.getLogger().info(ChatColor.RED + "[TopMinionsAddon] An error has occurred saveCplayers()");
        }
    }

    public CPlayer getCPlayerByUUID(String uuid){
        return CPLAYERS.get(uuid);
    }
    public void createCPlayer(Player player){
        CPLAYERS.put(player.getUniqueId().toString(),new CPlayer(player.getUniqueId().toString()));
    }
}
