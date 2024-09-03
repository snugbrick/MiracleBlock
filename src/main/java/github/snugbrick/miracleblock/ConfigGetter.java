package github.snugbrick.miracleblock;


import github.snugbrick.miracleblock.tools.LoadLangFiles;

import java.util.List;

@Deprecated
public class ConfigGetter {
    public static List<String> missionName = new LoadLangFiles().getMessageList("MissionName");
}
