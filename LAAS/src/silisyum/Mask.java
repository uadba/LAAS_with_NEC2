package silisyum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mask {
	List<MaskSegment> outerMaskSegments = new ArrayList<MaskSegment>();
	List<MaskSegment> innerMaskSegments = new ArrayList<MaskSegment>();

	public void addNewOuterMaskSegments(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level, double _weight)
	{
		outerMaskSegments.add(new MaskSegment(_name, _startAngle, _stopAngle, _numberOfPoints, _level, _weight));
		Collections.sort(outerMaskSegments);
	}
	
	public void deleteOuterMaskSegments(int index)
	{
		outerMaskSegments.remove(index);
	}
	
	public void addNewInnerMaskSegments(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level, double _weight)
	{
		innerMaskSegments.add(new MaskSegment(_name, _startAngle, _stopAngle, _numberOfPoints, _level, _weight));
		Collections.sort(innerMaskSegments);
	}
	
	public void deleteInnerMaskSegments(int index)
	{
		innerMaskSegments.remove(index);
	}
	
	public class MaskSegment implements Comparable<MaskSegment>{
		String name;
		Double startAngle;
		double stopAngle;
		int numberOfPoints;
		double level;
		double weight;
		double[] angles;
		double[] levels;
		double[] weights;
		
		public MaskSegment(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level, double _weight) {
			name = _name;
			startAngle = Double.valueOf(_startAngle);
			stopAngle = _stopAngle;
			numberOfPoints = _numberOfPoints;
			level = _level;
			weight = _weight;
			angles = new double[_numberOfPoints];
			levels = new double[_numberOfPoints];
			weights = new double[_numberOfPoints];
			
			double resolution = (_stopAngle - _startAngle)/(_numberOfPoints - 1); 
			
			angles[0] = _startAngle;
			levels[0] = _level;
			weights[0] = _weight;
			for (int i = 1; i < _numberOfPoints-1; i++) {
				angles[i] = angles[i-1] + resolution;
				levels[i] = _level;
				weights[i] = _weight;
			}
			angles[_numberOfPoints-1] = _stopAngle;			
			levels[_numberOfPoints-1] = _level;
			weights[_numberOfPoints-1] = _weight;
		}

		@Override
		public int compareTo(MaskSegment o) {			
			return startAngle.compareTo(o.startAngle);
		}
	}
}

