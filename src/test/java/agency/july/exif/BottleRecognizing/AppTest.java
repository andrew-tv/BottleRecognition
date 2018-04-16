package agency.july.exif.BottleRecognizing;

import static org.junit.Assert.*;

import org.junit.Test;
import agency.july.math.Polinomial;

public class AppTest {
	
	@Test
    public void roots() {		
		double[] roots = Polinomial.cubicRoots(new double[]{2, -1, 1, 3});
        assertEquals(4, roots.length);
        assertEquals(1d, roots[0], 1e-12);
        assertEquals(-1.1394, roots[1], 1e-3);
        assertEquals(0.4030, roots[2], 1e-3);
        assertEquals(0.6501, roots[3], 1e-3);
    }

	@Test
    public void lsm() {		
		double[] polinom = Polinomial.lsm(2, new double[]{0, 1, 2, 3}, new double[]{0, 1, 4, 9});
        assertEquals(3, polinom.length);
        assertEquals(0, polinom[0], 1e-12);
        assertEquals(0, polinom[1], 1e-12);
        assertEquals(1, polinom[2], 1e-12);
        
		polinom = Polinomial.lsm(3, 
				new double[]{0, .5, 1, 1.5, 2, 2.5, 3}, 
				new double[]{-4, -1, 0, 1, 4, 20, 30});
        assertEquals(4, polinom.length);
        assertEquals(-3.4285714285714284, polinom[0], 1e-12);
        assertEquals(4.285714285714286, polinom[1], 1e-12);
        assertEquals(-3.5714285714285716, polinom[2], 1e-12);
        assertEquals(2.0, polinom[3], 1e-12);

        System.out.println("d = " + polinom[0]);
        System.out.println("c = " + polinom[1]);
        System.out.println("b = " + polinom[2]);
        System.out.println("a = " + polinom[3]);
        
		polinom = Polinomial.lsm(3, 
				new double[]{0.4397665507228, 0.46743888955581, 0.53488892783788, 
						0.54640068501215, 0.7862859243501, 0.96772095365884},
				new double[]{.1, .2, .3, 0.35, .4, .5});
        System.out.println("d = " + polinom[0]);
        System.out.println("c = " + polinom[1]);
        System.out.println("b = " + polinom[2]);
        System.out.println("a = " + polinom[3]);
        
    }
	
	@Test
    public void gorner() {
		double value = Polinomial.gorner(2, new double[]{
				-3.4285714285714284, 
				4.285714285714286, 
				-3.5714285714285716, 
				2.0});
        assertEquals(6.857142857142856, value, 1e-12);

	    System.out.println("P(2) = " + value);
	}

}
