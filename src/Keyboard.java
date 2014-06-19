import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class Keyboard implements KeyListener{
	
	static Map<Integer, Boolean> keyMap = new HashMap<Integer, Boolean>();
	static char keyTyped=0;
	
	public static void initialise(Window w){
		new Keyboard(w);
		
		for(int i = 48; i < 122; i++){
			keyMap.put(i, false);
			
		}
		keyMap.put(KeyEvent.VK_ESCAPE, false);
		keyMap.put(KeyEvent.VK_SPACE, false);
	}
	
	public Keyboard(Window w){
		
		w.addKeyListener(this);
		
	}

	public void keyPressed(KeyEvent e) {
		
		keyMap.put(e.getKeyCode(), true);
		
	}

	public void keyReleased(KeyEvent e) {
		
		keyMap.put(e.getKeyCode(), false);
		
	}
	
	public void keyTyped(KeyEvent e) {
		keyTyped = e.getKeyChar();
	}
	
	public void resetKey(){
		keyTyped = 0;
	}
	
	public static char getKey(){
		return keyTyped;
	}
	

	
}
