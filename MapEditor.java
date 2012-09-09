


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import javax.swing.*;


public class MapEditor  extends JFrame implements MouseListener{
	/*int temps[] = new int[10];*/
	Dimension SIZE = Toolkit.getDefaultToolkit ().getScreenSize();

	int SCREEN_WIDTH = SIZE.width;
	int SCREEN_HEIGHT = SIZE.height;
	int numberOfTiles = 30;
	int tileSize= 32;
	int mapHeight = SIZE.height / 32 - 4;
	int mapWidth = SIZE.width / 32 - 2;
	int mouse_xScaled;
	int mouse_yScaled;
	int currentTool = 3;
	int opvalue;
	
	Color red = new Color(0xFFCC33);
	
	Choice c1 = new Choice();
	Button b1 = new Button("Save As");
	Button b2 = new Button("Save");
	Button b3 = new Button("Open");
	Button b4 = new Button("Quit");
	Button b5 = new Button("Select Background");
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	String[] tiles = new String[numberOfTiles];
	Image[] tileGfx = new Image[numberOfTiles];
	int[][] map = new int[mapWidth][mapHeight];
	int  xpos;
	int ypos;
	int xOffset = 50;
	int yOffset = 75;
	String selectedChoice;
	String version = "Alpha v0.2";
	String fileString;
	String fileStringFinal;
	
	JFileChooser fc = new JFileChooser();
	JOptionPane op = new JOptionPane();
	File fileToOpen;
	File fileToSave;
	
	Frame dialog = new Frame();
	
	Image icon = tk.getImage(getURL("images/Misc/Logo.png"));
	
	Image background;
	
	File backgroundToOpen;
	
	

	
	
	public void loadMap() {
		
			
		if(Config.debug)System.out.println("in loadMap");
		repaint();
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(fileToOpen));
			map = (int[][]) ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*map[0][0] = 4;
		map[0][20] = 4;
		map[1][0] = 4;
		map[1][1] = 4;*/
	}
/*	public void renderMap() {
		int x, y;
		System.out.println("in renderMap");
		for(x = 0; x < mapWidth; x++) {
			for(y = 0; y < mapHeight; y++) {
				System.out.print(map[x][y]);

				xpos= x*tileSize;
				ypos= y*tileSize;
				
				//render(tileGfx(map[x][y]), xpos, ypos);
			}
			System.out.println();
		}
	}*/
	public void saveMap() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave + ".map"));
		oos.writeObject(map);
		oos.close();
	}
	private URL getURL(String filename) {
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		}
		catch (Exception e) { }
		return url;
		
	}


	public void initTiles() {
//		tiles[0] = "Grass";
//		tiles[1] = "Dirt";
//		tiles[2] = "Rock";
//		tiles[3] = "Sand";
//		tiles[4] = "Ice";
			
			
			tileGfx[0] = tk.getImage(getURL("images/Terrain/Grid.png"));
			tileGfx[1] = tk.getImage(getURL("images/Terrain/Rock.png")); // Rock
			tileGfx[2] = tk.getImage(getURL("images/Terrain/Sand.png")); //Sand
			tileGfx[3] = tk.getImage(getURL("images/Terrain/SideGrass.png")); // Grass
			tileGfx[4] = tk.getImage(getURL("images/Terrain/TempleRock.png"));// Temple Rock
			tileGfx[5] = tk.getImage(getURL("images/Terrain/Blocks/Dirt.png")); // Dirt
			tileGfx[6] = tk.getImage(getURL("images/Terrain/Log.png")); // Log
			tileGfx[7] = tk.getImage(getURL("images/Terrain/Leaves.png")); // Leaves
			tileGfx[8] = tk.getImage(getURL("images/Scenery/Rose.png")); // Rose
			tileGfx[9] = tk.getImage(getURL("images/Scenery/Barrelcactus.png")); // Barrel Cactus
			tileGfx[10] = tk.getImage(getURL("images/Items/Chest.png")); // Chest
			tileGfx[11] = tk.getImage(getURL("images/Terrain/Rock.png"));
			tileGfx[12] = tk.getImage(getURL("images/Terrain/Blocks/Ice.png")); // Ice
			tileGfx[13] = tk.getImage(getURL("images/Terrain/Blocks/Snow.png")); // Snow
			tileGfx[14] = tk.getImage(getURL("images/Terrain/Blocks/Brick.png")); // Temple Brick
			tileGfx[15] = tk.getImage(getURL("images/Terrain/Blocks/Glass.png")); // Glass
			tileGfx[16] = tk.getImage(getURL("images/Terrain/Blocks/Redbrick.png")); // Bricks
			tileGfx[17] = tk.getImage(getURL("images/Terrain/Blocks/Metal.png")); // Metal
			tileGfx[18] = tk.getImage(getURL("images/Terrain/Blocks/Computer.png")); // Computer
			tileGfx[19] = tk.getImage(getURL("images/Terrain/Blocks/Water.png")); // Water
			tileGfx[20] = tk.getImage(getURL("images/Terrain/Blocks/Lava.png")); // Lava
	}
	public MapEditor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initTiles();
		// loadMap();
		setBackground(red);
		setIconImage(icon);
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		c1.add("Grass");
		c1.add("Dirt");
		c1.add("Rock");
		c1.add("Sand");
		c1.add("Glass");
		c1.add("Bricks");
		c1.add("Metal");
		c1.add("Computer");
		c1.add("Water");
		c1.add("Lava");
		c1.add("Snow");
		c1.add("Ice");
		c1.add("Temple Rock");
		c1.add("Temple Brick");
		c1.add("Log");
		c1.add("Leaves");
		c1.add("Rose");
		c1.add("Small Cacti");
		c1.add("Chest");
		c1.add("Delete");
		
		/*b1.setBounds(20, 20, 40, 40);
		b2.setBounds(20, 50, 40, 100);
		b3.setBounds(20, 80, 40, 160);
		b4.setBounds(20, 110, 40, 220);*/
		add(c1);
	
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		
		
	}
	public void renderOnScreenText(Graphics g) {
		g.setFont(new Font("TimesRoman",Font.BOLD,24));
		// g.drawString(version, 30, 50);
		
		
	}
	
	public static void main(String Args[]) {
		MapEditor f = new MapEditor();
		f.setLayout(new FlowLayout());
		f.setVisible(true);
		f.setTitle("DuskFire");
		f.addMouseListener(f);
		
		/*f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
			//f.dispose();
			System.exit(0);
			}
			
		});*/
	
	}
	
	public void paint(Graphics g) {
		if(fileToOpen == null) {
			fileString = "None Selected";
		}
		else if(fileToOpen != null) {
			fileString = fileToOpen.toString(); 
		}
		
		this.setTitle("DuskFire Map Editor " + "  Currently Editing: " + fileString);
		
		renderOnScreenText(g);
		//renderMap();
		Image img;
		setIconImage(icon);
		int x, y;
		if(Config.debug)System.out.println("in Paint");
		for(x = 0; x < mapWidth; x++) {
			for(y = 0; y < mapHeight; y++) {
				if(Config.debugMap)System.out.print(map[x][y]);
				xpos= x*tileSize + xOffset;
				ypos= y*tileSize + yOffset;
				img = tileGfx[map[x][y]];
				g.drawImage(img, xpos, ypos, this);
			}
			if(Config.debugMap)System.out.println();
		}
	}
	
	/*public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}*/
	/*public boolean mouseDown(Event evt, int mouse_x, int mouse_y) {
		
		mouse_x -= xOffset;
		mouse_y -= yOffset;
		mouse_xScaled = mouse_x / tileSize;
		mouse_yScaled = mouse_y / tileSize;
		if(Config.debug)System.out.println("Tile Placed At: " + mouse_xScaled + ", " + mouse_yScaled);
		map[mouse_xScaled][mouse_yScaled] = currentTool;
		repaint();
		
		
		
		return true;
		
	}*/
	
	
	
	
	
		
		
		
		
	
	public boolean action(Event evt, Object arg) {
		if(evt.target instanceof Button) {
			String selectedButton = arg.toString();
			if(Config.debug)System.out.println("Selected Button: " + selectedButton);
			if(selectedButton == "Quit") {
				if(Config.debug)System.out.println("Saving Map and Exiting");
				if(Config.debug)System.out.println("Map Editor Stopped");
				
					 if(JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?" +
					 		"\nAny unsaved maps will be lost.","Quitting Map Editor..",JOptionPane.YES_NO_OPTION) == 0) {
						dispose();
					 }

			}
			
			if(selectedButton == "Save As") {
				try {
					
					int returnVal = fc.showSaveDialog(MapEditor.this);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                fileToSave = fc.getSelectedFile();
		                saveMap();
		            }
		                
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(selectedButton == "Open") {
			int returnVal = fc.showOpenDialog(MapEditor.this);
					 if (returnVal == JFileChooser.APPROVE_OPTION) {
			          fileToOpen = fc.getSelectedFile();
			         
			          loadMap();
			                
			                
				}
					 
			}
			if(selectedButton == "Save") {
				JOptionPane.showMessageDialog(dialog, "Feature Disabled due to bug", "Note", JOptionPane.INFORMATION_MESSAGE);
				/*if(fc.getSelectedFile() == null) {
					int returnVal = fc.showSaveDialog(MapEditor.this);
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        fileToSave = fc.getSelectedFile();
				        saveMap();
				    }*/
				
			}
			
			
		}
		
		if(evt.target instanceof Choice) {
			
			selectedChoice = c1.getSelectedItem();
			
			if(Config.debug)System.out.println("Selected Item: " + selectedChoice);
			
			if(selectedChoice == "Grass") {
				currentTool = 3;
			}
			if(selectedChoice == "Dirt") {
				currentTool = 5;
			}
			if(selectedChoice == "Rock") {
				currentTool = 1;
			}
			if(selectedChoice == "Sand") {
				currentTool = 2;
			}
			if(selectedChoice == "Temple Rock") {
				currentTool = 4;
			}
			if(selectedChoice == "Log") {
				currentTool = 6;
			}
			if(selectedChoice == "Leaves") {
				currentTool = 7;
			}
			if(selectedChoice == "Rose") {
				currentTool = 8;
			}
			if(selectedChoice == "Small Cacti") {
				currentTool = 9;
			}
			if(selectedChoice == "Chest") {
				currentTool = 10;
			}
			if(selectedChoice == "Spawn Point") {
				currentTool = 11;
			}
			if(selectedChoice == "Ice") {
				currentTool = 12;
			}
			if(selectedChoice == "Snow") {
				currentTool = 13;
			}
			if(selectedChoice == "Temple Brick") {
				currentTool = 14;
			}
			if(selectedChoice == "Glass") {
				currentTool = 15;
			}
			if(selectedChoice == "Bricks") {
				currentTool = 16;
			}
			if(selectedChoice == "Metal") {
				currentTool = 17;
			}
			if(selectedChoice == "Computer") {
				currentTool = 18;
			}
			if(selectedChoice == "Water") {
				currentTool = 19;
			}
			if(selectedChoice == "Lava") {
				currentTool = 20;
			}
			if(selectedChoice == "Delete") {
				currentTool = 0;
			}
		}
		return true;	
		}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
		

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int mouse_x = arg0.getX();
		int mouse_y = arg0.getY();
		mouse_x -= xOffset;
		mouse_y -= yOffset;
		mouse_xScaled = mouse_x / tileSize;
		mouse_yScaled = mouse_y / tileSize;
		System.out.println("Tile Placed At: " + mouse_xScaled + ", " + mouse_yScaled);
		map[mouse_xScaled][mouse_yScaled] = currentTool;
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}


