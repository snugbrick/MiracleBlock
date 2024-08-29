package github.snugbrick.miracleblock;

import github.snugbrick.miracleblock.command.ToTemplateWorld;
import github.snugbrick.miracleblock.listener.MissionHandler;
import github.snugbrick.miracleblock.listener.NewPlayerRoomGenLis;
import github.snugbrick.miracleblock.mission.missionInven.MissionIconRegister;
import github.snugbrick.miracleblock.tools.LoadLangFiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class MiracleBlock extends JavaPlugin {
    private static JavaPlugin instance;
    private final FileConfiguration config = getConfig();


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();

        getLogger().info("MiracleBlock已经加载");
        registerLis();
        registerCommand();

        getLogger().info("正在加载template_world");
        new WorldGen().createTempRoom();

        getLogger().info("正在加载player_world");
        new WorldGen().createPlayersRoom();

        /*
        getLogger().info("正在加载已有玩家世界");
        OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer allPlayer : allPlayers) {
            UUID uuid = allPlayer.getUniqueId();
            WorldCreator playerRooms = new WorldCreator("_the_world_of_" + uuid);
            getLogger().info("正在加载" + allPlayer.getName() + "的世界");
            playerRooms.createWorld();
        }
        */

        getLogger().info("正在注册任务栏图标");
        new MissionIconRegister().registerIcon();

        getLogger().info("正在加载语言文件");
        new LoadLangFiles().loadMessagesFile();
        getLogger().info(new LoadLangFiles().getMessage("welcome"));
    }

    public FileConfiguration getMyConfig() {
        return config;
    }

    private void registerLis() {
        getServer().getPluginManager().registerEvents(new NewPlayerRoomGenLis(), this);
        getServer().getPluginManager().registerEvents(new MissionHandler(), this);
    }

    private void registerCommand() {
        getCommand("totemplateworld").setExecutor(new ToTemplateWorld());
    }

    @Override
    public void onDisable() {
        getLogger().info("MiracleBlock已经卸载");
        //ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
