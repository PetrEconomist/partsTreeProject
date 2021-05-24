package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
class GUI{
	

	
	
	
    public static void main(String args[]) {

        //Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        //Creating the MenuBar and adding components
        JMenuBar menuBar = setupMenuBar();
               

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
    }

    private static JMenuBar setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFiles = setupMenuFiles();
		JMenu menuHelp = setupMenuHelp();
        JMenu menuExamples = setupMenuExamples();
        menuBar.add(menuFiles);
        menuBar.add(menuHelp);
        menuBar.add(menuExamples);
        
        return menuBar;
	}
    
    private static JMenu setupMenuFiles() {
    	JMenu menuFiles = new JMenu("Files");
    	JMenuItem itemOpen = new JMenuItem("Open");
        JMenuItem itemSaveAs = new JMenuItem("Save as");
        
        menuFiles.add(itemOpen);
        menuFiles.add(itemSaveAs);
    	
    	return menuFiles;
    }
    
    private static JMenu setupMenuHelp() {
    	JMenu menuHelp = new JMenu("Help");
    	
    	return menuHelp;
    }
    
    private static JMenu setupMenuExamples() {
    	JMenu menuExamples = new JMenu("Java examples");
    	JMenuItem beepItem = new JMenuItem("Beep");
    	JMenuItem whereAmIItem = new JMenuItem("where am I");
    	menuExamples.add(beepItem);
    	menuExamples.add(whereAmIItem);
    	
    	ActionListener beepListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.printf("beeped\n");
        	}
        };
        
        ActionListener beepListener2 = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.printf("beeped two\n");
        	}
        };
        
        ActionListener whereAmIListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		File f = new File("OutFile.txt"); 
        		System.out.println("f.getAbsolutePath() = " + f.getAbsolutePath());
        	}
        };
        
        beepItem.addActionListener(beepListener);
        beepItem.addActionListener(beepListener2);
        whereAmIItem.addActionListener(whereAmIListener);
    	return menuExamples;
    }
    
}
