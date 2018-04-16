package agency.july.exif.BottleRecognizing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tutor {
	
	static String sourceFolder = "/Users/andrew/Work/Clients/Systembollaget/Recognizing/SamplesNew";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		File mlFile = new File ("ml.bin"); // Machine learning file
		ArrayList<Metrics> machineLerning;
		
		if ( mlFile.exists() ) {
			FileInputStream fi = new FileInputStream(mlFile);
			ObjectInputStream si = new ObjectInputStream(fi);
			machineLerning = (ArrayList<Metrics>) si.readObject();
			si.close();			
		} else {
			machineLerning = new ArrayList<Metrics>();			
		}
		

		new Executor("./");
		
		PathExtractor extractor = new PathExtractor();
		
		BufferedReader reader = new BufferedReader(new FileReader(sourceFolder + "/properties.txt"));
        String line;
        Map<String, String> lines = new HashMap<String, String>();
        while ((line = reader.readLine()) != null) {
        	String[] arrlines = line.split("\\t");
            lines.put(arrlines[1], line);
        }
        reader.close();
        
        File[] files = new File(sourceFolder)
        		.listFiles((d, name) -> (name.endsWith(".tif")||name.endsWith(".jpg")));
        for (File image : files) {
        	if (image.isFile()) {
        		System.out.println(image.getName());
        	  
				String output = extractor.extract(sourceFolder + "/" + image.getName());
				
				System.out.println(output);
				
				Paths paths = new Paths(output);
		
				String id = image.getName().split("\\.")[0];
				String[] property = lines.get(id).split("\\t");
				machineLerning.add(
					new Metrics(paths)
					.withId(property[1])
					.withType(property[2]) //"Steel cork"
					.withVolume(Integer.parseInt(property[0]))
				);
        	}
        }      
		
		FileOutputStream fo = new FileOutputStream(mlFile);
		ObjectOutputStream so = new ObjectOutputStream(fo);
		so.writeObject(machineLerning);
		so.flush();
		so.close();
	
		FileInputStream fi = new FileInputStream(mlFile);
		ObjectInputStream si = new ObjectInputStream(fi);
		ArrayList<Metrics> listMetrics = (ArrayList<Metrics>) si.readObject();
		si.close();
		
		for (Metrics m : listMetrics) {
			System.out.println(m.getId());
			System.out.println(m.getType());
			System.out.println(m.getVolume());
			for (int i= 0; i<m.getMetrics().length; i++) {
				System.out.println("m[" + i +"] = " + m.getMetrics()[i]);
			}
		}

	}

}
