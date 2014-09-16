package com.mower.lawn;

public class Lawn extends Grid {

	public Lawn(int boundryX, int boundryY) {
		super(boundryX, boundryY);
	}
	
	public boolean isInside(MowerCoordinate coordinate)
	{
		return coordinate.getX()<=this.boundryX&&coordinate.getY()<=this.boundryY
				&&coordinate.getX()>=0&&coordinate.getY()>=0;	
	}
	
	public void getMowerAssignment(int numOfMowers)
	{
		
		
	}
	
	

}
