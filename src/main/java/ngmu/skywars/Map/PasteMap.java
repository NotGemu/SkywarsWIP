package ngmu.skywars.Map;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class PasteMap extends BukkitRunnable {
    private final Map.Block[][][] blocks;
    private final Location location;
    private final int[] offset;

    public PasteMap(Map.Block[][][] blocks, Location location, int[] offset) {
        this.blocks = blocks;
        this.location = location;
        this.offset = offset;
    }

    @Override
    public void run() {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                for (int z = 0; z < blocks[x][y].length; z++) {
                    location.getWorld().getBlockAt(location.getBlockX() + x - offset[0], location.getBlockY() + y - offset[1], location.getBlockZ() + z - offset[2]).setType(blocks[x][y][z].type());
                    location.getWorld().getBlockAt(location.getBlockX() + x - offset[0], location.getBlockY() + y - offset[1], location.getBlockZ() + z - offset[2]).setBlockData(blocks[x][y][z].data());
                }
            }
        }
    }
}
