package com.mower;

import java.util.LinkedList;

import com.mower.exception.FatalException;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;


public class StandardMower implements Mower {

	
	protected Lawn lawn;
	protected MowerCoordinate initPosition;
	protected MowerCoordinate destPosition;
	
	protected LinkedList<String> footPrint;
	
	
	public enum TurnCMD {
		L,R
	}

	public StandardMower(Lawn lawn,MowerCoordinate initPosition)
	{
		this.lawn=lawn;
		this.initPosition=initPosition;
		this.footPrint=new LinkedList<String>();
		
	}
	
	
	public boolean move(String commandStr) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean move(char command) throws FatalException {
		
		if(!this.validateCommand(command))throw new FatalException("Invalid Command: "+command);
	
		
		return false;
	}


	public MowerCoordinate getCurrentLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public void mowLawn()
	{
		if(this.destPosition==null)this.destPosition=this.initPosition;
		
		System.err.println(this.destPosition.toString());
		System.err.println(this.destPosition.getRotationCount());
		
		
		MowerCoordinate pos=this.lawn.getAdjacentCoordinate(this.destPosition.getCopy());
		
		if(pos==null&&this.destPosition.getRotationCount()==4)
		{
			//no more moves
			//this.footPrint.removeLast();
			while(this.footPrint.getLast()=="R")
			{
				this.footPrint.removeLast();
			}
			return;
		}else if(pos==null)
		{
			this.footPrint.add("R");
			this.destPosition.rotateRight();
		}else
		{
			this.footPrint.add("M");
			this.destPosition=pos;
			this.destPosition.resetRotationCount();
		}
		this.mowLawn();
	}
	
	public LinkedList<String> getFootPrint()
	{
		return this.footPrint;
	}
	
	
	private boolean validateCommand(char command)
	{
		for (TurnCMD f : TurnCMD.values())
		{
			if(f.name().equals(command))
			{				
				return true;
			}		
		}
		return false;
		
	}

}
