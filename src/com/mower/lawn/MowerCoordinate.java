package com.mower.lawn;

public class MowerCoordinate extends Coordinate {


	private int facing=0;

	public MowerCoordinate(int x, int y, int facing) {
		super(x, y);
		this.facing = facing;
	}
	
	public MowerCoordinate turn(char dir)
	{
		dir=Character.toUpperCase(dir);
		MowerCoordinate destination=new MowerCoordinate(this.x, this.y, this.facing);
		
		switch(dir)
		{
			case 'L':
				destination.facing-=90;
				break;
			case 'R':
				destination.facing+=90;
				break;
			default:
				break;
		}
		
		if(destination.facing<0)destination.facing+=360;
		
		return destination;
	}

	public MowerCoordinate move() {
		
		MowerCoordinate destination=new MowerCoordinate(this.x, this.y, this.facing);
		
		int dir=(facing/90)%4;
		
		switch (dir) {
			case 0:
				destination.y += 1;
				break;
			case 2:
				destination.y-=1;
				break;
			case 3:
				destination.x-=1;
				break;
			case 1:
				destination.x+=1;
			default:
				break;
		}
		return destination;

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
	


}
