package mc.node;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NodePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("[NODE] Plugin Enabled");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("[NODE] Plugin Disabled");
    }
}
