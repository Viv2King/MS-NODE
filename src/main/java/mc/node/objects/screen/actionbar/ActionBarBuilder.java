package mc.node.objects.screen.actionbar;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for creating title and subtitle messages for Minecraft's title screen.
 * Supports custom titles, subtitles, and additional features like timings and click/hover actions.
 */
public abstract class ActionBarBuilder<T, B extends ActionBarBuilder<T, B>> {

    private final String display; // The title message
    private final Map<String, String> placeholders = new HashMap<>(); // Placeholder replacements

    /**
     * Constructor to initialize the TitleMessageBuilder with a title and subtitle.
     *
     * @param display The message to display.
     */
    protected ActionBarBuilder(String display) {
        this.display = display;
    }

    /**
     * Abstract method to convert the object to a string representation (overridden in subclasses).
     *
     * @param obj The object to convert.
     * @return The string representation of the object.
     */
    protected abstract String toString(T obj);

    /**
     * Adds a placeholder to be replaced in the title or subtitle message.
     *
     * @param placeholder The placeholder to replace.
     * @param replacement The value to replace the placeholder with.
     * @return The current builder instance (for chaining).
     */
    public B addPlaceholder(String placeholder, String replacement) {
        placeholders.put(placeholder, replacement);
        return (B) this; // Fluent interface: return the builder for chaining
    }

    /**
     * Builds the final title message, replacing any placeholders and applying formatting.
     *
     * @return The formatted title message as a string.
     */
    public String buildDisplay() {
        String builtDisplay = this.display;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            builtDisplay = builtDisplay.replace(entry.getKey(), entry.getValue());
        }
        return applyFormatting(builtDisplay);
    }


    /**
     * Converts the title and subtitle messages to TextComponents, including actions (if set).
     *
     * @return A map with title and subtitle TextComponents.
     */
    public void build(Player player) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(buildDisplay()), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * Applies Minecraft color formatting to the message, replacing '&' with color codes.
     *
     * @param message The message to format.
     * @return The formatted message.
     */
    private String applyFormatting(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Factory method to create a LegacyMessageBuilder.
     *
     * @return A new LegacyMessageBuilder instance.
     */
    public static LegacyActionBarBuilder legacy(String display) {
        return new LegacyActionBarBuilder(display);
    }
}