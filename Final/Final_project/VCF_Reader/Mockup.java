package VCF_Reader;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;  
     
public class Mockup extends JFrame
{
    private JButton centButton = new JButton("center");
    
    private JButton uploadButton = new JButton("upload vcf file");
    private JButton newuploadButton = new JButton("upload vcf file");
    private JButton auploadButton = new JButton("upload vcf file");
    
    private JButton aButton = new JButton("upload a vcf file");
    private JButton bButton = new JButton("upload no vcf file");
    private JButton cButton = new JButton("upload some vcf file");
    
    private JButton z = new JButton("z");
    private JButton x = new JButton("x");
    private JButton c = new JButton("c");
    private JButton v = new JButton("v");
    private JButton b = new JButton("b");
    private JButton n = new JButton("n");
    private JButton m = new JButton("m");
    private JButton l = new JButton("l");
    private JButton k = new JButton("k");
    
    public Mockup(String title)
    {
        super(title);
        setSize(1000,800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getCenterPanel(),BorderLayout.CENTER);
        getContentPane().add(getBottomPanel(),BorderLayout.SOUTH);
        getContentPane().add(getTopPanel(),BorderLayout.NORTH);
    }
    
    public static void main(String[] args) 
    {
        new Mockup("VCF_READER");
    }
    
    private class ActionAgent implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            int x = 0;
        }
    }
    
    private JPanel getBottomPanel()
    {
    	JPanel panel = new JPanel();
	
	panel.setLayout(new GridLayout(0,3));
	panel.add(uploadButton);
	panel.add(newuploadButton);
	panel.add(auploadButton);
	return panel;
    }
    
    private JPanel getTopPanel()
    {
    	JPanel panel = new JPanel();
	
	panel.setLayout(new GridLayout(0,3));
	panel.add(aButton);
	panel.add(bButton);
	panel.add(cButton);
	return panel;
    }
    
    private JPanel getCenterPanel()
    {
    	JPanel panel = new JPanel();
	
	panel.setLayout(new GridLayout(3,3));
	panel.add(z);
	panel.add(x);
	panel.add(c);
	panel.add(v);
	panel.add(b);
	panel.add(n);
	panel.add(m);
	panel.add(l);
	panel.add(k);

	return panel;
    }
}