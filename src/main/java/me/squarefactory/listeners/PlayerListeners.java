package me.squarefactory.listeners;

import me.squarefactory.Config;
import me.squarefactory.TridentLauncher;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListeners implements Listener {

    private final TridentLauncher plugin;
    private final Config config = Config.getInstance();

    public PlayerListeners(TridentLauncher plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTridentLaunch(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!event.getAction().isRightClick()) {
            return;
        }

        if (!hasTrident(player)) {
            return;
        }

        if (!shouldLaunch(player)) {
            return;
        }

        launchPlayer(player);
    }

    private void launchPlayer(Player player) {
        setLaunchVelocity(player);

        if (config.getParticleEnabled()) {
            setLaunchParticles(player);
        }

        if (config.getSoundEnabled()) {
            setLaunchSound(player);
        }
    }

    private boolean shouldLaunch(Player player) {
        boolean mainHandEnabled = config.getMainHandToggle();
        boolean offHandEnabled = config.getOffHandToggle();
        boolean needsRiptide = config.getRiptideToggle();
        boolean needsPermission = config.getPermissionToggle();

        if (needsPermission && !(player.isOp() || player.hasPermission("trident.launch"))) {
            return false;
        }

        if (needsRiptide && !hasRiptide(player)) {
            return false;
        }

        if (mainHandEnabled && !offHandEnabled) {
            return player.getInventory().getItemInMainHand().getType() == Material.TRIDENT;
        }

        if (!mainHandEnabled && offHandEnabled) {
            return player.getInventory().getItemInOffHand().getType() == Material.TRIDENT;
        }

        return mainHandEnabled || offHandEnabled;
    }

    private void setLaunchVelocity(Player player) {
        double launchSpeed = config.getLaunchSpeed();

        player.setVelocity(player.getEyeLocation().getDirection().multiply(launchSpeed));
    }

    private void setLaunchParticles(Player player) {
        Particle particle = config.getParticleType();
        int particleAmount = config.getParticleAmount();
        double particleSpeed = config.getParticleSpeed();

        new BukkitRunnable() {
            int particleCount = 0;

            @Override
            public void run() {
                if (particleCount < 20) {
                    Location playerLocation = player.getLocation().add(0, 1, 0);
                    playerLocation.getWorld().spawnParticle(particle, playerLocation, particleAmount, 0,0,0,particleSpeed);
                    particleCount++;
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private void setLaunchSound(Player player) {
        Sound sound = config.getSoundType();
        float volume = config.getSoundVolume();
        float pitch = config.getSoundPitch();

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    private boolean hasTrident(Player player) {
        boolean mainHandEnabled = config.getMainHandToggle();
        boolean offHandEnabled = config.getOffHandToggle();

        if (mainHandEnabled && !offHandEnabled) {
            return player.getInventory().getItemInMainHand().getType() == Material.TRIDENT;
        }

        if (!mainHandEnabled && offHandEnabled) {
            return player.getInventory().getItemInOffHand().getType() == Material.TRIDENT;
        }

        return player.getInventory().getItemInMainHand().getType() == Material.TRIDENT
                || player.getInventory().getItemInOffHand().getType() == Material.TRIDENT;
    }

    private boolean hasRiptide(Player player) {
        boolean mainHandEnabled = config.getMainHandToggle();
        boolean offHandEnabled = config.getOffHandToggle();

        if (mainHandEnabled && !offHandEnabled) {
            return player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.RIPTIDE);
        }

        if (!mainHandEnabled && offHandEnabled) {
            return player.getInventory().getItemInOffHand().containsEnchantment(Enchantment.RIPTIDE);
        }

        return player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.RIPTIDE)
                || player.getInventory().getItemInOffHand().containsEnchantment(Enchantment.RIPTIDE);
    }
}
