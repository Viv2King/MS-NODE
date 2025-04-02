package mc.node.objects.chat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class LegacyMessageBuilder extends MessageBuilder<String, LegacyMessageBuilder> {
    LegacyMessageBuilder(String message) {
        super(message);
    }

    protected String toString(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}