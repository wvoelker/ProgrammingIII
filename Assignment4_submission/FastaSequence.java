package Assignment4_submission;

import java.io.*;
import java.util.*;

public class FastaSequence
{
	//constructor statement for FastaSequence
	public FastaSequence(String header, String sequence)
		{
			this.header = header;
			this.sequence = sequence;
		}
	public static List<FastaSeqence> readFastaFile(String filepath) throws Exception
		{
			//generate BufferedReader object to read file
			List<FastaSequence>
			BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
			String line = new String;
			int increment = 0;
			while(line != null)
				{
					String line = reader.readLine();
					
					if(charAt(int 0).equals('>'))
						{
							if(increment != 0)
								{
									String[] sequence_final = new String[sequence_list.size()];
									for(int x = 0; x<sequence_final.length(); x++)
										{
											sequence_final[x] = sequence_list.get(x);
										}
									String sequence_combined = String.join("",sequence_final);
									FastaSequence completed = new FastaSequence(temp_header,sequence_combined);
								}
							String temp_header = line;
							List<String> sequence_list = new LinkedList<>();
						}
					else{sequence_list.add(line);}
					
					increment++;
					
				}

		}
	
	public String getHeader()
		{
			return this.header;
		}
	
	public String getSequence()
		{
			return this.sequence;
		}

	public float getGCRatio()
		{
			
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


