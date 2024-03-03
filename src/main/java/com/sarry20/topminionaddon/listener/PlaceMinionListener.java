package com.sarry20.topminionaddon.listener;

import com.sarry20.topminion.event.minion.MinionPlaceEvent;
import com.sarry20.topminion.models.minion.MinionObj;
import com.sarry20.topminion.models.minion.MinionObjManager;
import com.sarry20.topminionaddon.TopMinionAddon;
import com.sarry20.topminionaddon.model.CPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PlaceMinionListener implements Listener {
    private final TopMinionAddon plugin = TopMinionAddon.getInstance();
    @EventHandler
    public void atPlace(MinionPlaceEvent event){
        CPlayer cPlayer = plugin.getcPlayerManager().getCPlayerByUUID(event.getPlayer().getUniqueId().toString());
        if (cPlayer != null)
            event.setMinionsLimit(event.getMinionsLimit()+cPlayer.getLimit());
        List<MinionObj> minions = MinionObjManager.getPlayerMinions(event.getPlayer().getName());
        boolean isFirst = minions.stream().anyMatch(
                minionObj ->
                        minionObj.getType().equalsIgnoreCase(event.getType())
        );
        if (!isFirst)
            return;
        if (cPlayer == null)
            return;
        if (cPlayer.getPlacedTypes().contains(event.getType()))
            return;
        cPlayer.add1UniqueMinion(event.getType());
        if (cPlayer.getUniqueMinions() >= plugin.getConfigUtil().getYamlConfiguration().getInt("increaseLimit")){
            cPlayer.add1Limit();
        }
    }
}
