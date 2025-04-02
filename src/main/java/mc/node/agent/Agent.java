package mc.node.agent;

import lombok.Setter;
import mc.node.objects.chat.LegacyMessageBuilder;
import mc.node.objects.screen.actionbar.LegacyActionBarBuilder;
import mc.node.objects.screen.title.LegacyTitleBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public record Agent(UUID uniqueId, String username, @Setter Player player, int coins) {

    public Agent(UUID uniqueId, String username) {
        this(uniqueId, username, Bukkit.getPlayer(uniqueId), 0);
    }

    public boolean isOnline() {
        return getPlayer().map(Player::isOnline).orElse(false);
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    public void prepare() {
        getPlayer().ifPresent(player -> {
            player.setHealth(20.0D);
            player.setFoodLevel(20);
            player.setFireTicks(0);
            LegacyTitleBuilder.legacy("&a&lWelcome", "&b&lMC-Node")
                    .setFadeIn(20).setStay(60).setFadeOut(20).build(player);
            LegacyActionBarBuilder.legacy("&eYour health is: %health%")
                    .addPlaceholder("%health%", String.valueOf(player.getHealth()))
                    .build(player);
        });

        textAgent(LegacyMessageBuilder.legacy("&aRunning example")
                .setHoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent("This is a hover message!"))
                .setClickAction(ClickEvent.Action.RUN_COMMAND, "/example")
        );
    }

    public void textAgent(LegacyMessageBuilder messageBuilder) {
        getPlayer().ifPresent(player -> player.spigot().sendMessage(messageBuilder.build()));
    }

    @Override
    public String toString() {
        return "Agent{" +
                "uniqueId=" + uniqueId +
                ", username='" + username + '\'' +
                ", online=" + isOnline() +
                '}';
    }
}
