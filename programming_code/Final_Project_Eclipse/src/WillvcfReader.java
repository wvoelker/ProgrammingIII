
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.lang.*; 
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class WillvcfReader extends JFrame
{   
    //private JButton filterButton = new JButton("Filter"); //This button would trigger filter menu
    private JButton exportButton = new JButton("Export");//This button would allow you to export filtered/modified vcf files or plots (depending on what is visible)
    
    private JButton z = new JButton("vcfdock"); //Here would be current vcf files being viewed
    private JButton x = new JButton("meta/stats/plot");
    private JButton c = new JButton("meta/stats/plot");
    //private JButton v = new JButton("filters"); //Here would be any filters currently being applied to vcf files 
    private JButton b = new JButton("meta/stats/plot");
    private JButton n = new JButton("meta/stats/plot");
    private int file_number = 0;
    private DefaultListModel Entries = new DefaultListModel();
    private JList EntryDock = new JList(Entries);
    private String meta = new String();
    private ArrayList<String> meta_list = new ArrayList<>();
    private ArrayList<ArrayList> record_list = new ArrayList<>();
    private ArrayList<String> vcf_records = new ArrayList<>();
    private JFileChooser fileChooser = new JFileChooser();
    private JMenu dockMenu = new JMenu("Dock Options");
    private HashMap<Integer,ArrayList> StatHolder = new HashMap();
    private String statsText = "";
    private JTextArea tutorial = new JTextArea(40, 58);
    private JScrollPane tutorial_scroll = new JScrollPane(tutorial);
	  JMenuItem Meta = new JMenuItem("Meta Data");
	  JMenuItem Stats = new JMenuItem("VCF Statistics");
	  JMenu Plot = new JMenu("Plot");
    //EXPORT SUBMENU COMPONENTS
    JMenuItem Metax = new JMenuItem("Meta Data");
    JMenuItem Statsx = new JMenuItem("VCF Statistics");
    //JMenuItem Plotx = new JMenuItem("Plot");
    JMenuItem VCFx = new JMenuItem("Filtered VCF");
	  //SUBMENU FOR EXPORTS
    JMenu subMenux = new JMenu("Export");
    JMenuItem plotTypes = new JMenuItem("by Variant Types");
    JMenuItem chromTypes = new JMenuItem("by Chromosomes");
    private String intro_text = "Welcome to VCF_READER by William Voelker!\r\n"
    		+ "###################################################################\r\n"
    		+ "This program is designed to read structural variants from variant call format files and provide a quick and simple analysis of the data. \r\n"
    		+ "In order to get started you need a .vcf file that contains structural variants (not single nucleotide variants).\r\n"
    		+ "If you don't have a file handy, a sample file is provided with the program, titled 'Sniffles.Rio.vcf'.\r\n"
    		+ "###################################################################\r\n"
    		+ "IMPORT VCF\r\n"
    		+ "To begin your analysis, you first will want to import your vcf file. \r\n"
    		+ "You can do this by clicking in the top left File Menu > Import VCF. You can load multiple VCF files\r\n"
    		+ "into the program at a time, they will be stored in the file 'dock' on the left side of \r\n"
    		+ "the GUI. \r\n"
    		+ "###################################################################\r\n"
    		+ "RENAMING VCF\r\n"
    		+ "If you wish, you may now rename your vcf(s) held in the file dock. You can do this by\r\n"
    		+ "clicking the VCF you wish to rename, and then clicking in the top left Dock Options > Rename Cell.\r\n"
    		+ "You can then type in the new name for your vcf and click confirm. You may then exit out of the \r\n"
    		+ "Rename Cell Window. \r\n"
    		+ "###################################################################\r\n"
    		+ "VIEWING AND EXPORTING METADATA\r\n"
    		+ "To view metadata from a selected vcf within the dock, just click File Menu > View > Meta Data.\r\n"
    		+ "This will open a seperate window to allow you to peruse the metadata. Similarly, if you wish\r\n"
    		+ "to export the selected VCF's metadata to a text file, you can click File Menu > Export > Meta Data.\r\n"
    		+ "The exported file will be named after your dock entry, and will be exported to the same location as \r\n"
    		+ "this application. \r\n"
    		+ "###################################################################\r\n"
    		+ "VIEWING AND EXPORTING STATISTICS\r\n"
    		+ "To view statistics from a selected vcf within the dock, just click File Menu > View > VCF Statistics.\r\n"
    		+ "This will open a seperate window to show you the breakdown of variant by type and the variant by chromosome\r\n"
    		+ "Similarly, if you wish to export the selected VCF's statistics to a text file, you can click File Menu > Export > VCF Statistics.\r\n"
    		+ "The exported file will be named after your dock entry, and will be exported to the same location as \r\n"
    		+ "this application. \r\n"
    		+ "###################################################################\r\n"
    		+ "VIEWING PLOTS\r\n"
    		+ "To view a plot of VCF statistics in a seperate window, you may go to File Menu > View > Plot.\r\n"
    		+ "From here you may select either viewing variant density by either Chromosome or Variant Type.\r\n"
    		+ "The ordered key to the plot is located at the bottom of the plotting window. ";
    	
    
    public WillvcfReader(String title)
    {
        super(title);
        setSize(1000,800);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getCenterPanel(),BorderLayout.CENTER);
        getContentPane().add(getWestPanel(),BorderLayout.WEST);
        setJMenuBar(getMyMenuBar());
        setVisible(true);
        EntryDock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dockMenu.setEnabled(false);
        Statsx.setEnabled(false);
        Stats.setEnabled(false);
        Meta.setEnabled(false);
        Metax.setEnabled(false);
        Plot.setEnabled(false);
        tutorial.setText(intro_text);
        tutorial.setEditable(false);
        tutorial_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

   private JPanel getCenterPanel()
   {
   	JPanel panel = new JPanel();    
   	panel.add(tutorial_scroll);
   	return panel;
   }
    
   private JPanel getWestPanel()
   {
      JPanel panel = new JPanel();
      panel.add(EntryDock);
      EntryDock.addListSelectionListener(new ListSelectionListener()
         {
    	  	@Override
            public void valueChanged(ListSelectionEvent e)
               {
                  if (EntryDock.getSelectedIndex() == -1) 
                        {
                           meta = "";
                           vcf_records.clear();
                           dockMenu.setEnabled(false);
                           Stats.setEnabled(false);
                           Meta.setEnabled(false);
                           Metax.setEnabled(false);
                           Plot.setEnabled(false);
                           
                        } 
                  else 
                        {
                           int index = EntryDock.getSelectedIndex();
                           meta = meta_list.get(index);
                           vcf_records = record_list.get(index);
                           statsText = "";
                           //System.out.println("RECIEVED COMMAND$($($");
               			   stat_worker statcalc = new stat_worker(15);
                           statcalc.run();
                           dockMenu.setEnabled(true);
                           Statsx.setEnabled(true);
                           Meta.setEnabled(true);
                           Metax.setEnabled(true);
                           Stats.setEnabled(true);
                           Plot.setEnabled(true);
                        }
               }
         });
      
      return panel;
   }
    
    private JMenuBar getMyMenuBar()
    {
      JMenuBar jmenuBar = new JMenuBar();
      JMenu fileMenu = new JMenu("File Menu");
    	  fileMenu.setMnemonic('M');
    	  //VIEW SUBMENU COMPONENTS

	      fileMenu.add(subMenux);
	      subMenux.add(Metax);
	      subMenux.add(Statsx);
	      //subMenux.add(Plotx);
	      //subMenux.add(VCFx);
        //ADD MENUs
    	jmenuBar.add(fileMenu);
    	jmenuBar.add(dockMenu);
    	//DOCK MENU ITEMS
    	JMenuItem renameCell = new JMenuItem("Rename Cell");
    	dockMenu.add(renameCell);
    	renameCell.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    			name_worker namer = new name_worker(2);
    			namer.start();
    		}
    	});
    	//SUBMENU FOR VIEWING
    	JMenu subMenu = new JMenu("View");
    	fileMenu.add(subMenu);
    	subMenu.add(Meta);//DISPLAY META DATA OF CURRENT VCF
    	subMenu.add(Stats); //DISPLAY STATISTICS ON CURRENT VCF
    	subMenu.add(Plot); //SWITCH TO PLOT BUILDING LAYOUT
    	Plot.add(plotTypes);
    	Plot.add(chromTypes);
      //IMPORT FUNCTION TO READ VCF FILE
    	JMenuItem ImportVCF = new JMenuItem("Import VCF");
    	ImportVCF.setMnemonic('I');
    	fileMenu.add(ImportVCF);
    	//IMPORT VCF ACTION LISTENER
    	ImportVCF.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    		   read_worker ReadWorker = new read_worker(1);
    		   ReadWorker.start();//make worker to read in vcf file instead of awt thread
    		}
    	});
    	//Meta Action Listener
    	Meta.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
            meta_worker MetaWorker = new meta_worker(1);
            MetaWorker.start();
    		}
    	});
    	//Stats Action Listener
    	Stats.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			stat_shower statshower = new stat_shower(12);
    			statshower.start();
    		}
    	});
    	//Plot Action Listener
    	plotTypes.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			plot_worker PlotWorker = new plot_worker(1,StatHolder,"Types");
    			PlotWorker.start();
    		}
    	});
    	chromTypes.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			plot_worker PlotWorker = new plot_worker(1,StatHolder,"Chromosomes");
    			PlotWorker.start();
    		}
    	});
    	//Action Listener - Export Meta
    	Metax.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			String fileName = "./" + "meta" + Entries.get(EntryDock.getSelectedIndex());
    			fileName = fileName.replaceAll(" ","");
    			fileName = fileName.replaceAll(",","");
    			fileName = fileName.replaceAll("`","");
    			fileName = fileName + ".txt";
    			//System.out.println(fileName);
    			File metaFile = new File(fileName);
    			try {
					writeMeta(meta,metaFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    		}
    	});
    	Statsx.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
    		{
    			String fileName = "./" + "stats" + Entries.get(EntryDock.getSelectedIndex());
    			fileName = fileName.replaceAll(" ","");
    			fileName = fileName.replaceAll(",","");
    			fileName = fileName.replaceAll("`","");
    			fileName = fileName + ".txt";
    			//System.out.println(fileName);
    			File statFile = new File(fileName);
    			try {
					writeMeta(statsText,statFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    		}
		});
    	return jmenuBar;
    }
    public void vcf_read(File file)
    {
         try{
        	dockMenu.setEnabled(false);
        	this.setEnabled(false);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = new String();
            vcf_records.clear();
            meta = "";
      		int increment = 0;
            file_number++;
            //System.out.println("READ: " + file_number);
            while(line != null)
      		   {
                  line = reader.readLine();
                  try{
                     if(line!=null&&Character.valueOf(line.charAt(0)).equals('#'))
                        {
                           meta = meta + line + '\n';
                           //System.out.println("THERE IS META" + meta);
                        }
                     else
                        {
                           vcf_records.add(line);
                        }
                      }
                  catch(StringIndexOutOfBoundsException ex)
                     {
                        break;
                     }
                  //System.out.println(line);
               }
               Entries.addElement("VCFEntry" + String.valueOf(file_number));
               EntryDock.setModel(Entries);
               meta_list.add(meta);
               record_list.add(vcf_records);
               
               StatHolder.put(file_number,getStats(vcf_records));
            reader.close();
            dockMenu.setEnabled(true);
        	this.setEnabled(true);
            }
         catch(FileNotFoundException ex)
         {
            //System.out.println("ERROR: file not found");
         }
         catch(IOException ex)
         {
            //System.out.println("ERROR: IOException");
         }
    }
    
    public class read_worker extends Thread
      {
         private final int threadId;
         public read_worker(int threadId)
            {
               this.threadId = threadId;   
            }
         @Override
         public void run()
         {
            int returnVal = fileChooser.showOpenDialog(WillvcfReader.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
               {
                  File file = fileChooser.getSelectedFile();
                  vcf_read(file);
               }
         }
      }
    public class meta_worker extends Thread
    {
      private final int threadId;
      public meta_worker(int threadId)
         {
            this.threadId = threadId;   
         }
      @Override
      public void run()
      {
         new MetaViewer("Meta Information",meta);
      }
    }
    
    public class stat_shower extends Thread
    {
      private final int threadId;
      public stat_shower(int threadId)
         {
            this.threadId = threadId;   
         }
      @Override
      public void run()
      {
    	 Integer index = EntryDock.getSelectedIndex();

    	 ArrayList<ArrayList> target = StatHolder.get(index);
         new StatViewer("Stat Viewer",target);
         
      }
    }
    
    public class name_worker extends Thread
    {
      private final int threadId;
      public name_worker(int threadId)
         {
            this.threadId = threadId;   
         }
      @Override
      public void run()
      {
    	 dockMenu.setEnabled(false);
         new cellRenamer("NAME CELL");
      }
    }
    
    public class stat_worker extends Thread
    {
      private final int threadId;
      private boolean finished;
      public stat_worker(int threadId)
         {
            this.threadId = threadId;
            this.finished = false;
         }
      @Override
      public void run()
      {
    	 Integer index = EntryDock.getSelectedIndex();
     	 ArrayList<ArrayList> target = StatHolder.get(index+1);
    	 statStringer(target);
    	 this.finished = true;
      }
    }
    
    public class plot_worker extends Thread
    {
      private final int threadId;
      private final HashMap<Integer,ArrayList> statHolder;
      private final String choice;
      public plot_worker(int threadId,HashMap<Integer,ArrayList> statHolder,String choice)
         {
            this.threadId = threadId;
            this.statHolder = statHolder;
            this.choice = choice;
         }
      @Override
      public void run()
      {
    	 new plotViewer(statHolder,choice);
      }
    }
    
    public class MetaViewer extends JFrame
    {
      private JTextArea display = new JTextArea(40, 58);
      private JScrollPane results = new JScrollPane(display);
      public MetaViewer(String title,String Meta)
         {
           super(title);
           setSize(1000,800);
           setLocationRelativeTo(null);
           getContentPane().setLayout(new BorderLayout());
           getContentPane().add(getMetaPanel(Meta),BorderLayout.CENTER);
           setVisible(true);
           setDefaultCloseOperation(MetaViewer.DISPOSE_ON_CLOSE);
         }
      private JPanel getMetaPanel(String Meta)
         {
            JPanel panel = new JPanel();
            display.setEditable(false);
            results.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            panel.add(results);
            display.setText(Meta);
            return panel;
         }
    }
    public class StatViewer extends JFrame
    {
      private JTextArea display = new JTextArea(40, 58);
      private JScrollPane results = new JScrollPane(display);
      
      public StatViewer(String title,ArrayList stats)
         {
           super(title);
           setSize(1000,800);
           setLocationRelativeTo(null);
           getContentPane().setLayout(new BorderLayout());
           getContentPane().add(getStatPanel(stats),BorderLayout.CENTER);
           setVisible(true);
           setDefaultCloseOperation(StatViewer.DISPOSE_ON_CLOSE);
         }
      private JPanel getStatPanel(ArrayList<ArrayList> stats)
         {
            JPanel panel = new JPanel();
            display.setEditable(false);

            results.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            panel.add(results);
            display.setText(statsText);
            return panel;
         }
    }
    
    public class cellRenamer extends JFrame
    {
      private JTextArea insertName = new JTextArea();
      private JButton confirmName = new JButton("CONFIRM");
      public cellRenamer(String title)
         {
           super(title);
           setSize(200,200);
           setLocationRelativeTo(null);
           getContentPane().setLayout(new BorderLayout());
           setVisible(true);
           getContentPane().add(getNamePanel(),BorderLayout.CENTER);
           setDefaultCloseOperation(cellRenamer.DISPOSE_ON_CLOSE);
         }
      private JPanel getNamePanel()
      {
    	  JPanel panel = new JPanel();
    	  panel.setLayout(new GridLayout(2,2));
    	  panel.add(insertName);
    	  panel.add(confirmName);
    	  confirmName.addActionListener(new ActionListener()
      		{
      		public void actionPerformed(ActionEvent e)
      		{
      			Entries.setElementAt(insertName.getText(), EntryDock.getSelectedIndex());
      			EntryDock.setModel(Entries);
      		}
      		});
    	  return panel;
      }

    }
    
    public static void writeMeta(String meta,File outputFile) throws Exception
    {
    	//System.out.println("export called");
    	BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    	writer.write(meta);
    	writer.close();
    }
    
    public void statStringer(ArrayList<ArrayList> stats)
    {
        ArrayList<String> tempTypes = stats.get(0);
        ArrayList<String> tempLengths = stats.get(1);
        ArrayList<String> tempChroms = stats.get(2);
        HashMap<String,Integer> typeCount = countArray(tempTypes);
        HashMap<String,Integer> chromCount = countArray(tempChroms);
        statsText = statsText + "VARIANTS BY TYPE\n";
        for (Map.Entry<String, Integer> set : typeCount.entrySet())
        {
        	String count = String.valueOf(set.getValue());
        	statsText = statsText + set.getKey() + ": " + count + "\n";
        }
        statsText = statsText + "\nVARIANTS BY CHROMOSOME\n";
        for (Map.Entry<String, Integer> set : chromCount.entrySet())
        {
        	String count = String.valueOf(set.getValue());
        	statsText = statsText + set.getKey() + ": " + count + "\n";
        }
    }
    public HashMap<String,Integer> countArray(ArrayList<String> input)
    {
    	HashMap<String,Integer> output = new HashMap();
    	for(int x=0; x<input.size();x++) 
    	{
    		String tempkey = input.get(x);
    		//System.out.println(output.containsKey(tempkey));
    		if(output.containsKey(tempkey))
    		{
    			Integer count = output.get(tempkey)+1;
    			output.put(tempkey,count);
    		}
    		else
    		{
    			output.put(tempkey, 1);
    		}
    	}
    	return output;
    }
    
    public ArrayList getStats(ArrayList records)
    {
    	ArrayList stats = new ArrayList();
    	ArrayList types = new ArrayList();
    	ArrayList chroms = new ArrayList();
    	ArrayList lengths = new ArrayList();
    	
    	for(int x=0;x<records.size();x++)
    	{
    		if(records.get(x)!=null) 
    		{
	    		String temp = (String) records.get(x);
	    		String[] splitTemp = temp.split(";");
	    		for(int y=0;y<splitTemp.length;y++)
	    		{
	    			String caster = (String) splitTemp[y];
	    			if(caster.contains("SVTYPE"))
	    				{
	    					types.add(caster);
	    					//System.out.println(splitTemp[y]);
	    				}
	    		}
    		}
    	}
    	stats.add(types);
    	for(int x=0;x<records.size();x++)
    	{
    		if(records.get(x)!=null) 
    		{
	    		String temp = (String) records.get(x);
	    		String[] splitTemp = temp.split(";");
	    		for(int y=0;y<splitTemp.length;y++)
	    		{
	    			String caster = (String) splitTemp[y];
	    			if(caster.contains("SVLEN"))
	    				{
	    					lengths.add(caster);
	    					//System.out.println(splitTemp[y]);
	    				}
	    		}
    		}
    	}
    	stats.add(lengths);
    	for(int x=0;x<records.size();x++)
    	{
    		if(records.get(x)!=null) 
    		{
	    		String temp = (String) records.get(x);
	    		String[] splitTemp = temp.split(";");
	    		for(int y=0;y<splitTemp.length;y++)
	    		{
	    			String caster = (String) splitTemp[y];
	    			if(caster.contains("Chr")&&caster.contains("CHR2")==false)
	    				{
	    					String chrom = caster.substring(0, 5);
	    					chroms.add(chrom);
	    					//System.out.println(chrom);
	    				}
	    		}
    		}
    	}
    	stats.add(chroms);
    	
    	return stats;
    }
    
    public class plotViewer extends JFrame{
    	Plotter chart = new Plotter();
    	
    public plotViewer(HashMap<Integer,ArrayList> statHolder,String choice) 
    	{
    	super(choice);
    	Integer selected_index = EntryDock.getSelectedIndex();
    	ArrayList<ArrayList> stats = statHolder.get(selected_index+1);
        ArrayList<String> tempTypes = stats.get(0);
        ArrayList<String> tempChroms = stats.get(2);
        HashMap<String,Integer> typeCount = countArray(tempTypes);
        HashMap<String,Integer> chromCount = countArray(tempChroms);
        String key = "PLOT ORDER (LEFT TO RIGHT): ";
        Boolean cyan = true;


        if(choice=="Types") {
        for (Map.Entry<String, Integer> set : typeCount.entrySet()) {
        	if(cyan) {
        	chart.addBar(set.getKey(),Color.cyan, typeCount.get(set.getKey()));
        	key = key + " " + set.getKey() + "           ";
        	cyan = false;
        	//System.out.println("cyanbar" + set.getKey());
        	}
        	else{
        			chart.addBar(set.getKey(),Color.red,typeCount.get(set.getKey()));
        			key = key + " " + set.getKey() + "           ";
        			cyan = true;
        			//System.out.println("redbar" + set.getKey());
        		}
        	}
        }
    	else
    	{for (Map.Entry<String, Integer> set : chromCount.entrySet()) {
        	if(cyan) {
        	chart.addBar(set.getKey(),Color.cyan, chromCount.get(set.getKey()));
        	key = key + " " + set.getKey() + "          ";
        	cyan = false;}
        	else{
        			chart.addBar(set.getKey(),Color.red,chromCount.get(set.getKey()));
        			key = key + " " + set.getKey() + "           ";
        			cyan = true;
        		}
        	}
    	}
		this.setDefaultCloseOperation(plotViewer.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		JButton mapkey = new JButton(key);
		mapkey.setEnabled(false);
        getContentPane().add(chart,BorderLayout.CENTER);
        getContentPane().add(mapkey,BorderLayout.SOUTH);
        setSize(1000,800);
    	}
    	}
    //plot viewer
    public class Plotter extends JPanel
    {
    	private Map<String, Integer> bars =
                new LinkedHashMap<String, Integer>();
    	private ArrayList<Color> barcolor = new ArrayList<>();
    	private ArrayList<String> names = new ArrayList<>();
    	
    	/**
    	 * Add new bar to chart
    	 * @param color color to display bar
    	 * @param value size of bar
    	 */
    	public void addBar(String string,Color color, int value)
    	{
    		bars.put(string, value);
    		barcolor.add(color);
    		names.add(string);
    		repaint();
    	}
    	
    	@Override
    	protected void paintComponent(Graphics g)
    	{
    		// determine longest bar
    		
    		int max = Integer.MIN_VALUE;
    		for (Integer value : bars.values())
    		{
    			max = Math.max(max, value);
    			//System.out.println(value);
    		}
    		
    		// paint bars
    		
    		int width = (getWidth() / bars.size()) - 2;
    		//System.out.println(width);
    		int x = 1;
    		for (int y=0; y<barcolor.size();y++)
    		{
    			//System.out.println("barprinted");
    			int value = bars.get(names.get(y));
    			int height = (int) 
                                ((getHeight()-5) * ((double)value / max));
    			g.setColor(barcolor.get(y));
    			g.fillRect(x, getHeight() - height, width, height);
    			g.setColor(Color.black);
    			g.drawRect(x, getHeight() - height, width, height);
    			x += (width + 2);
    		}
    	}
    	@Override
    	public Dimension getPreferredSize() {
    		return new Dimension(bars.size() * 10 + 2, 50);
    	}

    }
    
    public static void main(String[] args)
        {
            new WillvcfReader("VCF_READER");
        }
    
}
        

