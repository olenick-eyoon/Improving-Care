package com.olenick.avatar.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.olenick.avatar.timer.Timer;

public class TimerObject {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createTimer() {
		Timer timer = new Timer();
		assertNotNull(timer);
	}
	
	@Test
	public void setLoginTime() {
		Timer timer = new Timer();
		timer.setLoginTime(1500);
	}

	@Test
	public void getLoginTime() {
		Timer timer = new Timer();
		timer.setLoginTime(1500);
		assertEquals(timer.getLoginTime(), 1500);
	}

	@Test
	public void getLoginTime_nullValue() {
		Timer timer = new Timer();
		assertNotNull(timer.getLoginTime());
	}
	
	@Test
	public void setIC1Time() {
		Timer timer = new Timer();
		timer.setIc1Time(1500);
	}

	@Test
	public void getIC1Time() {
		Timer timer = new Timer();
		timer.setIc1Time(1500);
		assertEquals(timer.getIc1Time(), 1500);
	}
	
	@Test
	public void setIC2Time() {
		Timer timer = new Timer();
		timer.setIc2Time(1500);
	}

	@Test
	public void getIC2Time() {
		Timer timer = new Timer();
		timer.setIc2Time(1500);
		assertEquals(timer.getIc2Time(), 1500);
	}
	
	@Test
	public void resetTimer() {
		Timer timer = new Timer();
		timer.setCompositeTime(1000);
		timer.setDemographicsTime(1000);
		timer.setIc1Time(1000);
		timer.setIc2Time(1000);
		timer.setLoginTime(1000);
		timer.setOverviewTime(1000);
		timer.setSideBySideTime(1000);
		timer.resetTimer();
		assertEquals(timer.getCompositeTime()+timer.getDemographicsTime()+timer.getIc1Time()+timer.getIc2Time()+timer.getLoginTime()+timer.getOverviewTime()+timer.getSideBySideTime() , 0);
	}
	
	
}
