package com.mower;

import java.util.LinkedList;
import com.mower.footprint.FootPrint;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;
import com.mower.lawn.MowerCoordinate.CoordinateType;


public class StandardMower extends Mower {


	public StandardMower(MowerCoordinate initPosition)
	{
		super(initPosition);
		this.footPrint=new LinkedList<FootPrint>();
	}
	
	public StandardMower(Lawn lawn,MowerCoordinate initPosition)
	{
		super(initPosition);
		this.lawn=lawn;
		this.footPrint=new LinkedList<FootPrint>();

	}
	
	 public boolean executeCommand()
	 {
		 if(this.command!=null)
		 {
			 String cmd=this.command.poll();
			 if(cmd!=null)
			 {
				 this.prevPosition=this.destPosition.getCopy();
				 this.prevPosition.setType(CoordinateType.START);
				 this.destPosition.action(cmd.charAt(0));
				 this.destPosition.setType(CoordinateType.END);
				 return true;
			 }
			 
		 }
		 return false;
	 }


	 /**
	  * Get the path to mow the entire lawn recursively
	  */
	public void mowLawn()
	{
		if(this.destPosition==null)this.destPosition=this.initPosition;
		
		MowerCoordinate pos=this.lawn.getAdjacentCoordinate(this.destPosition.getCopy());
		
		if(pos==null&&this.destPosition.getRotationCount()==4)
		{
			//no more moves
			while(this.footPrint.getLast().getCommand()=="R")
			{
				this.footPrint.removeLast();
			}
			return;
		}else if(pos==null)
		{
			FootPrint fp=new FootPrint(this.destPosition.getCopy(), "R");
			this.footPrint.add(fp);
			this.destPosition.rotateRight();
		}else
		{
			pos.setType(CoordinateType.END);
			FootPrint fp=new FootPrint(this.destPosition.getCopy(), "M");
			this.footPrint.add(fp);
			this.destPosition=pos;
			this.destPosition.resetRotationCount();
		}
		this.mowLawn();
	}
	

}
