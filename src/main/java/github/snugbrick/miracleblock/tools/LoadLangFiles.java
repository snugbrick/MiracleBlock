package github.snugbrick.miracleblock.tools;

import github.snugbrick.miracleblock.MiracleBlock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 23:16
 */
public class LoadLangFiles {
    private static FileConfiguration messagesConfig;

    public void loadMessagesFile() {
        File messagesFile = new File(MiracleBlock.getInstance().getDataFolder(), "zh_cn.yml");

        if (!messagesFile.exists()) {
            MiracleBlock.getInstance().saveResource("zh_cn.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessage(String key) {
        return messagesConfig.getString(key);
    }

    public List<String> getMessageList(String key) {
        return messagesConfig.getStringList(key);
    }

}
