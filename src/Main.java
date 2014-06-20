import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import common.SqlHelper;


public class Main extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mouse GM;
	private Combat combat;
	
	// Now the 'normal' variables we need throughout the game.
    private Timer timer; 
    
	//These variables deal with each screen
	private static boolean startScreen = true;
	private static boolean menuScreen = true;
	
	
	//--- Buttons---

	private static boolean newGame = false, continueButton = false, settingsButton = false;	//Menu buttons
	private static boolean fightButton = false, breedButton = false, endButton = false;		//Main game screen buttons
	private static boolean attackButton = false, runButton = false;							//Fight screen buttons
	private static boolean mchOne = false;													//Monster Card buttons
	
	//Used to disable button presses within the area of the Monster Card on the menu screen
	private static boolean menuScreenButtons = true;
	
	//Used in fights to set the HP at the start of a fight
	private static boolean hpSet=false;
		
	public static JFrame window;	//Game window
	public static JLabel label;
	public static Graphics gr;
	
	//---Player Variables---
	public Player player1 = new Player();
	
	//---Monster Variables---
	public Genetics wildMonster = new Genetics(0);	
	public Genetics playerMonster1 = new Genetics(1);
	public Genetics playerMonster2 = new Genetics(2);
	public Genetics playerMonster3 = new Genetics(3);
	public Genetics playerMonster4 = new Genetics(4);

	public Genetics playerParty[] = {playerMonster1, playerMonster2, playerMonster3, playerMonster4};
	
	//---Monster Card Variables---
	public MonsterCard MC1 = new MonsterCard();
	
	//---Storage for images---
	private GameImage GI_StartScreen;
	private GameImage GI_MenuScreen;
	private GameImage GI_GameScreen;
	private GameImage GI_FightScreen;
	private GameImage GI_FightAnim;
			
	//---Storage for sound---
	private GameSound GS_MainTheme;
	private GameSound GS_FightMusic;
	
	//Used to tell if sound is on or off
	private boolean sound = false;
	
	public Main(){
		combat = new Combat();
        //---Mouse---
        GM = new Mouse();
        addMouseListener( GM );         // for capturing clicks
        addMouseMotionListener( GM );   // for getting x and y positions, also for dragging

        //---Timer--- 
        //Every 25 ms the timer will call the 'actionPerformed()' method 
        // implemented just below. 'this' means that the timer works on this 'GamePlatform' Object.
        timer = new Timer(25, this);
        //Start the timer
        timer.start();
        
		//---Load Images---
		GI_StartScreen = new GameImage("StartScreen.png");
		GI_MenuScreen = new GameImage("MenuScreen.png");
		GI_GameScreen = new GameImage("GameScreen.png");
		GI_FightScreen = new GameImage("FightScreen.png");
		GI_FightAnim = new GameImage("Explosion.gif");
		//---Load Sounds---
        GS_MainTheme = new GameSound("ProfessorOak.mid");
        GS_FightMusic = new GameSound("WildBattle.mid");
	}

	private static void initUI(){
		//Creates window on screen
		SqlHelper db = new SqlHelper();
		db.connectDb("db//SaveData");
		db.createTable("db//SaveData", "Player", "(ID	INTEGER PRIMARY KEY		AUTOINCREMENT, WINS	INT	NOT NULL, LOSSES INT	NOT NULL");
		window = new JFrame();
		//Create instance of game and insert into window
		Main main = new Main();
		window.add(main);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(400, 300);
		window.setTitle("Monster Breeder");
		window.setSize(1280, 720);
		window.setResizable(false);
		
		window.setVisible(true);		
		
		Keyboard.initialise(window);
	}
	
	public static void main(String args[]){		
		initUI();
	}
	
    public void actionPerformed(ActionEvent e) {
        repaint();  
    }
	
    //The game is ran here, it is indirectly called by 'repaint()' from above.
    public void paint(Graphics g){
    	
        // Clear off-screen bitmap, we are starting with a clean window (i.e. empty)
        super.paintComponent(g);

        // Get a 'handle' to the window's graphics 'Canvas'
        Graphics2D canvas = (Graphics2D) g;

    	getInput();		//Keyboard input
    	musicPlayer();	//Music is from here

    	//Set text colour, font, and size.
    	canvas.setColor(Color.red);
    	canvas.setFont(new Font("Arial Black", Font.BOLD, 24));

    	wildMonster.presetMon(0);	//Loads wild monsters information into object
    	
    	//Start Screen
    	if(startScreen){
    		GI_StartScreen.draw( canvas, 0, 0);
    	}else if(menuScreen){
    		GI_MenuScreen.draw( canvas, 0, 0);	
    		
    		//Menu buttons
    		 if(buttonClick(180, 80, 125, 490)) newGame = true;
    		 if(buttonClick(410, 310, 125, 490)) continueButton = true;
    		 if(buttonClick(640, 540, 125, 490)) settingsButton = true;
    	}
		
    	if(newGame){
			newGameInstance(canvas);			
    	}
				
		if(continueButton){
			player1.loadPlayer();
			mainGameScreen(canvas);			
    	}
		
		if(settingsButton){
			settingsScreen(canvas);
    	}

		
		//Main Game
		if(menuScreenButtons == true){
			if(fightButton){
				fightScreen(canvas);
			}
			if(breedButton){
			// TODO This will take 2 player monsters and combine their chromosome values to make a new monster.
			}
			
			if(endButton){
				quitPressed();
			}
		}
					
				//Fight buttons
					//This wraps up all the calculations for combat into one click.
					//Player turn
					if(attackButton){
						attackPressed(canvas);

						}
					
					if(runButton){
						runPressed();
			}				
					//Reset mouse, not exactly the best way to handle it but it works
					GM.down = false;
}

    /*=========================*\
			Screens
	\*=========================*/

    public void mainGameScreen(Graphics2D canvas){
    	GI_GameScreen.draw(canvas, 0, 0);
    	menuScreen = false;
    	playerMonster1.monImage(canvas, 500, 300, false);
    	
    	monsterCardHandling(canvas, GM);
    	
        canvas.drawString("Wins: " +player1.returnWins()+"",10,60);
    	canvas.drawString("Losses: "+player1.returnLoss()+"",10,40);
    	
    	//Game buttons
    	if(buttonClick(660, 560, 50, 420)) fightButton = true; GM.down = false;
    	if(buttonClick(660, 560, 455, 825)) breedButton = true;
    	if(buttonClick(660, 560, 860, 1230)) endButton = true;
    	
    }

    public void fightScreen(Graphics2D canvas){
    	//Set previous screens off to clear for the new one.
    	newGame=false;
    	continueButton=false;
    	//Draw on the new screen.
    	GI_FightScreen.draw(canvas, 0, 0);

    	//Set the max and current HP for both monsters.
    	if(hpSet==false){
    		combat.setFight(playerMonster1, wildMonster);
    		hpSet=true;
    	}

    	//Both Monsters HP bars are then loaded.
    	hpBar(canvas);

    	//Monster Images
    	playerMonster1.monImage(canvas, 150, 350, false);
    	wildMonster.monImage(canvas, 950, 350, true);

    	//Result of attacks are displayed
    	//If is used so at start of fight and the strings are null, null will not be displayed
    	if(!(combat.getPlayerAttack() == null)){
    	canvas.drawString(""+combat.getPlayerAttack()+"",425,500);
    	canvas.drawString(""+combat.getEnemyAttack()+"",425, 470);	
    	}

    	winLossCheck();

    	//Fight Buttons
    	if(buttonClick(660, 560, 50, 420)) attackButton = true;
    	if(buttonClick(660, 560, 860, 1230)) runButton = true;
    }

    public void settingsScreen(Graphics2D canvas){
    	menuScreen = false;
    	canvas.drawString("Turn off sound", 100, 300);
    	if(buttonClick(350, 300, 100, 400)) sound = false;

    	canvas.drawString("Return to game", 100, 350);
    	if(buttonClick(400, 350, 100, 400)){ 
    		settingsButton = false; 
    		menuScreen = true;	
    	}
    }

    /*=========================*\
			Buttons
	\*=========================*/

    public boolean buttonClick(int bottomY, int topY, int leftX, int rightX){
    	if((GM.down == true)&&(GM.pointY <= bottomY)&&(GM.pointY >= topY)&&(GM.pointX >= leftX)&&(GM.pointX <= rightX)){
    		return true;	
    	}
    	return false;
    	}

    public void attackPressed(Graphics2D canvas){
    	combat.fightTurn(playerMonster1, wildMonster);

    	GI_FightAnim.draw(canvas, 200, 350);
    	GI_FightAnim.draw(canvas, 980, 350);
    	attackButton=false;	
    }

    public void quitPressed(){
    	//Save file
    	player1.savePlayer();
    	//saveParty();
    	//Quit					

    	System.exit(0);
    	}

    public void runPressed(){
    	//Add 1 loss to score
    	player1.addLoss();

    	endFight();

    	GM.down=false;
    }

    /*=========================*\
			New Game
	\*=========================*/

    public void newGameInstance(Graphics2D canvas){
    	playerMonster1.presetMon(0);
    	mainGameScreen(canvas);
    	} 


    /*=========================*\
			Save and load
	\*=========================*/

    public void saveParty(){
    	playerMonster1.saveMon("Save/Monster/PlayerParty1.txt");
    	playerMonster1.saveMon("Save/Monster/PlayerParty2.txt");
    	playerMonster1.saveMon("Save/Monster/PlayerParty3.txt");
    	playerMonster1.saveMon("Save/Monster/PlayerParty4.txt");
    	}

    /*=========================*\
			Fighting
	\*=========================*/	

    public void winLossCheck(){
    	if(combat.winLossCheck() == 1){
    		player1.addLoss();
    		endFight();
    	}else if(combat.winLossCheck() == 2){
    		player1.addWin();
    		endFight();
    	}
    }

    public void endFight(){
    	//Set hpSet back to false so HP will be generated again in the next fight
    	hpSet = false;
    	//Clear the screen for the new one.
    	fightButton = false;
    	//This needs to be changed to false to prevent looping
    	runButton = false;
    	//newGame is made true to bring you back to the main game screen.
    	newGame = true;
    	}

    public void hpBar(Graphics2D canvas){

    	canvas.setColor(Color.BLACK);
    	//Draw string in to show that this is the HP bar
    	canvas.drawString("Players HP", 20, 50);

    	//This creates the black border for the HP bar
    	canvas.drawRect(10, 60, (int)combat.getMaxHP(), 20);

    	//Now we set the color of the HP bar
    	canvas.setColor( new Color(255, 0, 0) );
    	// Draw a rectangle on the right hand side, making the inside of the bar.
    	canvas.fillRect(10,60,(int)combat.getCurHP(), 20);

    	//REPEAT FOR RIVAL

    	canvas.setColor(Color.BLACK);
    	//Draw string in to show that this is the HP bar
    	canvas.drawString("Rivals HP", 810, 50); 

    	//This creates the black border for the HP bar
    	canvas.drawRect(800, 60, (int)combat.getEnemyMaxHP(), 20);

    	//Now we set the color of the HP bar
    	canvas.setColor( new Color(255, 0, 0) );
    	// Draw a rectangle on the right hand side, making the inside of the bar.
    	canvas.fillRect(800,60,(int)combat.getEnemyCurHP(), 20);
    }

    /*=========================*\
 			Monster Card
	\*=========================*/

    public void monsterCardHandling(Graphics2D canvas, Mouse mouse){
    	if(buttonClick(460, 240, 530, 640)) mchOne = true;
    	if(mchOne == true){
    		MC1.displayCard(canvas, playerMonster1, GM);
    	}
    	exitCard(GM);
    	monsterCardButtonClash(GM);
    }

    //Exit button
    public void exitCard(Mouse mouse){
    	if((mouse.pointY < MC1.y + 23)&&(mouse.pointY > MC1.y + 5)&&(mouse.pointX > (MC1.x + 301)&&(mouse.pointX < MC1.x + 320))){
    		mchOne = false;
    		MC1.exitCard();
    	}
    }

  //This resolves the problem where buttons may be accidentally pressed while interacting with a Monster Card
    public void monsterCardButtonClash(Mouse mouse){
    	if((mouse.pointY < MC1.y)&&(mouse.pointY > MC1.y + 450)&&(mouse.pointX > MC1.x)&&(mouse.pointX < MC1.x + 325)){
    		menuScreenButtons = false;
    	}else{
    		menuScreenButtons = true;
    	}
    }

    /*=========================*\
			Misc.
	\*=========================*/

    //Deals with all music in game
    public void musicPlayer(){
    	if(sound == true){
    		if(fightButton){
    			GS_MainTheme.stopClip();
    			//This is rewinded every time you enter a fight.
    			GS_FightMusic.loopClip();
    		}else{
    			//This is where the fight music is rewinded.
    			GS_FightMusic.rewindClip();  
    			GS_FightMusic.stopClip();
    			//The main theme is never rewinded and just loops, this means when you exit a fight and the
    			//theme starts again, it will start from where it left off.
    			GS_MainTheme.loopClip(); 
    		}
    	}
    }

    //This takes care of all keyboard input.
    public void getInput(){
    	//Spacebar input
    	if(Keyboard.keyTyped == KeyEvent.VK_SPACE){
    	//Used to get past the start screen
    		if(startScreen){
    			startScreen = false;
    		}
    	}
    	
    	//Allows the game to be closed down with the escape key
    	if(Keyboard.keyTyped == KeyEvent.VK_ESCAPE){
    		//TODO Implement exit menu
    		player1.savePlayer();
    		saveParty();
    		System.exit(0);
    	}    
    }

}

