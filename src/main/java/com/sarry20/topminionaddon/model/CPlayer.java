package com.sarry20.topminionaddon.model;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPlayer implements ConfigurationSerializable {
    private final String uuid;
    private int limit;
    private int uniqueMinions;
    private final List<String> placedTypes;

    public CPlayer(String uuid) {
        this.uuid = uuid;
        this.limit = 0;
        this.uniqueMinions = 0;
        this.placedTypes = new ArrayList<>();
    }

    public CPlayer(Map<String, Object> map) {
        uuid = (String) map.get("uuid");
        limit = (int) map.get("limit");
        uniqueMinions = (int) map.get("uniqueMinions");
        placedTypes = (List<String>) map.get("placedTypes");
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("limit", limit);
        map.put("uniqueMinions",uniqueMinions);
        map.put("placedTypes",placedTypes);
        return map;
    }

    public String getUuid() {
        return uuid;
    }
    public void add1UniqueMinion(String type){
        placedTypes.add(type);
        uniqueMinions+=1;
    }
    public int getUniqueMinions() {
        return uniqueMinions;
    }
    public void add1Limit(){
        limit+=1;
    }
    public int getLimit() {
        return limit;
    }

    public List<String> getPlacedTypes() {
        return placedTypes;
    }
}
