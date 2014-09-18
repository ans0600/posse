package com.mower.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mower.Mower;
import com.mower.StandardMower;
import com.mower.exception.FatalException;
import com.mower.lawn.Coordinate;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;
import com.mower.lawn.MowerCoordinate.CoordinateType;

public class MowerTest {

	public Lawn lawn1;
	public Lawn lawn2;
	public Lawn lawn3;
	public MowerCoordinate m1;
	public MowerCoordinate m2;
	public MowerCoordinate m3;
	public MowerCoordinate m4;
	public String cmdValid1;
	public String cmdInvalid1;
	
	@Before
	public void setUp() throws Exception {
		lawn1=new Lawn(new Coordinate(2, 2));
		m1=new MowerCoordinate(0, 0, 0);
		lawn2=new Lawn(new Coordinate(0, 4));
		m2=new MowerCoordinate(0, 0, 0);
		lawn3=new Lawn(new Coordinate(1, 5));
		m3=new MowerCoordinate(0, 0, 0);
		m4=new MowerCoordinate(0, 0, 90);
		this.cmdValid1="MMMLLLRRR";
		this.cmdInvalid1="!@#&^%ZB ";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMowLawn1() {
		
		Mower mower=new StandardMower(lawn1, m1);
		mower.mowLawn();
		//System.err.println(mower.getFootPrint().toString());
		assertTrue(mower.getFootPrint().toString().equals("[M, M, R, M, M, R, M, M, R, M, R, M]"));
	}
	
	@Test
	public void testMowLawn2() {
		
		Mower mower=new StandardMower(lawn2, m2);
		mower.mowLawn();
		//System.err.println(mower.getFootPrint().toString());
		assertTrue(mower.getFootPrint().toString().equals("[M, M, M, M]"));
	}
	
	@Test
	public void testMowLawn3() {
		Mower mower=new StandardMower(lawn3, m3);
		mower.mowLawn();
		//System.err.println(mower.getFootPrint().toString());
		assertTrue(mower.getFootPrint().toString().equals("[M, M, M, M, M, R, M, R, M, M, M, M, M]"));
	}
	
	@Test
	public void testValidCommand() {
		Mower mower=new StandardMower(lawn3, m1);
		try {
			mower.setCommandStr(cmdValid1);
			for(int i=0;i<cmdValid1.length();i++)
			{
				assertTrue(mower.getCurrentCommand(true).equals(cmdValid1.substring(i, i+1)));
			}
		} catch (FatalException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidCommand() {
		Mower mower=new StandardMower(lawn3, m1);
		try {
			mower.setCommandStr(cmdInvalid1);
			fail("Unable to detect invalid command(s)");
		} catch (FatalException e) {
			assertTrue(e.getMessage().contains("Invalid Command"));
		}
	}
	
	@Test
	public void testExecuteEmptyCommand() {
		Mower mower=new StandardMower(lawn1, m1);
		assertFalse(mower.executeCommand());
		//Mower did not move
		assertTrue(mower.getCurrentLocation().equals(m1));
		assertTrue(mower.getCurrentLocation().getFacing()==m1.getFacing());
		assertTrue(mower.getCurrentLocation().getType().equals(CoordinateType.END));
	}

	@Test
	public void testMove1() {
		Mower mower=new StandardMower(lawn1, m1);
		try {
			mower.setCommandStr("M");
			assertTrue(mower.executeCommand());
			MowerCoordinate expectedLoc=new MowerCoordinate(0,1,0);
			assertTrue(mower.getCurrentLocation().equals(expectedLoc));
			assertTrue(mower.getCurrentLocation().getFacing()==expectedLoc.getFacing());
			assertTrue(mower.getCurrentLocation().getType().equals(CoordinateType.END));
		} catch (FatalException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMove2() {
		Mower mower=new StandardMower(lawn1, m4);
		try {
			mower.setCommandStr("MMM");
			while(mower.executeCommand());
			MowerCoordinate expectedLoc=new MowerCoordinate(3,0,90);
			System.err.println(mower.getCurrentLocation().toString());
			assertTrue(mower.getCurrentLocation().equals(expectedLoc));
			assertTrue(mower.getCurrentLocation().getFacing()==expectedLoc.getFacing());
			assertTrue(mower.getCurrentLocation().getType().equals(CoordinateType.END));
		} catch (FatalException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
//	
	@Test
	public void testRotate() {
		Mower mower=new StandardMower(lawn1, m1);
		try {
			mower.setCommandStr("R");
			assertTrue(mower.executeCommand());
			MowerCoordinate expectedLoc=new MowerCoordinate(0,0,90);
			assertTrue(mower.getCurrentLocation().equals(expectedLoc));
			assertTrue(mower.getCurrentLocation().getFacingText()==expectedLoc.getFacingText());
			assertTrue(mower.getCurrentLocation().getType().equals(CoordinateType.END));
			
			mower.setCommandStr("LL");		
			while(mower.executeCommand());
			expectedLoc=new MowerCoordinate(0,0,270);
			assertTrue(mower.getCurrentLocation().equals(expectedLoc));
			assertTrue(mower.getCurrentLocation().getFacingText()==expectedLoc.getFacingText());
			assertTrue(mower.getCurrentLocation().getType().equals(CoordinateType.END));
			
			mower.setCommandStr("LLLLLRRRRRR");
			while(mower.executeCommand());
			expectedLoc=new MowerCoordinate(0,0,0);
			assertTrue(mower.getCurrentLocation().equals(expectedLoc));
			assertTrue(mower.getCurrentLocation().getFacingText()==expectedLoc.getFacingText());
			assertTrue(mower.getCurrentLocation().getType().equals(CoordinateType.END));
		} catch (FatalException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	

}
