package agency.july.exif.BottleRecognizing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import agency.july.math.Polinomial;

public class App 
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException
    {
		File file = new File ("ml.bin");
		FileInputStream fi = new FileInputStream(file);
		ObjectInputStream si = new ObjectInputStream(fi);
		ArrayList<Metrics> machineLerning = (ArrayList<Metrics>) si.readObject();
		si.close();
		
		for (Metrics metrics : machineLerning) {
			double[] m = metrics.getMetrics();
			double min = m[0];
			double max = m[0];
			int iMin = 0;
			int iMax = 0;
			for (int i = 1; i < 10; i++) {
				if (m[i] < min) {
					min = m[i];
					iMin = i;
				} else if (m[i] > max) {
					max = m[i];
					iMax = i;					
				}
			}
			for (int i = 0; i < 10; i++) {
				if (m[i] > min && i < iMin) m[i] = min;
				if (m[i] < max && i > iMax) m[i] = max;
			}			
			double[] polinom = Polinomial.lsm(3, 
					new double[]{m[0]-0.05, m[1]-0.04, m[2]-0.03, m[3]-0.02, m[4]-0.01, m[5], m[6]+0.01, m[7]+0.02, m[8]+0.03, m[9]+0.04},
					new double[]{0.02777777777778, 0.03333333333333, 0.04166666666667, .1, .2, .3, 0.35, .4, .5, .6});
			double X = -(2*polinom[2])/(6*polinom[3]);
			double Y = Polinomial.gorner(X, polinom);
			double Y1 = Polinomial.gorner(X, new double[]{polinom[1], 2*polinom[2], 3*polinom[3]});
			System.out.println(metrics.getId() + "\t X = " + X  + "\t Y = " + Y  + "\t Y1 = " + Y1+  "\t X/Y = " + X/Y  + "\t Y/X = " + Y/X);
		}
    }
}
