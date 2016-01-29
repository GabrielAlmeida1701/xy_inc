package xy_inc;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static final int LARG_WINDOW = 800;
	public static final int LARG_GPS = 300;
	public static final int ALT_WINDOWS = 600;
	
	public static JFrame window = new JFrame("GPS");
	public static JFrame gpsCoords = new JFrame("Controller");
	
	public static void main(String[] args){
		MapDisplay map = new MapDisplay();
		GPSController gps = new GPSController(map);
		
		window.setPreferredSize(new Dimension(LARG_WINDOW, ALT_WINDOWS));
		gpsCoords.setPreferredSize(new Dimension(LARG_GPS, ALT_WINDOWS));
		window.setSize(LARG_WINDOW, ALT_WINDOWS);
		gpsCoords.setSize(LARG_GPS, ALT_WINDOWS);
		window.setResizable(false);
		gpsCoords.setResizable(false);
		window.setContentPane(map);
		gpsCoords.setContentPane(gps);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gpsCoords.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		gpsCoords.setVisible(true);
		window.setLocation(0, 0);
		gpsCoords.setLocation(LARG_WINDOW, 0);
	}
	
}
