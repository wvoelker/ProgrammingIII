package Assignment3_submission;

import java.util.*;
import java.math.*;
import java.lang.Math;

public class Assignment3
{
	public static String generateRandomSequence(char[] alphabet, float[] weights, int length) throws Exception
		{	
			if(CheckLengths(alphabet,weights) != true){throw new Exception("weights and alphabet length not equal");}
			if(alphabet.length < 1 || length < 1){throw new Exception("input of length 0 is not valid");}
			if(WeightsCheck(weights) != true){throw new Exception("Weights do not total 1");}
			LinkedList<Character> weighted_alphabet = generateWeightedAlphabet(weights,alphabet);
			//convert LinkedList to array
			Object[] objectArray = weighted_alphabet.toArray();
			int o = objectArray.length;
			char[] random_alphabet = new char[o];
			for(int x=0;x<o;x++)
				{
					random_alphabet[x] = (char) objectArray[x];
				}
			char[] random_seq = new char[length];
			Random random = new Random();
			for(int x=0; x<length; x++)
				{
					int wa = random.nextInt(random_alphabet.length);
					random_seq[x] = random_alphabet[wa];					
				}
			String final_seq = new String(random_seq);
			return final_seq;
		}
	
	public static boolean CheckLengths(char[] list1, float[] list2)
		{
			if(list1.length != list2.length){return false;}
			else{return true;}
		}

	public static boolean WeightsCheck(float[] weights)
		{	
			MathContext mc = new MathContext(2);
			BigDecimal sum = BigDecimal.valueOf(0);
			BigDecimal goal = BigDecimal.valueOf(1);
			for(int x=0; x<weights.length; x++)
				{
					sum = sum.add(BigDecimal.valueOf(weights[x]));
				}
			sum = sum.round(mc);
			if(sum.compareTo(goal) == 0){return true;}
			else{return false;}
		}
	
	public static LinkedList<Character> generateWeightedAlphabet(float[] weights, char[] alphabet) throws Exception
		{
			MathContext mc = new MathContext(6);
			BigDecimal sum = BigDecimal.valueOf(0);
			BigDecimal frequency = BigDecimal.valueOf(1000000);
			BigDecimal[] modified_weights = new BigDecimal[weights.length];
			
			for(int x=0; x<weights.length; x++)
				{
					modified_weights[x] = BigDecimal.valueOf(weights[x]);
					modified_weights[x] = modified_weights[x].round(mc);
					modified_weights[x] = modified_weights[x].multiply(frequency);
				}
			for(int x=0; x<modified_weights.length; x++)
				{
					sum = sum.add(modified_weights[x]);
				}
			LinkedList<Character> weighted_alphabet = new LinkedList<>();
			for(int x=0;x<weights.length; x++)
			{
				int mw = modified_weights[x].intValueExact();
				for(int y=0; y<mw; y++)
					{
						weighted_alphabet.add(alphabet[x]);
					}
			}
			return weighted_alphabet;
		}

	public static void main(String[] args) throws Exception
		{
			float[] dnaWeights = { .3f, .3f, .2f, .2f };
			char[] dnaChars = { 'A', 'C', 'G', 'T'  };
			
			// a random DNA 30 mer
			System.out.println(generateRandomSequence(dnaChars, dnaWeights,30));
			
			// background rate of residues from https://www.science.org/doi/abs/10.1126/science.286.5438.295
			float proteinBackground[] =
				{0.072658f, 0.024692f, 0.050007f, 0.061087f,
			        0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f,
			        0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f,
			        0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f,
			        0.032955f}; 
				

			char[] proteinResidues = 
					new char[] { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
								 'V', 'W', 'Y' };
			
			// a random protein with 30 residues
			System.out.println(generateRandomSequence(proteinResidues, proteinBackground, 30));
		}
}
