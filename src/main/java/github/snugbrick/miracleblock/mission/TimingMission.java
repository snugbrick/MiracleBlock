package github.snugbrick.miracleblock.mission;

public interface TimingMission {
    default void setTiming(int hour) {
    }

    default int getTiming() {
        return 0;
    }
}
