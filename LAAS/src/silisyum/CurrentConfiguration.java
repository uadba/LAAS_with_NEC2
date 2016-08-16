package silisyum;

public class CurrentConfiguration implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2913166957553830377L;
	
	// Antenna Parameters
	public int numberofElements;
	public double[] L;
	public double[] H;
	public boolean amplitudeIsUsed;
	public boolean phaseIsUsed;
	public boolean positionIsUsed;
	public double[] amplitudeValues;
	public double[] phaseValues;
	public double[] positionValues;
	
	// For Outer Mask
	public String[] nameForOuter;
	public double[] startAngleForOuter; // This is originally "Double" with a "D" in the Mask class
	public double[] stopAngleForOuter;
	public int[] numberOfPointsForOuter;
	public double[] levelForOuter;
	public double[] weightForOuter;
	
	// For Inner Mask
	public String[] nameForInner;
	public double[] startAngleForInner; // This is originally "Double" with a "D" in the Mask class
	public double[] stopAngleForInner;
	public int[] numberOfPointsForInner;
	public double[] levelForInner;
	public double[] weightForInner;
	
	// Algorithm Parameters
	public int populationNumber;
	public int maximumIterationNumber;
	public double F;
	public double Cr;
}