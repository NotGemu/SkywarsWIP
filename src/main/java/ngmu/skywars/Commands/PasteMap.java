package ngmu.skywars.Commands;

import ngmu.skywars.Map.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static ngmu.skywars.Util.Config.getMap;

public class PasteMap implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 1 || !(commandSender instanceof Player player)) {
            return false;
        }

        Map map = getMap(strings[0]);
        if (map == null) {
            return false;
        }

        player.sendMessage("Pasting map...");
        map.paste(player.getLocation());
        return true;
    }
}
