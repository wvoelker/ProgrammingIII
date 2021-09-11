package ASSIGNMENT2_SUBMISSION;

import java.util.Random;
import java.util.Calendar;
import java.util.Date;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class Assignment2
{
        public static boolean start()
	{
		while(true)
		{
		System.out.println("AMINO ACID QUIZ READY, TYPE S TO START");
		Scanner start_input = new Scanner(System.in);
		String check = start_input.next();
		if(check.equals("S") || check.equals("s"))
			{
				return true;
			}
		}
	}
	
	public static void quiz()
	{
		long startTime = System.currentTimeMillis();
		long time = 0;
		short num_correct = 0;
		while((time-startTime) < 30000)
			{
				time = System.currentTimeMillis();
				System.out.println((time-startTime)/1000);
			}
		System.out.println("TIME'S UP");
	}
	public static void main(String[] args)
        {
		String[] SHORT_NAMES =
		{ "A","R", "N", "D", "C", "Q", "E",
		"G",  "H", "I", "L", "K", "M", "F",
		"P", "S", "T", "W", "Y", "V" };

		String[] FULL_NAMES =
		{"alanine","arginine", "asparagine",
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine",
		"phenylalanine", "proline",
		"serine","threonine","tryptophan",
		"tyrosine", "valine"};

		if (start()==true)
			{
				quiz();
			}

	}
}




