package mc.node.agent.repository;

public interface Repository<T, ID> {

    void addAgent(ID UUID, T agent);

    void removeAgent(ID uuid);

    void updateAgent(T agent);
}