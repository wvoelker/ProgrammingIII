//Random Sequence Generator
//WILLIAM VOELKER
package git_programmingIII;

import java.util.Random;

public class Assignment1 
{
	public static void main(String[] args)
  	{
//QUESTION 1: WRITE CODE TO GENERATE 1000 3-MERS
		int AAAcount = 0;
		Random random = new Random();
		for(int counter1=0;counter1<1000;counter1++)
		{
			String sequence = "";
			for(int counter=0;counter<3;counter++)
			{
				int randomizer = random.nextInt(4);
				if(randomizer == 0)
				{
					sequence = sequence + "A";
				}
	 			if(randomizer == 1)
	                        {
	                                sequence = sequence + "T";
	                        }
	 			if(randomizer == 2)
	                        {
	                                sequence = sequence + "G";
	                        }
	 			if(randomizer == 3)
	                        {
	                                sequence = sequence + "C";
	                        }
			}
		if(sequence.equals("AAA")){AAAcount++;};
		System.out.println(counter1);
		System.out.println(sequence);
		}
//QUESTION 2: PRINT THE COUNT OF AAA
//WE WOULD EXPECT THIS COUNT TO EQUAL TO .25*.25*.25*1000 = 15.625
		System.out.println(AAAcount);
//QUESTION 3: MODIFIED FREQUENCY TEST
//p(A) = .12
//p(C) = .38
//p(G) = .39
//p(T) = .11
                int AAAcount = 0;
                Random random = new Random();
                for(int counter1=0;counter1<1000;counter1++)
                {
                        String sequence = "";
			//CHANGED COUNTER TO MORE EASILY CHANGE PROBABILITY
                        for(int counter=0;counter<3;counter++)
                        {
                                int randomizer = random.nextInt(100);
                                if(randomizer >= 0 and randomizer <= 11)
                                {
                                        sequence = sequence + "A";
                                }
                                if(randomizer >= 12 and randomizer <= 49)
                                {
                                        sequence = sequence + "T";
                                }
                                if(randomizer >= 50 and randomizer <= )
                                {
                                        sequence = sequence + "G";
                                }
                                if(randomizer == 3)
                                {
                                        sequence = sequence + "C";
                                }
                        }
                if(sequence.equals("AAA")){AAAcount++;};
                System.out.println(counter1);
                System.out.println(sequence);
                }

	}
}
