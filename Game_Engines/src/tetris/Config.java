package tetris;

import java.awt.Choice;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Config {

	public static String rotate, left, right, down, pause;
	private static ArrayList<Choice> choices;
	
	
	public static void openConfig(JFrame frame){
	
		choices = new ArrayList<Choice>();
		JFrame options = new JFrame("Options");
		options.setSize(400, 300);
		options.setResizable(false);
		options.setLayout(null);
		options.setLocationRelativeTo(frame); 
		addChoice("Left", options, 30, 10);
		options.setVisible(true);
		
	}
	
	public static void addChoice(String name, JFrame options, int x, int y){
		JLabel label = new JLabel(name);
		label.setBounds(x, y-20, 100, 20);
		Choice key = new Choice();
		for(String s : getKeyNames()){
			key.add(s);
		}
		key.setBounds(x, y, 100, 20);
		options.add(key);
		options.add(label);
		choices.add(key);
	}
	
	public static ArrayList<String> getKeyNames(){
		ArrayList<String> result = new ArrayList<String>(); 
		for(String s : KeyGetter.keys.keySet()){
			result.add(s);
		}
		return result;
	}
	
}
