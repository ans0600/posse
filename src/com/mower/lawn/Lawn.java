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
import com.mower.util.Util;

public class Lawn extends Grid  {

	private ArrayList<Mower> mowers;
	private HashMap<Coordinate, Coordinate> coordinateSet;

	private ArrayList<CollisionException> collisions;

	private ArrayList<MowerTask> mowerTasks;
	
	private Mower currentMower;
	private Mower probeMower;
	

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
	 * Start executing all command assigned to each mower on the lawn.
	 * Also detect the collision.
	 * Complexity o(n*m) where m is number of mowers and n is length of the
	 * longest command
	 * 
	 */
	public void startMowers() {
		this.collisions = new ArrayList<CollisionException>();
		if (this.mowers.size() > 0) {
			boolean hasCommand = false;
			int commandRunTime=0;
			do {
				// clear the coordinateSet for this move step
				this.coordinateSet = new HashMap<Coordinate, Coordinate>();
				hasCommand = false;
				for (int i = 0; i < this.mowers.size(); i++) {

					Mower m = this.mowers.get(i);
					boolean res = m.executeCommand();
					hasCommand = hasCommand || res;
					if(hasCommand)this.hasCollision(m,commandRunTime);
				}
				
				if(hasCommand)commandRunTime++;

			} while (hasCommand);
		}

	}

	private boolean hasCollision(Mower m,int commandRunTime) {
		Util.debugPrint("init"+m.getInitPosition(), Util.LOG_DEBUG);
		Util.debugPrint("-----"+m.getId(), Util.LOG_DEBUG);
		Util.debugPrint("Prev"+m.getPrevLocation()+" "+m.getPrevLocation().getType(), Util.LOG_DEBUG);
		Util.debugPrint("Curr"+m.getCurrentLocation()+" "+m.getCurrentLocation().getType(), Util.LOG_DEBUG);
		Util.debugPrint("-----\n", Util.LOG_DEBUG);
		return this.hasCollision(m, m.getPrevLocation(),commandRunTime)
				|| this.hasCollision(m, m.getCurrentLocation(),commandRunTime);

	}

	private boolean hasCollision(Mower m, MowerCoordinate c,int commandRunTime) {
		Util.debugPrint("Mower!!"+m,Util.LOG_DEBUG);
		if (this.coordinateSet.containsKey(c)) {
			MowerCoordinate existing = (MowerCoordinate) this.coordinateSet
					.get(c);
			if (commandRunTime!=0&&c.getType() == existing.getType()) {
				this.collisions.add(new CollisionException(m, c.getCopy(),
						CollisionType.BUMP));
				return true;
			} else if (c.getFacing() != existing.getFacing()&&Math.abs(c.getFacing() - existing.getFacing()) % 180==0) {
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
	 * Get the adjacent coordinate block towards the current block.
	 * Only return if the block is inside the lawn and it is not visited before
	 * @param origin Current block coordinate
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
	 * Get MowerTasks based on pre-solved linear equations
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
	
	/**
	 * Assign tasks on the lawn to mowers. Main function for part 2
	 * @param numOfMowers
	 */
	public void autoTaskMowers(int numOfMowers)
	{
		this.currentMower=null;
		this.calculateMowersTask(numOfMowers);
		this.probeMower=new StandardMower(this,new MowerCoordinate(0,0,0));
		this.probeMower.mowLawn();
		this.assignMowerTasksByFootprint(this.probeMower.getFootPrint());
		Util.debugPrint(this.mowers.size(),Util.LOG_DEBUG);
	}
	
	
	/**
	 * Assign list of movement(s) to mowers
	 * @param footPrint
	 */
	public void assignMowerTasksByFootprint(LinkedList<FootPrint> footPrint)
	{
		for(FootPrint fp:footPrint)
		{
			Util.debugPrint(fp.toDebugString(),Util.LOG_DEBUG);
			Util.debugPrint("Mower Task:"+this.mowerTasks.size()+" "+this.mowerTasks.get(0).getBlocksCount(),Util.LOG_DEBUG);
			if(this.mowerTasks.size()>0)
			{
				if(this.currentMower==null)
				{
					if(!fp.getCommand().equals("M"))continue;
					this.currentMower=new StandardMower(fp.getMowerCoordinate().getCopy());
					Util.debugPrint("New Mower Created:"+fp.getMowerCoordinate().getCopy(),Util.LOG_DEBUG);
				}
				
				if(fp.getCommand().equals("M")&&!this.mowerTasks.get(0).assignBlock())
				{
					this.mowerTasks.remove(0);
					this.mowers.add(this.currentMower);
					this.currentMower=null;
					Util.debugPrint("Curent Mower Finished!",Util.LOG_DEBUG);
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
		
		//what if there is still task left
		if(this.mowerTasks.size()==1)
		{
			Util.debugPrint("Mower Task Left: "+this.mowerTasks.size(),Util.LOG_DEBUG);
			this.currentMower=new StandardMower(this.mowers.get(this.mowers.size()-1).getInitPosition().getCopy().action('M'));
			this.mowers.add(this.currentMower);
		}
		
	}
	


}
