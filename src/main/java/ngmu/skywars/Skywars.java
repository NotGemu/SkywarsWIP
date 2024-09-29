package ngmu.skywars;

import ngmu.skywars.Commands.AddMap;
import ngmu.skywars.Commands.PasteMap;
import ngmu.skywars.Map.Map;
import ngmu.skywars.Util.Config;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.Objects;

public final class Skywars extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Config.init(this);
        plugin = this;
        Objects.requireNonNull(getCommand("addmap")).setExecutor(new AddMap());
        Objects.requireNonNull(getCommand("pastemap")).setExecutor(new PasteMap());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Config.saveMaps();
    }
}
