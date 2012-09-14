package uk.co.jacekk.bukkit.baseplugin.v1.command.args;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.jacekk.bukkit.baseplugin.v2.command.args.KeyValueArgumentProcessor;

public class TestKeyValueArgumentProcessor {
	
	@Test
	public void test(){
		KeyValueArgumentProcessor safe = new KeyValueArgumentProcessor(new String[]{"key=value", "test=another"}, "=");
		
		assertNotNull(safe.args);
		
		assertNotNull(safe.get("key"));
		assertNotNull(safe.get("TEST"));
		
		assertTrue(safe.contains("key"));
		assertTrue(safe.contains("TEST"));
		
		assertTrue(safe.get("key").equals("value"));
		assertTrue(safe.get("test").equals("another"));
		
		assertTrue(safe.getLeftOver().isEmpty());
		
		KeyValueArgumentProcessor extra = new KeyValueArgumentProcessor(new String[]{"key:value", "test:another", "extra"}, ":");
		
		assertNotNull(extra.args);
		
		assertNotNull(extra.get("key"));
		assertNotNull(extra.get("TEST"));
		
		assertTrue(extra.contains("key"));
		assertTrue(extra.contains("TEST"));
		
		assertTrue(extra.get("key").equals("value"));
		assertTrue(extra.get("test").equals("another"));
		
		assertFalse(extra.getLeftOver().isEmpty());
		assertTrue(extra.getLeftOver().get(0).equals("extra"));
	}
	
}
