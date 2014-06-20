import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class Player {
	private String name;		//Players Name
	private int wins, loss;		//Wins and losses
	
	//---Save File---
	private int[] saveInfoPlayer = {wins, loss};
	
	public Player(){
		name = "The Bey";
		
	}
	
	/*=========================*\
			Return values
	\*=========================*/	
	
	public String returnName(){
		return name;
	}
	
	public int returnWins(){
		return wins;
	}
	
	public int returnLoss(){
		return loss;
	}

	public void addLoss(){
		wins = wins + 1;
	}
	
	public void addWin(){
		loss = loss + 1;
	}
	
	/*=========================*\
			Save and load
	\*=========================*/	
	
	public void savePlayer(){
		File outputFile;
		BufferedWriter outputWriter;
		
	    	try{
	    		outputFile = new File("Save/Player.txt");
	    		outputWriter = new BufferedWriter(new FileWriter(outputFile));
	    		
	    	    //Now we update the variables
	    	    saveInfoPlayer[0] = wins; 
	    	    saveInfoPlayer[1] = loss; 
	    		
	    		for(int i = 0; i < saveInfoPlayer.length; i++){
	    			outputWriter.write(Integer.toString(saveInfoPlayer[i]));
	    			outputWriter.newLine();
	    	}    	
	    	outputWriter.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	   }
	}

	public void loadPlayer(){
		File inputFile;
		BufferedReader inputReader;
		
		try{
			inputFile = new File("Save/Player.txt");
			inputReader = new BufferedReader(new FileReader(inputFile));
			
			for(int i = 0; i < saveInfoPlayer.length; i++){
				saveInfoPlayer[i] = Integer.parseInt(inputReader.readLine());
			}
			
			inputReader.close(); 
		} catch(Exception e){
			e.printStackTrace();
		}
		
	//Now we update the variables
	wins = saveInfoPlayer[0];
	loss = saveInfoPlayer[1];

	}

}
