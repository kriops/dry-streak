package no.kriops.drystreak;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DryStreakPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DryStreakPlugin.class);
		RuneLite.main(args);
	}
}