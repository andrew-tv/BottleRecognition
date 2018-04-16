package agency.july.exif.BottleRecognizing;

import java.util.ArrayList;

public class Paths extends ArrayList<Path> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int w;
	private int h;

	public Paths (String pathsStr) {
		
		String[] pathsArr = pathsStr.split(" +: +|\\n|\\r");
		
		for (int i=0; i<pathsArr.length-4; i+=2) {
			add(new Path( pathsArr[i], pathsArr[i+1] ) );
		}
		this.w = Integer.parseInt(pathsArr[pathsArr.length-3]);
		this.h = Integer.parseInt(pathsArr[pathsArr.length-1]);
	}
	
	public Subpath getMostSubpath (int coordinate) {
		double max = -1d;
		Subpath maxSubpath = null;
		Subpath subpath;
		for (Path path : this) {
			subpath = path.getMostSubpath(coordinate);
			if (subpath.getAmplitude(coordinate) > max) {
				max = subpath.getAmplitude(coordinate);
				maxSubpath = subpath;
			}
		}
		return maxSubpath;
	}
	
	public Subpath getHighestSubpath () {
		return getMostSubpath(3);
	}
	
	public Subpath getWidestSubpath () {
		return getMostSubpath(2);
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
