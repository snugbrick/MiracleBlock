package github.snugbrick.miracleblock;


import github.snugbrick.miracleblock.tools.LoadLangFiles;

import java.util.List;

public class ConfigGetter {
    public static String welcome = MiracleBlock.getInstance().getConfig().getString("welcome");
    public static List<String> missionName = new LoadLangFiles().getMessageList("MissionName");
}
