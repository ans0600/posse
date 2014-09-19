package com.mower.lawn;

public class Grid {

	protected Coordinate boundry;
	
	protected boolean[][] visited;
	
	public Grid(Coordinate boundry)
	{
		this.boundry=boundry;
		//TODO double check
		this.visited=new boolean[this.boundry.getX()+1][this.boundry.getY()+1];
		//TODO set init point to visited
		this.visited[0][0]=true;
	}
	
	public boolean isInside(Coordinate coordinate)
	{
		return coordinate.getX()<=this.boundry.getX()&&coordinate.getY()<=this.boundry.getY()
				&&coordinate.getX()>=0&&coordinate.getY()>=0;	
	}
	
	public int getBlockCount()
	{
		if(this.boundry!=null)
		{
			return (this.boundry.getX()+1)*(this.boundry.getY()+1);
		}
		return 0;
	}
	
	
	
}
