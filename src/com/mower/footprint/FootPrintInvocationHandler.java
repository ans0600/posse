package com.mower.footprint;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.mower.Mower;

public class FootPrintInvocationHandler implements InvocationHandler {

	private FootPrint footprint;
	private FootPrintHandler footprintHandler;
	
	public FootPrintInvocationHandler(FootPrint footprint,FootPrintHandler handler)
	{
		this.footprint=footprint;
		this.footprintHandler=handler;
		
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object res=method.invoke(this.footprint, args);
		//System.err.println(method.getName());
		if(method.getName().equals("add")&&this.footprintHandler!=null)
		{
			this.footprintHandler.handleFootPrintAdd(args,(Mower) res);
		}
		
		return res;
		
	}

}
