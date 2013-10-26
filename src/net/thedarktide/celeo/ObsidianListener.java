package net.thedarktide.celeo;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

/**
 * Listener object for Obsidian.
 * @author Celeo
 */
public class ObsidianListener implements Listener
{

	private final DarkDiamondCore plugin;

	public ObsidianListener(DarkDiamondCore instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerSwing(PlayerAnimationEvent event)
	{
		Player player = event.getPlayer();
		if (event.getAnimationType() == null || !event.getAnimationType().equals(PlayerAnimationType.ARM_SWING))
			return;
		Block block = player.getTargetBlock(null, 10);
		if (block == null)
			return;
		if (block.getType().equals(Material.OBSIDIAN) && plugin.isBlockRecorded(block))
		{
			if (plugin.worldGuard.getGlobalRegionManager().canBuild(player, block))
				plugin.getBlockRecordFor(block).checkBreak();				
			else
			{
				player.sendMessage("§4You cannot break blocks here.");
				return;
			}
		}
		
	}

	@EventHandler
	public void onBlockDamage(BlockDamageEvent event)
	{
		Block block = event.getBlock();
		if (plugin.isBlockRecorded(block))
			plugin.getBlockRecordFor(block).checkBreak();
		else if (event.getPlayer().getInventory().getItemInHand().getType().equals(Material.IRON_PICKAXE))
			plugin.addRecord(block);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		plugin.removeRecord(event.getBlock());
	}

}