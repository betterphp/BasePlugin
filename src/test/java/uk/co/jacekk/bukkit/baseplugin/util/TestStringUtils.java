package uk.co.jacekk.bukkit.baseplugin.util;

import org.junit.Assert;

import org.junit.Test;

public class TestStringUtils {
	
	@Test
	public void testVersionComparison(){
		Assert.assertTrue(StringUtils.versionCompare("0.1", "0.2") == -1);
		Assert.assertTrue(StringUtils.versionCompare("0.2", "0.1") == 1);
		Assert.assertTrue(StringUtils.versionCompare("0.1", "0.1") == 0);
		
		Assert.assertTrue(StringUtils.versionCompare("0.8.7.6.5.4.3.2.1", "0.8.7.6.6.4.3.2.1") == -1);
		Assert.assertTrue(StringUtils.versionCompare("0.8.7.6.7.4.3.2.1", "0.8.7.6.6.4.3.2.1") == 1);
		
		Assert.assertTrue(StringUtils.versionCompare("0.1-beta", "0.2-alpha") == -1);
		Assert.assertTrue(StringUtils.versionCompare("0.2-alpha", "0.1-beta") == 1);
		Assert.assertTrue(StringUtils.versionCompare("0.1-snapshot", "0.1-snapshot") == 0);
		Assert.assertTrue(StringUtils.versionCompare("0.1-snapshot", "0.1-snapshot-b12") == -1);
		Assert.assertTrue(StringUtils.versionCompare("0.1-snapshot-b14", "0.1-snapshot-b12") == 1);
		Assert.assertTrue(StringUtils.versionCompare("0.1-dev", "0.1-snapshot") == 0);
		Assert.assertTrue(StringUtils.versionCompare("0.1-release", "0.1-beta") == 1);
	}
	
}
