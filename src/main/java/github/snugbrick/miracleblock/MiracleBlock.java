package github.snugbrick.miracleblock;

import github.snugbrick.miracleblock.Listener.NewPlayerJoinLis;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiracleBlock extends JavaPlugin {
    private static Plugin MiracleBlock;

    @Override
    public void onEnable() {
        MiracleBlock = this;

        getLogger().info("MiracleBlock已经加载");

        getServer().getPluginManager().registerEvents(new NewPlayerJoinLis(), this);
    }

    @Override
    public void onDisable() {
    }

    public Plugin getMiracleBlock() {
        return MiracleBlock;
    }
}
