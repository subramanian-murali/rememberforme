package com.eshwarinc.rememberforme;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.eshwarinc.rememberforme.model.IRememberForMe;
import com.eshwarinc.rememberforme.model.RememberForMeImpl;

public class RememberForMeImplTest {

	@Test
	public void testRememberingFor() {
		IRememberForMe toRemember = new RememberForMeImpl("sriram");
		assertEquals("sriram", toRemember.rememberingFor());
	}

	@Test
	public void testGetAllItemsRemembered() {
		IRememberForMe toRemember = new RememberForMeImpl("sriram");
		toRemember.rememberForMe("My Passport is in the bedroom");
		toRemember.rememberForMe("Batter is in refrigerator");
		toRemember.rememberForMe("Mango Lassi is in refrigerator");
		assertEquals(3, toRemember.getAllItemsRemembered().size());
	}

	@Test
	public void testGetItemsRememberedFor() {
		IRememberForMe toRemember = new RememberForMeImpl("sriram");
		toRemember.rememberForMe("My Passport is in the bedroom");
		toRemember.rememberForMe("Batter is in refrigerator");
		toRemember.rememberForMe("Mango Lassi is in refrigerator");
		assertEquals(1, toRemember.getItemsRememberedFor("passport").size());
		assertEquals(1, toRemember.getItemsRememberedFor("batter").size());
		assertEquals(1, toRemember.getItemsRememberedFor("lassi").size());
		assertEquals(1, toRemember.getItemsRememberedFor("bedroom").size());
		assertEquals(2, toRemember.getItemsRememberedFor("refrigerator").size());
	}

	@Test
	public void testGetItemsRememberedOn() {
		IRememberForMe toRemember = new RememberForMeImpl("sriram");
		toRemember.rememberForMe("My Passport is in the bedroom");
		toRemember.rememberForMe("Batter is in refrigerator");
		toRemember.rememberForMe("Mango Lassi is in refrigerator");
		assertEquals(3, toRemember.getItemsRememberedOn(new Date()).size());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		assertEquals(0, toRemember.getItemsRememberedOn(new Date(c.getTimeInMillis())).size());	
	}
}
