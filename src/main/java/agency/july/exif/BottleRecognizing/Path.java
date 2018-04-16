package agency.july.exif.BottleRecognizing;

import java.util.ArrayList;

public class Path extends ArrayList<Subpath> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public Path (String name, String subpathsStr) {
		this.name = name;
		
		String[] subpathsArr = subpathsStr.split(",(?=[-\\+],)");
		
		for (int i=0; i<subpathsArr.length; i++) {
			add(new Subpath(subpathsArr[i]));
		}
			
	}
	
	public Subpath getMostSubpath(int coordinate) {
		
		if (size() > 1) {
			Subpath highestSubpath = get(0);
			double height = get(0).getAmplitude(coordinate);
			double maxHeight = height;
			for (int i=1; i<size(); i++) {
				height = get(i).getAmplitude(coordinate);
				if ( height > maxHeight ) {
					height = maxHeight;
					highestSubpath = get(i);
				}
			}
			return highestSubpath;
		} else return get(0);
	}
	
	public Subpath getHighestSubpath() {
		return getMostSubpath(3);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
