//An enum used to establish the difficulty of the current game.
public enum Difficulty {
	Easy(10,10,10,600,600),Medium(16,16,30,647,652),Hard(25,25,90,707,710),Custom();
	private int rows,columns,mines,windowWidth,windowHeight;
	//Constructor for a custom difficulty.
	Difficulty(){}
	//Constructor for standard difficulties.
	Difficulty(int row,int column,int mine,int screenWidth,int screenHeight){
		rows=row;
		columns=column;
		mines=mine;
		windowWidth=screenWidth;
		windowHeight=screenHeight;
	}
	//Returns the number of rows for the current difficulty.
	public int getRows(){
		return rows;
	}
	//Sets the number of rows for the current difficulty to 'r'.
	public void setRows(int r){
		rows=r;
	}
	//Returns the number of columns for the current difficulty.
	public int getCols(){
		return columns;
	}
	//Sets the number of columns for the current difficulty to 'col'.
	public void setCols(int col){
		columns=col;
	}
	//Returns the number of mines for the current difficulty.
	public int getMines(){
		return mines;
	}
	//Sets the number of mines for the current difficulty to 'mine'.
	public void setMines(int mine){
		mines=mine;
	}
	//Returns the width of the window for the current difficulty.
	public int getWindowWidth(){
		return windowWidth;
	}
	//Sets the width of the window for the current difficulty to 'ww'.
	public void setWindowWidth(int ww){
		windowWidth=ww;
	}
	//Returns the height of the window for the current difficulty.
	public int getWindowHeight(){
		return windowHeight;
	}
	//Sets the height of the window for the current difficulty to 'wh'.
	public void setWindowHeight(int wh){
		windowHeight=wh;
	}
}
