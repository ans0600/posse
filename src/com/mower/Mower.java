package com.mower;

import com.mower.exception.FatalException;
import com.mower.lawn.MowerCoordinate;

public interface Mower {

	public boolean move(String commandStr);
	
	public boolean move(char command) throws FatalException;
	
	public MowerCoordinate getCurrentLocation();
	
	
	
}
