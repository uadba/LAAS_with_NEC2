package silisyum;

import java.util.Random;

public class DifferentialEvolution {
	
	private int numberofElements;
	private int problemDimension = 0;
	private int populationNumber;
	public double[][] members;
	private double[] memberFitness;
	private double[] Xtrial;
	private double[] temp;
	public int bestMember = -1;
	public double fitnessOfBestMember = 0;
	public int maximumIterationNumber;
	private double F;
	private double Cr;
	private int R1, R2, R3;
	private Random r;
	public int iterationIndex = 0;
	private double[] L;
	private double[] H;
	private double[] Ls;
	private double[] Hs;
    private boolean amplitudeIsUsed;
    private boolean phaseIsUsed;
    private boolean positionIsUsed;
	private Cost cost;
	private boolean iterationState = true;
	public double[] costValues;
	
	public DifferentialEvolution(int _numberofElements, int _populationNumber, int _maximumIterationNumber, double _F, double _Cr, double[] _L, double[] _H, AntennaArray _aA, AntennaArray _aAForP, Mask _mask, boolean _amplitudeIsUsed, boolean _phaseIsUsed, boolean _positionIsUsed) {
		
		numberofElements = _numberofElements;
		populationNumber = _populationNumber;
		maximumIterationNumber = _maximumIterationNumber;
		F = _F;
		Cr = _Cr;
		L = _L;
		H = _H;
	    amplitudeIsUsed = _amplitudeIsUsed;
	    phaseIsUsed = _phaseIsUsed;
	    positionIsUsed = _positionIsUsed;
	    
		if (amplitudeIsUsed) problemDimension = numberofElements;		
		if (phaseIsUsed) problemDimension += numberofElements;		
		if (positionIsUsed) problemDimension += numberofElements;
		
		cost = new Cost(numberofElements, _aA, _aAForP, _amplitudeIsUsed, _phaseIsUsed, positionIsUsed);
		r = new Random();		
		createArrays();
		initialize();
		costValues = new double[maximumIterationNumber];
	}
	
	private void createArrays() {
		members = new double[problemDimension][populationNumber];
		memberFitness = new double[populationNumber];
		Xtrial = new double[problemDimension];
		temp = new double[problemDimension];
		Ls = new double[problemDimension];
		Hs = new double[problemDimension];
	}

	private void initialize() {
		
		int delta = 0;
		if(amplitudeIsUsed) {
			for (int e = 0; e < numberofElements; e++) {
				Ls[e] = L[0];
				Hs[e] = H[0];
			}
			delta = numberofElements;
		}
		
		if (phaseIsUsed) {
			for (int e = 0; e < numberofElements; e++) {
				Ls[e + delta] = L[1];
				Hs[e + delta] = H[1];
			}
			delta += numberofElements;
		}		
		
		if (positionIsUsed) {
			for (int e = 0; e < numberofElements; e++) {
				Ls[e + delta] = L[2];
				Hs[e + delta] = H[2];
			}
		}		
		
		Random r = new Random();
		for (int m = 0; m < populationNumber; m++) {
			for (int d = 0; d < problemDimension; d++) {
				members[d][m] = Ls[d] + (Hs[d]-Ls[d])*r.nextDouble();
				temp[d] = members[d][m];
			}			
			memberFitness[m] = cost.function(temp);
			if(bestMember == -1) {
				bestMember = m;
				fitnessOfBestMember = memberFitness[m];
			}
			else if(memberFitness[m] < bestMember) {
				bestMember = m;
				fitnessOfBestMember = memberFitness[m];
			}
			
		}		
	}
	
	public boolean iterate() {
		
		for (int individual = 0; individual < populationNumber; individual++) {
			R1 = r.nextInt(populationNumber);
			R2 = r.nextInt(populationNumber);
			R3 = r.nextInt(populationNumber);
			
			int ri = r.nextInt(problemDimension);
			
			for (int d = 0; d < problemDimension; d++) {
				if(r.nextDouble() < Cr || ri == d) {
					Xtrial[d] = members[d][R3] + F * (members[d][R2] - members[d][R1]);
				} else {
					Xtrial[d] = members[d][individual];
				}
			}
			
			for (int d = 0; d < problemDimension; d++) {
				if(Xtrial[d]<Ls[d] || Xtrial[d]>Hs[d])
				{
					Xtrial[d] = Ls[d] + (Hs[d]-Ls[d])*r.nextDouble();
				}
			}
			
			double fitnessOfTrial = cost.function(Xtrial);
			if(fitnessOfTrial < memberFitness[individual]) {
				for (int d = 0; d < problemDimension; d++) {
					members[d][individual] = Xtrial[d];					
				}
				memberFitness[individual] = fitnessOfTrial;				
			}
			if(fitnessOfTrial < memberFitness[bestMember]) {
				bestMember = individual;
				fitnessOfBestMember = memberFitness[individual];
			}
		}
		
		costValues[iterationIndex] = fitnessOfBestMember;
		iterationIndex++;
		
		if(iterationIndex == maximumIterationNumber)
			iterationState = false;
		
		return iterationState;
	}
}
