package mc.node.agent.interfaces;

import mc.node.agent.Agent;

import java.util.UUID;

public interface IAgent {

    void addAgent(UUID uuid, Agent agent);

    void removeAgent(UUID uuid);

    Agent getAgent(UUID uuid);

}
