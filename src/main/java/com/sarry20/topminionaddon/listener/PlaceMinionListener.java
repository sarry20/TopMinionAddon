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
        if (cPlayer == null)
            return;
        event.setMinionsLimit(event.getMinionsLimit()+cPlayer.getLimit());
        if (cPlayer.getPlacedMaterials().contains(event.getMaterial()))
            return;
        boolean isFirst = MinionObjManager.getPlayerMinions(event.getPlayer().getName()).stream().anyMatch(
                minionObj ->
                        minionObj.getMaterial().equalsIgnoreCase(event.getMaterial())
        );

        if (!isFirst)
            return;

        cPlayer.add1UniqueMinion(event.getMaterial());
        if (cPlayer.getUniqueMinions() >=
                plugin.getConfigUtil().getInt("increaseLimit") *
                (cPlayer.getLimit() == 0 ? 1 : cPlayer.getLimit()+1)){
            cPlayer.add1Limit();
        }
    }
}
