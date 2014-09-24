package com.mower.footprint;

import com.mower.lawn.MowerCoordinate;

public class FootPrint {

	private MowerCoordinate mowerCoordinate;
	
	private String command;
	
	public FootPrint( MowerCoordinate mowerCoordinate,String command)
	{
		this.mowerCoordinate=mowerCoordinate;
		this.command=command;
	}

	public MowerCoordinate getMowerCoordinate() {
		return mowerCoordinate;
	}

	public String getCommand() {
		return command;
	}


	@Override
	public String toString() {
		return command;
	}
	
	public String toDebugString() {
		return "FootPrint2 [mowerCoordinate=" + mowerCoordinate + ", command="
				+ command + "]";
	}
	
	
	
	
	
}
