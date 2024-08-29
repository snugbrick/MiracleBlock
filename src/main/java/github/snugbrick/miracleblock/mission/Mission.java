package github.snugbrick.miracleblock.mission;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 21:11
 */
public abstract class Mission {

    abstract public void setMissionType(MissionType missionType);

    abstract public void setMissionStatus(MissionStatus missionStatus);

    abstract public MissionType getMissionType();

    abstract public MissionStatus getMissionStatus();

}
