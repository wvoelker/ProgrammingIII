package ASSIGNMENT2_SUBMISSION;

import java.util.Random;
import java.util.Calendar;
import java.util.Date;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.Random;

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
	public static boolean question(String[] full,String[] answers)
	{
		Random random = new Random();
		int x = random.nextInt(20);
		System.out.println(full[x]);
		Scanner scan = new Scanner(System.in);
		String ans = scan.next();
		if(ans.equals(answers[x]) || ans.equals(answers[x].toLowerCase()))
		{
			return true;
		}
		else{return false;}
	}
	public static void quiz(String[] full,String[] answers)
	{
		long startTime = System.currentTimeMillis();
		long time = 0;
		short num_correct = 0;
		boolean incorrect = false;
		while((time-startTime) < 30000)
			{
				time = System.currentTimeMillis();
				boolean bool = question(full,answers);
				if (bool==true)
				{
					System.out.println("CORRECT!");
					num_correct++;
				}
				else 
				{
					incorrect = true;
					System.out.println("WHOOPS, YOU MISSED THAT ONE.");
					System.out.println("YOU GOT " + num_correct + " CORRECT.");
					break;
				}
				System.out.println("TIME LEFT " + (30-(time-startTime)/1000));
			}
		if(incorrect == false)
		{
			System.out.println("TIME'S UP");
			System.out.println("YOU GOT " + num_correct + " CORRECT.");
		}
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
				quiz(FULL_NAMES,SHORT_NAMES);
			}

	}
}




