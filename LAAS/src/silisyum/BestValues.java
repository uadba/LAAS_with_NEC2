package silisyum;

public class BestValues {
	public int bestID;
	public double bestCostValue;
	public double[] valuesOfBestMember;
	
	public BestValues(int _bestID, double _bestCostValue, double[] _bestAmplitudes) {
		bestID = _bestID;
		bestCostValue = _bestCostValue;
		valuesOfBestMember = new double[_bestAmplitudes.length];
		for (int i = 0; i < _bestAmplitudes.length; i++) {
			valuesOfBestMember[i] = _bestAmplitudes[i];
		}
	}
}
