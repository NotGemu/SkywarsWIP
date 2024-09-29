package ngmu.skywars.Commands;

import ngmu.skywars.Map.Map;
import ngmu.skywars.Util.Config;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddMap implements CommandExecutor {
    record aLocation(int x, int y, int z) {}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 7 || !(commandSender instanceof Player player)) {
            return false;
        }

        aLocation start = new aLocation(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
        aLocation end = new aLocation(Integer.parseInt(strings[4]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]));
        int xLen = Math.abs(start.x - end.x) + 1;
        int yLen = Math.abs(start.y - end.y) + 1;
        int zLen = Math.abs(start.z - end.z) + 1;
        int xMin = Math.min(start.x, end.x);
        int yMin = Math.min(start.y, end.y);
        int zMin = Math.min(start.z, end.z);
        int xMax = Math.max(start.x, end.x);
        int yMax = Math.max(start.y, end.y);
        int zMax = Math.max(start.z, end.z);
        Map.Block[][][] blocks = new Map.Block[xLen][yLen][zLen];


        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                for (int z = zMin; z <= zMax; z++) {
                    Location location = new Location(player.getWorld(), x, y, z);
                    blocks[x - xMin][y - yMin][z - zMin] = new Map.Block(location.getBlock().getBlockData(), location.getBlock().getType());
                }
            }
        }

        if (Config.addMap(new Map(strings[0], blocks))) {
            player.sendMessage("Map added successfully!");
        } else {
            player.sendMessage("Map already exists!");
        }
        return true;
    }
}
