package mc.node.objects.screen.actionbar;

import org.bukkit.ChatColor;

public final class LegacyActionBarBuilder extends ActionBarBuilder<String, LegacyActionBarBuilder> {
    LegacyActionBarBuilder(String display) {
        super(display);
    }

    protected String toString(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}