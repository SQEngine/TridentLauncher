package me.squarefactory.commands;

import me.squarefactory.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

    private final Config config = Config.getInstance();
    private static final String DEFAULT_RELOAD_MESSAGE = "&aReloaded";
    private static final String DEFAULT_NO_PERMISSION_MESSAGE = "&cYou don't have permission";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (!player.hasPermission("trident.reload") || !player.isOp()) {
                sendMessage(sender, config.getNoPermissionMessage(), DEFAULT_NO_PERMISSION_MESSAGE);
                return true;
            }
        } else if (!(sender instanceof ConsoleCommandSender)) {
            return true;
        }
        Config.getInstance().reloadConfig();
        sendMessage(sender, config.getReloadMessage(), DEFAULT_RELOAD_MESSAGE);

        return true;
    }

    private void sendMessage(CommandSender sender, String configMessage, String defaultMessage) {
        String message = (configMessage == null || configMessage.isEmpty()) ? defaultMessage : configMessage;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getPluginPrefix() + message));
    }

}
