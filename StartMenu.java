


import java.awt.*;
import java.net.URL;
import java.util.Random;

import javax.swing.*;

public class StartMenu extends JFrame {
	Color orange = new Color(0xFFCC33);
	Dimension SIZE = Toolkit.getDefaultToolkit ().getScreenSize();
	
	
	int SCREEN_WIDTH = SIZE.width;
	int SCREEN_HEIGHT = SIZE.height;
	static Button b1 = new Button("Play");
	static Button b2 = new Button("Map Editor");
	static Button b3 = new Button("Exit DuskFire");
	static Button b4 = new Button("Settings");
	Random phraseGen = new Random();
	int PhraseNumberToPick;
	String PhraseTextToPick;
	JOptionPane op = new JOptionPane();
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image Title = tk.getImage(getURL("images/Misc/Title.png"));
	Image icon = tk.getImage(getURL("images/Misc/Logo.png"));
	
	public StartMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(orange);
		this.setTitle("Game :)");
		this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		
		
		add(b1);
		add(b2);
		add(b4);
		add(b3);
		
	}
	
	public static void main(String Args[]) {
		StartMenu f = new StartMenu();
		f.setVisible(true);
		f.setLayout(null);
		f.setTitle("DuskFire");
		
		b1.setBounds(600, 300, 150, 40);
		b2.setBounds(600, 380, 150, 40);
		b4.setBounds(600, 460, 150, 40);
		b3.setBounds(600, 540, 150, 40);
		System.out.println("Starting");
	}
	private URL getURL(String filename) {
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		}
		catch (Exception e) { }
		return url;
		
	}
	public void paint(Graphics g) {
		 g.drawImage(Title, 450, 100, this);
		 g.setFont(new Font("TimesRoman", Font.BOLD, 24));
		 g.setColor(new Color(0x660033));
		 
		 setIconImage(icon);
	}
	public boolean action(Event evt, Object arg) {
		if(evt.target instanceof Button) {
			String selectedButton = arg.toString();
			if(selectedButton == "Play") {
				String x[]={"A","B"};
				Game.main(x);
			}
			if(selectedButton == "Map Editor") {
			String x[]={"A","B"};
			MapEditor.main(x);
		
			}
			if(selectedButton == "Exit DuskFire") {
				System.out.println("Exiting Game");
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to exit DuskFire","Exiting DuskFire",JOptionPane.YES_NO_OPTION) == 0) {
					dispose();
				 }
				
			}
			
		}
		
		return true;
		
	}


}
