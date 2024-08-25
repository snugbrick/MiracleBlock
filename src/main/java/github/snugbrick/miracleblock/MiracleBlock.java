package github.snugbrick.miracleblock;

import github.snugbrick.miracleblock.Listener.MissionGetter;
import github.snugbrick.miracleblock.Listener.NewPlayerRoomGenLis;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MiracleBlock extends JavaPlugin {
    //测试
    public static MiracleBlock instance;
    private final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        instance = this;
        reloadConfig();

        getLogger().info("MiracleBlock已经加载");

        getServer().getPluginManager().registerEvents(new NewPlayerRoomGenLis(), this);
        getServer().getPluginManager().registerEvents(new MissionGetter(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("MiracleBlock已经卸载");
    }

    public Plugin getInstance() {
        return instance;
    }

    public FileConfiguration getMyConfig() {
        return config;
    }
}
