package com.mower.lawn;

public class Coordinate {

	protected int x;
	protected int y;
	protected int facing=0;
	
	public Coordinate(int x, int y)
	{
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	

	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	
	public static int getFacingByName(String f)
	{
		switch(f)
		{
			case "N":
				return 0;
			case "E":
				return 90;
			case "S":
				return 180;
			case "W":
				return 270;
			default:
					return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + facing;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (facing != other.facing)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}



	
	
	
	
	//abstract public MowerCoordinate getCopy();
	
}
