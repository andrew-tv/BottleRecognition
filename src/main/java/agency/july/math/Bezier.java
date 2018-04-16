package agency.july.math;

import java.util.Arrays;

public class Bezier {
	
	private int n; // degree of Bezier spline
	private double[] poligonX;
	private double[] poligonY;

	private int[] binomial;
	private int[][] N;
	private double[] polinomX;
	private double[] polinomY;

	public Bezier(double[] poligon) {
		this.n = poligon.length / 2 - 1;
		this.poligonX = new double[poligon.length / 2];
		this.poligonY = new double[poligon.length / 2];
		for (int i=0; i<this.poligonY.length; i++) {
			this.poligonX[i] = poligon[i*2];
			this.poligonY[i] = poligon[i*2+1];
		}
		this.binomial = Combinatorics.getBinomial(n);
		this.N = getNorma();
		
		this.polinomX = Matrix.multiplyByVector(this.N, this.poligonX);
		this.polinomY = Matrix.multiplyByVector(this.N, this.poligonY);
		/*
		System.out.println("polinomX = " + this.polinomX[0]);
		System.out.println("polinomX = " + this.polinomX[1]);
		System.out.println("polinomX = " + this.polinomX[2]);
		System.out.println("polinomX = " + this.polinomX[3]);
		System.out.println("polinomY = " + this.polinomY[0]);
		System.out.println("polinomY = " + this.polinomY[1]);
		System.out.println("polinomY = " + this.polinomY[2]);
		System.out.println("polinomY = " + this.polinomY[3]);
		*/
	}
	
	private int[][] getNorma() {
		int[][] N = new int[this.n + 1][this.n + 1];
		int sign = 1;
		int sign2;
		for (int i = 0; i < N.length; i++) {
			sign = -sign;
			sign2 = sign;
			for (int j = 0; j<=i; j++) {
				sign2 = -sign2;
				N[i][j] = sign2 * Combinatorics.getC(this.n,j) * Combinatorics.getC(this.n-j, this.n-i);
//				System.out.println("N[" +i+ "][" +j+"] = " + N[i][j]);
			}
			for (int j = i+1; j < N.length; j++) {
				N[i][j] = 0; // Все элементы над главной диагональю равны 0
			}
		}
		return N;
	}
	
	// Отфильтровывает только допустимые корни для параметра t (от 0 до 1), полученные путем решения кубического уравнения Polynomial.cubic()
	public static double[] getPermissibleRoots(double[] roots) {
		int count = 0;
		double[] t = new double[3];
		for (int i = 1; i<(roots[0] < 0 ? roots.length : 2); i++) {
			if (java.lang.Math.abs(roots[i] - 0.5) <= 0.5) t[count++] = roots[i];
		}
		return Arrays.copyOfRange(t, 0, count);
	}
	
	// Возвращает все допустимые значения параметра кривой Безье (t от 0 до 1) по заданному аргументу y
	public double[] getParamsByY (double y) {
		return Bezier.getPermissibleRoots(Polinomial.cubicRoots(new double[]{(this.polinomY[0]-y), this.polinomY[1], this.polinomY[2],  this.polinomY[3]})); // Решаем кубич. уравнение и оставляем только допустимые корни
	}

	// Возвращает все допустимые значения параметра кривой Безье (t от 0 до 1) по заданному аргументу x
	public double[] getParamsByX (double x) {
		return Bezier.getPermissibleRoots(Polinomial.cubicRoots(new double[]{(this.polinomX[0]-x), this.polinomX[1], this.polinomX[2],  this.polinomX[3]})); // Решаем кубич. уравнение и оставляем только допустимые корни
	}

	// Находит все пересечения (от 0 до 3) прямой паралельной оси ординат и кривой безье
	public double[] getXbyY (double y) {
		
		double[] params = getParamsByY(y); // Получаем значения всех допустимых параметров (t), при которых кривая Безье пересекается с прямой 
		double[] res = new double[params.length];
		
		for (int i = 0; i < params.length; i++) { // Находим все пересечения прямой с кривой Безье
			res[i] = Polinomial.gorner(params[i], this.polinomX);
		}
		return res;
	}
	
	// Находит все пересечения (от 0 до 3) прямой паралельной оси абцисс и кривой безье
	public double[] getYbyX (double x) {
		
		double[] params = getParamsByX(x); // Получаем значения всех допустимых параметров (t), при которых кривая Безье пересекается с прямой 
		double[] res = new double[params.length];
		
		for (int i = 0; i < params.length; i++) { // Находим все пересечения прямой с кривой Безье
			res[i] = Polinomial.gorner(params[i], this.polinomY);
		}
		return res;
	}
}
