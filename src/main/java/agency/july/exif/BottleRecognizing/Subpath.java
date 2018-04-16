package agency.july.exif.BottleRecognizing;

import java.util.Arrays;

public class Subpath {
	
	private double[] data; 
	private boolean closed;
	private int nodeCount;
	private double[] amplitudes = new double[]{-1d,-1d,-1d,-1d,-1d,-1d};
	private int[] mins = new int[]{-1,-1,-1,-1,-1,-1};
	private int[] maxes = new int[]{-1,-1,-1,-1,-1,-1};
	
	public Subpath (String subPathStr) {
		String[] dataStr = subPathStr.split(",");
		this.closed = dataStr[0].equals("-");
		this.data = new double[dataStr.length-1];
		this.nodeCount = data.length / 6;
		for (int i = 1; i<dataStr.length; i++) {
			data[i-1] = Double.parseDouble(dataStr[i]);
		}
	}
	
	public int getCount() {
		return nodeCount;
	}	

	public double[] getNode(int index) {
		return Arrays.copyOfRange(data, index*6, index*6 + 6);
	}	

	public double[] getSpline(int index) {
		if (index < data.length / 6 - 1) {
			return Arrays.copyOfRange(data, index*6+2, index*6 + 10);
		} else {
			double[] res = new double[8];
			for (int i=0; i<4; i++) {
				res[i] = data[data.length-4 + i];
				res[4+i] = data[i];
			}
			return res;
		}
	}
	
	public double getAmplitude (int coordinate) {
		if (this.amplitudes[coordinate] < 0) { // The amplitude is undefined
			double value = getNode(0)[coordinate];
			double min = value;
			double max = value;
			this.mins[coordinate] = 0;
			this.maxes[coordinate] = 0;
			
			for (int i = 1; i < getCount(); i++) {
				value = getNode(i)[coordinate];
				if ( value < min) {
					min = value;
					this.mins[coordinate] = i;
				} else if ( value > max ) {
					max = value;
					this.maxes[coordinate] = i;
				}
			}
			this.amplitudes[coordinate] = max - min;
		}
		return this.amplitudes[coordinate];
	}
	
	public double getAmplitudeX () {
		return getAmplitude(2);
	}

	public double getAmplitudeY () {
		return getAmplitude(3);
	}

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public int getIndexBiggestNode(int coordinate) {
		if (maxes[coordinate] < 0) getAmplitude(coordinate);
		return maxes[coordinate];
	}

	public int getIndexSmallestNode(int coordinate) {
		if (mins[coordinate] < 0) getAmplitude(coordinate);
		return mins[coordinate];
	}

}
