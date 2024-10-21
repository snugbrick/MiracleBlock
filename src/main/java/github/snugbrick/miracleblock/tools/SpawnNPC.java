package github.snugbrick.miracleblock.tools;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.skin.Skin;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class SpawnNPC {
    public static void spawnNPC(Location location, String name, String skinName) {
        spawnNPC(location, name, skinName, null);
    }

    public static void spawnNPC(Location location, String name, String skinName, Skin skin) {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC npc = registry.createNPC(EntityType.PLAYER, name);
        if (skin == null) {
            if (Skin.get(skinName, true).isValid()) {
                Skin.get(skinName, true).apply((SkinnableEntity) npc);
            }
        }
        npc.setProtected(true);
        npc.spawn(location);
    }
}
