package com.mower.util;

public class Util {

	public static final int LOG_DEBUG=3;
	public static final int LOG_STD=0;
	
	private static int logLevelSettings;
	
	
	public static void debugPrint(Object message,int logLevel)
	{
		if(logLevelSettings>=logLevel)
		{
			System.err.println(message);
		}
		
	}
	
	public static void setLogLevel(int logLevel)
	{
		logLevelSettings=logLevel;
	}
	
}
