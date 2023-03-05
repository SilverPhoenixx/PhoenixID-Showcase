package net.royalguardians.PhoenixID.user;

import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

public class UserID {
    int ordinal;
    String shortcut;
    LanguageEnum language;

    /**
     * @param String = Scoreboard Score
     * @param String = Old String
     */
    HashMap<Integer, String> scoreboardVariable = new HashMap<>();

    public UserID(int ordinal, String shortcut, LanguageEnum language) {
        this.ordinal = ordinal;
        this.shortcut = shortcut;
        this.language = language;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public String getShortcut() {
        return shortcut;
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }

    public HashMap<Integer, String> getScoreboardVariable() {
        return scoreboardVariable;
    }

    public void setScoreboardVariable(HashMap<Integer, String> scoreboardVariable) {
        this.scoreboardVariable = scoreboardVariable;
    }
}
