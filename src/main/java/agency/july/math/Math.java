package agency.july.math;

public class Math {

	public static void main(String[] args) {
		
		int[] C = Combinatorics.getBinomial(4);
		System.out.println(C.length);
		for (int i= 0; i<C.length; i++) {
			System.out.println(C[i]);
		}
		
		Bezier bezier = new Bezier(new double[]{0,0,1,2,2,0,3,0});
		
		double[] x = Polinomial.cubicRoots(new double[]{2,-1,1,3});
		
		for (int i= 0; i<x.length; i++) {
			System.out.println(x[i]);
		}
		
		double[] t = Bezier.getPermissibleRoots(new double[]{-1f,.001,1.5,1.5});
		for (int i= 0; i<t.length; i++) {
			System.out.println(t[i]);
		}
		
		System.out.println("Gorner :" + Polinomial.gorner(3f, new double[]{-1,2,-6,2}));
	}


}
