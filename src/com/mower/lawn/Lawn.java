package com.mower.lawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.mower.Mower;
import com.mower.StandardMower;
import com.mower.exception.CollisionException;
import com.mower.exception.CollisionException.CollisionType;
import com.mower.footprint.FootPrint;
import com.mower.task.MowerTask;

public class Lawn extends Grid  {

	private ArrayList<Mower> mowers;
	private HashMap<Coordinate, Coordinate> coordinateSet;

	private ArrayList<CollisionException> collisions;

	private ArrayList<MowerTask> mowerTasks;
	
	private Mower currentMower;
	private Mower probeMower;
	private boolean jumpNext=false;
	

	public Lawn(Coordinate boundry) {
		super(boundry);
		this.mowers = new ArrayList<Mower>();
		this.mowerTasks = new ArrayList<MowerTask>();

	}

	public void addMower(Mower m) {
		m.setLawn(this);
		this.mowers.add(m);
	}

	public ArrayList<CollisionException> getCollisions() {
		return this.collisions;
	}
	
	public ArrayList<MowerTask> getMowerTasks()
	{
		return this.mowerTasks;
	}
	
	public ArrayList<Mower> getMowers()
	{
		return this.mowers;
	}

	/**
	 * Complexity o(n*m) where m is number of mowers and n is length of the
	 * longest command
	 * 
	 */
	public void startMowers() {
		this.collisions = new ArrayList<CollisionException>();
		if (this.mowers.size() > 0) {
			boolean hasCommand = false;
			do {
				// clear the coordinateSet for this move step
				this.coordinateSet = new HashMap<Coordinate, Coordinate>();
				hasCommand = false;
				for (int i = 0; i < this.mowers.size(); i++) {

					Mower m = this.mowers.get(i);
					boolean res = m.executeCommand();
					//System.err.println(i + " " + res);
					hasCommand = hasCommand || res;
					this.hasCollision(m);
				}

			} while (hasCommand);
		}

	}

	private boolean hasCollision(Mower m) {
		return this.hasCollision(m, m.getInitPosition())
				|| this.hasCollision(m, m.getCurrentLocation());

	}

	private boolean hasCollision(Mower m, MowerCoordinate c) {
		// System.err.println("Has Collision "+c.toString());
		System.err.println("Mower!!"+m);
		if (this.coordinateSet.containsKey(c)) {
			// System.err.println("Key Exist: " + c.toString());
			MowerCoordinate existing = (MowerCoordinate) this.coordinateSet
					.get(c);
			if (c.getType() == existing.getType()) {
				// System.err.println("Collision Type A!!");
				this.collisions.add(new CollisionException(m, c.getCopy(),
						CollisionType.BUMP));
				return true;
			} else if (Math.abs(c.getFacing() - existing.getFacing()) == 180) {
				// System.err.println("Collision Type B");
				this.collisions.add(new CollisionException(m, c.getCopy(),
						CollisionType.RUNOVER));
				return true;
			}

		} else {
			this.coordinateSet.put(c, c);
		}
		return false;
	}

	/**
	 * For part 2
	 * 
	 * @param origin
	 * @return
	 */
	public MowerCoordinate getAdjacentCoordinate(MowerCoordinate origin) {
		MowerCoordinate dest = origin.action('M');

		if (this.isInside(dest) && !this.visited[origin.x][origin.y]) {
			this.visited[origin.x][origin.y] = true;
			return dest;

		} else {
			return null;
		}

	}

	/**
	 * For part 2
	 * 
	 * Get MowerTasks based on pre-calculated linear equation
	 * 
	 * @param numOfMowers
	 */
	public boolean calculateMowersTask(int numOfMowers) {
		this.mowerTasks.clear();
		int totalBlocks = this.getBlockCount();
		if (numOfMowers != 0 && totalBlocks != 0) {
			if (totalBlocks < numOfMowers) {
				//we decide to let other mower idle as there is insufficient job
				for(int i=0;i<totalBlocks;i++)
				{
					MowerTask t=new MowerTask(1);
					this.mowerTasks.add(t);
				}
				
			}
			
			else if(totalBlocks % numOfMowers==0)
			{
				for(int i=0;i<numOfMowers;i++)
				{
					MowerTask t=new MowerTask(totalBlocks/numOfMowers);
					this.mowerTasks.add(t);
				}
			}
			else {
				double d=(double)totalBlocks / (double)numOfMowers;
				int p1=(int) (totalBlocks - numOfMowers* Math.floor(d));
				int p2=(int) (Math.ceil(d) - Math.floor(d));
				int mowersDoingMoreTask=p1/p2;	
				int moreTaskCount = (int) Math.ceil(d);
				int mowersDoingLessTask = numOfMowers - mowersDoingMoreTask;
				int lessTaskCount =  (int) Math.floor(d);

				for(int i=0;i<mowersDoingLessTask;i++)
				{
					MowerTask t=new MowerTask(lessTaskCount);
					this.mowerTasks.add(t);
				}

				
				
				for(int i=0;i<mowersDoingMoreTask;i++)
				{
					MowerTask t=new MowerTask(moreTaskCount);
					this.mowerTasks.add(t);
				}

			}
			return true;

		}

		return false;
	}
	

	public void autoTaskMowers(int numOfMowers)
	{
		this.currentMower=null;
		this.calculateMowersTask(numOfMowers);
		this.probeMower=new StandardMower(this,new MowerCoordinate(0,0,0));
		this.probeMower.mowLawn();
		this.assignMowerTasksByFootprint(this.probeMower.getFootPrint());
		System.err.println(this.mowers.size());
	}
	
	public void assignMowerTasksByFootprint(LinkedList<FootPrint> footPrint)
	{
		for(FootPrint fp:footPrint)
		{
			System.err.println(fp.toDebugString());
			//System.err.println("Mower Task:"+this.mowerTasks.size()+" "+this.mowerTasks.get(0).getBlocksCount());
			if(this.mowerTasks.size()>0)
			{
				if(this.currentMower==null)
				{
					if(!fp.getCommand().equals("M"))continue;
					this.currentMower=new StandardMower(fp.getMowerCoordinate().getCopy());
					System.err.println("New Mower Created:"+fp.getMowerCoordinate().getCopy());
				}
				
				if(fp.getCommand().equals("M")&&!this.mowerTasks.get(0).assignBlock())
				{
					this.mowerTasks.remove(0);
					this.mowers.add(this.currentMower);
					this.currentMower=null;
					System.err.println("Curent Mower Finished!");
				}else
				{
					this.currentMower.insertCommand(fp.getCommand());
				}
				
			}
					
		}
		
		if(this.currentMower!=null)
		{
			this.mowers.add(this.currentMower);
			this.mowerTasks.remove(0);
		}
		
		//TODO what if there is still task left
		if(this.mowerTasks.size()==1)
		{
			System.err.println("Mower Task Left: "+this.mowerTasks.size());
			this.currentMower=new StandardMower(this.mowers.get(this.mowers.size()-1).getInitPosition().getCopy().action('M'));
			this.mowers.add(this.currentMower);
		}
		
	}
	


}
