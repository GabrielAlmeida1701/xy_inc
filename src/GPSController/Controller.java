package GPSController;

import java.awt.Point;
import java.util.ArrayList;

import MapDisplay.POI;

public class Controller {

	public ArrayList<POI> POIs = new ArrayList<>();
	
	private POI selectedPOI;
	private BDConnector bd;
	
	public Point MyPosition = new Point(0,0);
	
	public Controller(){ bd = new BDConnector(); }
	
	public void Mover(int x, int y){
		MyPosition = new Point (x, y);
	}
	
	public void CadastraPOI(int x, int y, String nome){
		Point xy = new Point(x, y);
		POI poi = new POI(xy, nome);
		POIs.add(poi);
		bd.AddPOI(poi);
	}
	
	public void Alterar(POI poi){
		int i = POIs.indexOf(poi);
		bd.ChangePOI(POIs.get(i), (i+1));
	}
	
	public void OpenGPS(){
		POIs = bd.GetPOIs();
	}
}
