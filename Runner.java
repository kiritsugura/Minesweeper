import java.awt.*;
import java.awt.event.*;
import java.util.IllegalFormatException;
import java.io.*;

import javax.swing.*;
//The Main class for the Minesweeper game. 
public class Runner {
	private static Difficulty current;
	private static JMenuBar menu;
	private static JMenu difficulty,customSubmenu,reset;
	private static JMenuItem easy,medium,hard,easyC,mediumC,hardC,resetStats,resetBoard;
	private static GameWindow f;
	private static File stats;
	private static String stat;
	private static int wins,loses;
	private static String diff,loaded;
	//Main method for the game
	public static void main(String[] args){
		loaded="MEDIUM";
		loadStats();
		createMenu();
		f=new GameWindow(current);
		f.setWLPanels(wins,loses);
		f.setWL(wins, loses);
		Container pane=f.getContentPane();
		pane.add(menu);
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent event){
				changePane();
				f.dispose();
			}
		});
	}
	//Loads the stats from the stats.txt file,or creates the stats.txt file if no file is found.
	private static void loadStats(){
		File temp=new File("");
		stats=new File("stats.txt");
		if(stats.exists()){
			try{
				FileInputStream str=new FileInputStream(stats.getAbsolutePath());
				StringBuilder builder=new StringBuilder();
				BufferedReader reader=new BufferedReader(new InputStreamReader(str,"UTF-8"));
				String line=reader.readLine();
				while(line!=null){
					builder.append(line);
					line=reader.readLine();
				}
				reader.close();
				stat=builder.toString();
			}catch(FileNotFoundException fnf){	
			}catch(IOException io){}
			getWL();
		}else{
			createStats();
		}
	}
	//Method that creates a default stats.txt File and writes it in the same directory as the .java files for the game.
	private static void createStats(){
		stats=new File("stats.txt");
		if(stats.exists()){
			stats.delete();
		}try{
			Writer writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stats.getAbsolutePath()),"UTF-8"));
			writer.write(createNewStats());
			writer.close();
		}catch(IOException io){}
	}	
	//Returns a String default value of 0 and 0 to be written to the stats.txt File.
	private static String createNewStats(){
		stat="[wins]="+wins+";[loses]="+loses+";"+"[difficulty]="+diff+";";
		return stat;
	}
	//Converts the win,loss and difficulty values from the stats.txt File into ints that are assigned to their respective variables.
	private static void getWL(){
		String s="";
		if(stat.indexOf("[wins]=")+7>0 && stat.indexOf(";")>0){
			wins=Integer.valueOf((stat.substring(stat.indexOf("[wins]=")+7,stat.indexOf(";"))));
		}else{
			wins=0;
		}
		s=stat.substring(stat.indexOf(";")+1);
		if(s.indexOf("[loses]=")+8>0 && s.indexOf(";")>0){
			loses=Integer.valueOf(s.substring(s.indexOf("[loses]=")+8,s.indexOf(";")));
		}else{
			loses=0;
						
		}
		s=s.substring(s.indexOf(";")+1);
		if(s.indexOf("[difficulty]=")+13>0 && s.indexOf(";")>0)
		loaded=s.substring(s.indexOf("[difficulty]=")+13,s.indexOf(";"));
		if(loaded.equalsIgnoreCase("EASY")){
			current=Difficulty.Easy;
		}else if(loaded.equalsIgnoreCase("MEDIUM")){
			current=Difficulty.Medium;
		}else if(loaded.equalsIgnoreCase("HARD")){
			current=Difficulty.Hard;
		}else{
			current=Difficulty.Easy;
		}
		if(wins==0 && loses==0 && current==Difficulty.Easy){
			createStats();
		}
	}	
	//change the diff variable which is used to save the difficulty to stats.txt.
	private static void changeDiff(Difficulty d){
		if(current.getCols()==Difficulty.Easy.getCols()){
			diff="EASY";
		}else if(current.getCols()==Difficulty.Medium.getCols()){
			diff="MEDIUM";
		}else if(current.getCols()==Difficulty.Hard.getCols()){
			diff="HARD";
		}
	}
	//Method used to change the difficulty and update the win and loss variables that are then written to stats.txt.
	private static void changePane(){
		changeDiff(current);
		if(f.getChanged()|| !diff.equalsIgnoreCase(loaded)){
			wins=f.getWins();
			loses=f.getLoses();
			f.setWL(wins, loses);
			createStats();
		}
		f.changeDifficulty(current);
		Container pane=f.getContentPane();
		pane.add(menu);	
		f.repaint();			
	}	
	//Method that is used to format the Custom difficulty mines and Custom Difficulty enum.
	private static void formatDifficulty(Difficulty d) throws IllegalFormatException{
		String mineNums=JOptionPane.showInputDialog(f,"Note:Board Must contain at least one mine or open space","Enter Number of Mines",JOptionPane.PLAIN_MESSAGE);
		if(mineNums !=null && !mineNums.isEmpty()){		
			Difficulty diff=Difficulty.Custom;
			try{
				diff.setCols(d.getCols());
				diff.setRows(d.getRows());
				diff.setWindowWidth(d.getWindowWidth());
				diff.setWindowHeight(d.getWindowHeight());
				if(Integer.valueOf(mineNums)>=1 && Integer.valueOf(mineNums)<(d.getRows()*d.getCols())){
					diff.setMines(Integer.valueOf(mineNums));
					current=diff;
					changePane();
				}else{
					throw new NumberFormatException();
				}
			}catch(NumberFormatException exc){
				JOptionPane.showMessageDialog(f,"Illegal Number of Mines input. Your input was: "+mineNums,"Input Error",JOptionPane.ERROR_MESSAGE);
			}		
		}
	}
	//Method that is used to instantiate the Menu and the Listeners associated with it.
	private static void createMenu(){
		menu=new JMenuBar();
		difficulty=new JMenu("Difficulty");
		reset=new JMenu("Reset");
		resetStats=new JMenuItem("Reset Stats");
		resetBoard=new JMenuItem("Reset Board");
		reset.add(resetBoard);
		reset.add(resetStats);
		resetStats.addActionListener(new ResetStatsAction());
		resetBoard.addActionListener(new ResetBoardAction());
		easy=new JMenuItem("Easy");
		easy.addActionListener(new easyList());
		medium=new JMenuItem("Medium");
		medium.addActionListener(new medList());	
		hard=new JMenuItem("Hard");
		hard.addActionListener(new hardList());			
		easyC=new JMenuItem("Easy(10x10)");
		easyC.addActionListener(new EasyCustom());
		mediumC=new JMenuItem("Medium(13x13)");
		mediumC.addActionListener(new MediumCustom());
		hardC=new JMenuItem("Hard(25x25)");
		hardC.addActionListener(new HardCustom());
		customSubmenu=new JMenu("Custom");
		customSubmenu.add(easyC);
		customSubmenu.add(mediumC);
		customSubmenu.add(hardC);
		difficulty.add(easy);
		difficulty.add(medium);
		difficulty.add(hard);
		difficulty.addSeparator();
		difficulty.add(customSubmenu);
		difficulty.setBounds(0,0,60,30);
		reset.setBounds(60,0,50,30);
		menu.add(difficulty);
		menu.add(reset);
		menu.setLayout(null);
		menu.setBounds(0,0,current.getWindowWidth(),30);
		menu.setBackground(new Color(200,200,200));			
	}
	//Action performed when the easy difficulty is selected from the menu, switches game to easy.
	private static class easyList implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			diff="EASY";
			current=Difficulty.Easy;
			changePane();		
		}	
	}
	//Action performed when the medium difficulty is selected from the menu, switches game to medium.
	private static class medList implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			diff="MEDIUM";
			current=Difficulty.Medium;
			changePane();
		}	
	}
	//Action performed when the hard difficulty is selected from the menu, switches game to hard.
	private static class hardList implements ActionListener{
		public void actionPerformed(ActionEvent e){
			diff="HARD";
			current=Difficulty.Hard;
			changePane();
		}	
	}	
	//Action performed when the custom easy difficulty is selected from the menu, switches game to a custom easy game.
	private static class EasyCustom implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			diff="EASY";
			formatDifficulty(Difficulty.Easy);			
		}
	}
	//Action performed when the custom medium difficulty is selected from the menu, switches game to a custom medium game.
	private static class MediumCustom implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			diff="MEDIUM";
			formatDifficulty(Difficulty.Medium);				
		}
	}	
	//Action performed when the custom hard difficulty is selected from the menu, switches game to a custom hard game.
	private static class HardCustom implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			diff="HARD";
			formatDifficulty(Difficulty.Hard);			
		}
	}
	//Action performed when the board needs to reset to a game of the same difficulty.
	private static class ResetBoardAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			changePane();	
		}
	}
	//Action performed when the reset stats Menu entry is pressed, creates a new default stats.txt in addition to reseting the board stats.
	private static class ResetStatsAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				wins=0;
				loses=0;
				createStats();
				f.setWL(wins,loses);
				f.setWLPanels(wins,loses);
			}catch(Exception ex){}
		}
	}		
}
