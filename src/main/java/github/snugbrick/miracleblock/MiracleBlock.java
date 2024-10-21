package github.snugbrick.miracleblock;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import github.snugbrick.miracleblock.command.SqlCommands;
import github.snugbrick.miracleblock.command.ToTemplateWorld;
import github.snugbrick.miracleblock.entity.MiracleEntityRegister;
import github.snugbrick.miracleblock.entity.cmmand.MiracleEntityGen;
import github.snugbrick.miracleblock.island.WorldGen;
import github.snugbrick.miracleblock.island.listener.IslandDistributionLis;
import github.snugbrick.miracleblock.items.MainRegister;
import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import github.snugbrick.miracleblock.items.command.GetMiracleItemStack;
import github.snugbrick.miracleblock.items.skill.listener.EnergyGatheringLis;
import github.snugbrick.miracleblock.items.skill.listener.Illusion;
import github.snugbrick.miracleblock.items.skill.listener.IronCurtain;
import github.snugbrick.miracleblock.items.weapon.command.SetInlaidCommand;
import github.snugbrick.miracleblock.items.weapon.listener.attackReachChanger;
import github.snugbrick.miracleblock.mission.listener.MissionHandler;
import github.snugbrick.miracleblock.mission.missionInven.MissionIconRegister;
import github.snugbrick.miracleblock.tools.Debug;
import github.snugbrick.miracleblock.tools.LoadLangFiles;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

/**
 * 目前依赖protocol easysql mysql驱动 Citizens
 */
public class MiracleBlock extends JavaPlugin {
    private static JavaPlugin instance;
    private final FileConfiguration config = getConfig();
    private static SQLManager sqlManager;
    private static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        instance = this;
        new Debug(0, "MiraBlock已进入加载");
        saveDefaultConfig();
        reloadConfig();

        initSql();//初始化数据库
        getLogger().info("Sql已经加载");
        try {
            SQLMethods.TABLE.runTasks("island_distribution", "player", "uuid", "island_serial");
            SQLMethods.TABLE.runTasks("mission_status", "player", "uuid", "finished_mission", "collected_mission");
            getLogger().info("数据库表已加载");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        registerLis();
        registerCommand();

        getLogger().info("正在加载语言文件");
        new LoadLangFiles().loadMessagesFile();

        getLogger().info("正在加载template_world");
        new WorldGen().createTempRoom();

        getLogger().info("正在加载player_world");
        try {
            new WorldGen().createPlayersRoom();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        getLogger().info("正在注册任务栏图标");
        new MissionIconRegister().registerIcon();

        getLogger().info("正在注册物品");
        new MainRegister().mainReg();
        new Debug(0, "已注册" + MiracleBlockItemStack.getAllMiracleBlockItemStack().size() + "个物品");

        try {
            getLogger().info("正在注册生物");
            MiracleEntityRegister.registerMiracleEntity();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            protocolManager = ProtocolLibrary.getProtocolManager();
            getLogger().info("ProtocolLib 已成功初始化!");
        } catch (Exception e) {
            getLogger().severe("无法初始化 ProtocolLib: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }

        getLogger().info("every thing is done,welcome to use MiraBlock!");
        new Debug(0, "  __  __ _           ____  _            _    ");
        new Debug(0, " |  \\/  (_)_ __ __ _| __ )| | ___   ___| | __");
        new Debug(0, " | |\\/| | | '__/ _` |  _ \\| |/ _ \\ / __| |/ /");
        new Debug(0, " | |  | | | | | (_| | |_) | | (_) | (__|   < ");
        new Debug(0, " |_|  |_|_|_|  \\__,_|____/|_|\\___/ \\___|_|\\_\\");
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
        getServer().getPluginManager().registerEvents(new attackReachChanger(), this);
        getServer().getPluginManager().registerEvents(new IronCurtain(), this);
        getServer().getPluginManager().registerEvents(new Illusion(), this);
        getServer().getPluginManager().registerEvents(new EnergyGatheringLis(), this);
    }

    private void registerCommand() {
        getCommand("totemplateworld").setExecutor(new ToTemplateWorld());
        getCommand("db-easy-sql").setExecutor(new SqlCommands());
        getCommand("get-miracle-item").setExecutor(new GetMiracleItemStack());
        getCommand("set-item-inlaid").setExecutor(new SetInlaidCommand());
        getCommand("miracle-entity-gen").setExecutor(new MiracleEntityGen());
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

    public static ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public static SQLManager getSqlManager() {
        return sqlManager;
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
