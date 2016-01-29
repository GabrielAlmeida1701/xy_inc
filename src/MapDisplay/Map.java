package MapDisplay;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xy_inc.GPSController;
import xy_inc.Main;
import GPSController.Controller;

public class Map {
	
	BufferedImage bg, me;
	Controller gps;
	
	public POI selectedPOI = null;
	
	public Map(Controller gps){
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/MapDisplays/Mapa.png"));
			me = ImageIO.read(getClass().getResourceAsStream("/MapDisplays/Me.png"));
			this.gps = gps;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Update(){
		gps.POIs.forEach(poi -> poi.Update(gps.MyPosition.x, gps.MyPosition.y));
	}
	
	public POI OnClick(Point mouse){
		selectedPOI = null;
		gps.POIs.forEach(poi -> {
			Rectangle rect = new Rectangle(poi.RelativePosition().x, poi.RelativePosition().y, 30, 30);
			if(rect.contains(mouse))
				selectedPOI = poi;
		});
		
		return selectedPOI;
	}
	
	public void Draw(Graphics2D g){
		g.drawImage(bg, 0, 0, 800, 600, null);
		g.drawImage(me, (Main.LARG_WINDOW/2)-10, (Main.ALT_WINDOWS/2)-10, 20, 20, null);
		g.drawString(gps.MyPosition.x+":"+gps.MyPosition.y,
				(Main.LARG_WINDOW/2)-10, (Main.ALT_WINDOWS/2)+20);
		
		gps.POIs.forEach(poi -> {
			int dx = (Main.LARG_WINDOW/2)-10;
			int dy = (Main.ALT_WINDOWS/2)-10;
			float d = (float)Math.sqrt(
					Math.pow((poi.RelativePosition().x-dx), 2) + Math.pow((poi.RelativePosition().y-dy), 2)
					);
			d /= 10;
			
			if(d <= 10)
				poi.Draw(g);
		});
	}

}
