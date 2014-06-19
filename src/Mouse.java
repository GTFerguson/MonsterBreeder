import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;


public class Mouse extends MouseAdapter implements MouseWheelListener {

	public int pointX;				//position X and Y of mouse  
	public int pointY;
	public boolean down;			//Button pressed
	public boolean dragged;			//Mouse is dragged
	
    public Mouse() {
        pointX=0;
        pointY=0;
        down = false;
       
     }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO after sound settings have been done we will add another boolean so the sound stops when the window is exited
		
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
		
		//Get location of mouse click
		pointX = mouse.getX();
		pointY = mouse.getY();
		down = true;
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
		//Get location of mouse click
		pointX = 0;
		pointY = 0;
		down = false;
		dragged = false;
		
	}
	
	public void mouseDragged(MouseEvent mouse){
        pointX = mouse.getX();   
        pointY = mouse.getY();
        down=false;
        dragged=true;
	}
}
