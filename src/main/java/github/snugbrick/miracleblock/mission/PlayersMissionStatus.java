package github.snugbrick.miracleblock.mission;

import github.snugbrick.miracleblock.SQLMethods;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

/**
 * 玩家的任务状态
 */
public class PlayersMissionStatus {
    public static void setPlayerMissionStatus(Player player, String missionName, MissionStatus missionStatus) throws SQLException {
        List<String> finishedMission = SQLMethods.QUERY.runTasks("mission_status",
                "finished_mission", "finished_mission", missionName, "player", player.getName());
        List<String> collected_mission = SQLMethods.QUERY.runTasks("mission_status",
                "collected_mission", "collected_mission", missionName, "player", player.getName());

        if (missionStatus.equals(MissionStatus.COLLECTED) && finishedMission != null) {

            if (finishedMission.contains(missionName)) {
                SQLMethods.DELETE.runTasks("mission_status",
                        "player", player.getName(),
                        "finished_mission", missionName);
            }
            if (!collected_mission.contains(missionName)) {
                SQLMethods.INSERT.runTasks("mission_status",
                        "player", player.getName(),
                        "uuid", player.getUniqueId().toString(),
                        "collected_mission", missionName);
            }

        } else if (missionStatus.equals(MissionStatus.COMPLETED) && !finishedMission.contains(missionName)) {

            SQLMethods.INSERT.runTasks("mission_status",
                    "player", player.getName(),
                    "uuid", player.getUniqueId().toString(),
                    "finished_mission", missionName);

        } else if (missionStatus.equals(MissionStatus.UNDONE)) {
            if (missionName.equals(finishedMission.get(0))) {
                SQLMethods.DELETE.runTasks("mission_status", "finished_mission", missionName);

            } else if (missionName.equals(collected_mission.get(0))) {
                SQLMethods.DELETE.runTasks("mission_status", "collected_mission", missionName);
            }
        }
    }

    public static List<String> getPlayerFinishedMission(Player player) throws SQLException {
        return SQLMethods.QUERY.runTasks("mission_status", "finished_mission", "player", player.getName());
    }

    public static List<String> getPlayerCollectedMission(Player player) throws SQLException {
        return SQLMethods.QUERY.runTasks("mission_status", "collected_mission", "player", player.getName());
    }

    public static MissionStatus getPlayerMissionStatus(Player player, String missionName) throws SQLException {
        List<String> checkFinished =
                SQLMethods.QUERY.runTasks("mission_status", "finished_mission", "player", player.getName());
        List<String> checkCollect =
                SQLMethods.QUERY.runTasks("mission_status", "collected_mission", "player", player.getName());

        if (checkFinished != null && checkFinished.contains(missionName)) {
            return MissionStatus.COMPLETED;
        } else if (checkCollect != null && checkCollect.contains(missionName)) {
            return MissionStatus.COLLECTED;
        }

        return MissionStatus.UNDONE;
    }

    public static boolean isUNDONE(Player player, String missionName) throws SQLException {
        MissionStatus missionStatus = getPlayerMissionStatus(player, missionName);
        return MissionStatus.UNDONE.equals(missionStatus);
    }

    public static boolean isCOMPLETED(Player player, String missionName) throws SQLException {
        MissionStatus missionStatus = getPlayerMissionStatus(player, missionName);
        return MissionStatus.COMPLETED.equals(missionStatus);
    }

    public static boolean isCOLLECTED(Player player, String missionName) throws SQLException {
        MissionStatus missionStatus = getPlayerMissionStatus(player, missionName);
        return MissionStatus.COLLECTED.equals(missionStatus);
    }
}
