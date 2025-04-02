package mc.node.agent;

import java.util.UUID;

public interface IAgentManager {

    void addAgent(UUID uuid, Agent agent);

    void removeAgent(UUID uuid);

    Agent getAgent(UUID uuid);

    void updateAgent(Agent updatedAgent);
}