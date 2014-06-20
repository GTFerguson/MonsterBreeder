import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/*This class deals with the generation of the monsters appearance and stats based on chromosomes in DNA.
  The first two digits of each variable is used to generate the monster's sprite. The last 2 digits are 
  used in the breeding class. */

public class Genetics { 
	
	//Storage for images	
	private GameImage GI_A;
	private GameImage GI_AW;
	private GameImage GI_AB;
	private GameImage GI_ABW;
	private GameImage GI_AR;
	private GameImage GI_ARW;
	private GameImage GI_ABRC; 
	private GameImage GI_ABRCW;	

	private int genetics[] = new int[26];
	private String name;

	//Wild Monster
	public Genetics(int reference){
		name = "Artie";
		
		//Reference number for finding information in save
		genetics[0] = reference;
		
		genetics[1] =0;						//Strength
		genetics[2] =0;						//Dexterity
		genetics[3] =0;						//Constitution
		genetics[4] =0;						//Wisdom
		genetics[5] =0;						//Charisma
		
		//Load Images
		GI_A = new GameImage("Alphan/A.png");
		GI_AW = new GameImage("Alphan/AW.png");
		GI_AB = new GameImage("Alphan/AB.png");
		GI_ABW = new GameImage("Alphan/ABW.png");

		GI_ARW = new GameImage("Alphan/ARW.png");
		GI_ABRC = new GameImage("Alphan/ABRC.png");
		GI_ABRCW = new GameImage("Alphan/ABRCW.png");
		
}

	public void presetMon(int id){
	//Red alphan starter
	if(id == 0){
		name = " ";
	
		//Stat Values
		
		//A lot of these values won't be used in this version of the game
		//Image Chromosome Variables
		
		genetics[6] =1111;					//Body
		genetics[7] =1211;					//Skin Colour	
		genetics[8] =1211;					//Wings
		genetics[9]	=11;					//Gender
		genetics[10] =1111;					//Hair Colour
		genetics[11] =1111;					//Eye Colour
		genetics[12] =1111;					//Arms
		genetics[13] =1111;					//Horns
		genetics[15] =1111;					//Hands
		genetics[16] =1111;					//Ability 0
		genetics[17] =1111;					//Ability 1
		genetics[18] =1111;					//Ability 2
		genetics[19] =1111;					//Ability 3

		//Stat Tier Chromosome Variables
		
		genetics[20] =3;					//Strength
		genetics[21] =3;					//Dexterity
		genetics[22] =3;					//Constitution
		genetics[23] =3;					//Wisdom
		genetics[24] =3;					//Charisma
		genetics[25] =11;					//Element
		
		GI_AR = new GameImage("Alphan/AR.png");
		
		if(genetics[1] == 0){
			statGen();
		}
	}
}

	public void monImage(Graphics2D canvas,int monX, int monY, boolean flip){
	
	//Each value is checked individually one by one with nested if's to choose which image is to be displayed
	//In later versions these will be done separately to create multiple images that layer on top of each other.

	//Alphan (currently the only monster available)
	if(String.valueOf(genetics[6]).substring(0,2).equalsIgnoreCase("11")){
	
		//White Alphan
		if(String.valueOf(genetics[7]).substring(0,2).equalsIgnoreCase("11")){
			
			//Wings
			chromWings(GI_A, GI_AW, canvas, monX, monY, flip);
		}
		
		//Red Alphan
		if(String.valueOf(genetics[7]).substring(0,2).equalsIgnoreCase("12")){
			
			//Wings
			chromWings(GI_AR, GI_ARW, canvas, monX, monY, flip);
		}
		
		//Blue Alphan
		if(String.valueOf(genetics[7]).substring(0,2).equalsIgnoreCase("13")){

			//Wings
			chromWings(GI_AB, GI_ABW, canvas, monX, monY,flip);
		}
		
		//BRC Alphan
		if(String.valueOf(genetics[7]).substring(0,2).equalsIgnoreCase("14")){

			//Wings
			chromWings(GI_ABRC, GI_ABRCW, canvas, monX, monY, flip);
	}
}
}

	//Wings
	public void chromWings(GameImage wingless, GameImage winged, Graphics2D canvas, int monX, int monY, boolean flip){
	//If flip is not true then we use drawRotatedScaled
	if(flip == false){
		if(String.valueOf(genetics[8]).substring(0,2).equalsIgnoreCase("11")){
			wingless.drawRotatedScaled(canvas, monX, monY, 0, 23);
		}else if(String.valueOf(genetics[8]).substring(0,2).equalsIgnoreCase("12")){
			winged.drawRotatedScaled(canvas, monX, monY, 0, 3);
		}
	//If flip is true then drawRSflipHor is used
	}else{
		if(String.valueOf(genetics[8]).substring(0,2).equalsIgnoreCase("11")){
			wingless.drawRSflipHor(canvas, monX, monY, 0, 23);
		}else if(String.valueOf(genetics[8]).substring(0,2).equalsIgnoreCase("12")){
			winged.drawRSflipHor(canvas, monX, monY, 0, 3);
		}}
}

	public String getGender(){
	if(String.valueOf(genetics[9]).substring(0,2).equalsIgnoreCase("11")){
		return "Male";
	}else{
		return "Female";
	}
}
		
	public void statGen(){
			/*	 Stat Tier Generation
				Tier 1 = 40-60
				Tier 2 = 60-90
				Tier 3 = 90-110
				Tier 4 = 110-130
				Tier 5 = 130-150
			*/
			
			//Strength
			if(String.valueOf(genetics[20]).substring(0,1).equalsIgnoreCase("3")){
				genetics[1] = 90 + (int)(Math.random()*20);
				}else{
					System.out.println("Unrecognised Chromosome value: Strength");
				}
					
			//Dexterity
			if(String.valueOf(genetics[21]).substring(0,1).equalsIgnoreCase("3")){
				genetics[2] = 90 + (int)(Math.random()*20);
				}else{
					System.out.println("Unrecognised Chromosome value: Dexterity");
				}
				
			//Constitution
			if(String.valueOf(genetics[22]).substring(0,1).equalsIgnoreCase("3")){
				genetics[3] = 90 + (int)(Math.random()*20);
				}else{
					System.out.println("Unrecognised Chromosome value: Constitution");
				}
				
			//Wisdom
			if(String.valueOf(genetics[23]).substring(0,1).equalsIgnoreCase("3")){
				genetics[4] = 90 + (int)(Math.random()*20);
				}else{
					System.out.println("Unrecognised Chromosome value: Wisdom");
				}
				
			//Charisma
			if(String.valueOf(genetics[24]).substring(0,1).equalsIgnoreCase("3")){
				genetics[5] = 90 + (int)(Math.random()*20);
				}else{
					System.out.println("Unrecognised Chromosome value: Charisma");
				}
		}

		/*
		 		Returns
		 */

	public String returnName(){
		return name;
	}

	public int returnStrength(){
		return genetics[1];				
	}
		
	public int returnDexterity(){
		return genetics[2];				
	}
		
	public int returnConstitution(){
		return genetics[3];				
	}
		
	public int returnWisdom(){
		return genetics[4];				
	}
		
	public int returnCharisma(){
		return genetics[5];				
	}

	public String returnElement(){
		if(String.valueOf(genetics[25]).substring(0,1).equalsIgnoreCase("1")){
			return "Neutral";
		}else{
			return null;
		}
	}
		
		/*
		 		Monster Saving
		 */
	
	public void saveMon(String path){
	   	File outputFile;
	   	BufferedWriter outputWriter;
    	
	   	try{
    		outputFile = new File(path);
    		outputWriter = new BufferedWriter(new FileWriter(outputFile));
		    		
    	for(int x = 0; x < genetics.length; x++){
	   		outputWriter.write(Integer.toString(genetics[x]));
	   		outputWriter.newLine();
		}
    		outputWriter.close();
	}catch(Exception e){
		e.printStackTrace();
		}
	}
}
