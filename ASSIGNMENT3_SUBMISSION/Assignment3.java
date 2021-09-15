package ASSIGNMENT3_SUBMISSION;

import java.util.*;

public class Assignment3
{
	public static String generateRandomSequence(char[] alphabet, float[] weights, int length) throws Exception
	{	
		if(CheckLengths(alphabet,weights) != true){throw new Exception("weights and alphabet length not equal");}
		if(alphabet.length < 1 || length < 1){throw new Exception("input of length 0 is not valid");}
		return null;
	}
	
	public static boolean CheckLengths(char[] list1, float[] list2)
		{
			if(list1.length != list2.length){return false;}
			else{return true;}
		}
	public static boolean WeightsCheck(float[] weights)
		{	
			float sum = 0f;
			for(int x=0; x<weights.length; x++)
				{
					sum = sum + weights[x];
				}
			if(sum == 1){return true;}
			else{return false;}
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
		System.out.println(generateRandomSequence(proteinResidues, dnaWeights, 10));
	}
}
