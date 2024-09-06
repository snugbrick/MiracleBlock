package github.snugbrick.miracleblock.mission.msg;

import github.snugbrick.miracleblock.tools.TasksLater;
import org.bukkit.entity.Player;

/**
 * 剧情交流
 */
public class MissionMsg {
    public static void mission1Msg(Player player) {
        TasksLater.sendMsgLater(player, "这...这里是哪...", 12);
        TasksLater.sendMsgLater(player, "世界...被水覆盖了吗...？", 14);
        TasksLater.sendMsgLater(player, "水里好像有着大量不明物质...", 17);
        TasksLater.sendMsgLater(player, "...", 18);
        TasksLater.sendMsgLater(player, "不管了，先上岸看看吧", 19);
        TasksLater.sendMsgLater(player, "那边好像有一个人注意到了我", 21);
        TasksLater.sendMission(player, "新任务：和吉米交谈", 21);
    }
}
