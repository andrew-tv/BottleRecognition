package agency.july.math;

import static java.lang.Math.*;

public class Vector {
	
	public static double distance(double[] a, double[] b) {
		double s = 0;
		for (int i=0; i < a.length; i++) s += (a[i]-b[i])*(a[i]-b[i]);
		return sqrt(s);
	}

	public static double distanceSqr(double[] a, double[] b) {
		double s = 0;
		for (int i=0; i < a.length; i++) s += (a[i]-b[i])*(a[i]-b[i]);
		return s;
	}

	public static double distanceSqr(double[] a, double[] b, double[] w) {
		double s = 0;
		for (int i=0; i < a.length; i++) s += (a[i]-b[i])*(a[i]-b[i]) * w[i];
		return s;
	}

	public static double[] weigh(double[] v, double[] w) {
		for (int i=0; i < v.length; i++) v[i] *= w[i];
		return v;
	}

}
