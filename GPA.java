 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel; 


public class GPA{
	// describes all my fields
	private JLabel courseTitle, gradeTitle, creditsTitle, currentTitle, targetTitle, requiredTitle, instructions, assumptions, blankCreds, title1, title2;
	private JTable entered;
	private JButton removeAll, remove, courseAdd, requiredGPA;
	private JComboBox<String> gradeText;
	private JPanel coursePanel, gpaPanel, removePanel, titlePanel, titlePanel2;
	private JTextField creditsText, courseText, output, targetText, requiredText;
	private DefaultTableModel model;
	private ArrayList<String> courses = new ArrayList<String> (); 
	private ArrayList<String> credits = new ArrayList <String> ();
	private ArrayList<String> grades = new ArrayList<String>();
	private HashMap<String, Double> val = new HashMap<String, Double>();
	private double totalCredits, totalGPA, courseGPA, target;
	private double blankCt = 0.0;
	private double required = 0.0;
	private double finalGPA = 0.0;


	public GPA()
	{
		
		val.put("A+", 4.0);
		val.put("A", 4.0);
		val.put("A-", 3.7);
		val.put("B+", 3.3);
		val.put("B", 3.0);
		val.put("B-", 2.7);
		val.put("C+", 2.3);
		val.put("C", 2.0);
		val.put("C-", 1.7);
		val.put("D+", 1.3);
		val.put("D", 1.0);
		val.put("D-", 0.7);
		val.put("F", 0.0);
		val.put("N/A", null);
		
		
		// sets up my JFrame and two panels
		JFrame fr = new JFrame();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setTitle("GPA CALCULATOR");
		fr.setSize(900, 300);
		fr.setLayout(new GridLayout(0,2));
		fr.setLocationRelativeTo(null);
		instructions = new JLabel("   Enter a course name, the number of credit hours, & the letter grade earned.     ");
		assumptions = new JLabel("        Assumptions: No non-integer or negative values are put in as credits.   ");
		blankCreds = new JLabel("If you want to add 15 blank credits, please just input 15 into the credit hours text box.");
		instructions.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		assumptions.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		blankCreds.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titlePanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		title1 = new JLabel("            INPUT YOUR COURSES!");
		title2 = new JLabel("            GPA ANALYSIS:                     ");
		title1.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 24));
		title2.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 24));
		titlePanel.add(title1);
		titlePanel2.add(title2);
		titlePanel.setBackground(new Color(94, 144, 237));
		titlePanel2.setBackground(new Color(237, 168, 94));
		coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		coursePanel.setBackground(new Color(94, 144, 237));
		coursePanel.add(titlePanel);
		gpaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		gpaPanel.setBackground(new Color(237, 168, 94));
		gpaPanel.add(titlePanel2);
		fr.add(coursePanel);
		fr.add(gpaPanel);
		
		// sets up all the course, credit, and grade add elements
		courseTitle = new JLabel("Course name: ");
		courseTitle.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		courseText = new JTextField(10);         
		courseAdd = new JButton("Add Course"); 
		courseAdd.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		courseAdd.addActionListener(new addCourseListener());
		creditsTitle = new JLabel("Credit hours: ");
		creditsTitle.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		creditsText = new JTextField(10);
		gradeTitle = new JLabel("Letter grade: ");
		gradeTitle.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		String[] numbers = new String[] {"N/A","A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F"};
		gradeText = new JComboBox<>(numbers);
		gradeText.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));

		
		// adds all the elements for the course addition to the course panel
		coursePanel.add(instructions);
		coursePanel.add(assumptions);
		coursePanel.add(blankCreds);
		coursePanel.add(courseTitle);
		coursePanel.add(courseText);
		coursePanel.add(creditsTitle);
		coursePanel.add(creditsText);
		coursePanel.add(gradeTitle);
		coursePanel.add(gradeText);
		coursePanel.add(courseAdd);

		
		// sets up a table to add course, credit, and grade elements then adds it to the course panel
		model = new DefaultTableModel();
		entered =  new JTable(model); 
		entered.setBackground(Color.LIGHT_GRAY);
		model.addColumn("Course Name");
		model.addColumn("Credit Hours");
		model.addColumn("Letter grade");
		output = new JTextField(10);
		output.setEditable(false);
		coursePanel.add(entered);
	
		// sets up all the GPA elements 
		currentTitle = new JLabel("Current GPA   ");
		currentTitle.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		targetTitle = new JLabel("Target GPA ");
		targetTitle.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		targetText = new JTextField(10);
		requiredTitle = new JLabel("Required GPA  ");
		requiredTitle.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		requiredText = new JTextField(10);
		requiredGPA = new JButton("Calculate Required GPA!");
		requiredGPA.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		requiredGPA.addActionListener(new targetGPAListener());
		
		// adds all the GPA elements to the GPA panel
		gpaPanel.add(currentTitle);
		gpaPanel.add(output);
		gpaPanel.add(targetTitle);
		gpaPanel.add(targetText);
		gpaPanel.add(requiredTitle);
		gpaPanel.add(requiredText);
		gpaPanel.add(requiredGPA);

		// sets up all the remove elements, and then adds it a remove panel that is then added to the course panel
		removeAll = new JButton("Clear course list.");
		removeAll.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		removeAll.addActionListener(new removeAllListener());
		remove = new JButton("Remove last entered course.");
		remove.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 12));
		remove.addActionListener(new removeListener());
		removePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		removePanel.setBackground(new Color(94, 144, 237));
		removePanel.add(removeAll);
		removePanel.add(remove);
		coursePanel.add(removePanel);
		
		// makes sure all the elements added to my JFrame are visible
		fr.setVisible(true);
	}
		
	// removes the last added class, and recalculate the current GPA based on the removed class when the remove last class button is pressed
	private class removeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			if(val.get(grades.get(grades.size()-1)) == null)
			{
				courseGPA = 0;
				blankCt = blankCt - Double.parseDouble(credits.get(credits.size()-1));
			}
			
			else
			{
			totalCredits -= Double.parseDouble(credits.get(credits.size()-1));
			totalGPA = totalGPA - Double.parseDouble(credits.get(credits.size()-1)) * val.get(grades.get(grades.size()-1));
			
			}
			grades.remove(grades.size()-1);
			credits.remove(credits.size()-1);
			model.removeRow(model.getRowCount()-1);			
			finalGPA= totalGPA / totalCredits;
			output.setText(String.valueOf(finalGPA));

		}
	}
	
	// removes all the elements and resets the current GPA to 0 when the remove all button is pressed 
	private class removeAllListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			model.setRowCount(0);
			courses.clear();
			credits.clear();
			grades.clear();
			totalCredits = 0.0;
			totalGPA = 0.0;
			courseGPA = 0.0;
			target = 0.0;
			blankCt = 0.0;
			required = 0.0;
			finalGPA = 0.0;
			output.setText(String.valueOf(finalGPA));
		}
	}
	
	
	// finds the required GPA based on the input of the target GPA, and displays it as an output for the required GPA w/ messages
	private class targetGPAListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			target = Double.parseDouble(targetText.getText());
			double totalHours = totalCredits + blankCt; 
			required = ((target * totalHours) - totalGPA)/blankCt;
			if(required > 4.0)
			{
				//requiredText.setText(String.valueOf(required));
				requiredText.setText(String.valueOf(required));
				targetTitle.setText("Your required GPA is above a 4.0 so you gotta add more credit hours.");
			}
			if(required < 2.0)
			{
				//requiredText.setText(String.valueOf(required));
				requiredText.setText(String.valueOf(required));
				targetTitle.setText("Your required GPA is below a 2.0 so you gotta take fewer credits.");
			}
			else
			{
				requiredText.setText(String.valueOf(required));
			}
		}
	}
	
	// implements the addition of courses, grades, and credits as well as converts inputed values into current GPA 
	private class addCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		val.put("A+", 4.0);
		val.put("A", 4.0);
		val.put("A-", 3.7);
		val.put("B+", 3.3);
		val.put("B", 3.0);
		val.put("B-", 2.7);
		val.put("C+", 2.3);
		val.put("C", 2.0);
		val.put("C-", 1.7);
		val.put("D+", 1.3);
		val.put("D", 1.0);
		val.put("D-", 0.7);
		val.put("F", 0.0);
		val.put("N/A", null);
		
		
		String courseInput = courseText.getText();
		String creditsInput = creditsText.getText();
		String gradeInput = gradeText.getSelectedItem().toString();
		String[] inputs= {courseInput, creditsInput, gradeInput};
		model.addRow(inputs);
		Double numHours = Double.parseDouble(creditsInput);
		if(val.get(gradeInput) == null)
		{
			courseGPA = 0;
			blankCt = blankCt + numHours;
			numHours = 0.0;
		}
		else
		{
			courseGPA = val.get(gradeInput) * numHours;

		}
		totalCredits = totalCredits + numHours;
		totalGPA = courseGPA + totalGPA;
		grades.add(gradeInput);
		credits.add(creditsInput);
		courses.add(courseInput);
		finalGPA = totalGPA/totalCredits;
		output.setText(String.valueOf(finalGPA));

		}
		
		
	}
	
	// main method that allows the class to run 
	public static void main(String[] args) 
	{
		new GPA();
	}
	
}