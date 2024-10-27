package github.snugbrick.miracleblock.entity.cmmand;

import github.snugbrick.miracleblock.items.MainItemStack;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.List;

@Deprecated
public class MiracleEntityGen implements TabExecutor {
    //Bukkit World to NMS World
    //org.bukkit.World bukkitWorld = nmsWorld.getWorld();
    // NMS World to Bukkit World
    //net.minecraft.server.v1_16_R3.World nmsWorld = ((CraftWorld) bukkitWorld).getHandle();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("miracle-entity-gen") && sender instanceof Player) {
            WorldServer world = ((CraftWorld) ((Player) sender).getWorld()).getHandle();

            switch (args[0]) {
                case "Second_Binary_Star":
                    //SecondBinaryStar.Companion.spawnNPC("Second_Binary_Star", ((Player) sender).getLocation(),);
                    //MiracleEntitySlime entityGen = new MiracleEntitySlime(((Player) sender).getLocation());

                    //entityGen.setPosition(((Player) sender).getLocation().getX(),
                    //((Player) sender).getLocation().getY(), ((Player) sender).getLocation().getZ());

                    //world.addEntity(new MiracleEntitySlime(((Player) sender).getPlayer().getLocation()), CreatureSpawnEvent.SpawnReason.CUSTOM);
                    break;
                case "query":
                    sender.sendMessage(MainItemStack.getAllMiracleBlockItemStack().toString());
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
