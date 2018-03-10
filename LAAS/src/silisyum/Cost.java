package silisyum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Cost {
	
//	private int numberofElements;
	private AntennaArray aA;
	private AntennaArray aAForP;
	
//    private boolean amplitudeIsUsed;
//    private boolean phaseIsUsed;
//    private boolean positionIsUsed;
	private double[] aci;
	private double[] dB;
	
	public Cost(int _numberofElements, AntennaArray _aA, AntennaArray _aAForP, boolean _amplitudeIsUsed, boolean _phaseIsUsed, boolean _positionIsUsed) {
//		numberofElements = _numberofElements;
		aA = _aA;
		aAForP = _aAForP;
//	    amplitudeIsUsed = _amplitudeIsUsed;
//	    phaseIsUsed = _phaseIsUsed;
//	    positionIsUsed = _positionIsUsed;
	
		aA.createLongArrays();
		aAForP.createLongArrays();
		
		aci = new double[361];
		dB = new double[361];
	}
	
	public double function(double[] theVector) {
		
		double result = 0;
//		
//		int delta = 0;
//		if (amplitudeIsUsed) {
//			// this is for amplitudes	
//			for (int index = 0; index < numberofElements; index++) {
//				aA.amplitude[index] = theVector[index];
//			}
//			delta = numberofElements;
//		}
//		
//		if (phaseIsUsed) {
//			// this is for phases
//			for (int index = 0; index < numberofElements; index++) {
//				aA.phase[index] = theVector[index + delta];
//			}
//			delta += numberofElements;
//		}
//		
//		if (positionIsUsed) {
//			// this is for positions. It starts with 1 instead of 0
//			aA.position[0] = 0;
//			for (int index = 1; index < numberofElements; index++) {
//				aA.position[index] = aA.position[index - 1] + 0.5 + theVector[index + delta];
//			} 
//		}
//		
//		aA.createPatternForOptimization();
//
//		if (aA.numberOfSLLOuters > 0) {
//			// ------------ for Outers ------------
//			for (int z = 0; z < aA.angleForOptimization_ForOuters.length; z++) {
//				if (aA.patternForOptimization_dB_ForOuters[z] > aA.levels_ForOuters[z])
//				{
//					result += aA.weights_ForOuters[z] * (aA.patternForOptimization_dB_ForOuters[z] - aA.levels_ForOuters[z]);
//				}
//			}
//		}
//		
//		if (aA.numberOfSLLInners > 0) {
//			// ------------ for Inners ------------
//			for (int z = 0; z < aA.angleForOptimization_ForInners.length; z++) {
//				if (aA.levels_ForInners[z] > aA.patternForOptimization_dB_ForInners[z]) {
//					result += aA.weights_ForInners[z] * (aA.levels_ForInners[z] - aA.patternForOptimization_dB_ForInners[z]);
//				}
//			} 
//		}		
		
		double sll = -3;
		for(int sayac=0; sayac<75; sayac++)
		{			
			if(dB[sayac] > sll)
				result += (dB[sayac] - sll)*(dB[sayac] - sll);
		}
		
		
		return result;
	}
	
	public void dosyadanOku() {
		
		String butunDosya = "";
			
			try {

				butunDosya = new String(Files.readAllBytes(Paths.get("sonuc.out")));
										
				Scanner sc = new Scanner(butunDosya);
				sc.useDelimiter("DEGREES[\r\n]+").next();						
				sc.useDelimiter("\\s").next();
				
				String blok = sc.useDelimiter("[\r\n]+[\r\n]+[\r\n]+").next();
				sc.close();
				sc = new Scanner(blok);
				
				String devam_mi = "+"; // Devam
				int sayac=0;
				while(devam_mi == "+") {
					// Theta
					sc.useDelimiter("\\S").next();
					sc.useDelimiter("\\s");						
					sc.next();
					
					// Phi
					sc.useDelimiter("\\S").next();
					sc.useDelimiter("\\s");						
					aci[sayac] += Double.parseDouble(sc.next());
					
					// Vertical ve Horizontali atla
					sc.useDelimiter("\\S").next();
					sc.useDelimiter("\\s").next();
					sc.useDelimiter("\\S").next();
					sc.useDelimiter("\\s").next();
					
					// Total dB
					sc.useDelimiter("\\S").next();
					sc.useDelimiter("\\s");						
					dB[sayac] += Double.parseDouble(sc.next());
					
					// Alt satira gec
					sc.useDelimiter("[\r\n]+").next();
					if(sc.hasNext() == false) break; // Bisey kalmamis birak gitsin.		
					sc.useDelimiter("\\s").next();							

					sayac++;
				}
					

				sc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
