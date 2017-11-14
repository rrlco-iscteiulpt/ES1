package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Interface {



	private JFrame frame;
	//
	//
	public Interface() {
		addFrameContent();
	}

	private void addFrameContent() {

		frame = new JFrame("Filtro Anti-SPAM");
		frame.setLocation(50,50);
		frame.setSize(600, 500);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		//panelCenterWest.add();
		frame.add(panel,BorderLayout.CENTER);


		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(50, 30, 300, 50);

	}
		//			for(int i = 0; i != width; i++){
		//				for(int j =0; j!=height ; j++){
		//					JLabel aux = new JLabel();
		//					
		//					int auxi = i;
		//					int auxj = j;
		//					
		//					aux.addMouseListener(new MouseAdapter() {
		//						public void mouseClicked(MouseEvent e){
		//						System.out.println("janela: " + Integer.toString(auxi) + "," + Integer.toString(auxj));
		//						}
		//		
		//					});
		//					Border border = BorderFactory.createLineBorder(Color.BLACK,1);
		//					aux.setBorder(border);
		//					frame.add(aux);
		//				}
		//			}
		//		
		//		}
		//		
		public void open() {
			frame.setVisible(true);
		}
		//		
	
	public static void main(String[] args) {
		Interface grid = new Interface();
		grid.open();
	}

}
//
