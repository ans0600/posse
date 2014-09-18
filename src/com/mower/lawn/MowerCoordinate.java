package com.mower.lawn;

public class MowerCoordinate extends Coordinate {

	public enum CoordinateType {
		START,END
	}
	
	private int rotationCount=0;
	private CoordinateType type=CoordinateType.START;

	
	public CoordinateType getType() {
		return type;
	}

	public void setType(CoordinateType type) {
		this.type = type;
	}

	public MowerCoordinate(int x, int y, int facing) {
		super(x, y);
		this.facing = facing;
	}
	
	//Todo fixme
	public MowerCoordinate action(char cmd)
	{
		cmd=Character.toUpperCase(cmd);
		
		switch(cmd)
		{
			case 'L':
				this.facing-=90;
				break;
			case 'R':
				this.facing+=90;
				break;
			case 'M':
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
				break;
			default:
				break;
		}
		
		if(this.facing<0)this.facing+=360;
		
		return this;
		
	}
	
//	public MowerCoordinate turn(char dir)
//	{
//		dir=Character.toUpperCase(dir);
//		//MowerCoordinate destination=new MowerCoordinate(this.x, this.y, this.facing);
//		
//		switch(dir)
//		{
//			case 'L':
//				this.facing-=90;
//				break;
//			case 'R':
//				this.facing+=90;
//				break;
//			default:
//				break;
//		}
//		
//		if(this.facing<0)this.facing+=360;
//		
//		return this;
//	}
//
//	public MowerCoordinate move() {
//		
//	//	MowerCoordinate destination=new MowerCoordinate(this.x, this.y, this.facing);
//		
//		int dir=(facing/90)%4;
//		
//		switch (dir) {
//			case 0:
//				this.y += 1;
//				break;
//			case 2:
//				this.y-=1;
//				break;
//			case 3:
//				this.x-=1;
//				break;
//			case 1:
//				this.x+=1;
//			default:
//				break;
//		}
//		return this;
//
//	}
	
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
	
	
	public String getFacingText()
	{
		int dir=(this.facing/90)%4;
		
		switch (dir) {
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
		return x+" "+y+" "+this.getFacingText();
	}
	
	
	
	public MowerCoordinate getCopy()
	{
		MowerCoordinate m=new MowerCoordinate(x, y, facing);
		m.rotationCount=this.rotationCount;
		return m;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
}
