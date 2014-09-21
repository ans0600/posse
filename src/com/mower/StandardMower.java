package com.mower;

import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

import com.mower.footprint.FootPrint;
import com.mower.footprint.FootPrintImpl;
import com.mower.footprint.FootPrintInvocationHandler;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;
import com.mower.lawn.MowerCoordinate.CoordinateType;


public class StandardMower extends Mower {


	public StandardMower(MowerCoordinate initPosition)
	{
		super(initPosition);
		//this.footPrint=new FootPrint<String>();

		this.footPrint=(FootPrint<String>) Proxy.newProxyInstance(FootPrint.class.getClassLoader(), 
				new Class<?>[]{FootPrint.class}, new FootPrintInvocationHandler(new FootPrintImpl<String>(),null));
	}
	
	public StandardMower(Lawn lawn,MowerCoordinate initPosition)
	{
		super(initPosition);
		this.lawn=lawn;
		this.footPrint=new FootPrintImpl<String>();	
		this.footPrint=(FootPrint<String>) Proxy.newProxyInstance(FootPrint.class.getClassLoader(), 
				new Class<?>[]{FootPrint.class}, new FootPrintInvocationHandler(new FootPrintImpl<String>(),lawn));

	}
	
	 public boolean executeCommand()
	 {
		 if(this.command!=null)
		 {
			 String cmd=this.command.poll();
			 if(cmd!=null)
			 {
				 this.destPosition.action(cmd.charAt(0));
				 this.destPosition.setType(CoordinateType.END);
				 return true;
			 }
			 
		 }
		 return false;
	 }



	public void mowLawn()
	{
		if(this.destPosition==null)this.destPosition=this.initPosition;
		
//		System.err.println(this.destPosition.toString());
//		System.err.println(this.destPosition.getRotationCount());
		
		
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
	
	
	
	
	

}
