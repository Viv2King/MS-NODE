package mc.node.agent.repository;

import mc.node.agent.Agent;

import java.util.Optional;
import java.util.UUID;

public interface AgentRepository extends Repository<Agent, UUID> {

    Optional<Agent> findByUUID(UUID uuid);

}