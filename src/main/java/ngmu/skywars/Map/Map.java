package ngmu.skywars.Map;

import ngmu.skywars.Skywars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class Map {
    public record Block(BlockData data, Material type) {}
    private final String name;
    private final Block[][][] blocks;
    private final int[] offset;

    public Map(String name, Block[][][] blocks) {
        this.name = name;
        this.blocks = blocks;
        this.offset = calculateOffset();
    }

    public String getName() {
        return name;
    }

    public Block[][][] getBlocks() {
        return blocks;
    }

    private int[] calculateOffset() {
        int[] offset = new int[3];
        offset[0] = blocks.length / 2;
        offset[1] = blocks[0].length / 2;
        offset[2] = blocks[0][0].length / 2;
        return offset;
    }

    public void paste(Location location) {
        PasteMap pasteMap = new PasteMap(blocks, location, offset);
        pasteMap.runTask(Skywars.plugin);
    }
}
