package mc.node.objects.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class to build a chat message with placeholders, clickable actions, and hover events.
 * It supports flexible message construction for integration with Minecraft's chat systems.
 *
 * @param <T> The type of the object used for conversion in toString method.
 * @param <B> The type of the builder subclass (for fluent interface).
 */
public abstract class MessageBuilder<T, B extends MessageBuilder<T, B>> {

    private final String message; // The original message template
    private final Map<String, String> placeholders = new HashMap<>(); // Map for placeholders and their replacements
    private ClickEvent clickAction = null;  // Store click action for the message (if any)
    private HoverEvent hoverEvent = null;   // Store hover event for the message (if any)

    /**
     * Constructor to initialize the MessageBuilder with a message.
     *
     * @param message The message template.
     */
    protected MessageBuilder(String message) {
        this.message = message;
    }

    /**
     * Abstract method to convert the message object to a string.
     * This method is implemented in the subclass to provide specific message formatting.
     *
     * @param obj The object to convert to a string.
     * @return The string representation of the object.
     */
    protected abstract String toString(T obj);

    /**
     * Adds a placeholder for the message to be replaced later.
     *
     * @param placeholder The placeholder key to be replaced.
     * @param replacement The value that will replace the placeholder.
     * @return The current builder instance (for chaining).
     */
    public B addPlaceholder(String placeholder, String replacement) {
        placeholders.put(placeholder, replacement);
        return (B) this; // Fluent interface: return the builder itself for chaining
    }

    /**
     * Sets a clickable action (e.g., open URL or run command) for the message.
     *
     * @param action The action to be triggered (e.g., OPEN_URL or RUN_COMMAND).
     * @param value  The value associated with the action (e.g., URL or command).
     * @return The current builder instance (for chaining).
     */
    public B setClickAction(ClickEvent.Action action, String value) {
        this.clickAction = new ClickEvent(action, value);
        return (B) this; // Fluent interface: return the builder itself for chaining
    }

    /**
     * Sets a hover event for the message.
     *
     * @param action The hover action (e.g., SHOW_TEXT).
     * @param value  The text or components to show when the message is hovered over.
     * @return The current builder instance (for chaining).
     */
    public B setHoverEvent(HoverEvent.Action action, BaseComponent... value) {
        this.hoverEvent = new HoverEvent(action, value);
        return (B) this; // Fluent interface: return the builder itself for chaining
    }

    /**
     * Builds the final message with placeholders replaced by their values.
     * It applies color formatting and prepares the message for use.
     *
     * @return The formatted message as a string with placeholders replaced.
     */
    public String buildMessage() {
        String builtMessage = this.message;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            builtMessage = builtMessage.replace(entry.getKey(), entry.getValue());
        }
        return applyFormatting(builtMessage);
    }

    /**
     * Builds the final message, either as a TextComponent (with actions) or a plain message string.
     *
     * @return The final TextComponent for integration with Minecraft chat.
     */
    public TextComponent build() {
        TextComponent component = new TextComponent(buildMessage());

        // Apply click action if present
        if (this.clickAction != null) {
            component.setClickEvent(this.clickAction);
        }

        // Apply hover event if present
        if (this.hoverEvent != null) {
            component.setHoverEvent(this.hoverEvent);
        }

        return component;
    }

    /**
     * Applies color formatting to the message, translating '&' to Minecraft color codes.
     *
     * @param message The message to format.
     * @return The formatted message with Minecraft color codes.
     */
    private String applyFormatting(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Factory method to create a LegacyMessageBuilder.
     *
     * @param message The message template.
     * @return A new LegacyMessageBuilder instance.
     */
    public static LegacyMessageBuilder legacy(String message) {
        return new LegacyMessageBuilder(message);
    }
}
