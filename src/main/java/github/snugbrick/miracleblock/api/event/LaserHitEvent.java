package github.snugbrick.miracleblock.api.event;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class LaserHitEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Entity shooter;
    private final Entity target;
    private final double damage;

    public LaserHitEvent(Entity shooter, Entity target, double damage) {
        this.shooter = shooter;
        this.target = target;
        this.damage = damage;
    }

    public Entity getTarget() {
        return target;
    }

    public double getDamage() {
        return damage;
    }

    public Entity getShooter() {
        return shooter;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
