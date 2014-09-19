package com.mower.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mower.Mower;
import com.mower.StandardMower;
import com.mower.exception.CollisionException;
import com.mower.exception.FatalException;
import com.mower.exception.CollisionException.CollisionType;
import com.mower.lawn.Coordinate;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;
import com.mower.task.MowerTask;

public class LawnTest {

	public MowerCoordinate m1;
	public MowerCoordinate m1a;
	public MowerCoordinate m2;
	public MowerCoordinate m3;
	public MowerCoordinate m4;
	public Mower mower1;
	public Mower mower2;
	public Mower mower3;
	public Mower mower4;
	public Mower mower5;
	public Mower mower6;
	public Lawn lawn;
	public Lawn lawn2;
	
	@Before
	public void setUp() throws Exception {
		lawn=new Lawn(new Coordinate(5, 5));
		lawn2=new Lawn(new Coordinate(1, 8));
		m1=new MowerCoordinate(0, 0, 0);
		m1a=new MowerCoordinate(0, 0, 90);
		m2=new MowerCoordinate(5, 5, 0);
		m3=new MowerCoordinate(-1, 2, 0);
		m4=new MowerCoordinate(3, -5, 0);
		
		this.mower1=new StandardMower(new MowerCoordinate(1,1,90));
		this.mower2=new StandardMower(new MowerCoordinate(2,1,270));
		this.mower3=new StandardMower(new MowerCoordinate(0,0,0));
		this.mower4=new StandardMower(new MowerCoordinate(0,2,180));
		this.mower5=new StandardMower(new MowerCoordinate(0,0,0));
		this.mower6=new StandardMower(new MowerCoordinate(0,1,0));
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
	public void testCollisionTypeBump() {
		try {
			mower3.setCommandStr("M");
			mower4.setCommandStr("M");
		} catch (FatalException e) {
			e.printStackTrace();
			fail("Unable to set the command");
		}
		lawn.addMower(mower3);
		lawn.addMower(mower4);
		lawn.startMowers();
				
		assertTrue(lawn.getCollisions().size()==2);		
		for(int i=0;i<lawn.getCollisions().size();i++)
		{
			CollisionException ex=lawn.getCollisions().get(i);
			if(ex.getMower()==mower3)
			{
				assertTrue(ex.getCoordinate().equals(mower4.getCurrentLocation()));
			}else if(ex.getMower()==mower4)
			{				
				assertTrue(ex.getCoordinate().equals(mower3.getCurrentLocation()));
			}else
			{
				fail("Unknown Mower in collision list");
			}
				
		}
	}

	
	@Test
	public void testCollisionTypeRunOver() {

		try {
			mower1.setCommandStr("M");
			mower2.setCommandStr("M");
		} catch (FatalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Unable to set the command");
		}
		lawn.addMower(mower1);
		lawn.addMower(mower2);
		lawn.startMowers();
				
		assertTrue(lawn.getCollisions().size()==2);		
		for(int i=0;i<lawn.getCollisions().size();i++)
		{
			CollisionException ex=lawn.getCollisions().get(i);
			if(ex.getMower()==mower1)
			{
				assertTrue(ex.getCoordinate().equals(mower2.getCurrentLocation()));
			}else if(ex.getMower()==mower2)
			{				
				assertTrue(ex.getCoordinate().equals(mower1.getCurrentLocation()));
			}else
			{
				fail("Unknown Mower in collision list");
			}
				
		}
		
	}
	
	@Test
	public void testNoCollision() {

		try {
			mower5.setCommandStr("M");
			mower6.setCommandStr("M");
		} catch (FatalException e) {
			e.printStackTrace();
			fail("Unable to set the command");
		}
		lawn.addMower(mower5);
		lawn.addMower(mower6);
		lawn.startMowers();
				
		assertTrue(lawn.getCollisions().size()==0);		

	}
	
	@Test
	public void testCollisionChasing() {

		try {
			mower5.setCommandStr("MM");
			mower6.setCommandStr("M");
		} catch (FatalException e) {
			e.printStackTrace();
			fail("Unable to set the command");
		}
		lawn.addMower(mower5);
		lawn.addMower(mower6);
		lawn.startMowers();
				
		assertTrue(lawn.getCollisions().size()==2);	
		
		for(int i=0;i<lawn.getCollisions().size();i++)
		{
			CollisionException ex=lawn.getCollisions().get(i);
			//System.err.println(ex.toString());
			if(ex.getMower()==mower5)
			{
				assertTrue(ex.getCoordinate().equals(mower6.getCurrentLocation()));
			}else if(ex.getMower()==mower6)
			{				
				assertTrue(ex.getCoordinate().equals(mower5.getCurrentLocation()));
			}else
			{
				fail("Unknown Mower in collision list");
			}
				
		}
	}
	
	@Test
	public void getMowerTaskUneven() {
		assertTrue(this.lawn2.calculateMowersTask(7));
		
		int mowersDoingMoreTask=0;
		int mowersDoingLessTask=0;
		
		ArrayList<MowerTask> tasks=this.lawn2.getMowerTasks();
	
		assertTrue(tasks.size()==7);
		
		for(MowerTask m:tasks)
		{
			if(m.getBlocksCount()==2)
			{
				mowersDoingMoreTask++;
			}
			else if(m.getBlocksCount()==1)
			{
				mowersDoingLessTask++;
			}
			else
			{
				fail("Invalid mower task: "+m.getBlocksCount());
			}
		}
		
		assertTrue((mowersDoingLessTask+mowersDoingMoreTask)==7);
		assertTrue(((mowersDoingMoreTask)*3+(mowersDoingLessTask)*2)==lawn2.getBlockCount());
	}
	
	
	@Test
	public void getMowerTaskEven() {
		assertTrue(this.lawn2.calculateMowersTask(9));
		

		ArrayList<MowerTask> tasks=this.lawn2.getMowerTasks();
	
		assertTrue(tasks.size()==9);
		
		for(MowerTask m:tasks)
		{
			if(m.getBlocksCount()!=2)
			{
				fail("Invalid mower task: "+m.getBlocksCount());
			}

		}
		
	}
	
	@Test
	public void getMowerTaskInsufficient() {
		assertTrue(this.lawn2.calculateMowersTask(100));
		

		ArrayList<MowerTask> tasks=this.lawn2.getMowerTasks();
	
		
		assertTrue(tasks.size()==this.lawn2.getBlockCount());
		
		for(MowerTask m:tasks)
		{
			if(m.getBlocksCount()!=1)
			{
				fail("Invalid mower task: "+m.getBlocksCount());
			}

		}
		
	}
	
}
