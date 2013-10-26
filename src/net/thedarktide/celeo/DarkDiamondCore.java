package net.thedarktide.celeo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * Removes diamonds, buffs iron.<br>
 * Started June 9th, 2012.
 * @author Celeo
 */
public class DarkDiamondCore extends JavaPlugin
{

	/*
	 * VARS
	 */

	private final Logger log = Logger.getLogger("Minecraft");
	private final boolean DEBUGGING = true;
	private IronBuffer ironBuffer = null;
	private DiamondRemover diamondRemover = null;
	private ObsidianListener obsidianListener = null;
	public List<ObsidianRecord> records = null;
	public static Integer breakTime = null;
	public WorldGuardPlugin worldGuard;

	/*
	 * Overrides
	 */

	@Override
	public void onEnable()
	{
		ironBuffer = new IronBuffer(this);
		diamondRemover = new DiamondRemover(this);
		obsidianListener = new ObsidianListener(this);
		records = new ArrayList<ObsidianRecord>();
		getDataFolder().mkdirs();
		if (!new File(getDataFolder(), "/config.yml").exists())
		{
			saveDefaultConfig();
			breakTime = Integer.valueOf(1);
		}
		else
			breakTime = Integer.valueOf(getConfig().getInt("breaktime"));
		setupWorldGuard();
		log("Enabled with a break time of " + breakTime + ".");
	}

	@Override
	public void onDisable()
	{
		log("Disabled");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.isOp() || player.hasPermission("darkscrewdiamonds.reload"))
			{
				reloadConfig();
				breakTime = Integer.valueOf(getConfig().getInt("breaktime"));
				player.sendMessage("§7Reloaded");
			}
			else
				player.sendMessage("§cYou cannot use this command.");
		}
		else
		{
			reloadConfig();
			breakTime = Integer.valueOf(getConfig().getInt("breaktime"));
			log("Reloaded");
		}
		return true;
	}

	public void setupWorldGuard()
	{
		Plugin test = getServer().getPluginManager().getPlugin("WorldGuard");
		if (test != null)
		{
			worldGuard = (WorldGuardPlugin) test;
			debug("Connected to WorldGuard");
		}
		else
		{
			log("ERROR: Could not connect to WorldGuard, disabling ...");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	/*
	 * Logging
	 */

	public void log(String message)
	{
		log.info("[DarkScrewDiamonds] " + message);
	}

	public void debug(String message)
	{
		if (DEBUGGING)
			log(message);
	}

	/*
	 * Functions
	 */

	/**
	 * 
	 * @param toCheck
	 * @return
	 */
	public boolean isBlockRecorded(Block toCheck)
	{
		int x = toCheck.getX();
		int y = toCheck.getY();
		int z = toCheck.getZ();
		for (ObsidianRecord record : records)
		{
			Location loc = record.getBlock().getLocation();
			if (loc.getX() == x && loc.getY() == y && loc.getZ() == z)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param toGet
	 * @return
	 */
	public ObsidianRecord getBlockRecordFor(Block toGet)
	{
		int x = toGet.getX();
		int y = toGet.getY();
		int z = toGet.getZ();
		for (ObsidianRecord record : records)
		{
			Location loc = record.getBlock().getLocation();
			if (loc.getX() == x && loc.getY() == y && loc.getZ() == z)
				return record;
		}
		return null;
	}

	/**
	 * 
	 * @param toAdd
	 * @param delayTime
	 */
	public void addRecord(Block toAdd)
	{
		try
		{
			records.add(new ObsidianRecord(this, toAdd, 2L));
		}
		catch (Exception e) { }
	}

	/**
	 * 
	 * @param toRemove
	 */
	public void removeRecord(Block toRemove)
	{
		if (!isBlockRecorded(toRemove))
			return;
		records.remove(getBlockRecordFor(toRemove));
	}

	/*
	 * GET and SET
	 */

	public IronBuffer getIronBuffer()
	{
		return ironBuffer;
	}

	public void setIronBuffer(IronBuffer ironBuffer)
	{
		this.ironBuffer = ironBuffer;
	}

	public DiamondRemover getDiamondRemover()
	{
		return diamondRemover;
	}

	public void setDiamondRemover(DiamondRemover diamondRemover)
	{
		this.diamondRemover = diamondRemover;
	}

	public ObsidianListener getObsidianListener()
	{
		return obsidianListener;
	}

	public void setObsidianListener(ObsidianListener obsidianListener)
	{
		this.obsidianListener = obsidianListener;
	}

	public boolean isDEBUGGING()
	{
		return DEBUGGING;
	}

}