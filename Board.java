import java.awt.*;
import java.util.Random;
//A board object the holds all the information necessary to setup the board and change it based on user input.
public class Board{
	private Square[] board;
	private int width,height,mineNums,placedFlags,winHeight,winWidth,clicks,wins,loses;
	private int[] mines,boardMines,flags;
	private letterSize size;
	private boolean hasLost,hasWon,finish;
	//Constructor for the board object.
	public Board(int wid,int heigh,int ms,int windowWidth,int windowHeight,letterSize sz){
		clicks=0;
		size=sz;
		winHeight=windowHeight;
		winWidth=windowWidth;
		height=heigh;
		width=wid;
		placedFlags=0;
		mineNums=ms;
		board=new Square[height*width];
		boardMines=new int[height*width];
		mines=new int[mineNums];
		flags=new int[mineNums];		
		populate(boardMines);
		populate(mines);
		populate(flags);
		Square.setDimensions(width,height);
		createSquares(board,height*width,width,height,winWidth,winHeight);	
		genMines(mines,width*height);		
		setMines(board,mines);
		setNumOfAdjMines();	
	}
	//Sets the wins and loses.
	public void setWL(int w,int l){
		wins=w;
		loses=l;
	}
	//Returns the number of wins.
	public int getWins(){
		return wins;
	}
	//Returns the number of loses.
	public int getLoses(){
		return loses;
	}
	//Returns the number of flags left that can be placed.
	public int getFlagNums(){
		if(mineNums-placedFlags>0){
			return (mineNums-placedFlags);
		}else{
			return 0;
		}
	}	
	//Populate array with default values of -1.
	private void populate(int[] array){
		for(int i=0;i<array.length;i++){
			array[i]=-1;
		}
	}
	//Places a flag on an Square without one.
	public void placeFlag(int loc){
		if(!hasLost && !hasWon){
			if(flags[flags.length-1]<0 && !contains(flags,loc) && !board[loc].getWasSelected()&& placedFlags<mineNums){
				flags[placedFlags]=loc;
				placedFlags++;
			}else if(contains(flags,loc)){
				removeFlag(loc);
			}
		}
		if(!hasWon){
			checkWin();
		}
	}
	//Removes a flag from a square with one.
	private void removeFlag(int loc){
		if(!(flags[0]==-1)){
			for(int i=0;i<placedFlags;i++){
				if(flags[i]==loc){
					removeItem(flags,i,placedFlags);
					break;
				}
			}
			placedFlags--;
		}
	}	
	//Remove an item from an array.
	private void removeItem(int[] array, int index,int size){
		for(int i=index;i<size-1;i++){
			array[i]=array[i+1];
		}
		array[size-1]=-1;
	}
	//Recursive method that 'reveals' squares.
	public void checkGuess(int location){
		if((!hasWon||finish) && !hasLost && !contains(flags,location)){
			if(board[location].getMined()){
				if(clicks==0){
					adjustFirst(location,mines);
					checkGuess(location);
				}else{
					hasLost=true;
					loses++;
					System.out.println("Loses="+loses);
				}
			}else if(!board[location].getWasSelected()){
				clicks++;
				int mineNums=seeMines(board,location);
				board[location].setWasSelected(true);
				boardMines[location]=mineNums;
				board[location].setNumOfAdjacent(mineNums);
				int[] adj=board[location].getAdjacents();	
				if(mineNums==0){
					for(int i=0;i<adj.length;i++){
						if(!board[adj[i]].getMined()){
							checkGuess(adj[i]);
						}
					}
				}
			}
		}
		if(!hasWon){
			checkWin();
		}
	}
	//Checks to see if the player has won the game.
	private void checkWin(){
		if(mineNums-placedFlags==0){
			for(int i=0;i<flags.length;i++){
				if(board[flags[i]].getMined()){
					if(i==placedFlags-1){
						wins++;
						hasWon=true;
					}
					continue;
				}
				else{
					break;
				}
			}
		}
		if(clicks==board.length-mineNums){
			hasWon=true;
			wins++;
		}
	}
	//Adjusts the board if the first spot clicked is a mine.
	public void adjustFirst(int loc,int[] mines){
		board[loc].setMined(false);
		int target=0;
		for(int i=0;i<mines.length;i++){
			if(loc==mines[i]){
				target=i;
				break;
			}
		}
		while(mines[target]==loc){
			int ran=new Random().nextInt(width*height);
			if(!contains(mines,ran)){
				mines[target]=ran;
			}
		}
	}
	//Sets number of adjacent in square for later.
	private void setNumOfAdjMines(){
		for(int i=0;i<board.length;i++){
			board[i].setNumOfAdjacent(seeMines(board,i));
		}
	}
	//Returns number of adjacent mines.
	private int seeMines(Square[] bo,int loc){
		int adj=0;
		for(int i=0;i<bo[loc].getAdjacents().length;i++){
			if(contains(mines,board[loc].getAdjacents()[i])){
				adj++;
			}
		}
		return adj;
	}
	//Creates and instantiates the squares.
	private void createSquares(Square[] board,int size,int widt,int heig,int totalWidth,int totalHeight){
		int xpos=0;
		int ypos=0;
		int index=0;
		int wid=(int)(((double)totalWidth)/((double)widt));
		int hei=(int)(((double)totalHeight)/((double)heig));
		for(int i=0;i<widt;i++){
			for(int in=0;in<heig;in++){
				board[index]=new Square(index,xpos,ypos,wid,hei);
				ypos+=(int)(((double)totalHeight)/((double)heig));
				index++;
			}
			xpos+=(int)(((double)totalWidth)/((double)widt));
			ypos=0;
		}
	}
	//Finds the clicked square based on x and y coordinates.
	public int findClickedSquare(int x,int y){
		for(int i=0;i<board.length;i++){
			if(x>board[i].getX() && y>board[i].getY() && x<board[i].getX()+board[i].getSquareWidth() && y<board[i].getY()+board[i].getSquareHeight()){
				return board[i].getLocation();
			}
		}
		return -1;
	}
	//Set the squares containing mines to a mined status.
	private void setMines(Square[] board,int[] mines){
		for(int i=0;i<mines.length;i++){
			board[mines[i]].setMined(true);
		}
	}
	//Set the color for the numbers displayed the number of adjacent mines.
	private Color setColor(int adja){
		if(adja==0){
			return Color.black;
		}else if(adja==1){
			return Color.blue;
		}else if(adja==2){
			return Color.green;
		}else if(adja==3){
			return Color.red;
		}else if(adja==4){
			return new Color(50,50,150);
		}else if(adja==5){
			return new Color(150,50,50);
		}else if(adja==6){
			return new Color(200,40,200);
		}else if(adja==7){
			return Color.cyan;
		}else{
			return new Color(150,40,80);
		}
	}
	//Fills out the rest of the board that has not been clicked on.
	private void fillOutRest(){
		finish=true;
		for(int i=0;i<board.length;i++){
			if(!board[i].getMined() && !board[i].getWasSelected()){
				checkGuess(i);
			}
		}
		finish=false;
	}
	//Draw method for the board and each component of the game.
	public void draw(Graphics2D g){
		if(hasWon & !finish){
			fillOutRest();
		}	
		for(int i=0;i<board.length;i++){
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.gray);
			g.fillRect(board[i].getX(),board[i].getY(),board[i].getSquareWidth(),board[i].getSquareHeight());
			g.setColor(Color.black);
			g.drawRect(board[i].getX(),board[i].getY(),board[i].getSquareWidth(),board[i].getSquareHeight());
		}
		for(int in=0;in<boardMines.length;in++){
			if(boardMines[in]>=0){
				g.setFont(new Font("Custom",Font.PLAIN,size.getFontSize()));
				g.setColor(setColor(boardMines[in]));
				g.drawString(String.valueOf(boardMines[in]), board[in].getX()+size.getXLetter(), board[in].getY()+size.getYLetter());
			}
		}
		if(flags[0]>=0){
			for(int ind=0;ind<flags.length;ind++){
				if(flags[ind]>=0){
					g.setFont(new Font("Flag",Font.PLAIN,size.getFontSize()-(size.getFontSize()/4)));
					g.setColor(new Color(220,180,20));
					g.drawString("\u00b6", board[flags[ind]].getX()+size.getXFlag(), board[flags[ind]].getY()+size.getYFlag());
				}
			}	
		}
		if(hasLost){
			for(int i=0;i<mines.length;i++){
				g.setFont(new Font("Custom",Font.PLAIN,size.getFontSize()));
				g.setColor(Color.BLACK);
				g.drawString("\u06de", board[mines[i]].getX()+size.getMinex(), board[mines[i]].getY()+size.getMiney());
			}
		}
	}
	//Generate mine positions based on random ints.
	private void genMines(int[] m,int bs){
		for(int i=0;i<m.length;i++){
			int ran=new Random().nextInt(bs);
			if(!contains(m,ran)){
				m[i]=ran;
				continue;
			}
			i--;
		}
	}
	//Returns true if 'in' is in the array 'm',else false.
	private boolean contains(int[] m,int in){
		for(int i=0;i<m.length;i++){
			if(in==m[i])
				return true;
			else
				continue;
		}
		return false;
	}
}