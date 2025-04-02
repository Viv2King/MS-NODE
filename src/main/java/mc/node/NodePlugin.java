package mc.node;

import org.bukkit.plugin.java.JavaPlugin;

public final class NodePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Node.INSTANCE.start(this);
    }

    @Override
    public void onDisable() {
        Node.INSTANCE.stop();
    }
}