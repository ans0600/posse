package com.mower.exception;

import com.mower.Mower;
import com.mower.lawn.Coordinate;
import com.mower.lawn.MowerCoordinate;

public class CollisionException extends FatalException {
	
	public enum CollisionType {
		BUMP("bump into other mower(s)"),RUNOVER("run over other mower(s)");
		private String value;
		
		private CollisionType(String value)
		{
			this.value=value;
		}
	}
	
	private MowerCoordinate coordinate;
	private Mower mower;
	private CollisionType collisionType;

	
	public CollisionException(Mower m, MowerCoordinate c, CollisionType type)
	{
		this.mower=m;
		this.coordinate=c;
		this.collisionType=type;
		System.err.println("INit"+m.toString());
	}

//	public void setCoordinate(Coordinate coordinate) {
//		this.coordinate = coordinate;
//	}
//
//	public void setMower(Mower mower) {
//		this.mower = mower;
//	}
//	
//	
//	public void setCollisionType(CollisionType collisionType) {
//		this.collisionType = collisionType;
//	}
	
	public MowerCoordinate getCoordinate() {
		return coordinate;
	}

	public Mower getMower() {
		return mower;
	}

	public CollisionType getCollisionType() {
		return collisionType;
	}
	

	@Override
	public String toString() {
		return "Mower ID:"+mower.getId()+" will "+collisionType.value+" at "+coordinate;
	}



	
	

}
