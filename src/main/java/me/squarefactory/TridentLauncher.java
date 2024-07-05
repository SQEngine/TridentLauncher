package me.squarefactory;

import me.squarefactory.commands.Reload;
import me.squarefactory.listeners.PlayerListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class TridentLauncher extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        getCommand("reload").setExecutor(new Reload());

        Config.getInstance().loadConfig();
    }

    public static TridentLauncher getInstance() {
        return getPlugin(TridentLauncher.class);
    }
}
