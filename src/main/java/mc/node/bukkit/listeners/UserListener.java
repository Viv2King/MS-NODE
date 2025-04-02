package mc.node.bukkit.listeners;

import lombok.RequiredArgsConstructor;
import mc.node.agent.Agent;
import mc.node.manager.AgentManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class UserListener implements Listener {

    private final AgentManager agentManager;

    /**
     * Handles player join events. Initializes and prepares the player's agent.
     *
     * @param event the player join event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        String playerName = player.getName();

        Agent agent = new Agent(playerId, playerName);
        agent.prepare();

        // Add the agent to the manager
        agentManager.addAgent(playerId, agent);
        Bukkit.getConsoleSender().sendMessage("Agent created and prepared for player: " + playerName);
    }

    /**
     * Handles player quit events. Removes the player's agent from the manager.
     *
     * @param event the player quit event.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        agentManager.removeAgent(playerId);
        Bukkit.getConsoleSender().sendMessage("Agent removed for player: " + player.getName());
    }
}
