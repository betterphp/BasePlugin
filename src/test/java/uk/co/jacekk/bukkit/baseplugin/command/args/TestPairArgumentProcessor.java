package uk.co.jacekk.bukkit.baseplugin.command.args;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.jacekk.bukkit.baseplugin.v6.command.args.PairArgumentProcessor;

public class TestPairArgumentProcessor {
	
	@Test
	public void test(){
		PairArgumentProcessor safe = new PairArgumentProcessor(new String[]{"key", "value", "test", "another"});
		
		assertNotNull(safe.args);
		
		assertNotNull(safe.get("key"));
		assertNotNull(safe.get("TEST"));
		
		assertTrue(safe.contains("key"));
		assertTrue(safe.contains("TEST"));
		
		assertTrue(safe.get("key").equals("value"));
		assertTrue(safe.get("test").equals("another"));
		
		PairArgumentProcessor extra = new PairArgumentProcessor(new String[]{"key", "value", "test", "another", "extra"});
		
		assertNotNull(extra.args);
		
		assertNotNull(extra.get("key"));
		assertNotNull(extra.get("TEST"));
		
		assertTrue(extra.contains("key"));
		assertTrue(extra.contains("TEST"));
		
		assertTrue(extra.get("key").equals("value"));
		assertTrue(extra.get("test").equals("another"));
	}
	
}
