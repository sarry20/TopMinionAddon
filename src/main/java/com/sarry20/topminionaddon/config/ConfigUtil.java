package com.sarry20.topminionaddon.config;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public class ConfigUtil {
    private static final String DEFAULT_EXTENSION = ".yml";
    private final String rawName;
    private final String fileName;
    private final JavaPlugin plugin;
    private final File folder;
    private YamlConfiguration yamlConfiguration;
    private final File file;

    public ConfigUtil(String fileName, JavaPlugin plugin, File folder, boolean hasResource){
        this.rawName = fileName;
        this.fileName = fileName + DEFAULT_EXTENSION;
        this.plugin = plugin;
        this.folder = folder;
        this.file = this.createFile(hasResource);
    }
    public ConfigUtil(String fileName, JavaPlugin plugin, boolean hasResource){
        this(fileName,plugin,plugin.getDataFolder(),hasResource);
    }
    private File createFile(boolean hasResource) {
        File file = new File(folder, fileName);
        if (!file.exists()) {
            try {
                folder.mkdirs();
                if (!hasResource) file.createNewFile();
                else Files.copy(Objects.requireNonNull(plugin.getClass().getClassLoader().getResourceAsStream(fileName)), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
    public void save(){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                yamlConfiguration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public void saveSync(){
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public List<Integer> getIntList(String path){
//        return yamlConfiguration.getIntegerList(rawName+"."+path);
//    }
//    public List<?> getList(String path){
//        return yamlConfiguration.getList(rawName+"."+path);
//    }
//
//    public List<String> getStringList(String path){
//        return yamlConfiguration.getStringList(rawName+"."+path);
//    }
//
//    public String getString(String path){
//        return yamlConfiguration.getString(rawName+"."+path);
//    }

    public Integer getInt(String path){
        return yamlConfiguration.getInt(rawName+"."+path);
    }

//    public Object get(String path){
//        return yamlConfiguration.get(rawName+"."+path);
//    }
//
//    public void set(String path, Object value,Object def){
//        if (value != null) yamlConfiguration.set(rawName+"."+path, value);
//        else yamlConfiguration.set(rawName+"."+path, def);
//    }
//    public void reloadConfig() {
//        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
//    }

    public YamlConfiguration getConfig() {
        return yamlConfiguration;
    }

}
