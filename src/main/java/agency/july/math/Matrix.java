package agency.july.math;

public class Matrix {

	// Произведение матрицы на вектор-столбец
	public static double[] multiplyByVector (double[][] A, double[] V) { // A - матрица (m x n), V - вектор (m),
		int m = V.length;
		int n = A.length;
		if (A[0].length != m) throw new Error("The dimensions of the matrix or vector are unacceptable.");
		
		double[] resV = new double[n]; // resV - вектор-столбец (n) (результат)
		for (int i=0; i < n; i++) {
			resV[i] = 0;
			for (int j=0; j < m; j++) {
				resV[i] += A[i][j] * V[j];
			}
		}
		return resV;
	}
	public static double[] multiplyByVector (int[][] A, double[] V) { // A - матрица (m x n), V - вектор (m),
		int m = V.length;
		int n = A.length;
		if (A[0].length != m) throw new Error("The dimensions of the matrix or vector are unacceptable.");
		
		double[] resV = new double[n]; // resV - вектор-столбец (n) (результат)
		for (int i=0; i < n; i++) {
			resV[i] = 0;
			for (int j=0; j < m; j++) {
				resV[i] += A[i][j] * V[j];
			}
		}
		return resV;
	}

}
