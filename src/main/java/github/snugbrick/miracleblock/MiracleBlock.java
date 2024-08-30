package github.snugbrick.miracleblock;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import github.snugbrick.miracleblock.command.SqlCommands;
import github.snugbrick.miracleblock.command.ToTemplateWorld;
import github.snugbrick.miracleblock.island.WorldGen;
import github.snugbrick.miracleblock.island.listener.IslandDistributionLis;
import github.snugbrick.miracleblock.mission.listener.MissionHandler;
import github.snugbrick.miracleblock.mission.missionInven.MissionIconRegister;
import github.snugbrick.miracleblock.tools.LoadLangFiles;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public class MiracleBlock extends JavaPlugin {
    private static JavaPlugin instance;
    private final FileConfiguration config = getConfig();
    private static SQLManager sqlManager;

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

        getLogger().info("正在注册任务栏图标");
        new MissionIconRegister().registerIcon();

        getLogger().info("正在加载语言文件");
        new LoadLangFiles().loadMessagesFile();
        getLogger().info(new LoadLangFiles().getMessage("welcome"));

        initSql();
        getLogger().info("Sql已经加载");

        getLogger().info("every thing is done,welcome to use MiracleBlock!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MiracleBlock已经卸载");
        //ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        if (Objects.nonNull(sqlManager)) {
            EasySQL.shutdownManager(sqlManager);
        }
    }

    public FileConfiguration getMyConfig() {
        return config;
    }

    private void registerLis() {
        getServer().getPluginManager().registerEvents(new IslandDistributionLis(), this);
        getServer().getPluginManager().registerEvents(new MissionHandler(), this);
    }

    private void registerCommand() {
        getCommand("totemplateworld").setExecutor(new ToTemplateWorld());
        getCommand("db-easy-sql").setExecutor(new SqlCommands());

    }

    private void initSql() {
        if (StringUtils.isBlank(SqlSettingGetter.driver) || StringUtils.isBlank(SqlSettingGetter.url)
                || StringUtils.isBlank(SqlSettingGetter.username) || StringUtils.isBlank(SqlSettingGetter.password)) {
            Bukkit.getLogger().info("缺少必备启动配置，将关闭插件!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            sqlManager = EasySQL.createManager(SqlSettingGetter.driver, SqlSettingGetter.url, SqlSettingGetter.username, SqlSettingGetter.password);

            if (sqlManager == null) {
                throw new SQLException("创建SQLManager失败!");
            }

            if (!sqlManager.getConnection().isValid(5)) {
                this.getLogger().info("是数据库超时了！");
            }
        } catch (SQLException e) {
            this.getLogger().info("是数据库寄了！错误: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public static SQLManager getSqlManager() {
        return sqlManager;
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
