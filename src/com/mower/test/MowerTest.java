package com.mower.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mower.StandardMower;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;

public class MowerTest {

	public Lawn lawn1;
	public Lawn lawn2;
	public Lawn lawn3;
	public MowerCoordinate m1;
	public MowerCoordinate m2;
	public MowerCoordinate m3;
	
	@Before
	public void setUp() throws Exception {
		lawn1=new Lawn(2,2);
		m1=new MowerCoordinate(0, 0, 0);
		lawn2=new Lawn(0,4);
		m2=new MowerCoordinate(0, 0, 0);
		lawn3=new Lawn(1,5);
		m3=new MowerCoordinate(0, 0, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMowLawn1() {
		
		StandardMower mower=new StandardMower(lawn1, m1);
		mower.mowLawn();
		System.err.println(mower.getFootPrint().toString());
		assertTrue(mower.getFootPrint().toString().equals("[M, M, R, M, M, R, M, M, R, M, R, M]"));
	}
	
	@Test
	public void testMowLawn2() {
		
		StandardMower mower=new StandardMower(lawn2, m2);
		mower.mowLawn();
		System.err.println(mower.getFootPrint().toString());
		assertTrue(mower.getFootPrint().toString().equals("[M, M, M, M]"));
	}
	
	@Test
	public void testMowLawn3() {
		StandardMower mower=new StandardMower(lawn3, m3);
		mower.mowLawn();
		System.err.println(mower.getFootPrint().toString());
		assertTrue(mower.getFootPrint().toString().equals("[M, M, M, M, M, R, M, R, M, M, M, M, M]"));
	}

}
