package com.mower.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.mower.Mower;
import com.mower.StandardMower;
import com.mower.exception.CollisionException;
import com.mower.exception.FatalException;
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
	public Lawn lawn3;
	
	@Before
	public void setUp() throws Exception {
		lawn=new Lawn(new Coordinate(5, 5));
		lawn2=new Lawn(new Coordinate(1, 8));
		lawn3=new Lawn(new Coordinate(2, 2));
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
		this.mower1.setId(1);
		this.mower2.setId(2);
		this.mower3.setId(3);
		this.mower4.setId(4);
		this.mower5.setId(5);
		this.mower6.setId(6);
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
		assertFalse(coordinateSet.containsKey(m2));
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
				
		assertTrue(lawn.getCollisions().size()==1);		
		for(int i=0;i<lawn.getCollisions().size();i++)
		{
			CollisionException ex=lawn.getCollisions().get(i);
			if(ex.getMower()==mower4)
			{
				assertTrue(ex.getCoordinate().equals(new MowerCoordinate(0,1,180)));
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
				
		assertTrue(lawn.getCollisions().size()==1);		
		for(int i=0;i<lawn.getCollisions().size();i++)
		{
			CollisionException ex=lawn.getCollisions().get(i);
			if(ex.getMower()==mower2)
			{
				assertTrue(ex.getCoordinate().equals(new MowerCoordinate(2,1,270)));
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
				
		assertTrue(lawn.getCollisions().size()==1);	
		
		for(int i=0;i<lawn.getCollisions().size();i++)
		{
			CollisionException ex=lawn.getCollisions().get(i);
			//System.err.println(ex.toString());
			if(ex.getMower()==mower6)
			{
				assertTrue(ex.getCoordinate().equals(new MowerCoordinate(0,1,0)));
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
			if(m.getBlocksCount()==3)
			{
				mowersDoingMoreTask++;
			}
			else if(m.getBlocksCount()==2)
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
	public void getMowerTaskSame() {
		assertTrue(this.lawn2.calculateMowersTask(18));
		

		ArrayList<MowerTask> tasks=this.lawn2.getMowerTasks();
	
		assertTrue(tasks.size()==18);
		
		for(MowerTask m:tasks)
		{
			if(m.getBlocksCount()!=1)
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
	
	@Test
	public void autoAssignTaskForLawnEvenCase1() {
		
		lawn.autoTaskMowers(2);
		ArrayList<Mower> mowers=lawn.getMowers();
		assertTrue(mowers.size()==2);
		int cmdCount=-1;
		for(Mower m:mowers)
		{
			int totalMoveCommands=0;
			String cmd;
			while((cmd=m.getCurrentCommand(true))!=null)
			{
				if(cmd=="M")totalMoveCommands++;
				
			}
			
			if(cmdCount==-1)
			{
				cmdCount=totalMoveCommands;
			}else
			{
				assertTrue(cmdCount==totalMoveCommands);
			}
			
			
		}
	}
	
	@Test
	public void autoAssignTaskForLawnEvenCase2() {
		
		lawn.autoTaskMowers(12);
		
		ArrayList<Mower> mowers=lawn.getMowers();
		assertTrue(mowers.size()==12);
		
		int cmdCount=-1;
		for(Mower m:mowers)
		{
			int totalMoveCommands=0;
			String cmd;
			while((cmd=m.getCurrentCommand(true))!=null)
			{
				if(cmd=="M")totalMoveCommands++;
				
			}
			
			if(cmdCount==-1)
			{
				cmdCount=totalMoveCommands;
			}else
			{
				assertTrue(cmdCount==totalMoveCommands);
			}
			
			
		}
	}
	
	@Test
	public void autoAssignTaskForLawnEvenCase3() {
		
		lawn3.autoTaskMowers(3);
		
		ArrayList<Mower> mowers=lawn3.getMowers();
		assertTrue(mowers.size()==3);
		
		for(int i=0;i<mowers.size();i++)
		{
			Mower m=mowers.get(i);
			if(i==0)
			{
				assertTrue(m.getInitPosition().toString().equals("0 0 N"));
				assertTrue(m.getCommand().toString().equals("[M, M, R]"));
			}
			else if(i==1)
			{
				assertTrue(m.getInitPosition().toString().equals("1 2 E"));
				assertTrue(m.getCommand().toString().equals("[M, R, M]"));
				
			}else if(i==2)
			{				
				assertTrue(m.getInitPosition().toString().equals("2 0 W"));
				assertTrue(m.getCommand().toString().equals("[M, R, M]"));
				
			}
			
		}
	}
	
	@Test
	public void autoAssignTaskForLawnUnEvenCase1() {
		
		lawn2.autoTaskMowers(12);
		
		ArrayList<Mower> mowers=lawn2.getMowers();
		assertTrue(mowers.size()==12);
		
		int blockCount=0;
		for(Mower m:mowers)
		{
			int totalMoveCommands=0;
			String cmd;
			while((cmd=m.getCurrentCommand(true))!=null)
			{
				if(cmd=="M")totalMoveCommands+=2;
				
			}	
			if(totalMoveCommands==0)
			{
				blockCount++;
			}else
			{
				blockCount+=totalMoveCommands;
			}
			
		}
		assertTrue(blockCount==18);
	}
	
	@Test
	public void autoAssignTaskForLawnUnEvenCase2() {
		
		lawn2.autoTaskMowers(5);
		
		ArrayList<Mower> mowers=lawn2.getMowers();
		assertTrue(mowers.size()==5);
		
		for(int i=0;i<mowers.size();i++)
		{
			Mower m=mowers.get(i);
			if(i==0)
			{
				assertTrue(m.getInitPosition().toString().equals("0 0 N"));
				assertTrue(m.getCommand().toString().equals("[M, M]"));
			}
			else if(i==1)
			{
				assertTrue(m.getInitPosition().toString().equals("0 3 N"));
				assertTrue(m.getCommand().toString().equals("[M, M]"));
				
			}else if(i==2)
			{
				assertTrue(m.getInitPosition().toString().equals("0 6 N"));
				assertTrue(m.getCommand().toString().equals("[M, M, R, M, R]"));
				
			}
			else if(i==3)
			{
				assertTrue(m.getInitPosition().toString().equals("1 7 S"));
				assertTrue(m.getCommand().toString().equals("[M, M, M]"));
				
			}
			else if(i==4)
			{
				assertTrue(m.getInitPosition().toString().equals("1 3 S"));
				assertTrue(m.getCommand().toString().equals("[M, M, M]"));
				
			}
		}
	}
	
	@Test
	public void autoAssignTaskForTooManyCase1() {
		
		lawn2.autoTaskMowers(100);
		
		ArrayList<Mower> mowers=lawn2.getMowers();
		assertTrue(mowers.size()==18);
		
		for(Mower m:mowers)
		{
			assertTrue(m.getCommand().size()==0);
			
		}
		
	}
}
