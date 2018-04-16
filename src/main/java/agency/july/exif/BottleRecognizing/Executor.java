package agency.july.exif.BottleRecognizing;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Executor {
	
	static File dir;
	
	public Executor(String where) {
		dir = new File (where);
	}

	static String executeCommand(String[] command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
//			p = Runtime.getRuntime().exec(command, new String[] {"PATH=/opt/local/bin:/opt/local/sbin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"}, new File ("/Users/andrew/ImageMagick") );
			p = Runtime.getRuntime().exec(command, null, dir );
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

}
