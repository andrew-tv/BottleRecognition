package agency.july.exif.BottleRecognizing;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.drew.imaging.ImageProcessingException;

import agency.july.math.Bezier;

public class Photopaths {
	
	public static void main(String[] args) throws ImageProcessingException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		new Executor("/Users/andrew/Work/Clients/Systembollaget/Recognizing/650");
		
		PathExtractor extractor = new PathExtractor();
		
		String output = extractor.extract("58_9809010.tif");
		
		System.out.println(output);
		
		Paths paths = new Paths(output);
		
		System.out.println("Image Width : " + paths.getW());
		System.out.println("Image Height : " + paths.getH());
		
		double[] spline0 = paths.get(0).get(0).getSpline(0);
		System.out.println("B1 : " + spline0[0] + " : " + spline0[1] );
		System.out.println("B2 : " + spline0[2] + " : " + spline0[3] );
		System.out.println("B3 : " + spline0[4] + " : " + spline0[5] );
		System.out.println("B4 : " + spline0[6] + " : " + spline0[7] );
		
		double[] spline1 = paths.get(0).get(0).getSpline(1);
		System.out.println("B1 : " + spline1[0] + " : " + spline1[1] );
		System.out.println("B2 : " + spline1[2] + " : " + spline1[3] );
		System.out.println("B3 : " + spline1[4] + " : " + spline1[5] );
		System.out.println("B4 : " + spline1[6] + " : " + spline1[7] );
		
		double[] splineLast = paths.get(0).get(0).getSpline(paths.get(0).get(0).getCount()-1);
		System.out.println("getNodeCount : " + paths.get(0).get(0).getCount() );
		System.out.println("B1 : " + splineLast[0] + " : " + splineLast[1] );
		System.out.println("B2 : " + splineLast[2] + " : " + splineLast[3] );
		System.out.println("B3 : " + splineLast[4] + " : " + splineLast[5] );
		System.out.println("B4 : " + splineLast[6] + " : " + splineLast[7] );
		
		Bezier bezier = new Bezier(spline1);
		double[] t = bezier.getParamsByY(3500);
		for (int i= 0; i<t.length; i++) {
			System.out.println("t = " + t[i]);
		}

		double[] X = bezier.getXbyY(3500);
		for (int i= 0; i<X.length; i++) {
			System.out.println("x = " + X[i]);
		}
					
		Metrics metrics = new Metrics(paths);
		
		File file = new File ("/Users/andrew/Work/Clients/Systembollaget/Recognizing/650/store.bin");
		FileOutputStream fo = new FileOutputStream(file);
		ObjectOutputStream so = new ObjectOutputStream(fo);
		so.writeObject(metrics);
		so.flush();
		so.close();
		
		FileInputStream fi = new FileInputStream(file);
		ObjectInputStream si = new ObjectInputStream(fi);
		Metrics m = (Metrics) si.readObject();
		si.close();
		
		for (int i= 0; i<m.getMetrics().length; i++) {
			System.out.println("m[" + i +"] = " + m.getMetrics()[i]);
		}

		System.out.println("Done");

		/*
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		for (Directory directory : metadata.getDirectories() ) {
		    for (Tag tag : directory.getTags()) {
		        System.out.format("[%s] - %s = %s",
		            directory.getName(), tag.getTagName(), tag.getDescription());
		    }
		    if (directory.hasErrors()) {
		        for (String error : directory.getErrors()) {
		            System.err.format("ERROR: %s", error);
		        }
		    }
		}
		
		for (Directory directory : metadata.getDirectories()) {
	        System.out.println(directory.getName());
	        if (directory.getName().equals("Photoshop")) {
			    for (Tag tag : directory.getTags()) {
			    	if (tag.getTagType() == 2000)
			    		System.out.println(tag.getTagName() + " >> " + tag.getTagType() + " >> " + tag.getDescription());
			    }
	        }
		}
*/		
	}

}
