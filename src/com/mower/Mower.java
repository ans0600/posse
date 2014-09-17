package com.mower;

import java.util.LinkedList;
import java.util.Queue;

import com.mower.exception.FatalException;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;

public abstract class Mower {

	public enum MowerCMD {
		L('L'),R('R'),M('M');
		private char value;
		
		private MowerCMD(char value)
		{
			this.value=value;
		}
	}
	
	protected Lawn lawn;
	protected MowerCoordinate initPosition;
	protected MowerCoordinate destPosition;
	
	protected LinkedList<String> footPrint;
	
	protected Queue<String> command;
	
	
	protected boolean validateCommand(char c)
	{
		for (MowerCMD f : MowerCMD.values())
		{
			if(f.name().equals(c))
			{				
				return true;
			}		
		}
		return false;
		
	}
	
	public MowerCoordinate getInitPosition()
	{
		return this.initPosition;
	}
	
	public MowerCoordinate getCurrentLocation() {
		return this.destPosition;
	}
	
	public void setLawn(Lawn lawn)
	{
		this.lawn=lawn;
	}
	
	public void setCommandStr(String command) throws FatalException
	{
		this.command=new LinkedList<String>();
		
		for(int i=0;i<command.length();i++)
		{
			char c=command.charAt(i);
			if(true||this.validateCommand(c))
			{
				this.command.add(String.valueOf(c));
			}
			else
			{
				throw new FatalException("Invalid Command:"+ String.valueOf(c));
			}

		}
		
	}
	
	abstract public boolean executeCommand() throws FatalException;
	
	abstract public void mowLawn();
	
}
