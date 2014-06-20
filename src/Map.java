import java.awt.Graphics2D;


public class Map {
	
	private int reference;
	private int layerOne[][] = new int[99][99];
	
	//---Tiles---
	private GameImage GI_Grass;

	
	public Map(int ref){
		reference = ref;
		
		loadMap();
		GI_Grass = new GameImage("Map/Tiles/Grass.png");
		}


	public void loadMap(){	
		/*	
		  	Loading the map we input each value into the Map object
		  	with each row represent the X coordinates and the columns
		 	being the Y coordinates.
		 */
			
	if(reference == 0){
		int[][] layerOne = {
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //1
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //2
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //3
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //4
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //5
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //6
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //7
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //8
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //9
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //10
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //11
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //12
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //13
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //14
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //15
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //16
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //17
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //18
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //19
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //20
			};
		}
	}

	public void renderMap(Graphics2D canvas){
		/*	
		 	To render the map we have to check each value in
		  	the array, this is done through a nested for statement.
			To get the size right we use x*32 and y*32 as each tile
			in this version will be 32 pixel squares.
		*/
		
		for(int x = 0; x < layerOne.length; x++){
			for(int y = 0; y < layerOne[x].length; y++){
				if(layerOne[x][y] == 1){
					GI_Grass.draw(canvas, 0 + (x*32), 0 + (y*32));
				}
			  }
			}
	}
}