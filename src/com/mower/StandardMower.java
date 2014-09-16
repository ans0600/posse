package com.mower;

import com.mower.exception.FatalException;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;


public class StandardMower implements Mower {

	
	protected Lawn lawn;
	protected MowerCoordinate initPosition;
	protected MowerCoordinate destPosition;
	
	public enum TurnCMD {
		L,R
	}

	public StandardMower(Lawn lawn,MowerCoordinate initPosition)
	{
		this.lawn=lawn;
		this.initPosition=initPosition;
		
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
