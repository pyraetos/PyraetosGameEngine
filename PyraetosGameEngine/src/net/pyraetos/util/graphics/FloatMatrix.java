package net.pyraetos.util.graphics;

import static net.pyraetos.util.Sys.cos;
import static net.pyraetos.util.Sys.sin;
import net.pyraetos.util.Matrix;

public class FloatMatrix extends Matrix<Float>{

	public FloatMatrix(Float[][] matrix){
		super(matrix);
	}

	public FloatMatrix(FloatVector... columns){
		super(columns);
	}
	
	public FloatMatrix multiply(Matrix<Float> right){
		FloatMatrix product = FloatMatrix.zeros(right.getWidth());
		for(int x = 0; x < right.getWidth(); x++){
			for(int y = 0; y < this.getHeight(); y++){
				float dotProduct = 0f;
				for(int i = 0, j = 0; i < this.getWidth() && j < right.getHeight(); i++, j++){
					dotProduct += this.get(i, y) * right.get(x, j);
				}
				product.set(x, y, dotProduct);
			}
		}
		return product;
	}
	
	public static FloatMatrix identity(int size){
		Float[][] matrix = new Float[size][];
		for(int i = 0; i < size; i++){
			Float[] col = new Float[size];
			for(int j = 0; j < size; j++){
				col[j] = 0f;
			}
			col[i] = 1f;
			matrix[i] = col;
		}
		return new FloatMatrix(matrix);
	}
	
	public static FloatMatrix zeros(int rows, int cols){
		Float[][] matrix = new Float[cols][];
		for(int i = 0; i < cols; i++){
			Float[] column = new Float[rows];
			for(int j = 0; j < rows; j++)
				column[j] = 0f;
			matrix[i] = column;
		}
		return new FloatMatrix(matrix);
	}
	
	public static FloatMatrix zeros(int size){
		return zeros(size, size);
	}
	
	public static FloatMatrix rotation(float p, float y, float r){
		FloatMatrix pitch = FloatMatrix.zeros(3);
		FloatMatrix yaw = FloatMatrix.zeros(3);
		FloatMatrix roll = FloatMatrix.zeros(3);
		
		//pitch
		pitch.set(0, 0, 1f);	pitch.set(1, 0, 0f);		pitch.set(2, 0, 0f);
		pitch.set(0, 1, 0f);	pitch.set(1, 1, cos(p));	pitch.set(2, 1, -sin(p));
		pitch.set(0, 2, 0f);	pitch.set(1, 2, sin(p));	pitch.set(2, 2, cos(p));
		
		//yaw
		yaw.set(0, 0, cos(y));	yaw.set(1, 0, 0f);	yaw.set(2, 0, sin(y));
		yaw.set(0, 1, 0f);		yaw.set(1, 1, 1f);	yaw.set(2, 1, 0f);
		yaw.set(0, 2, -sin(y));	yaw.set(1, 2, 0f);	yaw.set(2, 2, cos(y));
		
		//roll
		roll.set(0, 0, cos(r));	roll.set(1, 0, -sin(r));	roll.set(2, 0, 0f);
		roll.set(0, 1, sin(r));	roll.set(1, 1, cos(r));		roll.set(2, 1, 0f);
		roll.set(0, 2, 0f);		roll.set(1, 2, 0f);			roll.set(2, 2, 1f);
		
		return yaw.multiply(pitch).multiply(roll);
	}
	
	public static FloatMatrix rotation(FloatVector v){
		return rotation(v.getX(), v.getY(), v.getZ());
	}
	
}
