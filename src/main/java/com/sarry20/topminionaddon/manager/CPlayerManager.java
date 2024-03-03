package com.sarry20.topminionaddon.manager;

import com.sarry20.lib.data.ConfigHelper;
import com.sarry20.topminionaddon.model.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CPlayerManager {
    private final List<CPlayer> CPLAYERS = new ArrayList<>();

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
                    CPLAYERS.add(cPlayer);
                }
            }
        } catch (Exception ex) {
            Bukkit.getLogger().info(ChatColor.RED + "[TopMinionsAddon] An error has occurred loadCPlayers()");
        }
    }

    public void saveCPlayers() {
        try {
            for (CPlayer cPlayer : CPLAYERS) {
                ConfigHelper configHelper = new ConfigHelper("plugins//TopMinionAddon//data//" + cPlayer.getUuid() + ".yml");
                FileConfiguration config = configHelper.getYamlConfiguration();
                config.set("CPlayer", cPlayer);
                configHelper.saveConfig();
            }
        } catch (Exception ex) {
            Bukkit.getLogger().info(ChatColor.RED + "[TopMinionsAddon] An error has occurred saveCplayers()");
        }
    }

    public CPlayer getCPlayerByUUID(String uuid){
        return CPLAYERS.stream().filter(cPlayer -> cPlayer.getUuid().equalsIgnoreCase(uuid)).findFirst().orElse(null);
    }
    public void createCPlayer(Player player){
        CPLAYERS.add(new CPlayer(player.getUniqueId().toString()));
    }
}
