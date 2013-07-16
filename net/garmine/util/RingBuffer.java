package net.garmine.util;

import java.util.Arrays;

/**
 * A fixed size FIFO array-based storage. AKA a ring buffer.
 * 
 * @author Garmine
 * @param <E> - element type in the buffer
 */
public class RingBuffer<E>{

	/** Element storage */
	private final Object[] VALUES;		//idiotic, stupid smoke & mirrors... <3 Java
	/** Length of {@link #VALUES} i.e. the max size */
	private final int MAX;
	/** Begin of data inside {@link #VALUES}, inclusive */
	private int begin = 0;
	/** End of data inside {@link #VALUES}, exclusive */
	private int end = 0;
	/** Flag if empty, needed for a corner case (begin==end if it's full OR empty!) */
	private boolean empty = true;
	
	/**
	 * Constructs a new RingBuffer of given size. Note: size cannot be modified after creation!
	 * @param size - The size of the buffer.
	 */
	public RingBuffer(int size){
		if (size<=0) throw new IllegalArgumentException("Size must be positive!");
		VALUES = new Object[size];
		MAX = size;
	}
	
	/**
	 * Gets the size of the buffer.
	 * @return The size of the buffer.
	 */
	public int size() {
		if(empty){
			return 0;
		}else if(begin < end){
			return end-begin;
		}else if(begin > end){
			return MAX-begin+end;
		}else{
			return MAX;
		}
	}

	/**
	 * Checks if the buffer is empty.
	 * @return True, if empty.
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Clears the buffer.
	 */
	public void clear() {
		for (int i=0; i<MAX; i++) VALUES[i] = null;
		empty = true;
		begin = end = 0;
	}

	/**
	 * Pushes an element on the buffer.
	 * @param e - element to push
	 * @return True, if successful.
	 */
	public boolean push(E e) {
		VALUES[end] = e;
		
		if (end==begin && !empty) begin++;
		end++;
		
		if (end==MAX) end=0;
		if (begin==MAX) begin=0;
		
		empty = false;
		return true;
	}

	/**
	 * Removes the first element of the buffer.
	 * @return The first element of the buffer.
	 */
	@SuppressWarnings("unchecked")
	public E pop() {
		if (empty) return null;
		
		E ret = (E)VALUES[begin];
		VALUES[begin] = null;
		
		begin++;
		if (begin==MAX) begin=0;
		
		if(begin == end){
			empty = true;
			begin = end = 0;
		}
		
		return ret;
	}

	/**
	 * Peeks the first element of the buffer (does NOT remove it!)
	 * @return The first element of the buffer.
	 */
	@SuppressWarnings("unchecked")
	public E peek() {
		if (empty) return null;
		return (E)VALUES[begin];
	}
	
	/**
	 * Gets the contents of the buffer.
	 * @return An array containing all the elements in the buffer.
	 */
	@SuppressWarnings("unchecked")
	public E[] getElements(){
		if (empty) return (E[]) new Object[0];
		Object ret[] = new Object[size()];
		int n=0;
		for(int i=begin;; i++){
			if (i==MAX) i=0;
			if (n!=0 && i==end) break;
			ret[n++] = VALUES[i];
		}
		return (E[])ret;
	}

	@Override
	public String toString(){
		return Arrays.toString(getElements());
	}
}
