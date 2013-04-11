package uk.co.jacekk.bukkit.baseplugin.command.args;

import org.junit.Assert;

import org.junit.Test;

import uk.co.jacekk.bukkit.baseplugin.command.args.PairArgumentProcessor;

public class TestPairArgumentProcessor {
	
	@Test
	public void testPairArgumentProcessor(){
		PairArgumentProcessor safe = new PairArgumentProcessor(new String[]{"key", "value", "test", "another"});
		
		Assert.assertNotNull(safe.args);
		
		Assert.assertNotNull(safe.get("key"));
		Assert.assertNotNull(safe.get("TEST"));
		
		Assert.assertTrue(safe.contains("key"));
		Assert.assertTrue(safe.contains("TEST"));
		
		Assert.assertTrue(safe.get("key").equals("value"));
		Assert.assertTrue(safe.get("test").equals("another"));
		
		PairArgumentProcessor extra = new PairArgumentProcessor(new String[]{"key", "value", "test", "another", "extra"});
		
		Assert.assertNotNull(extra.args);
		
		Assert.assertNotNull(extra.get("key"));
		Assert.assertNotNull(extra.get("TEST"));
		
		Assert.assertTrue(extra.contains("key"));
		Assert.assertTrue(extra.contains("TEST"));
		
		Assert.assertTrue(extra.get("key").equals("value"));
		Assert.assertTrue(extra.get("test").equals("another"));
	}
	
}
