package com.mower.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mower.StandardMower;
import com.mower.exception.FatalException;
import com.mower.lawn.Coordinate;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;

public class LawnTest {

	public MowerCoordinate m1;
	public MowerCoordinate m1a;
	public MowerCoordinate m2;
	public MowerCoordinate m3;
	public MowerCoordinate m4;
	public Lawn lawn;
	
	@Before
	public void setUp() throws Exception {
		lawn=new Lawn(new Coordinate(5, 5));
		m1=new MowerCoordinate(0, 0, 0);
		m1a=new MowerCoordinate(0, 0, 90);
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
	
	@Test
	public void testSet() {
		HashMap<Coordinate, Coordinate> coordinateSet=new HashMap<Coordinate, Coordinate>();
		coordinateSet.put(m1, m1);
		System.err.println(coordinateSet.containsKey(m2));
	}
	
	@Test
	public void testCollision() {
	//	StandardMower mower1=new StandardMower(new MowerCoordinate(1,1,90));
		
	//	StandardMower mower2=new StandardMower(new MowerCoordinate(2,1,270));
		
		StandardMower mower3=new StandardMower(new MowerCoordinate(0,0,0));
		StandardMower mower4=new StandardMower(new MowerCoordinate(0,2,180));
		
		try {
		//	mower1.setCommandStr("M");
		//	mower2.setCommandStr("M");
			mower3.setCommandStr("M");
			mower4.setCommandStr("M");
		} catch (FatalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//lawn.addMower(mower1);
		//lawn.addMower(mower2);
		lawn.addMower(mower3);
		lawn.addMower(mower4);
		lawn.startMowers();
	}

}
