package github.snugbrick.miracleblock.mission;

import github.snugbrick.miracleblock.SQLMethods;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

/**
 * 玩家的任务状态
 */
public class PlayersMissionStatus {
    private Player player;
    private MissionStatus missionStatus;

    public void setPlayerMissionStatus(Player player, String missionName, MissionStatus missionStatus) {
    }

    public List<String> getPlayerFinishedMission(Player player) throws SQLException {
        return SQLMethods.QUERY.runTasks("mission_status", "finished_mission", "player", player.getName());
    }

    public MissionStatus getPlayerMissionStatus(Player player, String missionName) throws SQLException {
        List<String> checkFinished =
                SQLMethods.QUERY.runTasks("mission_status", "finished_mission", "player", player.getName());
        List<String> checkCollect =
                SQLMethods.QUERY.runTasks("mission_status", "collected_mission", "player", player.getName());

        if (checkFinished.iterator().hasNext() && checkCollect.iterator().hasNext()) {
            String aimFinishedMission = checkFinished.iterator().next();
            String aimCollectedMission = checkCollect.iterator().next();

            if (aimCollectedMission.equals(missionName)) return MissionStatus.COLLECTED;
            else if (aimFinishedMission.equals(missionName)) return MissionStatus.COMPLETED;
            else return MissionStatus.UNDONE;
        }

        return null;
    }
}
