package net.royalguardians.PhoenixID.scoreboard;

import org.bukkit.entity.Player;

public enum  RankEnum {

    ADMIN("§cAdmin §7|§c ", "", "§cAdmin §7| §c", "", 0),
    SRBUILDER("§6SrBuilder §7|§6 ", "", "§6SrBuilder §7|§6 ", "", 1),
    BUILDER("§6Builder §7|§6 ", "", "§6Builder §7|§6 ", "", 2),
    JUNIOR("§eJunior §7|§e ", "", "§eJunior §7|§e ", "", 3),
    TRAINEE("§aTrainee §7|§a ", "", "§aTrainee §7|§a ", "", 4),
    FRIEND("§bFriend §7|§b ", "", "§bFriend §7|§b ", "", 5),
    PLAYER("§7", "", "§7", "", 6);

    private String tablistPrefix, tablistSuffix, teamPrefix, teamSuffix;
    private int teamid;

    private RankEnum(String tablistPrefix, String tablistSuffix, String teamPrefix, String teamSuffix, int teamid) {
        this.tablistPrefix = tablistPrefix;
        this.tablistSuffix = tablistSuffix;
        this.teamPrefix = teamPrefix;
        this.teamSuffix = teamSuffix;

        this.teamid  = teamid;
    }

    public String getTablistPrefix() {
        return tablistPrefix;
    }

    public String getTablistSuffix() {
        return tablistSuffix;
    }

    public String getTeamPrefix() {
        return teamPrefix;
    }

    public String getTeamSuffix() {
        return teamSuffix;
    }

    public int getTeamid() {
        return teamid;
    }

    public static RankEnum  getRank(Player player) {
        for(RankEnum rankEnum : values()) {
            if(player.hasPermission("PhoenixID.RANK." + rankEnum.toString())) return rankEnum;
        }
        return PLAYER;
    }
}
