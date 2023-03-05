package net.royalguardians.PhoenixID.listener;

import net.royalguardians.PhoenixID.PhoenixID;
import net.royalguardians.PhoenixID.scoreboard.RankEnum;
import net.royalguardians.PhoenixID.scoreboard.ScoreboardAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        PhoenixID.db.getPlayer(e.getName(), e.getUniqueId(), userID -> {
            PhoenixID.ids.put(e.getUniqueId(), userID);
            Bukkit.getScheduler().runTask(PhoenixID.getInstance(), () -> {
                PhoenixRegisteredEvent event = new PhoenixRegisteredEvent(e.getUniqueId(), e.getName(), userID);
                Bukkit.getServer().getPluginManager().callEvent(event);

            });
            });

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        RankEnum rankEnum =  RankEnum.getRank(event.getPlayer());
        ScoreboardAPI.addTeam(ScoreboardAPI.getScoreboard(), event.getPlayer(), "" + rankEnum.getTeamid());
        ScoreboardAPI.sendTablist(rankEnum.getTablistPrefix(), rankEnum.getTeamSuffix(), event.getPlayer(), Bukkit.getOnlinePlayers());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(PhoenixID.ids.containsKey(e.getPlayer())) PhoenixID.ids.remove(e.getPlayer());
    }
}
