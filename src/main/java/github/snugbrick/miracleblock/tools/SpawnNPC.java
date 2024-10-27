package github.snugbrick.miracleblock.tools;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class SpawnNPC {
    public static void spawnNPC(Location location, String name, String skinName, Boolean isProtected) {
        spawnNPC(location, name, skinName, null, isProtected);
    }

    public static void spawnNPC(Location location, String name, String skinName, Trait trait, Boolean isProtected) {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC npc = registry.createNPC(EntityType.PLAYER, name);
        npc.getOrAddTrait(SkinTrait.class).setSkinName(skinName, false);
        if (trait != null) {
            npc.addTrait(trait);
        }
        npc.setProtected(isProtected);
        npc.spawn(location);
    }
}
