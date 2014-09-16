package com.mower.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mower.lawn.MowerCoordinate;

public class MowerCoordinateTest {

	public MowerCoordinate m1;
	
	@Before
	public void setUp() throws Exception {
		m1=new MowerCoordinate(0, 0, 0);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMoveCoordinateCase1() {
		
		MowerCoordinate c=new MowerCoordinate(1, 2, 0);
		
		MowerCoordinate dest=c.turn('L').move()
				.turn('L').move()
				.turn('L').move()
				.turn('L').move().move();
		

		assertTrue(dest.toString().equals("1 3 N"));
	}

	
	@Test
	public void testMoveCoordinateCase2() {
		
		MowerCoordinate c=new MowerCoordinate(3, 3, 90);
		
		MowerCoordinate dest=c.move().move().turn('R')
				.move().move().turn('R')
				.move().turn('R').turn('R').move();
		
		assertTrue(dest.toString().equals("5 1 E"));
	}
}
