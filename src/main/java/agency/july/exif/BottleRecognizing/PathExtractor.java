package agency.july.exif.BottleRecognizing;

public class PathExtractor {

	String extract (String from) {

		String[] command;
		
		command = new String[]{
				"/usr/local/bin/exiftool", 
				"-config", 
				"photoshop_paths.config", 
				"-allpathpix", // allpaths
				"-userparam", 
				"anchoronly",
				"-ImageWidth",
				"-ImageHeight",
				from};

		return Executor.executeCommand(command);
		
	}

}
