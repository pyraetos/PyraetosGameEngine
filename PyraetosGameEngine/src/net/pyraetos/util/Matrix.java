package net.pyraetos.util;

public class Matrix<T>{

	private T[][] matrix;
	
	public Matrix(T[][] matrix){
		if(matrix.length == 0 || matrix[0].length == 0){
			System.err.println("Matrix cannot be 0 in any dimension!");
			System.exit(1);
		}
		this.matrix = matrix;
	}
	
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public Matrix(Vector<T>... rows){
		if(rows.length == 0 || rows[0].size() == 0){
			System.err.println("Matrix cannot be 0 in any dimension!");
			System.exit(1);
		}
		Object[][] matrix = new Object[rows[0].size()][rows.length];
		for(int i = 0; i < rows.length/*2*/; i++)
			for(int j = 0; j < rows[i].size()/*5*/; j++){
				matrix[j][i] = rows[i].get(j);
			}
		this.matrix = (T[][])matrix;
	}
	
	public T get(int x, int y){
		try{
			return matrix[x][y];
		}catch(Exception e){
			return null;
		}
	}
	
	public void set(int x, int y, T t){
		try{
			matrix[x][y] = t;
		}catch(Exception e){
			return;
		}
	}
	
	public int getWidth(){
		return matrix.length;
	}
	
	public int getHeight(){
		return matrix[0].length;
	}
	
	@Override
	public String toString(){
		String string = "";
		for(int y = 0; y < getHeight(); y++){
			for(int x = 0; x < getWidth(); x++){
				string += get(x, y) + " ";
			}
			string += "\n";
		}
		return string;
	}

}