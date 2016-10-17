import java.awt.*;
import javax.swing.*;
//A JPanel used to display the board on using Java Graphics.
public class gamePanel extends JPanel{
	private Board board;
	private int adjust;
	//Standard constructor for the gamepanel.
	public gamePanel(Difficulty diff,int rows,int cols,int mines,int wid,int hei){	
		adjust=setAdj(diff);
		board=new Board(rows,cols,mines,wid,hei-adjust,determineSize(diff));
		this.setBackground(Color.red);			
	}
	//Passes the x and y coordinates from the clicked spot on the JFrame(GameWindow) to the board and returns the location(Square number).
	public int findClickedSpot(int x,int y){
		return board.findClickedSquare(x, y);
	}
	//Checks the location returned by findClickedSpot() and edits the board based on the of location.
	public void checkGuess(int location){
		board.checkGuess(location);
	}
	//The paint component that is used for the Graphics that draw the board.
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		board.draw((Graphics2D)g);
	}
	//Passes the location location of the flag from a gamPanel object down to the board so it can place a flag.
	public void placeFlag(int x,int y){
		board.placeFlag(board.findClickedSquare(x, y));
	}
	//Set the adjustment of the height of the gamePanel based on the difficulty(for uniformity).
	public int getFlagNums(){
		return board.getFlagNums();
	}	
	//Passes the number of wins and loses from the JFrame(GameWindow) to the board for setup.
	public void setWL(int w,int l){
		board.setWL(w,l);
	}
	//Returns the number of wins from the board in order to update the stats.txt file.
	public int getWins(){
		return board.getWins();
	}
	//Returns the number of loses from the board in order to update the stats.txt file.
	public int getLoses(){
		return board.getLoses();
	}
	//Returns the proper letterSize adjustment based upon the difficulty.
	public letterSize determineSize(Difficulty dif){
		if(dif.getCols()==Difficulty.Easy.getCols()){
			return letterSize.Easy;
		}else if(dif.getCols()==Difficulty.Medium.getCols()){
			return letterSize.Medium;
		}else{
			return letterSize.Hard;
		}			
	}
	//Passes the number of remaining number of flags that can be placed from the board to the JFrame(GameWindow).
	public int setAdj(Difficulty dif){
		if(dif.getCols()==Difficulty.Easy.getCols()){
			return 56;
		}else if(dif.getCols()==Difficulty.Medium.getCols()){
			return 52;
		}else{
			return 48;
		}	
	}
}
