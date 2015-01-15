package net.pyraetos.util.graphics;

import static net.pyraetos.util.Sys.cos;
import static net.pyraetos.util.Sys.sin;
import net.pyraetos.util.Matrix;

public class FloatMatrix extends Matrix<Float>{

	public FloatMatrix(Float[][] matrix){
		super(matrix);
	}

	public FloatMatrix(FloatVector... rows){
		super(rows);
	}
	
	public FloatMatrix(int rows, int cols){
		super(rows, cols);
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
	
	public static FloatMatrix translation(float dx, float dy, float dz){
		FloatMatrix m = new FloatMatrix(4, 4);
		
		m.set(0, 0, 1f);	m.set(1, 0, 0f);	m.set(2, 0, 0f);	m.set(3, 0, dx);
		m.set(0, 1, 0f);	m.set(1, 1, 1f);	m.set(2, 1, 0f);	m.set(3, 1, dy);
		m.set(0, 2, 0f);	m.set(1, 2, 0f);	m.set(2, 2, 1f);	m.set(3, 2, dz);
		m.set(0, 3, 0f);	m.set(1, 3, 0f);	m.set(2, 3, 0f);	m.set(3, 3, 1f);
		
		return m;
		
	}
	
	public static FloatMatrix rotation(float p, float y, float r){
		float sp = sin(p);	float cp = cos(p);
		float sy = sin(y);	float cy = cos(y);
		float sr = sin(r);	float cr = cos(r);
		FloatMatrix m = new FloatMatrix(4, 4);

		m.set(0, 0, cy*cr + sy*sp*sr);	m.set(1, 0, -cy*sr + sy*sp*cr);	m.set(2, 0, sy*cp);	m.set(3, 0, 0f);
		m.set(0, 1, cp*sr);				m.set(1, 1, cp*cr);				m.set(2, 1, -sp);	m.set(3, 1, 0f);
		m.set(0, 2, -sy*cr + cy*sp*sr);	m.set(1, 2, sy*sr + cy*sp*cr);	m.set(2, 2, cy*cp);	m.set(3, 2, 0f);
		m.set(0, 3, 0f);				m.set(1, 3, 0f);				m.set(2, 3, 0f);	m.set(3, 3, 1f);
		
		return m;
	}
	
	public static FloatMatrix scale(float i, float j, float k){
		FloatMatrix m = new FloatMatrix(4, 4);

		m.set(0, 0, i);		m.set(1, 0, 0f);	m.set(2, 0, 0f);	m.set(3, 0, 0f);
		m.set(0, 1, 0f);	m.set(1, 1, j);		m.set(2, 1, 0f);	m.set(3, 1, 0f);
		m.set(0, 2, 0f);	m.set(1, 2, 0f);	m.set(2, 2, k);		m.set(3, 2, 0f);
		m.set(0, 3, 0f);	m.set(1, 3, 0f);	m.set(2, 3, 0f);	m.set(3, 3, 1f);
		return m;
	}
	
	public static FloatMatrix translation(FloatVector v){
		return translation(v.getX(), v.getY(), v.getZ());
	}
	
	public static FloatMatrix rotation(FloatVector v){
		return rotation(v.getX(), v.getY(), v.getZ());
	}
	
	public static FloatMatrix scale(FloatVector v){
		return scale(v.getX(), v.getY(), v.getZ());
	}
	
}
