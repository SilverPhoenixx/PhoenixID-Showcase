package net.royalguardians.PhoenixID.command;

import net.royalguardians.PhoenixID.PhoenixID;
import net.royalguardians.PhoenixID.user.LanguageEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        if(!PhoenixID.lang.contains(player.getUniqueId()) || player.hasPermission("PhoenixID.Language")) {
            switch (strings.length) {
                case 1: {
                    LanguageEnum languageEnum = LanguageEnum.getEnumByLang(strings[0].toUpperCase(Locale.ROOT));
                    if(languageEnum == null) {
                        player.sendMessage("§bLanguages: §f" + getLanguageList());
                        return true;
                    }
                    if(languageEnum == PhoenixID.ids.get(player.getUniqueId()).getLanguage()) {
                        player.sendMessage("§bYou use already §9" + languageEnum.getName());
                        return true;
                    }
                    PhoenixID.lang.add(player.getUniqueId());
                    PhoenixID.ids.get(player.getUniqueId()).setLanguage(languageEnum);
                    PhoenixID.db.setLanguage(player.getUniqueId(), languageEnum.getLang());
                    player.sendMessage("§bLanuage set to: §9" + languageEnum.getName());
                    return true;
                }
                default: {
                    player.sendMessage("§bLanguages: §f" + getLanguageList());
                    return true;
                }
            }
        } else {
            player.sendMessage("§cYou have already set the language in this round.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            return getLanguageList();
        }
        return new ArrayList<>();
    }

    public List<String> getLanguageList() {
        List<String> langs = new ArrayList<>();
            Arrays.stream(LanguageEnum.values()).forEach(lang -> {
                langs.add(lang.toString());
            });
            return langs;
    }
}
