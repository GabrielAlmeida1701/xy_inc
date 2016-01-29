package MapDisplay;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xy_inc.Main;

public class POI{
	
	private Point xy, cursor = new Point(0,0);
	private BufferedImage icon;
	
	private String nome;
	
	public POI(Point xy, String nome){
		try {
			this.xy = xy;
			this.nome = nome;
			icon = ImageIO.read(getClass().getResourceAsStream("/MapDisplays/POI_Icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SetPosition(Point xy){this.xy = xy;}
	public Point GetPosition(){ return xy; }
	public void SetNome(String nome){this.nome = nome;}
	
	public void Update(int x, int y){
		cursor = new Point(x, y);
	}
	
	public Point RelativePosition(){
		if(cursor == null)
			System.out.println("null");
		return new Point (390-(cursor.x-xy.x), 290+(cursor.y-xy.y));
	}
	
	public float Distancia(){
		int dx = (Main.LARG_WINDOW/2)-10;
		int dy = (Main.ALT_WINDOWS/2)-10;
		float d = (float)Math.sqrt(
			Math.pow((RelativePosition().x-dx), 2) + Math.pow((RelativePosition().y-dy), 2)
		);
		
		return d;
	}
	
	public String GetNome(){ return nome; }
	
	public void Draw(Graphics2D g){
		int dx = 390-(cursor.x-xy.x);
		int dy = 290+(cursor.y-xy.y);
		
		g.drawImage(icon, dx, dy, 30, 30, null);
		g.drawString(nome, dx, dy);
	}

}
