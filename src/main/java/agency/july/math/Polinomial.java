package agency.july.math;

import static java.lang.Math.*;

public class Polinomial {
	
	final static double zeroing = 1e-12;
	
	// Calculation of the value of a polynomial according to the Horner scheme
	public static double gorner (double t, double[] A) { // a0 + a1*t + a2*t**2 + a3*t**3 ...
		int n = A.length-1;
		double b = A[n];
		for(int i = n-1; i >= 0; i--) {
			b = A[i]+b*t;
		}
		return b; 
	}

	// The solution of a linear equation of the form a0 + a1*x = 0, a1 != 0
	public static double[] linearRoot (double[] A) {
		if (abs(A[1]) < zeroing) return null; 
		return new double[] {-1d,-A[0]/A[1]};
	}

	// The solution of a quadratic equation of the form a0 + a1*x + a2*x**2 = 0, a2 != 0
	public static double[] quadraticRoots (double[] A) {
		if (abs(A[2]) < zeroing) return Polinomial.linearRoot(A); // It is a linear equation
		double a = A[2];
		double b = A[1];
		double c = A[0];
		double d = b*b - 4*a*c;
		a = a*2;
		double[] x = new double[3];
		x[0] = -1f;
		if ( d > zeroing ) { // Discriminant is greater than zero - two different roots
			d = sqrt(d);
			x[1] = (-b+d)/a;
			x[2] = (-b-d)/a;
		} else if ( d > -zeroing ) { // Discriminant is close to zero - two identical roots
			x[1] = -b/a;
			x[2] = x[0];
		} else x[0] = 1f; // No real roots
		return x;
	}

	// The solution of a cubic equation of the form a0 + a1*x + a2*x**2 + a3*x**3 = 0, a3 != 0
	public static double[] cubicRoots (double[] A) {
		if (abs(A[3]) < zeroing) return Polinomial.quadraticRoots(A); // It is a quadratic equation
		double a = A[2]/A[3];
		double b = A[1]/A[3];
		double c = A[0]/A[3];
		double pi2 = 2*PI;
		double q = (a*a-3.*b)/9.;
		double r = (a*(2.*a*a-9.*b)+27.*c)/54.;
		double r2 = r*r;
		double q3 = q*q*q;
		double[] x = new double[4];
	  
		x[0] = r2 < q3 ? -1d : 1d; 
	  if(x[0] < 0) { // All real roots
		double t = acos(r/sqrt(q3));
		a /= 3.;
		q = -2.* sqrt(q);
		x[1]=q*cos(t/3.)-a;			// The first real root
		x[2]=q*cos((t+pi2)/3.) - a;	// The second real root
		x[3]=q*cos((t-pi2)/3.) - a; // The third real root
	  } else { // Not all real roots
		  int sign;
		if(r <= 0.) {
			r = -r;
			sign = 1;
		} else {
			sign = -1;
		}
		double aa = sign * pow(r + sqrt(r2-q3), 1./3.);
		double bb = (abs(aa) > zeroing) ? q/aa : 0.;
		a /= 3.;
		q = aa+bb;
		r = aa-bb; 
		x[1] = q-a; // A real root
		x[2] = (-0.5)*q-a; // The real part of the complex conjugate root
		x[3] = (sqrt(3.)*0.5)*abs(r); // The positive imaginary part of the complex conjugate root
	  }
		return x;
	}
	
	// Least square method. Returns the vector of the coefficients of a polynomial of the form: a0 + a1*t + a2*t**2 + a3*t**3 ...
	// K - степень искомого полинома;
	// X и Y - векторы, координаты исходных точек (K < N, где N количество точек)
	public static double[] lsm (int K, double[] X, double[] Y) { 

		int N = X.length; // Number of point
		//init square S matrix, A and B vectors
		double[] B = new double[K+1];
		double[] A = new double[K+1];
		for(int i=0; i<=K; i++) {
			A[i] = 0;
			B[i] = 0;
		}
		double[][] S = new double[K+1][K+1];
		for(int i=0; i<=K; i++) {
			for(int j=0; j<=K; j++) {
				S[i][j] = 0;
				for(int k=0; k<N; k++) {
					S[i][j] += pow(X[k], (double)(i+j)); //Math. ???
				}
			}
//		$.writeln("S[" + i + "] = " + S[i]);
		}
		//Make the diagonal without zeros
		double temp=0;
		for(int i=0; i<=K; i++){
			if(S[i][i]==0){
				for(int j=0; j<=K; j++){
					if(j==i) continue;
					if((S[j][i] != 0) && (S[i][j] != 0)) {
						for(int k=0; k<=K; k++) {
							temp = S[j][k];
							S[j][k] = S[i][k];
							S[i][k] = temp;
						}
						temp = B[j];
						B[j] = B[i];
						B[i] = temp;
						break;
					}
				}
			}
		}
		//init free koefficients column
		for(int i=0; i<=K; i++) {
			for(int k=0; k<N; k++){
				B[i] += pow(X[k], (double)i) * Y[k]; // Math. ???
			}
		}
		for(int k=0; k<=K; k++){
			for(int i=k+1; i<=K; i++){
				if(S[k][k]==0) throw new Error("Solution is not exist.");
				double M = S[i][k] / S[k][k];
				for(int j=k; j<=K; j++) {
					S[i][j] -= M * S[k][j];
				}
				B[i] -= M*B[k];
			}
		}
		for(int i=K; i>=0; i--) {
			double s = 0;
			for(int j = i; j<=K; j++) s += S[i][j]*A[j];
			A[i] = (B[i] - s) / S[i][i];
		}
		return A;
	}

}
