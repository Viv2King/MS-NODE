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
git clone https://github.com/Viv2King/MS-NODE.git
