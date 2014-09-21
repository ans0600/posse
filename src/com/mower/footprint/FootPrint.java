package com.mower.footprint;



public interface FootPrint<E>  {


	public E getLast();
	
	public void removeLast();
	
	public boolean add(E e);
	
	public String toString();
}
