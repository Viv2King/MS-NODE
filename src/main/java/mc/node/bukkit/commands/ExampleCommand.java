package mc.node.bukkit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ExampleCommand extends Command {

    public ExampleCommand(String name, String description, String... aliases) {
        super(name, description, "/help", Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player player) {
            player.sendMessage("Example command running!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "This command can only be executed by players.");
        return true;
    }
}
