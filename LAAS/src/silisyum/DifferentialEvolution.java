package silisyum;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	
	public DifferentialEvolution(int _numberofElements, int _populationNumber, int _maximumIterationNumber, double _F, double _Cr, double[] _L, double[] _H, AntennaArray _aA, AntennaArray _aAForP, Mask _mask, boolean _amplitudeIsUsed, boolean _phaseIsUsed, boolean _positionIsUsed) throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
		
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

	private void initialize() throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
		
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
			
			// .inp kaynak dosyasini düzenle
			PrintWriter writer = new PrintWriter("kaynak.inp", "UTF-8");
			writer.println("CM forw: 90, 0 ; back: 0, 0");
			writer.println("CE");
			writer.println("GW 1 7 0 0 0 0 0 0.058193 4.0591e-4");
			writer.println("GW 2 7 0.0625 0 0 0.0625 0 0.058193 4.0591e-4");
			writer.println("GW 3 7 0.125 0 0 0.125 0 0.058193 4.0591e-4");
			writer.println("GW 4 7 0.1875 0 0 0.1875 0 0.058193 4.0591e-4");
			writer.println("GW 5 7 0.25 0 0 0.25 0 0.058193 4.0591e-4");
			writer.println("GW 6 7 0.3125 0 0 0.3125 0 0.058193 4.0591e-4");
			writer.println("GW 7 7 0.375 0 0 0.375 0 0.058193 4.0591e-4");
			writer.println("GW 8 7 0.4375 0 0 0.4375 0 0.058193 4.0591e-4");
			writer.println("GE 0");
			writer.println("EK");

			writer.println("EX 0 1 4 0 " + temp[0] + " 0");
			writer.println("EX 0 2 4 0 " + temp[1] + " 0");
			writer.println("EX 0 3 4 0 " + temp[2] + " 0");
			writer.println("EX 0 4 4 0 " + temp[3] + " 0");
			writer.println("EX 0 5 4 0 " + temp[4] + " 0");
			writer.println("EX 0 6 4 0 " + temp[5] + " 0");
			writer.println("EX 0 7 4 0 " + temp[6] + " 0");
			writer.println("EX 0 8 4 0 " + temp[7] + " 0");
			
			writer.println("GN -1");
			writer.println("FR 0 1 0 0 2400 0");
			writer.println("RP 0 1 361 1000 90 0 0 1");
			writer.close();
			
			// nec2++'i calistir
			try {
				
				Process pro = Runtime.getRuntime().exec(new String[]{"nec2++", "-i", "kaynak.inp", "-o", "sonuc.out"});
				pro.waitFor();
				
			} catch (IOException e) {
				e.printStackTrace();				
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
	
	public boolean iterate() throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
		
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
			
			// .inp kaynak dosyasini düzenle
			PrintWriter writer = new PrintWriter("kaynak.inp", "UTF-8");
			writer.println("CM forw: 90, 0 ; back: 0, 0");
			writer.println("CE");
			writer.println("GW 1 7 0 0 0 0 0 0.058193 4.0591e-4");
			writer.println("GW 2 7 0.0625 0 0 0.0625 0 0.058193 4.0591e-4");
			writer.println("GW 3 7 0.125 0 0 0.125 0 0.058193 4.0591e-4");
			writer.println("GW 4 7 0.1875 0 0 0.1875 0 0.058193 4.0591e-4");
			writer.println("GW 5 7 0.25 0 0 0.25 0 0.058193 4.0591e-4");
			writer.println("GW 6 7 0.3125 0 0 0.3125 0 0.058193 4.0591e-4");
			writer.println("GW 7 7 0.375 0 0 0.375 0 0.058193 4.0591e-4");
			writer.println("GW 8 7 0.4375 0 0 0.4375 0 0.058193 4.0591e-4");
			writer.println("GE 0");
			writer.println("EK");
			writer.println("EX 0 1 4 0 " + Xtrial[0] + " 0");
			writer.println("EX 0 2 4 0 " + Xtrial[1] + " 0");
			writer.println("EX 0 3 4 0 " + Xtrial[2] + " 0");
			writer.println("EX 0 4 4 0 " + Xtrial[3] + " 0");
			writer.println("EX 0 5 4 0 " + Xtrial[4] + " 0");
			writer.println("EX 0 6 4 0 " + Xtrial[5] + " 0");
			writer.println("EX 0 7 4 0 " + Xtrial[6] + " 0");
			writer.println("EX 0 8 4 0 " + Xtrial[7] + " 0");
			
			writer.println("GN -1");
			writer.println("FR 0 1 0 0 2400 0");
			writer.println("RP 0 1 361 1000 90 0 0 1");
			writer.close();
			
			// nec2++'i calistir
			try {
				
				Process pro = Runtime.getRuntime().exec(new String[]{"nec2++", "-i", "kaynak.inp", "-o", "sonuc.out"});
				pro.waitFor();
				
			} catch (IOException e) {
				e.printStackTrace();				
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
