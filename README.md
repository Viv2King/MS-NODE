
# MC Node Plugin

This repository contains a simple Minecraft plugin for managing user interactions, commands, and events. The plugin utilizes the Bukkit API and provides a basic structure for registering commands, listeners, and managing agents.

## Overview

The core functionality is contained in the `Node` enum, which is responsible for managing the plugin's lifecycle, including starting and stopping the plugin, registering commands and listeners, and interacting with the `AgentManager`.

### Features:
- **Agent Management**: Manages agents via the `AgentManager`.
- **Command Registration**: Registers an example command (`/example`).
- **Event Handling**: Registers an event listener for user actions.
- **Graceful Plugin Shutdown**: Unregisters event listeners during plugin shutdown.

## Project Structure

- **Node Enum**: Central class responsible for the plugin's startup and shutdown process.
- **UserListener**: Handles user-related events.
- **ExampleCommand**: Registers an example command.
- **AgentManager**: Manages agents for the plugin.
- **Registry**: Utility class to register commands and listeners.
  
## Requirements

- Java 8 or higher
- Bukkit/Spigot server
- Lombok dependency

## Installation

1. Clone the repository:

```bash
git clone https://github.com/yourusername/mc-node.git
```

2. Build the project using your preferred build tool (e.g., Maven or Gradle).

3. Place the compiled plugin `.jar` file into the `/plugins` folder of your Bukkit/Spigot server.

4. Restart your server to load the plugin.

## Usage

Once the plugin is installed, the `example` command will be available. Use it as follows:

```bash
/example
```

## Plugin Lifecycle

### Start

The plugin lifecycle starts by calling `Node.INSTANCE.start(plugin)`, which registers the necessary event listeners and commands. 

```java
Node.INSTANCE.start(plugin);
```

### Stop

When stopping the plugin, the event listeners are unregistered, ensuring that no memory leaks occur:

```java
Node.INSTANCE.stop();
```

## Example Code

```java
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
```

## Contributing

If you'd like to contribute to this project, feel free to open a pull request with improvements, bug fixes, or additional features.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
