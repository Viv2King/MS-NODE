package mc.node.objects.screen.title;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for creating title and subtitle messages for Minecraft's title screen.
 * Supports custom titles, subtitles, and additional features like timings and click/hover actions.
 */
public abstract class TitleBuilder<T, B extends TitleBuilder<T, B>> {

    private final String title; // The title message
    private final String subtitle; // The subtitle message
    private final Map<String, String> placeholders = new HashMap<>(); // Placeholder replacements
    private int fadeIn = 20; // Default fade-in duration
    private int stay = 60;   // Default stay duration
    private int fadeOut = 20; // Default fade-out duration

    /**
     * Constructor to initialize the TitleMessageBuilder with a title and subtitle.
     *
     * @param title    The title message to display.
     * @param subtitle The subtitle message to display.
     */
    protected TitleBuilder(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
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
     * Sets the fade-in duration for the title screen (in ticks).
     *
     * @param fadeIn The fade-in duration in ticks.
     * @return The current builder instance (for chaining).
     */
    public B setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return (B) this;
    }

    /**
     * Sets the duration the title stays on the screen (in ticks).
     *
     * @param stay The stay duration in ticks.
     * @return The current builder instance (for chaining).
     */
    public B setStay(int stay) {
        this.stay = stay;
        return (B) this;
    }

    /**
     * Sets the fade-out duration for the title screen (in ticks).
     *
     * @param fadeOut The fade-out duration in ticks.
     * @return The current builder instance (for chaining).
     */
    public B setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return (B) this;
    }


    /**
     * Builds the final title message, replacing any placeholders and applying formatting.
     *
     * @return The formatted title message as a string.
     */
    public String buildTitle() {
        String builtTitle = this.title;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            builtTitle = builtTitle.replace(entry.getKey(), entry.getValue());
        }
        return applyFormatting(builtTitle);
    }

    /**
     * Builds the final subtitle message, replacing any placeholders and applying formatting.
     *
     * @return The formatted subtitle message as a string.
     */
    public String buildSubtitle() {
        String builtSubtitle = this.subtitle;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            builtSubtitle = builtSubtitle.replace(entry.getKey(), entry.getValue());
        }
        return applyFormatting(builtSubtitle);
    }

    /**
     * Converts the title and subtitle messages to TextComponents, including actions (if set).
     *
     * @return A map with title and subtitle TextComponents.
     */
    public void build(Player player) {
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + buildTitle() + "\"}");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + buildSubtitle() + "\"}");

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
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
    public static LegacyTitleBuilder legacy(String title, String subtitle) {
        return new LegacyTitleBuilder(title, subtitle);
    }
}