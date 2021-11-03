package Assignment5_submission;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;//extra import to make sure uses swing timer instead of util timer

public class Assignment5 extends JFrame
{   
   	private String[] answers =
		{ "A","R", "N", "D", "C", "Q", "E",
		"G",  "H", "I", "L", "K", "M", "F",
		"P", "S", "T", "W", "Y", "V" };

		private String[] questions =
		{"alanine","arginine", "asparagine",
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine",
		"phenylalanine", "proline",
		"serine","threonine","tryptophan",
		"tyrosine", "valine"};
      
      private int answer = 0;
      private int current_time = 0;
      
      private JButton startButton = new JButton("Start");
      private JButton cancelButton = new JButton("Cancel");
      private JButton submitButton = new JButton("Submit");
      private Timer timer = new Timer(1000, this::timeUpdate);
      private JLabel time_left = new JLabel("Time Left: " + 30);
      private JLabel answered_right = new JLabel("Number Correct: " + 0);
      private JLabel answered_wrong = new JLabel("Number Incorrect: " + 0);
      private JLabel question = new JLabel("");
      private int number_right = 0;
      private int number_wrong = 0;
      private String ans = new String();
      private JLabel latest = new JLabel("");
      
      JTextField user_input = new JTextField("");
      
      public Assignment5(String title)
         {
            super(title);
            setSize(1000,800);
            setLocationRelativeTo(null);
            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(getSouthPanel(),BorderLayout.SOUTH);
            getContentPane().add(getCenterPanel(),BorderLayout.CENTER);
            getContentPane().add(getWestPanel(),BorderLayout.WEST);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cancelButton.setEnabled(false);
            submitButton.setEnabled(false);
         }
      public String getQuestion(String[] library)
         {
            Random random = new Random();
		      answer = random.nextInt(20);
            return(library[answer]);
         }
      public JPanel getSouthPanel()
         {
            JPanel panel = new JPanel();
   	    
   	      panel.setLayout(new GridLayout(2,3));
            panel.add(new JLabel("Input Answer: "));
            panel.add(user_input);
            panel.add(startButton);
            panel.add(cancelButton);
            panel.add(submitButton);

            startButton.addActionListener(new ActionListener()
    	         {
    		         public void actionPerformed(ActionEvent e)
    		            {
        			         timer.start();
                        cancelButton.setText("Cancel");
                        cancelButton.setEnabled(true);
                        startButton.setEnabled(false);
                        submitButton.setEnabled(true);
    		            }
    	         });
            cancelButton.addActionListener(new ActionListener()
               {
                  public void actionPerformed(ActionEvent e)
                     {
                        timer.stop();
                        startButton.setEnabled(true);
                        cancelButton.setEnabled(false);
                        submitButton.setEnabled(false);
                        time_left.setText("Time Left: " + 30);
                        current_time = 0;
                        number_right = 0;
                        number_wrong = 0;
                        answered_wrong.setText("Number Incorrect: " + 0);
                        answered_right.setText("Number Correct: " + 0);
                        latest.setText("CANCELLED");
                     }
               });
            submitButton.addActionListener(new ActionListener()
               {
                  public void actionPerformed(ActionEvent e)
                     {
                        ans = user_input.getText();
                        if(ans.equals(answers[answer]) || ans.equals(answers[answer].toLowerCase()))
                           {
                              number_right++;
                              user_input.setText("");
                              latest.setText("CORRECT!");
                              question.setText(getQuestion(questions));
                              answered_right.setText("Number Correct: " + number_right);
                              
                           }
                         else
                           {
                              number_wrong++;
                              user_input.setText("");
                              latest.setText("WRONG :(");
                              question.setText(getQuestion(questions));
                              answered_wrong.setText("Number Incorrect: " + number_wrong);
                           }
                     }
               });
            
            return panel;
         }
      public JPanel getCenterPanel()
         {
            JPanel panel = new JPanel();
            question.setText(getQuestion(questions));
            panel.add(question);
            return panel;
         }
      public JPanel getWestPanel()
         {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4,1));
            panel.add(time_left);
            panel.add(answered_right);
            panel.add(answered_wrong);
            panel.add(latest);
            return panel;
         }
      public void timeUpdate(ActionEvent e)
         {
            if(current_time < 30)
               {
                  current_time++;
                  time_left.setText("Time Left: " + (30-current_time));
               }
            else
               {
                  timer.stop();
                  current_time = 0;
                  cancelButton.setText("Restart?");
                  submitButton.setEnabled(false);
                  time_left.setText("QUIZ OVER");
                  answered_right.setText("YOU GOT " + number_right + " CORRECT!");
                  answered_wrong.setText("YOU GOT " + number_wrong + " WRONG!");
                  
               }
         } 
      public static void main(String[] args) 
        {
            new Assignment5("Amino Quiz");
        }
}