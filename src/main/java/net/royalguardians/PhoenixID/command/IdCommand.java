package net.royalguardians.PhoenixID.command;

import net.royalguardians.PhoenixID.PhoenixID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IdCommand  implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (!(commandSender instanceof Player)) return true;
            Player player = (Player) commandSender;
            if(PhoenixID.ids.get(player.getUniqueId()) == null) {
                player.sendMessage("§cId not found");
                return true;
            }
            player.sendMessage("§eYour Id:§6 " + PhoenixID.ids.get(player.getUniqueId()).getOrdinal());
            return true;
        }
}
