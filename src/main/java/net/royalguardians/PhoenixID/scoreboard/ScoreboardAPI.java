package net.royalguardians.PhoenixID.scoreboard;

import net.royalguardians.PhoenixID.PhoenixID;
import net.royalguardians.PhoenixID.user.UserID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardAPI {

    private static Scoreboard scoreboard = registerNormalScoreboard();

    public static Scoreboard registerNormalScoreboard() {
        int i = 0;
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for(RankEnum rankEnum : RankEnum.values()) {
            scoreboard.registerNewTeam("" + i);
            scoreboard.getTeam("" + i).setPrefix(rankEnum.getTablistPrefix());
            scoreboard.getTeam(""+ i).setSuffix(rankEnum.getTablistSuffix());
            i++;
        }
        return scoreboard;
    }



    public static void addTeam(Scoreboard scoreboard, Player p, String team) {
        scoreboard.getTeam(team).addEntry(p.getName());
    }


    public static void sendTablist(String prefix, String suffix, Player player, Collection<? extends Player> sendedPlayer) {
        String name = prefix + player.getDisplayName() + suffix;
        player.setPlayerListName(name);
    }



    public static Scoreboard getScoreboard() {
        return scoreboard;
    }
}
