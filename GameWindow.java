import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//The JFrame that is the window for the minesweeper program.
public class GameWindow extends JFrame{
	private gamePanel panel;
	private boolean changed;
	private int win,lose;
	private Difficulty current;
	private JLabel flags,winLabel,loseLabel;
	private double time;
	//Constructor for the GameWindow JFrame.
	public GameWindow(Difficulty d){
		super("Minesweeper");		
		current=d;
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		super.setResizable(false);
		instantiate();
		this.addMouseListener(new MouseInput());
		panel.repaint();
	}
	//Method that instantiates all the Objects needed for the game to run properly(setup method).
	public void instantiate(){
		this.setSize(current.getWindowWidth(),current.getWindowHeight());
		flags=new JLabel("Remaining Flags: ");
		winLabel=new JLabel("Wins:"+win);
		loseLabel=new JLabel("Loses:"+lose);
		panel=new gamePanel(current,current.getRows(),current.getCols(),current.getMines(),current.getWindowWidth(),current.getWindowHeight());
		panel.setBounds(0,30,current.getWindowWidth(),current.getWindowHeight());
		flags.setBounds(120,0,130,30);
		flags.setText(flags.getText()+panel.getFlagNums());
		winLabel.setBounds(250,0,100,30);
		loseLabel.setBounds(350,0,100,30);
		Container pane=getContentPane();
		pane.removeAll();
		pane.add(panel);
		pane.add(flags);
		pane.add(winLabel);
		pane.add(loseLabel);
		this.addMouseListener(new MouseInput());
		panel.repaint();
	}
	//Used to set the board through the JPanel(gamePanel) to the correct win and loss values.
	public void setWL(int w,int l){
		panel.setWL(w,l);
	}
	//Sets the win and loss values to their proper values determined by the runner.
	public void setWLPanels(int wins,int loses){
		win=wins;
		lose=loses;
		winLabel.setText("Wins:"+wins);
		loseLabel.setText("Loses:"+loses);
	}
	//Changes the difficulty and the board to reflect the change of state.
	public void changeDifficulty(Difficulty d){
		panel=null;
		flags=null;
		loseLabel=null;
		winLabel=null;		
		current=d;
		instantiate();
		panel.setWL(win,lose);
		
	}
	//Returns the changed boolean which is used to determine if the win/loss information needs to be updated from the board to stats.txt.
	public boolean getChanged(){
		return changed;
	}
	//Returns the number of wins that the JFrame(GameWindow) has for comparison to the stats.txt file.
	public int getWins(){
		changed=false;
		return win;
	}
	//Returns the number of losses that the JFrame(GameWindow) has for comparison to the stats.txt file.
	public int getLoses(){
		changed=false;
		return lose;
	}
	//MouseAdapter used to feed user input into the JFrame(GameWindow) via the Mouse.
	private class MouseInput extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			if(e.getButton()==MouseEvent.BUTTON1&& e.getX()-3>0 && e.getY()-56>0){	
				int loc=panel.findClickedSpot(e.getX()-3, e.getY()-56);
				panel.checkGuess(loc);
				panel.repaint();
			}
			if(e.getButton()==MouseEvent.BUTTON3 && e.getX()-3>0 && e.getY()-56>0 && time+200<System.currentTimeMillis()){
				panel.placeFlag(e.getX()-3, e.getY()-56);
				flags.setText(flags.getText().substring(0,flags.getText().indexOf(':')+1)+panel.getFlagNums());
				flags.repaint();
				panel.repaint();
				time=System.currentTimeMillis();
			}
			if(panel.getLoses()>lose||panel.getWins()>win){
				changed=true;
				lose=panel.getLoses();
				win=panel.getWins();
				setWLPanels(win,lose);
			}
		}

	}
}