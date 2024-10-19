package github.snugbrick.miracleblock.items.skill;

import net.minecraft.world.entity.Entity;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 雷击技能
 */
public class LightStrike implements _Ability {
    private static final Random random = new Random();
    private final World world;
    private final Location startLoc, targetLoc;
    private final List<List<Double>> locationList1 = new ArrayList<>();

    /**
     * 生成雷击
     *
     * @param world  世界
     * @param x      玩家坐标x
     * @param y      玩家坐标y
     * @param z      玩家坐标z
     * @param entity 目标生物
     */
    public LightStrike(World world, double x, double y, double z, Entity entity) {
        this(world, new Location(world, x, y, z), new Location(world, entity.getX(), entity.getEyeY(), entity.getZ()));
    }

    /**
     * @param world     指定世界
     * @param startLoc  起始坐标
     * @param targetLoc 目标坐标
     */
    public LightStrike(World world, Location startLoc, Location targetLoc) {
        this.world = world;
        this.startLoc = startLoc;
        this.targetLoc = targetLoc;
    }

    @Override
    public void runTasks() {
        List<Double> locationStart = new ArrayList<>();
        List<Double> locationEnd = new ArrayList<>();

        locationStart.add(startLoc.getX());
        locationStart.add(startLoc.getY());
        locationStart.add(startLoc.getZ());
        locationEnd.add((targetLoc.getX()));
        locationEnd.add((targetLoc.getY()));
        locationEnd.add((targetLoc.getZ()));

        locationList1.add(locationStart);
        locationList1.add(locationEnd);

        refineLocationList(locationList1);
    }

    @Override
    public void genParticle() {
        for (List<Double> locationGet : locationList1) {
            world.spawnParticle(Particle.END_ROD, new Location(world, locationGet.get(0), locationGet.get(1), locationGet.get(2)), 0, 0, 0, 0, 0);
        }
    }

    @Override
    public void playSound() {

    }

    private static void refineLocationList(List<List<Double>> locationList) {
        boolean refined;
        do {
            refined = false;
            for (int i = 0; i < locationList.size() - 1; i++) {
                List<Double> point1 = locationList.get(i);
                List<Double> point2 = locationList.get(i + 1);
                double distance = calculateDistance(point1, point2);
                if (distance > 0.2) {
                    List<Double> midpoint = calculateMidpoint(point1, point2);
                    addRandomOffset(midpoint, distance);
                    locationList.add(i + 1, midpoint);
                    refined = true;
                }
            }
        } while (refined);
    }

    private static double calculateDistance(List<Double> point1, List<Double> point2) {
        return Math.sqrt(Math.pow(point2.get(0) - point1.get(0), 2) +
                Math.pow(point2.get(1) - point1.get(1), 2) +
                Math.pow(point2.get(2) - point1.get(2), 2));
    }

    private static List<Double> calculateMidpoint(List<Double> point1, List<Double> point2) {
        List<Double> midpoint = new ArrayList<>();
        midpoint.add((point1.get(0) + point2.get(0)) / 2);
        midpoint.add((point1.get(1) + point2.get(1)) / 2);
        midpoint.add((point1.get(2) + point2.get(2)) / 2);
        return midpoint;
    }

    private static void addRandomOffset(List<Double> midpoint, double distance) {
        double offset = distance / 8;
        midpoint.set(0, midpoint.get(0) + (random.nextDouble() - 0.5) * offset * 2);
        midpoint.set(1, midpoint.get(1) + (random.nextDouble() - 0.5) * offset * 2);
        midpoint.set(2, midpoint.get(2) + (random.nextDouble() - 0.5) * offset * 2);
    }
}
