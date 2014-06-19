import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class MonsterCard {
	
	private GameImage GI_MonsterCard;
	private boolean mcDrag, startUp = true;
	public int x, y, width, height, xDif, yDif;
	
	public MonsterCard(){
		
		GI_MonsterCard = new GameImage("MonsterCard.png");
		
		if(startUp == true){
			x = 100;		
			y = 100;
			startUp = false;
		}

		


	}
	
	public void displayCard(Graphics2D canvas, Genetics monster, Mouse mouse){
			GI_MonsterCard.draw(canvas, x, y);
			monster.monImage(canvas, x + 65, y + 65, false);
		
			canvas.setColor(Color.darkGray);
			canvas.setFont(new Font("Terminal", Font.BOLD, 24));
			
			canvas.drawString(""+monster.returnName(), 100 + x, 315 + y);
			canvas.drawString(""+monster.returnStrength(), 100 + x, 340 + y);
			canvas.drawString(""+monster.returnDexterity(), 100 + x, 365 + y);
			canvas.drawString(""+monster.returnConstitution(), 100 + x, 390 + y);
			canvas.drawString(""+monster.returnWisdom(), 100 + x, 415 + y);
			canvas.drawString(""+monster.returnCharisma(), 100 + x, 440 + y);
		
			dragCard(mouse);
	}
	
	public void dragCard(Mouse mouse){
		if((mouse.dragged == true)&&(mouse.pointX > x)&&(mouse.pointX < (x + GI_MonsterCard.getWidth()))&&(mouse.pointY > y)&&(mouse.pointY < (y + GI_MonsterCard.getHeight()))){
			if(mcDrag == false){
				xDif = mouse.pointX - x;
				yDif = mouse.pointY - y;
				mcDrag = true;
			}
	
			if(mcDrag = true){
				x = mouse.pointX - xDif;
				y = mouse.pointY - yDif;
			}
		}
		
		if(mouse.dragged == false){
			mcDrag = false;
		}
	}
	
	public void exitCard(){
		startUp = true;
	}
}
