package mc.node.bukkit.listeners;

import lombok.RequiredArgsConstructor;
import mc.node.agent.Agent;
import mc.node.agent.AgentManager;
import mc.node.objects.chat.MessageBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@RequiredArgsConstructor
public class BlockListener implements Listener {

    private final AgentManager agentManager;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent breakEvent) {
        Player player = breakEvent.getPlayer();

        Agent agent = agentManager.getAgent(player.getUniqueId());
        if (agent == null) return;

        // Create a new Agent with updated coins
        Agent updatedAgent = new Agent(agent.uniqueId(), agent.username(), agent.player(), agent.coins() + 1);
        agent.textAgent(MessageBuilder.legacy("&a[MC-NODE] Break Block by %player% and add 1 coin").addPlaceholder("%player%", player.getName()));
        agent.textAgent(MessageBuilder.legacy("&a[MC-NODE] You have now " + agent.coins() + " coins."));

        // Update the agent in the manager
        agentManager.updateAgent(updatedAgent);
    }
}
