package mc.node.manager;

import lombok.Getter;
import mc.node.agent.Agent;
import mc.node.agent.interfaces.IAgent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages Agent instances using a unique UUID identifier.
 * Provides methods to add, remove, and retrieve agents in a thread-safe manner.
 */
@Getter
public class AgentManager implements IAgent {

    /**
     * A thread-safe map storing agents by their UUID.
     */
    private final Map<UUID, Agent> agents = new ConcurrentHashMap<>();

    /**
     * Adds an agent to the manager.
     *
     * @param uuid  The unique identifier of the agent.
     * @param agent The Agent instance to be added.
     */
    @Override
    public void addAgent(UUID uuid, Agent agent) {
        agents.put(uuid, agent);
    }

    /**
     * Removes an agent from the manager.
     *
     * @param uuid The unique identifier of the agent to be removed.
     */
    @Override
    public void removeAgent(UUID uuid) {
        agents.remove(uuid);
    }

    /**
     * Retrieves an agent by UUID.
     *
     * @param uuid The unique identifier of the agent.
     * @return The Agent instance associated with the UUID, or null if not found.
     */
    @Override
    public Agent getAgent(UUID uuid) {
        return agents.get(uuid);
    }
}
