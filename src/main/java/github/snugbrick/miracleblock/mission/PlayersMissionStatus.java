package github.snugbrick.miracleblock.mission;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.28 20:01
 */
public class PlayersMissionStatus extends Mission {
    private MissionStatus missionStatus;

    @Override
    public void setMissionType(MissionType missionType) {

    }

    @Override
    public void setMissionStatus(MissionStatus missionStatus) {
        this.missionStatus = missionStatus;
    }

    @Override
    public MissionType getMissionType() {
        return null;
    }

    @Override
    public MissionStatus getMissionStatus() {
        return missionStatus;
    }
}
