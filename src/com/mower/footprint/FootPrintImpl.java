package com.mower.footprint;

import java.util.LinkedList;

public class FootPrintImpl<E> implements FootPrint<E> {

	private LinkedList<E> l;
	
	public FootPrintImpl()
	{
		l=new LinkedList<E>();
	}
	
	@Override
	public E getLast() {
		return l.getLast();
	}

	@Override
	public void removeLast() {
		l.removeLast();
	}

	@Override
	public boolean add(E e) {
		return l.add(e);
		
	}
	public String toString()
	{
		return l.toString();
	}

}
