package silisyum;

public class AntennaArray {
	
	double lambda = 1;
	double beta = 2*Math.PI/lambda;
	int numberofElements;
	public double[] amplitude;
	public double[] position;
	public double[] phase;
	public int numberofSamplePoints;
	public double[] angle;
	private double[] pattern;
	public double[] pattern_dB;
	public double[] angleForOptimization_ForOuters;
	public double[] patternForOptimization_ForOuters;
	public double[] patternForOptimization_dB_ForOuters;
	public double[] levels_ForOuters;
	public double[] weights_ForOuters;
	public double[] angleForOptimization_ForInners;
	public double[] patternForOptimization_ForInners;
	public double[] patternForOptimization_dB_ForInners;
	public double[] levels_ForInners;
	public double[] weights_ForInners;
	private Mask mask;
	public int numberOfSLLOuters;
	public int numberOfSLLInners;
	public double biggestOne;	
	
	public AntennaArray(int _numberofElements, int _numberofSamplePoints, Mask _mask) {
		
		numberofSamplePoints = _numberofSamplePoints;
		numberofElements = _numberofElements;
		mask = _mask;
		createArrays();
		initializeArrays();

	}
	

	public void createArrays() {
		amplitude = new double[numberofElements];
		position = new double[numberofElements];
		phase = new double[numberofElements];
		createAnlgeAndPatternArrays();
	}
	
	private void createAnlgeAndPatternArrays() {
		angle = new double[numberofSamplePoints];
		pattern = new double[numberofSamplePoints];
		pattern_dB = new double[numberofSamplePoints];
	}
	
	public void initializeArrays() {
		for (int i = 0; i < numberofElements; i++) {
			amplitude[i] = DefaultConfiguration.amplitudeValue;
			phase[i] = DefaultConfiguration.phaseValue;
			position[i] = i*DefaultConfiguration.positionValue*lambda;
		}
	}

	public double patternFunction(double theta)
	{
		double result = 0;
		double result_real = 0;
		double result_img = 0;
		for (int e = 0; e<numberofElements; e++)
		{
			result_real = result_real + amplitude[e]*Math.cos(position[e]*beta*Math.cos((theta)/180*Math.PI) + ((phase[e])/180*Math.PI));
			result_img = result_img + amplitude[e]*Math.sin(position[e]*beta*Math.cos((theta)/180*Math.PI) + ((phase[e])/180*Math.PI));			
		}
		result = Math.sqrt(result_real*result_real + result_img*result_img);
					
		return result;
	}
	
	public void createLongArrays() {
		numberOfSLLOuters = mask.outerMaskSegments.size(); 
		if (numberOfSLLOuters > 0) {
			Mask.MaskSegment SLL_outer = null;
			int numberOfAnglesForOuters = 0;
			for (int n = 0; n < numberOfSLLOuters; n++) {
				SLL_outer = mask.outerMaskSegments.get(n);
				numberOfAnglesForOuters += SLL_outer.angles.length;
			}
			angleForOptimization_ForOuters = new double[numberOfAnglesForOuters];
			patternForOptimization_ForOuters = new double[numberOfAnglesForOuters];
			patternForOptimization_dB_ForOuters = new double[numberOfAnglesForOuters];
			levels_ForOuters = new double[numberOfAnglesForOuters];
			weights_ForOuters = new double[numberOfAnglesForOuters];
		}
		
		numberOfSLLInners = mask.innerMaskSegments.size();		
		if (numberOfSLLInners > 0) {
			Mask.MaskSegment SLL_inner = null;
			int numberOfAnglesForInners = 0;
			for (int n = 0; n < numberOfSLLInners; n++) {
				SLL_inner = mask.innerMaskSegments.get(n);
				numberOfAnglesForInners += SLL_inner.angles.length;
			}
			angleForOptimization_ForInners = new double[numberOfAnglesForInners];
			patternForOptimization_ForInners = new double[numberOfAnglesForInners];
			patternForOptimization_dB_ForInners = new double[numberOfAnglesForInners];
			levels_ForInners = new double[numberOfAnglesForInners];
			weights_ForInners = new double[numberOfAnglesForInners];
		}
	}
	
	public void createPattern() {
		
		if(numberofSamplePoints != angle.length) {
			createAnlgeAndPatternArrays();
		}			
		
		angle[0] = 0;
		double biggestOne = patternFunction(angle[0]);
		pattern[0] = patternFunction(angle[0]);
		for (int i = 1; i < numberofSamplePoints; i++) {
			angle[i] = 180*((double)i/(numberofSamplePoints-1));
			pattern[i] = patternFunction(angle[i]);
			if(pattern[i]>biggestOne) biggestOne = pattern[i];
		}
		
		for (int i = 0; i < numberofSamplePoints; i++) {
			pattern_dB[i] = 20*Math.log10(pattern[i] / biggestOne);
		}
	}

	public void createPatternForOptimization() {	
		// Create an array for the all mask values
		// For this purpose, we have to make a loop.
		// Then, we set angles into the elements of this array.
		
		int i;
		biggestOne = 0;
		
		if (numberOfSLLOuters > 0) {
			// ------------ for Outers ------------
			int numberOfSLLOuters = mask.outerMaskSegments.size();
			Mask.MaskSegment SLL_outer = null;
			i = 0;
			while (i < angleForOptimization_ForOuters.length) {
				for (int n = 0; n < numberOfSLLOuters; n++) {
					SLL_outer = mask.outerMaskSegments.get(n);
					for (int j = 0; j < SLL_outer.angles.length; j++) {
						angleForOptimization_ForOuters[i] = SLL_outer.angles[j];
						levels_ForOuters[i] = SLL_outer.levels[j];
						weights_ForOuters[i] = SLL_outer.weights[j];
						i++;
					}
				}
			}

			biggestOne = patternFunction(angleForOptimization_ForOuters[0]);
			patternForOptimization_ForOuters[0] = patternFunction(angleForOptimization_ForOuters[0]);
			for (int z = 1; z < angleForOptimization_ForOuters.length; z++) { // Attention please it starts from "1"
				patternForOptimization_ForOuters[z] = patternFunction(angleForOptimization_ForOuters[z]);
				if (patternForOptimization_ForOuters[z] > biggestOne)
					biggestOne = patternForOptimization_ForOuters[z];
			}
		}
		
		if (numberOfSLLInners > 0) {
			// ------------ for Inners ------------
			numberOfSLLInners = mask.innerMaskSegments.size();
			Mask.MaskSegment SLL_inner = null;
			i = 0;
			while (i < angleForOptimization_ForInners.length) {
				for (int n = 0; n < numberOfSLLInners; n++) {
					SLL_inner = mask.innerMaskSegments.get(n);
					for (int j = 0; j < SLL_inner.angles.length; j++) {
						angleForOptimization_ForInners[i] = SLL_inner.angles[j];
						levels_ForInners[i] = SLL_inner.levels[j];
						weights_ForInners[i] = SLL_inner.weights[j];
						i++;
					}
				}
			}

			if (numberOfSLLOuters < 1) biggestOne = patternFunction(angleForOptimization_ForInners[0]);
			patternForOptimization_ForInners[0] = patternFunction(angleForOptimization_ForInners[0]);
			for (int z = 1; z < angleForOptimization_ForInners.length; z++) { // Attention please it starts from "1"
				patternForOptimization_ForInners[z] = patternFunction(angleForOptimization_ForInners[z]);
				if(patternForOptimization_ForInners[z]>biggestOne) biggestOne = patternForOptimization_ForInners[z];
			}
		}
		
		if (numberOfSLLOuters > 0) {
			for (int z = 0; z < angleForOptimization_ForOuters.length; z++) {
				patternForOptimization_dB_ForOuters[z] = 20 * Math.log10(patternForOptimization_ForOuters[z] / biggestOne);
			}
		}			
		
		if (numberOfSLLInners > 0) {
			for (int z = 0; z < angleForOptimization_ForInners.length; z++) {
				patternForOptimization_dB_ForInners[z] = 20 * Math.log10(patternForOptimization_ForInners[z] / biggestOne);
			}
		}
	}
}
