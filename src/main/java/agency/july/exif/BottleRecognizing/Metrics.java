package agency.july.exif.BottleRecognizing;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.io.Serializable;

import agency.july.math.Bezier;

public class Metrics implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	private int volume;
	private double[] metrics;
	
	public Metrics (Paths paths) {
		this.metrics = makeMetrics(paths);		
	}
	
	public Metrics withId (String id) {
		this.id = id;
		return this;
	}
	
	public Metrics withType (String type) {
		this.type = type;
		return this;
	}
	
	public Metrics withVolume (int volume) {
		this.volume = volume;
		return this;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double[] getMetrics() {
		return metrics;
	}

	public void setMetrics(double[] metrics) {
		this.metrics = metrics;
	}
	
	public void setMetrics(Paths paths) {
		this.metrics = makeMetrics(paths);
	}
	
	public static double[] makeMetrics(Paths paths) {
		
		Bezier bezier;
		
		final double h = paths.getH();
		final double w = paths.getW();
		final double z = w/2;
		final double[] levels = new double[]{h/36, h/30, h/24, h/10, 2*h/10, 3*h/10, 35*h/100, 4*h/10, 5*h/10, 6*h/10, 9*h/10};
//*
		double[] metrics = new double[levels.length+1];
/*/
		double[] metrics = new double[levels.length+2];
//*/
		for (int j=0; j<metrics.length; j++) metrics[j] = w;

		Subpath workPath = paths.getHighestSubpath();

		for (int i=0; i<workPath.getCount(); i++) {
			double[] spline = workPath.getSpline(i);
			for (int j=0; j<levels.length; j++) {
				if ( (spline[1] > levels[j] && spline[7] < levels[j]) ||
					(spline[7] > levels[j] && spline[1] < levels[j])) {
					bezier = new Bezier(spline);
					double newValue = abs(bezier.getXbyY(levels[j])[0] - z);
					if ( abs(newValue - metrics[j]) / z > 0.02 ) 
						metrics[j] = min(metrics[j], newValue);
					else
						metrics[j] = (metrics[j] + newValue) / 2;
				}
			}
		}
/*
		metrics[metrics.length-2] = w;
		for (int j=1; j<6; j++) {
			metrics[metrics.length-2] = min(metrics[j], metrics[metrics.length-2]);
		}
//*/		
		
		for (int i= 0; i<metrics.length-1; i++) {
			metrics[i] = metrics[i]/z;
		}
		
		metrics[metrics.length-1] = w/h;
		
		return metrics;		
	}
}
