package agency.july.math;

public class Combinatorics {
	
	public static int getC(int n, int m) {
		return getBinomial(n)[m];
	}

	public static int[] getBinomial(int n) {
		int[] f = new int[n+1];
		int[] c = new int[n+1];
		float m = (n+1)/2;
		
		f[0] = 1;
		c[0] = 1;
		c[n] = 1;
		for (int i=1; i<c.length; i++) f[i] = f[i-1] * i;
		for (int i=1; i<=m; i++) {
			c[i] = f[n] / (f[i] * f[n-i]);
			c[n-i] = c[i];
		}
		return c;
	}

}
