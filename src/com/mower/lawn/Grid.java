package com.mower.lawn;

public class Grid {

	protected int boundryX;
	
	protected int boundryY;
	
	protected boolean[][] visited;
	
	public Grid(int boundryX,int boundryY)
	{
		this.boundryX=boundryX;
		this.boundryY=boundryY;
		//TODO double check
		this.visited=new boolean[boundryX+1][boundryY+1];
		//TODO set init point to visited
		this.visited[0][0]=true;
	}
	
	public boolean isInside(MowerCoordinate coordinate)
	{
		return coordinate.getX()<=this.boundryX&&coordinate.getY()<=this.boundryY
				&&coordinate.getX()>=0&&coordinate.getY()>=0;	
	}
	
	public MowerCoordinate getAdjacentCoordinate(MowerCoordinate origin)
	{
		MowerCoordinate dest=origin.move();
		
		if(this.isInside(dest)&&!this.visited[origin.x][origin.y])
		{
			this.visited[origin.x][origin.y]=true;
			return dest;
			
		}else
		{
			return null;
		}
		
	}
	
}
