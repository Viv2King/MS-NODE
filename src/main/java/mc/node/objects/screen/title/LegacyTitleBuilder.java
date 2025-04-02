package mc.node.objects.screen.title;

import org.bukkit.ChatColor;

public final class LegacyTitleBuilder extends TitleBuilder<String, LegacyTitleBuilder> {
    LegacyTitleBuilder(String title, String subtitle) {
        super(title, subtitle);
    }

    protected String toString(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}