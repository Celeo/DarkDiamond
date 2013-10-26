package net.thedarktide.celeo;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * 
 * @author Celeo
 */
public class DiamondRemover implements Listener
{

	private final DarkDiamondCore plugin;

	public DiamondRemover(DarkDiamondCore instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		if (block != null && block.getType().equals(Material.DIAMOND_ORE))
			block.setType(Material.EMERALD_ORE);
	}

}