package ngmu.skywars.Util;

import ngmu.skywars.Map.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Config {
    private static Plugin plugin;
    private static ArrayList<String> names;
    private static ArrayList<Map> maps;

    public static void init(Plugin plugin) {
        Config.plugin = plugin;
        plugin.saveDefaultConfig();
        names = new ArrayList<>();
        if (plugin.getConfig().get("list") == null) {names = new ArrayList<>();}
        else {Collections.addAll(names, Objects.requireNonNull(plugin.getConfig().getString("list")).split(","));}
        maps = getMaps();
    }

    public static void saveMaps() {
        if (maps.isEmpty()) {
            return;
        }
        for (Map map : maps) {
            plugin.getConfig().set("maps." + map.getName() + ".lengths", map.getBlocks().length + "," + map.getBlocks()[0].length + "," + map.getBlocks()[0][0].length);
            for (int x = 0; x < map.getBlocks().length; x++) {
                for (int y = 0; y < map.getBlocks()[x].length; y++) {
                    for (int z = 0; z < map.getBlocks()[x][y].length; z++) {
                        plugin.getConfig().set("maps." + map.getName() + ".blocks." + x + "." + y + "." + z + ".type", map.getBlocks()[x][y][z].type().toString());
                        plugin.getConfig().set("maps." + map.getName() + ".blocks." + x + "." + y + "." + z + ".data", map.getBlocks()[x][y][z].data().getAsString());
                    }
                }
            }
        }

        plugin.getConfig().set("list", String.join(",", names));
        plugin.saveConfig();
    }

    public static Map getMap(String name) {
        return maps.stream().filter(map -> map.getName().equals(name)).findFirst().orElse(null);
    }

    public static boolean addMap(Map map) {
        if (names.contains(map.getName())) {
            return false;
        }

        names.add(map.getName());
        maps.add(map);
        return true;
    }

    public static ArrayList<Map> getMaps() {
        ArrayList<Map> mapLis = new ArrayList<>();
        for (String name : names) {
            if (plugin.getConfig().getString("maps." + name + ".lengths") == null) {
                continue;
            }
            String[] len = Objects.requireNonNull(plugin.getConfig().getString("maps." + name + ".lengths")).split(",");
            int xLen = Integer.parseInt(len[0]);
            int yLen = Integer.parseInt(len[1]);
            int zLen = Integer.parseInt(len[2]);
            Map.Block[][][] blocks = new Map.Block[xLen][yLen][zLen];

            for (int x = 0; x < xLen; x++) {
                for (int y = 0; y < yLen; y++) {
                    for (int z = 0; z < zLen; z++) {
                        Material type = Material.valueOf(Objects.requireNonNull(plugin.getConfig().getString("maps." + name + ".blocks." + x + "." + y + "." + z + ".type")));
                        BlockData data = Bukkit.createBlockData(Objects.requireNonNull(plugin.getConfig().getString("maps." + name + ".blocks." + x + "." + y + "." + z + ".data")));
                        blocks[x][y][z] = new Map.Block(data, type);
                    }
                }
            }

            mapLis.add(new Map(name, blocks));
        }

        return mapLis;
    }
}
