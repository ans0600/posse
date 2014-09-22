package com.mower.task;

public class MowerTask {

	private int blocksCount;
	
	private boolean inProgress;
	
	public MowerTask(int blocksCount)
	{
		this.blocksCount=blocksCount;
		this.inProgress=false;
	}
	
	public int getBlocksCount()
	{
		return this.blocksCount;
	}
	
	public boolean assignBlock()
	{
		this.inProgress=true;
		this.blocksCount--;
		return this.blocksCount>=1;
	}
	public boolean isInProgress()
	{
		return this.inProgress;
	}
	
}
