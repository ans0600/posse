package com.mower;

import java.util.LinkedList;

import com.mower.exception.FatalException;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;
import com.mower.lawn.MowerCoordinate.CoordinateType;


public class StandardMower extends Mower {


	public StandardMower(MowerCoordinate initPosition)
	{
		this.initPosition=initPosition;
		this.footPrint=new LinkedList<String>();		
	}
	
	public StandardMower(Lawn lawn,MowerCoordinate initPosition)
	{
		this.lawn=lawn;
		this.initPosition=initPosition;
		this.footPrint=new LinkedList<String>();
		
	}
	
	
	 public boolean executeCommand()
	 {
		 if(this.command!=null)
		 {
			 String cmd=this.command.poll();
			 if(cmd!=null)
			 {
				 this.destPosition=this.initPosition.getCopy().action(cmd.charAt(0));
				 this.destPosition.setType(CoordinateType.END);
				 return true;
			 }
			 
		 }
		 return false;
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
			pos.setType(CoordinateType.END);
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
	
	
	

}
