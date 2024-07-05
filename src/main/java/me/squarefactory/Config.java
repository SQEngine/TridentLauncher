package me.squarefactory;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;

public class Config {

    private final static Config instance = new Config();
    private File file;
    private YamlConfiguration config;
    private boolean particleEnabled;
    private Particle particleType;
    private int particleAmount;
    private double particleSpeed;
    private boolean soundEnabled;
    private Sound soundType;
    private float soundVolume;
    private float soundPitch;
    private boolean mainHandToggle;
    private boolean offHandToggle;
    private double launchSpeed;
    private boolean riptideToggle;
    private boolean permissionToggle;
    private String pluginPrefix;
    private String reloadMessage;
    private String noPermissionMessage;

    private Config() {
    }

    public void loadConfig() {
        file = new File(TridentLauncher.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()) {
            TridentLauncher.getInstance().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        particleEnabled = config.getBoolean("particle.enabled", true);
        particleType = Particle.valueOf(config.getString("particle.type", "TOTEM"));
        particleAmount = config.getInt("particle.amount", 2);
        particleSpeed = config.getDouble("particle.speed", 0.1);

        soundEnabled = config.getBoolean("sound.enabled", true);
        soundType = Sound.valueOf(config.getString("sound.type", "ITEM_TRIDENT_RIPTIDE_3"));
        soundVolume = Float.parseFloat(Objects.requireNonNull(config.getString("sound.volume", "1.0")));
        soundPitch = Float.parseFloat(Objects.requireNonNull(config.getString("sound.pitch", "1.0")));

        mainHandToggle = config.getBoolean("toggle-hand.main-hand", true);
        offHandToggle = config.getBoolean("toggle-hand.off-hand", true);

        pluginPrefix = config.getString("message.prefix", "&8[&3Trident Launcher&8] ");
        reloadMessage = String.valueOf(config.getString("message.reload"));
        noPermissionMessage = config.getString("message.no-permission");

        launchSpeed = Double.parseDouble(Objects.requireNonNull(config.getString("trident.launch-speed", "2.0")));
        riptideToggle = config.getBoolean("trident.need-riptide", true);
        permissionToggle = config.getBoolean("trident.need-permission", false);
    }

    public void reloadConfig() {
        file = new File(TridentLauncher.getInstance().getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(file);

        loadConfig();
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConfig(String path, Object value) {
        config.set(path, value);

        saveConfig();
    }

    public boolean getParticleEnabled() {
        return particleEnabled;
    }
    public void setParticleEnabled(boolean particleEnabled) {
        this.particleEnabled = particleEnabled;

        setConfig("particle.enabled", particleEnabled);
    }

    public Particle getParticleType() {
        return particleType;
    }
    public void setParticleType(Particle particleType) {
        this.particleType = particleType;

        setConfig("particle.type", particleType);
    }

    public int getParticleAmount() {
        return particleAmount;
    }
    public void setParticleAmount(int particleAmount) {
        this.particleAmount = particleAmount;

        setConfig("particle.amount", particleAmount);
    }

    public double getParticleSpeed() {
        return particleSpeed;
    }
    public void setParticleSpeed(double particleSpeed) {
        this.particleSpeed = particleSpeed;

        setConfig("particle.speed", particleSpeed);
    }

    public boolean getSoundEnabled() {
        return soundEnabled;
    }
    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;

        setConfig("sound.enabled", soundEnabled);
    }

    public Sound getSoundType() {
        return soundType;
    }
    public void setSoundType(Sound soundType) {
        this.soundType = soundType;

        setConfig("sound.type", soundType);
    }

    public float getSoundVolume() {
        return soundVolume;
    }
    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;

        setConfig("sound.volume", soundVolume);
    }

    public float getSoundPitch() {
        return soundPitch;
    }
    public void setSoundPitch(float soundPitch) {
        this.soundPitch = soundPitch;

        setConfig("sound.pitch", soundPitch);
    }

    public boolean getMainHandToggle() {
        return mainHandToggle;
    }
    public void setMainHandToggle(boolean mainHandToggle) {
        this.mainHandToggle = mainHandToggle;

        setConfig("toggle-hand.main-hand", mainHandToggle);
    }

    public boolean getOffHandToggle() {
        return offHandToggle;
    }
    public void setOffHandToggle(boolean offHandToggle) {
        this.offHandToggle = offHandToggle;

        setConfig("toggle-hand.off-hand", offHandToggle);
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }
    public void setPluginPrefix(String pluginPrefix) {
        this.pluginPrefix = pluginPrefix;

        setConfig("message.prefix", pluginPrefix);
    }

    public String getReloadMessage() {
        return reloadMessage;
    }
    public void setReloadMessage(String reloadMessage) {
        this.reloadMessage = reloadMessage;

        setConfig("message.reload", reloadMessage);
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }
    public void setNoPermissionMessage(String noPermissionMessage) {
        this.noPermissionMessage = noPermissionMessage;

        setConfig("message.no-permission", noPermissionMessage);
    }

    public double getLaunchSpeed() {
        return launchSpeed;
    }
    public void setLaunchSpeed(double launchSpeed) {
        this.launchSpeed = launchSpeed;

        setConfig("trident.launch-speed", launchSpeed);
    }

    public boolean getRiptideToggle() {
        return riptideToggle;
    }
    public void setRiptideToggle(boolean riptideToggle) {
        this.riptideToggle = riptideToggle;

        setConfig("trident.need-riptide", riptideToggle);
    }

    public boolean getPermissionToggle() {
        return permissionToggle;
    }
    public void setPermissionToggle(boolean permissionToggle) {
        this.permissionToggle = permissionToggle;

        setConfig("trident.need-permission", permissionToggle);
    }

    public static Config getInstance() {
        return instance;
    }
}
