package com.mower.lawn;

public class MowerCoordinate extends Coordinate {


	private int facing=0;
	private int rotationCount=0;

	public MowerCoordinate(int x, int y, int facing) {
		super(x, y);
		this.facing = facing;
	}
	
	public MowerCoordinate turn(char dir)
	{
		dir=Character.toUpperCase(dir);
		//MowerCoordinate destination=new MowerCoordinate(this.x, this.y, this.facing);
		
		switch(dir)
		{
			case 'L':
				this.facing-=90;
				break;
			case 'R':
				this.facing+=90;
				break;
			default:
				break;
		}
		
		if(this.facing<0)this.facing+=360;
		
		return this;
	}

	public MowerCoordinate move() {
		
	//	MowerCoordinate destination=new MowerCoordinate(this.x, this.y, this.facing);
		
		int dir=(facing/90)%4;
		
		switch (dir) {
			case 0:
				this.y += 1;
				break;
			case 2:
				this.y-=1;
				break;
			case 3:
				this.x-=1;
				break;
			case 1:
				this.x+=1;
			default:
				break;
		}
		return this;

	}
	
	public MowerCoordinate rotateRight() {
		this.facing+=90;
	//	if(this.facing<0)this.facing+=360;
		this.rotationCount++;
		return this;
	}
	
	public int getRotationCount()
	{
		return this.rotationCount;
	}
	
	public void resetRotationCount()
	{
		this.rotationCount=0;
	}
	
	
	public static String getFacingText(int facing)
	{
		facing=(facing/90)%4;
		
		switch (facing) {
			case 0:
				return "N";
			case 2:
				return "S";
			case 3:
				return "W";
			case 1:
				return "E";
			default:
				break;
		}
		return "";
		
	}

	@Override
	public String toString() {
//		return "MowerCoordinate [facing=" + facing + ", x=" + x + ", y=" + y
//				+ "]";
		
		return x+" "+y+" "+getFacingText(this.facing);
	}
	
	public MowerCoordinate getCopy()
	{
		MowerCoordinate m=new MowerCoordinate(x, y, facing);
		m.rotationCount=this.rotationCount;
		return m;
		
	}
	


}
