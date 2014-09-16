package com.mower.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;

public class LawnTest {

	public MowerCoordinate m1;
	public MowerCoordinate m2;
	public MowerCoordinate m3;
	public MowerCoordinate m4;
	public Lawn lawn;
	
	@Before
	public void setUp() throws Exception {
		lawn=new Lawn(5,5);
		m1=new MowerCoordinate(0, 0, 0);
		m2=new MowerCoordinate(5, 5, 0);
		m3=new MowerCoordinate(-1, 2, 0);
		m4=new MowerCoordinate(3, -5, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsInside() {
		assertTrue(this.lawn.isInside(m1));
		assertTrue(this.lawn.isInside(m2));
		assertFalse(this.lawn.isInside(m3));
		assertFalse(this.lawn.isInside(m4));
	}

}
