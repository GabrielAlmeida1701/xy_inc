package GPSController;

import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import MapDisplay.POI;

public class BDConnector {

	public Connection Connect(){
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String user = "root";
			String pass = "1234";
			String url = "jdbc:mysql://localhost/xy_inc?autoReconnect=true&useSSL=false";
			
			conn = DriverManager.getConnection(url, user, pass);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Nao foi possivel conectar\n ao banco de dados");
		}
		
		return conn;
	}
	
	public void AddPOI(POI poi){
		try {
			Connection conn = Connect();
			Statement stt = conn.createStatement();
			String query = "insert into pois (x, y, nome)"+
			"value ("+poi.GetPosition().x+","+poi.GetPosition().y+",'"+poi.GetNome()+"')";
			stt.executeUpdate(query);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ChangePOI(POI poi, int id){
		try {
			Connection conn = Connect();
			String query = "update pois set x=?, y=?, nome=? where id=?";
			PreparedStatement prep = conn.prepareStatement(query);
			prep.setInt(1, poi.GetPosition().x);
			prep.setInt(2, poi.GetPosition().y);
			prep.setString(3, poi.GetNome());
			prep.setInt(4, id);
			
			prep.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<POI> GetPOIs(){
		ArrayList<POI> pois = new ArrayList<>();
		try {
			Connection conn = Connect();
			Statement stt = conn.createStatement();
			String query = "select * from pois";
			ResultSet result = stt.executeQuery(query);
			while(result.next()){
				Point xy = new Point(result.getInt(2), result.getInt(3));
				POI newPoi = new POI(xy, result.getString(4));
				pois.add(newPoi);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pois;
	}
	
}
