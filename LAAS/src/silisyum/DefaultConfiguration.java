package silisyum;

public class DefaultConfiguration
{
	// Antenna Parameters
	public static int numberofElements = 8;
	public static double[] L = {0, 0, -0.1}; // initial values of amplitude, phase, and position minimum limits
	public static double[] H = {1, 30, 0.1}; // initial values of amplitude, phase, and position maximum limits  
	public static boolean amplitudeIsUsed = true;
	public static boolean phaseIsUsed = false;
	public static boolean positionIsUsed = false;
	public static double amplitudeValue = 1;
	public static double phaseValue = 0;
	public static double positionValue = 0.5;
	
	// For Outer Mask
	public static String[] nameForOuter;
	public static double[] startAngleForOuter; // This is originally "Double" with a "D" in the Mask class
	public static double[] stopAngleForOuter;
	public static int[] numberOfPointsForOuter;
	public static double[] levelForOuter;
	public static double[] weightForOuter;
	
	// For Inner Mask
	public static String[] nameForInner;
	public static double[] startAngleForInner; // This is originally "Double" with a "D" in the Mask class
	public static double[] stopAngleForInner;
	public static int[] numberOfPointsForInner;
	public static double[] levelForInner;
	public static double[] weightForInner;
	
	// Algorithm Parameters
	public static int populationNumber = 70;
	public static int maximumIterationNumber = 1000;
	public static double F = 0.7;
	public static double Cr = 0.95;	
 
}