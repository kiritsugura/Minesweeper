//An enum used for the adjustment of the text displayed in a minesweeper game.
public enum letterSize {
	Easy(50,16,45,17,39,3,46),Medium(32,12,30,13,25,3,30),Hard(20,9,23,9,18,4,22),Custom();
	private int fontSize,xletter,yletter,xflag,yflag,minex,miney;
	//Used for the instantiation of a custom game.
	letterSize(){};
	//Used for the instantiation of a standard game.
	letterSize(int fs,int xl,int yl,int xf,int yf,int mx,int my){
		fontSize=fs;
		xletter=xl;
		yletter=yl;
		xflag=xf;
		yflag=yf;
		minex=mx;
		miney=my;
	}
	//Returns the font size used for the difficulty.
	public int getFontSize(){
		return fontSize;
	}
	//Sets the font size used for the difficulty.
	public void setFontSize(int fs){
		fontSize=fs;
	}		
	//Returns the x-coordinate number adjustment.
	public int getXLetter(){
		return xletter;
	}
	//Sets the x-coordinate number adjustment to 'xl'.
	public void setXLetter(int xl){
		xletter=xl;
	}
	//Returns the y-coordinate number adjustment.
	public int getYLetter(){
		return yletter;
	}
	//Sets the y-coordinate number adjustment to 'yl'.
	public void setYLetter(int yl){
		yletter=yl;
	}		
	//Returns the x-coordinate flag adjustment.
	public int getXFlag(){
		return xflag;
	}
	//Sets the x-coordinate flag adjustment to 'xf'.
	public void setXFlag(int xf){
		xflag=xf;
	}
	//Returns the y-coordinate flag adjustment.
	public int getYFlag(){
		return yflag;
	}
	//Sets the y-coordinate flag adjustment to 'yf'.
	public void setYFlag(int yf){
		yflag=yf;
	}
	//Returns the x-coordinate mine adjustment.
	public int getMinex(){
		return minex;
	}
	//Sets the x-coordinate mine adjustment to 'mx'.
	public void setMinex(int mx){
		minex=mx;
	}	
	//Returns the y-coordinate mine adjustment.
	public int getMiney(){
		return miney;
	}
	//Sets the y-coordinate mine adjustment to 'my'.
	public void setMiney(int my){
		miney=my;
	}		
}
