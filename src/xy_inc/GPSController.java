package xy_inc;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import GPSController.Controller;
import MapDisplay.POI;

@SuppressWarnings("serial")
public class GPSController extends JPanel{

	private MapDisplay map;
	private Controller ctrl;
	
	public boolean alterando;
	
	public GPSController(MapDisplay map){
		this.map = map;
		ctrl = new Controller();
		ctrl.OpenGPS();
		map.SetGPSController(ctrl, this);
		InitComps();
		BntsFuncs();
		
		x_CampAlt.setEnabled(false);
		y_CampAlt.setEnabled(false);
		nome_AltCamp.setEnabled(false);
		saveBnt.setEnabled(false);
	}
	
	public void AtualizaCtrl(POI poi){
		x_CampAlt.setText(poi.GetPosition().x+"");
		y_CampAlt.setText(poi.GetPosition().y+"");
		distancia.setText(poi.Distancia()+"");
		nome_AltCamp.setText(poi.GetNome());
		NomePOI.setText(poi.GetNome());
	}
	
	public void clear(){
		x_CampAlt.setEnabled(false);
		y_CampAlt.setEnabled(false);
		nome_AltCamp.setEnabled(false);
		saveBnt.setEnabled(false);
	}
	
	public void clearCamps(){
		x_CampAlt.setText("");
		y_CampAlt.setText("");
		distancia.setText("----");
		NomePOI.setText("Nenhum POI selecionado");
	}
	
	public void BntsFuncs(){
		MoveBnt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				try{
					int x = 0, y = 0;				
					if(!xCamp.getText().equals(""))
						x = Integer.parseInt(xCamp.getText());
					if(!yCamp.getText().equals(""))
						y = Integer.parseInt(yCamp.getText());
				
					ctrl.Mover(x, y);
				}catch(Exception e){
					ctrl.Mover(0, 0);
				}
			}
		});
		
		CadBnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	try{
	                int x = Integer.parseInt(x_CadCamp.getText());
	                int y = Integer.parseInt(y_CadCamp.getText());
	                String nome = nome_CadCamp.getText();
	                if(nome.equals("")){
	                	JOptionPane.showMessageDialog(null, "Preencha todos os campos");
	                	return;
	                }
	                ctrl.CadastraPOI(x, y, nome);
            	}catch(Exception ex){
            		JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            	}
                x_CadCamp.setText("");
				y_CadCamp.setText("");
				nome_CadCamp.setText("");
            }
        });
		
		CleanBnt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				x_CadCamp.setText("");
				y_CadCamp.setText("");
				nome_CadCamp.setText("");
			}
		});
		
		AltBnt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(map.Selected != null){
					x_CampAlt.setEnabled(true);
					y_CampAlt.setEnabled(true);
					nome_AltCamp.setEnabled(true);
					saveBnt.setEnabled(true);
					alterando = true;
				}
			}
		});
		
		saveBnt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int x = map.Selected.GetPosition().x,
						y = map.Selected.GetPosition().y;
				String nome = map.Selected.GetNome();
				try{
					x = Integer.parseInt(x_CampAlt.getText());
					y = Integer.parseInt(y_CampAlt.getText());
					nome = map.Selected.GetNome();
					if(!nome_AltCamp.getText().equals(""))
						nome = nome_AltCamp.getText();
				}catch(Exception ex){
					x = map.Selected.GetPosition().x;
					y = map.Selected.GetPosition().y;
					nome = map.Selected.GetNome();
					JOptionPane.showMessageDialog(null, "Nada foi alterado");
				}
				
				map.Selected.SetPosition(new Point(x,y));
				map.Selected.SetNome(nome);
				ctrl.Alterar(map.Selected);
				alterando = false;
				clear();
			}
		});
	}
	
	public void InitComps(){
		xCamp = new JTextField();
        x_Label = new JLabel();
        yCamp = new JTextField();
        y_Label = new JLabel();
        MoveBnt = new JButton();
        jSeparator1 = new JSeparator();
        x_CadCamp = new JTextField();
        x_Label2 = new JLabel();
        y_Label2 = new JLabel();
        y_CadCamp = new JTextField();
        nome_Label = new JLabel();
        nome_CadCamp = new JTextField();
        CadBnt = new JButton();
        CleanBnt = new JButton();
        jSeparator2 = new JSeparator();
        NomePOI = new JLabel();
        dist_Label = new JLabel();
        distancia = new JLabel();
        Position_Label = new JLabel();
        x_CampAlt = new JTextField();
        y_CampAlt = new JTextField();
        AltBnt = new JButton();
        altNome_Label = new JLabel();
        nome_AltCamp = new JTextField();
        saveBnt = new JButton();
        cad_Label = new JLabel();

        x_Label.setText("X:");

        y_Label.setText("Y:");

        MoveBnt.setText("Mover");

        x_Label2.setText("X:");

        y_Label2.setText("Y:");

        nome_Label.setText("Nome:");

        CadBnt.setText("Cadastrar");

        CleanBnt.setText("Limpar");

        NomePOI.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        NomePOI.setHorizontalAlignment(SwingConstants.CENTER);
        NomePOI.setText("Nenhum POI selecionado");

        dist_Label.setText("Distancia:");

        distancia.setText("----");

        Position_Label.setText("Posição:");

        AltBnt.setText("Alterar");

        altNome_Label.setText("Alterar Nome");

        saveBnt.setText("Salvar");

        cad_Label.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cad_Label.setHorizontalAlignment(SwingConstants.CENTER);
        cad_Label.setText("Cadastrar Novo POI (Ponto de Interesse)");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(x_Label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xCamp, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(y_Label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yCamp, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addComponent(jSeparator1)
            .addComponent(NomePOI, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(x_CampAlt, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(y_CampAlt))
                            .addComponent(nome_AltCamp, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBnt))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dist_Label)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(distancia))
                            .addComponent(altNome_Label))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Position_Label)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AltBnt)))
                .addGap(31, 31, 31))
            .addComponent(jSeparator2)
            .addComponent(cad_Label, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CadBnt)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                                .addComponent(CleanBnt))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(y_Label2)
                                    .addComponent(x_Label2)
                                    .addComponent(nome_Label))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(x_CadCamp)
                                    .addComponent(y_CadCamp)
                                    .addComponent(nome_CadCamp, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(MoveBnt)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(xCamp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(x_Label)
                    .addComponent(yCamp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(y_Label))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MoveBnt)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cad_Label, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(x_CadCamp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(x_Label2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(y_Label2)
                    .addComponent(y_CadCamp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nome_Label)
                    .addComponent(nome_CadCamp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(CadBnt)
                    .addComponent(CleanBnt))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(NomePOI, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dist_Label)
                    .addComponent(distancia))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(Position_Label)
                    .addComponent(AltBnt))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(x_CampAlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(y_CampAlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveBnt)
                .addGap(14, 14, 14)
                .addComponent(altNome_Label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nome_AltCamp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE)));
	}
	
	private JButton AltBnt;
    private JButton CadBnt;
    private JButton CleanBnt;
    private JButton MoveBnt;
    private JLabel NomePOI;
    private JLabel Position_Label;
    private JLabel altNome_Label;
    private JLabel cad_Label;
    private JLabel dist_Label;
    private JLabel distancia;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JTextField nome_AltCamp;
    private JTextField nome_CadCamp;
    private JLabel nome_Label;
    private JButton saveBnt;
    private JTextField xCamp;
    private JTextField x_CadCamp;
    private JTextField x_CampAlt;
    private JLabel x_Label;
    private JLabel x_Label2;
    private JTextField yCamp;
    private JTextField y_CadCamp;
    private JTextField y_CampAlt;
    private JLabel y_Label;
    private JLabel y_Label2; 
}
