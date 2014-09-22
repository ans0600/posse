package com.mower.footprint;

import com.mower.lawn.MowerCoordinate;

public class FootPrint2 {

	private MowerCoordinate mowerCoordinate;
	
	private String command;
	
	public FootPrint2( MowerCoordinate mowerCoordinate,String command)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FootPrint2 [mowerCoordinate=" + mowerCoordinate + ", command="
				+ command + "]";
	}
	
	
	
}
