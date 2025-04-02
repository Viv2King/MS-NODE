package mc.node;

import lombok.Getter;
import mc.node.bukkit.commands.ExampleCommand;
import mc.node.bukkit.listeners.BlockListener;
import mc.node.bukkit.listeners.UserListener;
import mc.node.agent.AgentManager;
import mc.node.objects.plugin.registry.Registry;
import org.bukkit.event.HandlerList;

@Getter
public enum Node {

    INSTANCE;

    private NodePlugin plugin;

    private final AgentManager agentManager = new AgentManager();

    public void start(NodePlugin plugin) {
        this.plugin = plugin;

        Registry.registerListeners(plugin, new UserListener(agentManager), new BlockListener(agentManager));
        Registry.registerCommand("example", new ExampleCommand("example", "nothing", "ex", "ee"));
    }

    public void stop() {
        HandlerList.unregisterAll(plugin);
    }

}