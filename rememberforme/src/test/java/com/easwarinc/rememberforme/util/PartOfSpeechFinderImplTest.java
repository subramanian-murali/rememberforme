package com.eshwarinc.rememberforme.util;

import org.junit.Test;

import com.eshwarinc.rememberforme.util.IPartOfSpeechFinder;
import com.eshwarinc.rememberforme.util.PartOfSpeechFinderImpl;

public class PartOfSpeechFinderImplTest {
	
	IPartOfSpeechFinder finder = PartOfSpeechFinderImpl.getInstance();

	@Test
	public void testGetNouns() {
		System.out.println(finder.getNouns("Passport is in the bedroom"));
		System.out.println(finder.getNouns("Sriram's Passport is sent to Embassy"));
	}

}
