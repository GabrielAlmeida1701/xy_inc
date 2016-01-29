package xy_inc;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GPSController.Controller;
import MapDisplay.Map;
import MapDisplay.POI;

@SuppressWarnings("serial")
public class MapDisplay extends JPanel implements Runnable{

	private Thread thread;
	private BufferedImage img;
	private boolean running;
	private Graphics2D g;
	
	private Controller gps;
	private GPSController gpsCtrl;
	private Map map;
	
	public POI Selected;
	
	public void SetGPSController(Controller gps, GPSController gpsCtrl){
		this.gps = gps;
		this.gpsCtrl = gpsCtrl;
	}

	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void init(){
		img = new BufferedImage(Main.LARG_WINDOW, Main.ALT_WINDOWS, BufferedImage.TYPE_4BYTE_ABGR);
		g = (Graphics2D)img.getGraphics();
		map = new Map(gps);
		addMouseListener(ml);
		running = true;
	}
	
	MouseListener ml = new MouseListener(){
		@Override
		public void mouseClicked(MouseEvent e) {
			gpsCtrl.clear();
			Selected = map.OnClick(e.getPoint());
			if(Selected == null)
				gpsCtrl.clearCamps();
		}
		
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	};
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait = 5;
		
		while(running){
			start = System.nanoTime();
			draw();
			drawToScreen();
			update();
			elapsed = System.nanoTime() - start;			
			wait = (1000/60) - elapsed / 1000000;
			if(wait < 0) wait = 5;
			try {
				Thread.sleep(wait);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(img, 0, 0, Main.LARG_WINDOW, Main.ALT_WINDOWS, null);
		g2.dispose();
	}
	
	private void draw(){
		map.Draw(g);
	}
	
	private void update(){
		map.Update();
		
		if(map.selectedPOI != null && !gpsCtrl.alterando)
			gpsCtrl.AtualizaCtrl(map.selectedPOI);
		
		Point newLocation = Main.window.getLocation();
		newLocation.x += Main.LARG_WINDOW;
		Main.gpsCoords.setLocation(newLocation);
	}
	
}
