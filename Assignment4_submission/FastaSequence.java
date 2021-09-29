package Assignment4_submission;

import java.io.*;
import java.util.*;

public class FastaSequence
{
	//constructor statement for FastaSequence
	private final String header;
	private final String sequence;
	public FastaSequence(String header, String sequence)
		{
			this.header = header;
			this.sequence = sequence;
		}
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
		{
			//generate BufferedReader object to read file
			BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
			String line = new String();
			int increment = 0;
			List<FastaSequence> final_list = new LinkedList<>();
			List<String> sequence_list = new LinkedList<>();
			String temp_header = new String();
			while(line != null)
				{
					line = reader.readLine();
					
					if(Character.valueOf(line.charAt(0)).equals('>'))
						{
							if(increment != 0)
								{
									String[] sequence_final = new String[sequence_list.size()];
									for(int x = 0; x<sequence_final.length; x++)
										{
											sequence_final[x] = sequence_list.get(x);
										}
									String sequence_combined = String.join("",sequence_final);
									FastaSequence completed = new FastaSequence(temp_header,sequence_combined);
									final_list.add(completed);
								}
							temp_header = line;
							sequence_list = new LinkedList<>();
						}
					else{sequence_list.add(line);}
					increment++;
				}
			reader.close();
			return final_list;

		}
	
	public String getHeader()
		{
			return this.header.substring(1,this.header.length());
		}
	
	public String getSequence()
		{
			return this.sequence;
		}

	public float getGCRatio()
		{
			char[] GC_list = new char[this.sequence.length()];
			char basepair = 'x';
			float GC_counter = 0;
			for(int x=0; x<GC_list.length; x++)
				basepair = this.sequence.charAt(x);
				if(Character.valueOf(basepair).equals('G')||Character.valueOf(basepair).equals('C')||Character.valueOf(basepair).equals('g')||Character.valueOf(basepair).equals('c'))
					{
						GC_counter++;
					}
			float GC_ratio = GC_counter/(Integer.valueOf(sequence.length()).floatValue());
			return GC_ratio;
		}
	
	public static void writeTableSummary( List<FastaSequence> list, File outputFile) throws Exception
		{
		}

	public static void main(String[] args) throws Exception
		{
		     List<FastaSequence> fastaList = 
			FastaSequence.readFastaFile(
				"c:\\pointsToSome\\FastaFile.txt");

		     for( FastaSequence fs : fastaList)
		     {
		         System.out.println(fs.getHeader());
		         System.out.println(fs.getSequence());
		         System.out.println(fs.getGCRatio());
		      }

		     File myFile = new File("c:\\yourFilePathHere\\out.txt");

		     writeTableSummary( fastaList,  myFile);
	
		}
}


