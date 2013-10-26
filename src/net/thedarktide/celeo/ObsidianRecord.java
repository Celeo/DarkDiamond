package net.thedarktide.celeo;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Record object for keeping track of Obsidian blocks.
 * @author Celeo
 */
public class ObsidianRecord
{

	private final DarkDiamondCore plugin;
	private final Block block;
	private final long breakAt;
	private final long start;
	private long lastHit;
	private final long buffer = 500;

	public ObsidianRecord(DarkDiamondCore instance, Block b, long l) throws Exception
	{
		plugin = instance;
		block = b;
		start = System.currentTimeMillis();
		breakAt = start + (l * 1000);
		lastHit = start;
		if (!b.getType().equals(Material.OBSIDIAN))
			throw new Exception("Block passed to the object instantiator not of the type Obsidian!");
	}

	public void checkBreak()
	{
		long now = System.currentTimeMillis();
		if (now > lastHit + buffer)
		{
			discardRecord();
			return;
		}
		if (now > breakAt)
			breakBlock();
		else
			lastHit = now;
	}

	public void breakBlock()
	{
		block.breakNaturally();
		plugin.removeRecord(block);
	}

	public void discardRecord()
	{
		plugin.removeRecord(block);
	}

	public DarkDiamondCore getPlugin() 
	{
		return plugin;
	}

	public Block getBlock()
	{
		return block;
	}

	public long getBreakAt()
	{
		return breakAt;
	}

	public long getStart()
	{
		return start;
	}

	public long getLastHit()
	{
		return lastHit;
	}

	public void setLastHit(long lastHit)
	{
		this.lastHit = lastHit;
	}

	public long getBuffer()
	{
		return buffer;
	}

}