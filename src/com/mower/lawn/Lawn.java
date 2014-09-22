package com.mower.lawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.mower.Mower;
import com.mower.StandardMower;
import com.mower.exception.CollisionException;
import com.mower.exception.CollisionException.CollisionType;
import com.mower.footprint.FootPrint;
import com.mower.footprint.FootPrint2;
import com.mower.footprint.FootPrintHandler;
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
					System.err.println(i + " " + res);
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
		if (this.coordinateSet.containsKey(c)) {
			// System.err.println("Key Exist: " + c.toString());
			MowerCoordinate existing = (MowerCoordinate) this.coordinateSet
					.get(c);
			if (c.getType() == existing.getType()) {
				// System.err.println("Collision Type A!!");
				this.collisions.add(new CollisionException(m, c,
						CollisionType.BUMP));
				return true;
			} else if (Math.abs(c.getFacing() - existing.getFacing()) == 180) {
				// System.err.println("Collision Type B");
				this.collisions.add(new CollisionException(m, c,
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
				
			}else if(totalBlocks == numOfMowers)
			{
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
			    
				if(lessTaskCount>1)
				{
					for(int i=0;i<mowersDoingLessTask;i++)
					{
						MowerTask t=new MowerTask(lessTaskCount-1);
						this.mowerTasks.add(t);
					}
				}
				
				
				for(int i=0;i<mowersDoingMoreTask;i++)
				{
					MowerTask t=new MowerTask(moreTaskCount-1);
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
		
		LinkedList<FootPrint2> footPrint=this.probeMower.getFootPrint();
		String prevCmd=null;
		for(FootPrint2 fp:footPrint)
		{
			System.err.println(fp.toString());
			//System.err.println("Mower Task:"+this.mowerTasks.size()+" "+this.mowerTasks.get(0).getBlocksCount());
			if(this.mowerTasks.size()>0)
			{
				if(this.currentMower==null)
				{
					if(this.mowers.size()>0)
					{
						if(this.jumpNext)
						{
						    if(fp.getCommand().equals("M"))this.jumpNext=false;
						    System.err.println("Jump!!");
							continue;
						}
					}
					this.currentMower=new StandardMower(fp.getMowerCoordinate().getCopy());
					System.err.println("New Mower Crated:"+fp.getMowerCoordinate().getCopy());
				}
				this.currentMower.insertCommand(fp.getCommand());
				System.err.println("Insert:"+fp.getCommand());
				if(fp.getCommand().equals("M")&&!this.mowerTasks.get(0).assignBlock())
				{
					this.mowerTasks.remove(0);
					
					this.mowers.add(this.currentMower);
					this.currentMower=null;
					this.jumpNext=true;
				}
			}else
			{
				//assign special stand still tasks
				 
				if(this.mowers.size()>0)
				{
					if(this.jumpNext)
					{
					    if(fp.getCommand().equals("M"))this.jumpNext=false;
					    prevCmd=fp.getCommand();
					    System.err.println("Jump22222");
						continue;
					}
				}
				
				if(prevCmd==null||prevCmd.equals("M"))
				{
					Mower m=new StandardMower(fp.getMowerCoordinate().getCopy());
					this.mowers.add(m);
					System.err.println("New Mower Crated222:"+fp.getMowerCoordinate().getCopy());
				}
				prevCmd=fp.getCommand();
			}
			
			
		}
		

		
		
		System.err.println(this.mowers.size());
	}

//	@Override
//	public void handleFootPrintAdd(Object[] args,Mower next) {
//		System.err.println("Cmd is: "+args[0]+" "+next.getCurrentLocation());
//		if(this.mowerTasks.size()>0&&args.length>0)
//		{
//			String cmd=(String)args[0];
//			
//			if(this.currentMower==null)
//			{
//				
//				//the next mower need to start at the next block
////				if(this.mowers.size()>0)
////				{
////					Mower lastMower=this.mowers.get(this.mowers.size()-1);
////					if(lastMower.getCurrentLocation().getX()==this.probeMower.getCurrentLocation().getX()&&
////							lastMower.getCurrentLocation().getY()==this.probeMower.getCurrentLocation().getY()&&
////							lastMower.getCurrentLocation().getFacing()==this.probeMower.getCurrentLocation().getFacing())
////						{
////							System.err.println("SSSSSS");
////							return;
////						}
////				}
//				if(this.mowers.size()>0)
//				{
//					if(this.jumpNext)
//					{
//					    if(cmd.equals("M"))this.jumpNext=false;
//					    System.err.println("Jump!!");
//						return;
//					}
//				}
//				this.currentMower=new StandardMower(this.probeMower.getCurrentLocation().getCopy());
//				System.err.println("New Mower Crated:"+this.probeMower.getCurrentLocation());
//					
//				
//			}
//			this.currentMower.insertCommand(cmd);
//			System.err.println("Insert:"+cmd);
//
//			 if(cmd.equals("M")&&!this.mowerTasks.get(0).assignBlock())
//			{
//				this.mowerTasks.remove(0);
//				this.mowers.add(this.currentMower);
//				this.currentMower=null;
//				this.jumpNext=true;
//			}
//			
//		}
//		else if(args.length>0)
//		{
//			//assign special stand still tasks
//			String cmd=(String)args[0];
//			
//			if(this.mowers.size()>0)
//			{
//				if(this.jumpNext)
//				{
//				    if(cmd.equals("M"))this.jumpNext=false;
//				    System.err.println("Jump22222");
//					return;
//				}
//			}
//			
//			if(cmd.equals("M"))
//			{
//				Mower m=new StandardMower(this.probeMower.getCurrentLocation().getCopy());
//				this.mowers.add(m);
//				System.err.println("New Mower Crated:"+this.probeMower.getCurrentLocation());
//			}
//			
//		}
		
//	}

}
