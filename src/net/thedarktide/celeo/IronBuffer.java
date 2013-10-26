package net.thedarktide.celeo;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author Celeo
 */
public class IronBuffer implements Listener
{

	private final DarkDiamondCore plugin;

	public IronBuffer(DarkDiamondCore instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public static void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		ItemStack i = player.getInventory().getItemInHand();
		Block block = event.getBlock();
		if (isRepairable(block) && isIronTool(i) && new Random().nextInt(3) == 0 && i.getDurability() > 0)
			i.setDurability((short) (i.getDurability() - 1));
	}

	public static boolean isRepairable(Block block)
	{
		Material material = block.getType();
		switch (material)
		{
		case TORCH:
		case GRASS:
		case SIGN:
		case SAPLING:
		case PAINTING:
		case RED_MUSHROOM:
		case BROWN_MUSHROOM:
		case YELLOW_FLOWER:
		case BED:
		case GLASS:
		case REDSTONE:
		case REDSTONE_TORCH_ON:
		case REDSTONE_TORCH_OFF:
		case REDSTONE_WIRE:
			return false;
		default:
			return true;
		}
	}

	public static boolean isIronTool(ItemStack itemStack)
	{
		Material material = itemStack.getType();
		switch (material)
		{
		case IRON_AXE:
		case IRON_HOE:
		case IRON_PICKAXE:
		case IRON_SPADE:
		case IRON_SWORD:
			return true;
		default:
			return false;
		}
	}

	@SuppressWarnings("incomplete-switch")
	public static boolean isIronArmor(ItemStack itemStack)
	{
		Material material = itemStack.getType();
		switch (material)
		{
		case IRON_BOOTS:
		case IRON_CHESTPLATE:
		case IRON_HELMET:
		case IRON_LEGGINGS:
			return true;
		}
		return false;
	}

}