//A standard Square Object that represents one 'tile' on a minesweeper board.
public class Square {
	private boolean isMined;
	private int loc,x,y,sqrwidth,sqrheight;
	private static int WIDTH,HEIGHT;
	private int[] adj;  
	private int numOfAdjacentMines;
	private boolean wasSelected;
	//Constructor for a Square object.
	public Square(int location,int xpos,int ypos,int squareWidth,int squareHeight){
		x=xpos;
		y=ypos;
		sqrwidth=squareWidth;
		sqrheight=squareHeight;
		loc=location;
		setAdjacentSquares(loc);
	}
	//Sets dimensions of board
	public static void setDimensions(int wid,int heig){
		WIDTH=wid;
		HEIGHT=heig;
	}
	//Puts the adjacent square locations in the adj int array.
	private void setAdjacentSquares(int location){
		if(location==0||location==(HEIGHT-1)||location==((HEIGHT*WIDTH)-HEIGHT)||location==((HEIGHT*WIDTH)-1)){
			adj=new int[3];
			if(location==0){
				adj[0]=location+1;
				adj[1]=location+HEIGHT;
				adj[2]=location+HEIGHT+1;			
			}else if(location==(HEIGHT-1)){
				adj[0]=location-1;
				adj[1]=location+HEIGHT-1;
				adj[2]=location+HEIGHT;				
			}else if(location==((HEIGHT*WIDTH)-HEIGHT)){
				adj[0]=location-HEIGHT;
				adj[1]=location-HEIGHT+1;
				adj[2]=location+1;				
			}else{
				adj[0]=location-HEIGHT-1;
				adj[1]=location-HEIGHT;
				adj[2]=location-1;				
			}			
		}else if(location>0 && location<HEIGHT){
			adj=new int[5];
			adj[0]=location-1;
			adj[1]=location+1;			
			adj[2]=location+HEIGHT-1;
			adj[3]=location+HEIGHT;
			adj[4]=location+HEIGHT+1;	
		}else if(location>((HEIGHT*WIDTH)-HEIGHT) && location<(HEIGHT*HEIGHT)){
			adj=new int[5];
			adj[0]=location-HEIGHT-1;			
			adj[1]=location-HEIGHT;
			adj[2]=location-HEIGHT+1;
			adj[3]=location-1;
			adj[4]=location+1;
		}else if(location%HEIGHT==0){
			adj=new int[5];			
			adj[0]=location-HEIGHT;
			adj[1]=location-HEIGHT+1;
			adj[2]=location+1;
			adj[3]=location+HEIGHT;
			adj[4]=location+HEIGHT+1;			
		}else if(location%HEIGHT==HEIGHT-1){
			adj=new int[5];
			adj[0]=location-HEIGHT-1;
			adj[1]=location-HEIGHT;
			adj[2]=location-1;
			adj[3]=location+HEIGHT-1;
			adj[4]=location+HEIGHT;			
		}else{
			adj=new int[8];
			adj[0]=location-HEIGHT-1;
			adj[1]=location-HEIGHT;
			adj[2]=location-HEIGHT+1;
			adj[3]=location-1;
			adj[4]=location+1;
			adj[5]=location+HEIGHT-1;
			adj[6]=location+HEIGHT;
			adj[7]=location+HEIGHT+1;
		}		
	}
	//Returns number of adjacent squares with mines.
	public int getNumOfAdjacents(){
		return numOfAdjacentMines;
	}
	//Returns true if the square has been 'activated',false otherwise.
	public boolean getWasSelected(){
		return wasSelected;
	}
	//Sets the Square to a selected or unselected state.
	public void setWasSelected(boolean b){
		wasSelected=b;
	}
	//Sets the number of adjacent mines based on adjacent tiles.
	public void setNumOfAdjacent(int in){
		numOfAdjacentMines=in;
	}
	//Returns true if the Square contains a mine, false otherwise.
	public boolean getMined(){
		return isMined;
	}
	//Sets the mined variable to the value of b.
	public void setMined(boolean b){
		isMined=b;
	}
	//Returns the x-coordinate of the top left corner of the Square.
	public int getX(){
		return x;
	}
	//Returns the y-coordinate of the top left corner of the Square.
	public int getY(){
		return y;
	}
	//Returns the width of the Square.
	public int getSquareWidth(){
		return sqrwidth;
	}
	//Returns the height of the Square.
	public int getSquareHeight(){
		return sqrheight;
	}	
	//Returns the location 'loc' that contains the location of the square on the board.
	public int getLocation(){
		return loc;
	}
	//Sets the location to the value of 'lo'.
	public void setLoaction(int lo){
		loc=lo;
	}
	//Returns the int array that contains the adjacent Squares to this Square.
	public int[] getAdjacents(){
		return adj;
	} 
}