package Assignment6_submission;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

//do some kind of slow calculation which reports results periodically and has a cancel option, shows results if cancelled and amount of time in calculation
public class Assignment6 extends JFrame
{

   private Timer timer = new Timer(100, this::timeUpdate);
   private float current_time = 0;
   private String result_text = new String();
   private JTextArea display = new JTextArea(40, 58);
   private JButton startButton = new JButton("Start");
   private JButton cancelButton = new JButton("Cancel");
   private float time_taken = 0;
   
   private JScrollPane results = new JScrollPane(display);

   public Assignment6(String title)
   {
      super(title);
      setSize(1000,800);
      setLocationRelativeTo(null);
      getContentPane().setLayout(new BorderLayout());
      getContentPane().add(getSouthPanel(),BorderLayout.SOUTH);
      getContentPane().add(getCenterPanel(),BorderLayout.CENTER);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
   
   public JPanel getSouthPanel()
   {
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(2,2));
      panel.add(startButton);
      panel.add(cancelButton);
      
      startButton.addActionListener(new ActionListener()
    	   {
    		   public void actionPerformed(ActionEvent e)
    		      {
   		         timer.start();
                  cancelButton.setText("Cancel");
                  cancelButton.setEnabled(true);
                  startButton.setEnabled(false);
    		      }
    	   });
         
      cancelButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
               {
      	         timer.stop();
                  time_taken = current_time/10;
                  result_text = result_text + time_taken + "\n";
                  display.setText(result_text);
                  current_time = 0;
                  cancelButton.setText("Cancel");
                  cancelButton.setEnabled(false);
                  startButton.setEnabled(true);
               }
         });
               
      return panel;
   }
   
   public JPanel getCenterPanel()
   {
      JPanel panel = new JPanel();
      display.setEditable(false);
      results.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      panel.add(results);
      return panel;
      
   }
   class calculator extends Thread
   {
      
      @Override
      public void run()
      {
         int x = 1;
      }
   }
   public void timeUpdate(ActionEvent e)
   {
      current_time++;//in 1/10ths of a second
   }
   
   public static void main(String[] args) 
   {
      new Assignment6("Slow GUI Task");
   }
}