package github.snugbrick.miracleblock;

import github.snugbrick.miracleblock.Listener.MissionGetter;
import github.snugbrick.miracleblock.Listener.NewPlayerRoomGenLis;
import github.snugbrick.miracleblock.command.ToTemplateWorld;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class MiracleBlock extends JavaPlugin {
    public static MiracleBlock instance;
    private final FileConfiguration config = getConfig();


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();

        getLogger().info("MiracleBlock已经加载");
        registerLis();
        registerCommand();

        getLogger().info("正在创建template_world");
        new TemplateWorldGen().createTempRoom();
    }

    public FileConfiguration getMyConfig() {
        return config;
    }

    private void registerLis() {
        getServer().getPluginManager().registerEvents(new NewPlayerRoomGenLis(), this);
        getServer().getPluginManager().registerEvents(new MissionGetter(), this);
    }

    private void registerCommand() {
        getCommand("totemplateworld").setExecutor(new ToTemplateWorld());
    }


    @Override
    public void onDisable() {
        getLogger().info("MiracleBlock已经卸载");
    }
}
