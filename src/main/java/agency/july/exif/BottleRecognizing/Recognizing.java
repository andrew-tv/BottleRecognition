package agency.july.exif.BottleRecognizing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import agency.july.math.Vector;

public class Recognizing {
	
	static String sourceFolder = "/Users/andrew/Work/Clients/Systembollaget/Recognizing/tiffs";
	static String targetFolder = "/Users/andrew/Work/Clients/Systembollaget/Recognizing";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		double[] m = new double[12];
		double[] w = new double[]{1,1,1,1,1,1,1,1,1,1,1,1};
		
		File file = new File ("ml.bin");
		FileInputStream fi = new FileInputStream(file);
		ObjectInputStream si = new ObjectInputStream(fi);
		ArrayList<Metrics> machineLerning = (ArrayList<Metrics>) si.readObject();
		si.close();
					
/*
		for (Metrics m : machineLerning) {
			System.out.println(m.getId());
			System.out.println(m.getType());
			System.out.println(m.getVolume());
			for (int i= 0; i<m.getMetrics().length; i++) {
				System.out.println("m[" + i +"] = " + m.getMetrics()[i]);
			}
		}
*/
		new Executor("./");
		
		PathExtractor extractor = new PathExtractor();
		
        File[] files = new File(sourceFolder)
        		.listFiles((d, name) -> (name.endsWith(".tif")||name.endsWith(".jpg")));
		System.out.println("files.length = " + files.length);

        for (File image : files) {
        	if (image.isFile()) {
        		System.out.println(image.getName());
        	  
				String output = extractor.extract(sourceFolder + "/" + image.getName());
				
				System.out.println(output);

				Paths paths = new Paths(output);
		
				Metrics bottle = new Metrics(paths);
		
//				for (int i= 0; i<bottle.getMetrics().length; i++) {
//					System.out.println("m[" + i +"] = " + m.getMetrics()[i]);
//				}
				
				m = bottle.getMetrics();
				
				if (m[11] > .65) {  // Most likely it is a box
					w = new double[]{0,0,0,0,1,1,1,1,1,1,1,1};
				} else {
					w = new double[]{1,1,1,1,1,1,1,1,1,1,1,1};
					if ( m[1] > m[2] ) w[1] = 0;
					else w[2] = 0;
				}
								
				double min = 100d;
				double d = min;
				Metrics nearestMetrics = null;
				for (Metrics mlm : machineLerning) {
					d = Vector.distanceSqr(bottle.getMetrics(), mlm.getMetrics(), w);
					if (d < min) {
						min = d;
						nearestMetrics = mlm;
					}
				}
		
				if (min < 0.1) {
					System.out.println( "Confidential" );
					System.out.println( "d = " + min );
					image.renameTo(new File(targetFolder + "/" + nearestMetrics.getVolume() + "/" + image.getName()));
				} else {
					System.out.println( "Confidential" );
					System.out.println( "d = " + min );					
				}
        	}
        }
	}

}
