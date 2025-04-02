package mc.node.objects.plugin.registry;

import lombok.experimental.UtilityClass;
import mc.node.NodePlugin;
import mc.node.objects.plugin.reflection.FieldAccessor;
import mc.node.objects.plugin.reflection.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Utility class for handling command and listener registration in a high-performance manner.
 */
@UtilityClass
public class Registry {

    // Cached reference to the Bukkit CommandMap to avoid reflection overhead.
    private CommandMap commandMap;

    static {
        try {
            initializeBukkitMappings();
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to initialize Bukkit field mappings", e);
        }
    }

    /**
     * Initializes the Bukkit field mappings using reflection.
     *
     * @throws ClassNotFoundException if a required class is not found.
     * @throws NoSuchFieldException   if a required field is missing.
     * @throws IllegalAccessException if access to a field is denied.
     */
    private static void initializeBukkitMappings() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // Using reflection to access the SimpleCommandMap and the 'knownCommands' field.
        final Class<?> craftServerClass = Bukkit.getServer().getClass();
        FieldAccessor<SimpleCommandMap> fieldAccessor = Reflections.getField(craftServerClass, SimpleCommandMap.class);

        commandMap = fieldAccessor.get(Bukkit.getServer());
        if (commandMap != null) {
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
        } else {
            throw new IllegalStateException("Failed to retrieve CommandMap.");
        }
    }

    /**
     * Registers a single command in the CommandMap if it is not already registered.
     *
     * @param commandName the name of the command to register.
     * @param command     the Command object to register.
     */
    public void registerCommand(String commandName, Command command) {
        if (commandMap.getCommand(commandName) != null && commandMap.getCommand(commandName).isRegistered()) {
            Bukkit.getConsoleSender().sendMessage("Command " + commandName + " is already registered!");
            return;
        }

        commandMap.register(commandName, command);
        Bukkit.getConsoleSender().sendMessage("Registered command: " + commandName);
    }

    /**
     * Registers a single listener with the plugin.
     *
     * @param listener the listener to register.
     * @param plugin   the plugin instance to associate the listener with.
     */
    public void registerListener(Listener listener, NodePlugin plugin) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        Bukkit.getConsoleSender().sendMessage("Registered listener: " + listener.getClass().getSimpleName());
    }

    /**
     * Registers multiple listeners with the plugin efficiently.
     *
     * @param plugin    the plugin instance to associate the listeners with.
     * @param listeners an array of listeners to register.
     */
    public void registerListeners(NodePlugin plugin, Listener... listeners) {
        // Using Arrays.stream for potential further optimizations.
        Arrays.stream(listeners).forEach(listener -> registerListener(listener, plugin));
    }
}
