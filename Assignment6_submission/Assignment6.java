package Assignment6_submission;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.lang.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import java.math.*;

//do some kind of slow calculation which reports results periodically and has a cancel option, shows results if cancelled and amount of time in calculation
public class Assignment6 extends JFrame
{

   private Timer timer = new Timer(10, this::timeUpdate);
   private float current_time = 0;
   public static String result_text = new String();
   private static JTextArea display = new JTextArea(40, 58);
   private JButton startButton = new JButton("Start");
   private JButton cancelButton = new JButton("Cancel");
   private JTextArea threadAsk = new JTextArea(1,1);
   private JTextArea targetRange = new JTextArea(1,1);
   private int factorialTarget = 1;
   private int threadCount = 1;
   private JButton threadButton = new JButton("Confirm Thread Count Below");
   private JButton factorialButton = new JButton("Confirm Factorial Below");
   private JButton resultsButton = new JButton("Display Results");
   private float time_taken = 0;
   private static AtomicInteger counter = new AtomicInteger(1);
   private static AtomicInteger result_count = new AtomicInteger(1);
   private Semaphore semaphore = new Semaphore(1,true);
   private static ConcurrentHashMap<Integer,BigInteger> factorial_results = new ConcurrentHashMap<>();
   private boolean finished = false;
   private boolean cancelled = false;
   private static Integer conversion = Integer.valueOf(0);
   private Calculator tempcalc = new Calculator(1,semaphore);

   private JScrollPane results = new JScrollPane(display);

   public Assignment6(String title)
   {
      super(title);
      setSize(1000,800);
      setLocationRelativeTo(null);
      getContentPane().setLayout(new BorderLayout());
      getContentPane().add(getSouthPanel(),BorderLayout.SOUTH);
      getContentPane().add(getCenterPanel(),BorderLayout.CENTER);
      getContentPane().add(getWestPanel(),BorderLayout.WEST);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      resultsButton.setEnabled(false);
      cancelButton.setEnabled(false);
      startButton.setEnabled(false);
      setVisible(true);
   }
   
   public JPanel getSouthPanel()
   {
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(3,2));
      panel.add(startButton);
      panel.add(cancelButton);
      panel.add(resultsButton);
      
      startButton.addActionListener(new ActionListener()
    	   {
    		   public void actionPerformed(ActionEvent e)
    		      {
                  result_text = "";
                  display.setText(result_text);
                  factorial_results.clear();
                  counter = new AtomicInteger(1);
                  result_count = new AtomicInteger(1);
                  cancelled = false;
   		         timer.start();
                  Initializer ini = new Initializer();
                  ini.start();
                  cancelButton.setText("Cancel");
                  cancelButton.setEnabled(true);
                  startButton.setEnabled(false);
                  threadButton.setEnabled(false);
                  factorialButton.setEnabled(false);
                  resultsButton.setEnabled(false);
    		      }
    	   });
         
      cancelButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
               {
      	         timer.stop();
                  time_taken = current_time/100;
                  cancelled=true;
                  tempcalc.interrupt();
                  result_text = result_text + "CANCELLED\n";
                  cancelButton.setText("Cancel");
                  cancelButton.setEnabled(false);
                  startButton.setEnabled(true);
                  threadButton.setEnabled(true);
                  factorialButton.setEnabled(true);
                  threadAsk.setEnabled(true);
                  targetRange.setEnabled(true);
                  current_time=0;
                  resultsButton.setEnabled(true);
               }
         });
         
      resultsButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
               {
                  resultsButton.setEnabled(false);
                  DisplayBot displayer = new DisplayBot();
                  displayer.start();
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
   
   public JPanel getWestPanel()
   {
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(4,3));
      panel.add(factorialButton);
      panel.add(targetRange);
      panel.add(threadButton);
      panel.add(threadAsk);
      threadButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
               {
                  try 
                     {
                        threadCount = Integer.parseInt(threadAsk.getText());
                        semaphore = new Semaphore(threadCount,true);
                        threadButton.setEnabled(false);
                        threadAsk.setEnabled(false);
                        resultsButton.setEnabled(false);
                        if(factorialButton.isEnabled()==false)
                        {
                           startButton.setEnabled(true);
                        }
                     }
                  catch(NumberFormatException i)
                     {
                        threadAsk.setText("ERROR: Integer Required!");
                        threadCount = 1;
                     }
               }
         });
      factorialButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
               {
                  try 
                     {
                        factorialTarget = Integer.parseInt(targetRange.getText());
                        factorialButton.setEnabled(false);
                        targetRange.setEnabled(false);
                        resultsButton.setEnabled(false);
                        if(threadButton.isEnabled()==false)
                           {
                              startButton.setEnabled(true);
                           }
                     }
                  catch(NumberFormatException i)
                     {
                        targetRange.setText("ERROR: Integer Required!");
                        factorialTarget = 1;
                     }
               }
         });
      
      return panel;
   }
   class Calculator extends Thread
   {
      private final Semaphore semaphore;
      private final int input;
      private final int threadID;
      public Calculator(int input,Semaphore semaphore)
         {
            this.input = input;
            this.threadID = input;
            this.semaphore = semaphore;
            try{semaphore.acquire();}
            catch(InterruptedException i){System.out.println("interrupt");}
         }
      
      @Override
      public void run() //get factorial within range given
      { 
         try
         {
            BigInteger temp = new BigInteger("0");
            temp = findFactorial(input);
            Deploy(input,temp);
            if(input == factorialTarget)
               {
                  timer.stop();
                  time_taken = current_time/100;
                  result_text = result_text + "COMPLETED\n";
                  result_text = result_text + "TIME TAKEN: " + String.valueOf(time_taken) + " seconds" + "\n";
                  display.setText(result_text);
                  current_time=0;
                  targetRange.setEnabled(true);
                  threadButton.setEnabled(true);
                  factorialButton.setEnabled(true);
                  cancelButton.setEnabled(false);
                  threadAsk.setEnabled(true);
                  resultsButton.setEnabled(true);
               }
                  
         }
         catch(Exception ex)
         {
            ex.printStackTrace();
            System.exit(1);
         }
         finally
         {
            semaphore.release();
         }
         
         
      }
   }
   
   class Initializer extends Thread
   {
      @Override
      public void run()
         {
            thread_loop();
         }
   }
   public void timeUpdate(ActionEvent e)
   {
      current_time++;//in 1/10ths of a second
      repaint();
   }
   
   public BigInteger findFactorial(int input) //factorial function for computationally intensive and slow task
   {
      BigInteger factorial = new BigInteger("1");
      BigInteger mult_val = new BigInteger("1");
      for(int x=1;x<=input;x++)
      {
         mult_val = new BigInteger(String.valueOf(x));
         factorial = factorial.multiply(mult_val);
      }
      return factorial;
   }
   
   public static synchronized void Deploy(int input,BigInteger factorial)//lock the output stream to just one thread at a time
   {
      conversion = Integer.valueOf(input);
      factorial_results.put(conversion,factorial);
      int tempint = result_count.getAndAdd(1);
      result_text = "Factorials Completed: " + String.valueOf(tempint) + "\n";
      display.setText(result_text);
   }
   
   public void thread_loop()
   {
      for(int x=1;x<=factorialTarget;x++)
         {
            if(cancelled==true){break;}
            else
               {
                  Calculator temp = new Calculator(counter.getAndAdd(1),semaphore);
                  temp.start();
               }
         }
   }  
   
   class DisplayBot extends Thread
   {
      @Override
      public void run()
      {
         result_text = "";
         for(int x=1;x<=factorialTarget;x++)
            {
               BigInteger nextfact = factorial_results.get(x);
               if(nextfact==null){}
               else
                  {
                     result_text = result_text + String.valueOf(nextfact) + "\n";
                     display.setText(result_text);
                  }
            }
      }
   }
   public static void main(String[] args) 
   {
      new Assignment6("Slow GUI Task");
   }
}