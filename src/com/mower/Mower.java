package com.mower;

import java.util.LinkedList;
import java.util.Queue;

import com.mower.exception.FatalException;
import com.mower.footprint.FootPrint;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;
import com.mower.lawn.MowerCoordinate.CoordinateType;

public abstract class Mower {

	public enum MowerCMD {
		L('L'),R('R'),M('M');
		private char value;
		
		private MowerCMD(char value)
		{
			this.setValue(value);
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}
	}
	
	protected int id;
	
	protected Lawn lawn;
	protected MowerCoordinate initPosition;
	protected MowerCoordinate prevPosition;
	protected MowerCoordinate destPosition;
	
	protected LinkedList<FootPrint> footPrint;
	
	protected Queue<String> command;
	
	
	public Mower(MowerCoordinate initPosition)
	{
		this.initPosition=initPosition;
		this.destPosition=this.initPosition.getCopy();
		this.prevPosition=this.initPosition.getCopy();
		this.destPosition.setType(CoordinateType.END);
		this.command=new LinkedList<String>();
	}
	
	protected boolean validateCommand(char c)
	{
		for (MowerCMD f : MowerCMD.values())
		{
			if(f.name().equals(String.valueOf(c)))
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
	
	public MowerCoordinate getPrevLocation() {
		return this.prevPosition;
	}
	
	public Queue<String> getCommand()
	{
		return this.command;
	}
	
	public String getCommandString(boolean optimized)
	{
		if(this.command!=null)
		{
			StringBuilder sb=new StringBuilder();
			int count=0;
			for(String s:this.command)
			{
				if(optimized&&!s.equals("M")&&count==this.command.size()-1)break;
				sb.append(s);
				count++;
			}
			return sb.toString();
		}
		return "";
		
	}
	
	public void setCurrentLocation(MowerCoordinate destPosition) {
		this.destPosition=destPosition;
	}
	
	public void setLawn(Lawn lawn)
	{
		this.lawn=lawn;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCommandStr(String command) throws FatalException
	{
		this.command.clear();
		
		for(int i=0;i<command.length();i++)
		{
			char c=command.charAt(i);
			if(this.validateCommand(c))
			{
				this.command.add(String.valueOf(c));
			}
			else
			{
				throw new FatalException("Invalid Command:"+ String.valueOf(c));
			}

		}
		
	}
	
	public String getCurrentCommand(boolean shouldRemoveFromQueue)
	{
		return shouldRemoveFromQueue?this.command.poll():this.command.peek();
		
	}
	
	public boolean insertCommand(String c)
	{
		if(this.command==null)this.command=new LinkedList<String>();
		return this.command.add(c);
	}
	
	public LinkedList<FootPrint> getFootPrint()
	{
		return this.footPrint;
	}
	
	abstract public boolean executeCommand();
	
	abstract public void mowLawn();
	
}
