package mc.node;

import lombok.Getter;
import mc.node.bukkit.commands.ExampleCommand;
import mc.node.bukkit.listeners.UserListener;
import mc.node.manager.AgentManager;
import mc.node.objects.plugin.registry.Registry;
import org.bukkit.event.HandlerList;

@Getter
public enum Node {

    INSTANCE;

    private NodePlugin plugin;

    private final AgentManager agentManager = new AgentManager();

    public void start(NodePlugin plugin) {
        this.plugin = plugin;

        Registry.registerListener(new UserListener(agentManager), plugin);
        Registry.registerCommand("example", new ExampleCommand("example", "nothing", "ex", "ee"));
    }

    public void stop() {
        HandlerList.unregisterAll(plugin);
    }

}