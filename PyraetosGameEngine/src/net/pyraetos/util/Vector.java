package net.pyraetos.util;

public class Vector<T>{

	protected T[] elements;
	protected int size;
	
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public Vector(T... elements){
		size = elements.length;
		if(size == 0)
			this.elements = (T[]) new Object[1];
		else
			this.elements = elements;
	}
	
	public T getX(){
		return elements[0];
	}
	
	public T getY(){
		return elements[1];
	}

	public T getZ(){
		return elements[2];
	}
	
	public T get(int index){
		return elements[index];
	}
	
	public void set(int index, T value){
		elements[index] = value;
	}
	
	public int space(){
		return elements.length;
	}
	
	public int size(){
		return size;
	}
	
	@SuppressWarnings("unchecked")
	public void pushBack(T value){
		if(elements[space() - 1] != null){
			Object[] temp = new Object[space() * 2];
			for(int i = 0; i < size(); i++){
				temp[i] = elements[i];
			}
			temp[space()] = value;
			elements = (T[])temp;
		}else{
			elements[size] = value;
		}
		size++;
	}
	
	@SuppressWarnings("unchecked")
	public void erase(int index){
		if(size <= index || index > size)
			return;
		if(size == 1){
			elements = (T[])new Object();
			size = 0;
			return;
		}
		Object[] temp = new Object[elements.length - 1];
		for(int i = 0, n = 0; i < elements.length; i++){
			if(i == index)
				continue;
			temp[n] = elements[i];
			n++;
		}
		elements = (T[])temp;
		size--;
	}
	
	public void eraseLast(){
		erase(size - 1);
	}
	
	@Override
	public String toString(){
		if(size == 0)
			return "[]";
		String string = "[";
		int last = size - 1;
		for(int i = 0; i < last; i++){
			if(elements[i + 1] == null){
				last = i;
				break;
			}
			string += elements[i] + ", ";
		}
		return string + elements[last].toString() + "]";
	}
}
