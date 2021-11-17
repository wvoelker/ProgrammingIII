package VCF_Reader;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.lang.*; 
import java.io.*;
     
public class Mockup extends JFrame
{   
    private JButton filterButton = new JButton("Filter"); //This button would trigger filter menu
    private JButton exportButton = new JButton("Export");//This button would allow you to export filtered/modified vcf files or plots (depending on what is visible)
    
    private JButton z = new JButton("vcfdock"); //Here would be current vcf files being viewed
    private JButton x = new JButton("meta/stats/plot");
    private JButton c = new JButton("meta/stats/plot");
    private JButton v = new JButton("filters"); //Here would be any filters currently being applied to vcf files 
    private JButton b = new JButton("meta/stats/plot");
    private JButton n = new JButton("meta/stats/plot");
    private String meta = new String();
    private JFileChooser fileChooser = new JFileChooser();
    
    public Mockup(String title)
    {
        super(title);
        setSize(1000,800);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getCenterPanel(),BorderLayout.CENTER);
        getContentPane().add(getWestPanel(),BorderLayout.WEST);
        setJMenuBar(getMyMenuBar());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

   private JPanel getCenterPanel()
   {
   	JPanel panel = new JPanel();
   	    
   	panel.setLayout(new GridLayout(2,2));
   	panel.add(x);
   	panel.add(c);
   	panel.add(b);
   	panel.add(n);
   	return panel;
   }
    
   private JPanel getWestPanel()
   {
      JPanel panel = new JPanel();
        
      panel.setLayout(new GridLayout(2,0));
      panel.add(z);
      panel.add(v);
      return panel;
   }
    
    private JMenuBar getMyMenuBar()
    {
      JMenuBar jmenuBar = new JMenuBar();
    	JMenu fileMenu = new JMenu("Menu");
    	fileMenu.setMnemonic('M');
    	//VIEW SUBMENU COMPONENTS
    	JMenuItem Meta = new JMenuItem("Meta Data");
    	JMenuItem Stats = new JMenuItem("VCF Statistics");
    	JMenuItem Plot = new JMenuItem("Plot");
      //EXPORT SUBMENU COMPONENTS
      JMenuItem Metax = new JMenuItem("Meta Data");
    	JMenuItem Statsx = new JMenuItem("VCF Statistics");
    	JMenuItem Plotx = new JMenuItem("Plot");
      JMenuItem VCFx = new JMenuItem("Filtered VCF");
    	//SUBMENU FOR EXPORTS
      JMenu subMenux = new JMenu("Export");
      fileMenu.add(subMenux);
      subMenux.add(Metax);
      subMenux.add(Statsx);
      subMenux.add(Plotx);
      subMenux.add(VCFx);
      //ADD MENU
    	jmenuBar.add(fileMenu);
    	//SUBMENU FOR VIEWING
    	JMenu subMenu = new JMenu("View");
    	fileMenu.add(subMenu);
    	subMenu.add(Meta);//DISPLAY META DATA OF CURRENT VCF
    	subMenu.add(Stats); //DISPLAY STATISTICS ON CURRENT VCF
    	subMenu.add(Plot); //SWITCH TO PLOT BUILDING LAYOUT
      //IMPORT FUNCTION TO READ VCF FILE
    	JMenuItem ImportVCF = new JMenuItem("Import VCF");
    	ImportVCF.setMnemonic('I');
    	fileMenu.add(ImportVCF);
    	//IMPORT VCF ACTION LISTENER
    	ImportVCF.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    		   int returnVal = fileChooser.showOpenDialog(Mockup.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
               File file = fileChooser.getSelectedFile();
               vcf_read(file);} //make worker to read in vcf file instead of awt thread
    		}
    	});
    	//Meta Action Listener
    	Meta.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
        			int x = 0;//placeholder for rearranging the screen to show metadata
    		}
    	});
    	//Stats Action Listener
    	Stats.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			int x = 0;//placeholder for rearranging screen to show statistics on vcf
    		}
    	});
    	//Plot Action Listener
    	Plot.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			int x = 0;//placeholder for rearranging the screen to show plots
    		}
    	});
    	return jmenuBar;
    }
    public void vcf_read(File file)
    {
         try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = new String();
      		int increment = 0;
            while(line != null)
      		   {
                  line = reader.readLine();
                  try{
                     if(line!=null&&Character.valueOf(line.charAt(0)).equals('#'))
                        {
                           meta = meta + line + '\n';
                           System.out.println("THERE IS META" + meta);
                        }
                      }
                  catch(StringIndexOutOfBoundsException ex)
                     {
                        break;
                     }
                  System.out.println(line);
               }
            reader.close();
            }
         catch(FileNotFoundException ex)
         {
            System.out.println("ERROR: file not found");
         }
         catch(IOException ex)
         {
            System.out.println("ERROR: IOException");
         }
    }
        
    public static void main(String[] args)
        {
            new Mockup("VCF_READER");
        }
    
}
        
