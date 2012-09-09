


import java.awt.*;
import java.io.*;
import java.net.URL;
import javax.swing.*;



public class Game  extends JFrame {
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	Dimension SIZE = Toolkit.getDefaultToolkit ().getScreenSize();

	int SCREEN_WIDTH = SIZE.width;
	int SCREEN_HEIGHT = SIZE.height;
	int numberOfTiles = 40;
	int tileSize= 32;
	int mapHeight = SIZE.height / 32 - 4;
	int mapWidth = SIZE.width / 32 - 2;
	int mouse_xScaled;
	int mouse_yScaled;
	int currentTool = 3;
	int opvalue;
	
	int key2;
	int[][] map = new int[mapWidth][mapHeight];
	int  xpos;
	int ypos;
	int xOffset = 50;
	int yOffset = 75;
	int player_X = 0;
	int player_Y = 0;
	int[] nonSolidBlock = new int[] {0, 8};
	
	
	
	String[] tiles = new String[numberOfTiles];
	String selectedChoice;
	String fileString;
	String fileStringFinal;
	
	Choice c1 = new Choice();
	
	Button b1 = new Button("Save As");
	Button b2 = new Button("Save");
	Button b3 = new Button("Open");
	Button b4 = new Button("Quit");
	
	JFileChooser fc = new JFileChooser();
	
	JOptionPane op = new JOptionPane();
	
	File fileToOpen;
	File fileToSave;
	
	Frame dialog = new Frame();
	
	Image[] tileGfx = new Image[numberOfTiles];
	Image icon = tk.getImage(getURL("images/Misc/Logo.png"));
	Image Player = tk.getImage(getURL("images/Enemies/Forest/Snail.png"));
	Image img;
	Image Title;
	Image background;
	
	Rectangle playerBounds;
	
	
	
	Color red = new Color(0xFFCC33);
	
	
	
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
			tileGfx[11] = tk.getImage(getURL("images/Terrain/Rock.png")); // Spawn Point
			tileGfx[12] = tk.getImage(getURL("images/Terrain/Blocks/Ice.png")); // Ice
			tileGfx[13] = tk.getImage(getURL("images/Terrain/Blocks/Snow.png")); // Snow
			tileGfx[14] = tk.getImage(getURL("images/Terrain/Blocks/Brick.png")); // Temple Brick
			tileGfx[15] = tk.getImage(getURL("images/Terrain/Blocks/Glass.png")); // Glass
			tileGfx[16] = tk.getImage(getURL("images/Terrain/Blocks/Redbrick.png")); // Bricks
			tileGfx[17] = tk.getImage(getURL("images/Terrain/Blocks/Metal.png")); // Metal
			tileGfx[18] = tk.getImage(getURL("images/Terrain/Blocks/Computer.png")); // Computer
			tileGfx[19] = tk.getImage(getURL("images/Terrain/Blocks/Water.png")); // Water
			tileGfx[20] = tk.getImage(getURL("images/Terrain/Blocks/Lava.png")); // Lava
			background = tk.getImage(getURL("images/Terrain/Blocks/Background.png"));
			
	}
	public Game() {
		
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// loadMap();
		
		setBackground(red);
		setIconImage(icon);
		initTiles();
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(Game.this);
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
         fileToOpen = fc.getSelectedFile();
         loadMap();
         playerSpawn();
         }
		 else if(returnVal == JFileChooser.CANCEL_OPTION) {
			 dispose();
		 }
		 this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		 add(b4);
		 
		
	}
	public void playerSpawn() {
		
		for(int i = 0; i < 100; i++) {
			if(Config.debug)System.out.println("player_X: " + player_X + " player_Y: " + player_Y);
			if(Config.debug)System.out.println("current map" + map[player_X][player_Y]);
			if(Config.debug)System.out.println("below map" + map[player_X][player_Y+1]);
			
			if (checkSolid(map[player_X][player_Y+1]) == true) {
				if(Config.debug)System.out.println("I: " + i);
				if(Config.debug)System.out.println("player_Y: " + player_Y);
				break;
				
			}
			player_Y++;
			repaint();
		
		}
	}
	public void renderOnScreenText(Graphics g) {
		g.setFont(new Font("TimesRoman",Font.BOLD,24));
		// g.drawString(version, 30, 50);
		
		
	}
	public void updatePlayer(Graphics g, int x, int y) {
		g.drawImage(Player, x, y, this);
	}
	
	
	public static void main(String Args[]) {
		Game f = new Game();
		f.setLayout(new FlowLayout());
		f.setVisible(true);
		
		
		/*f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
			//f.dispose();
			System.exit(0);
			}
			
		});*/
	
	}
	
	public void paint(Graphics g) {
		
		
		Player.getHeight(this);
		g.drawImage(background, 0, 0, this);
		this.setTitle("DuskFire");
		renderOnScreenText(g);
		//renderMap();
		
		int x, y;
		System.out.println("in Paint");
		for(x = 0; x < mapWidth; x++) {
			for(y = 0; y < mapHeight; y++) {
				if(Config.debugMap)System.out.print(map[x][y]);
				xpos= x*tileSize + xOffset;
				ypos= y*tileSize + yOffset;
				img = tileGfx[map[x][y]];
				
				g.drawImage(img, xpos, ypos, this);
				
				g.drawImage(Player, player_X*tileSize + xOffset, player_Y*tileSize + yOffset, this);
				}
			if(Config.debugMap)System.out.println();
			}
	}
	
	/*public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}*/
	
	
	
	
	
		
		
		
		
	
	public boolean action(Event evt, Object arg) {
		if(evt.target instanceof Button) {
			String selectedButton = arg.toString();
			if(Config.debug)System.out.println("Selected Button: " + selectedButton);
			if(selectedButton == "Quit") {
				if(Config.debug)System.out.println("Saving Map and Exiting");
				if(Config.debug)System.out.println("Map Editor Stopped");
				
					 if(JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?" +
					 		"\nAny unsaved Progress will be lost","Quitting Game..",JOptionPane.YES_NO_OPTION) == 0) {
						dispose();
					 }

			}
			
		                
			
					 
			
		
		}
		
		
		return true;	
		}
	
	public boolean keyDown(Event evt, int key) {
		
		System.out.println(key);
		
		if(key == 119) {
			
			System.out.println("Up");
			if (checkSolid(map[player_X][player_Y-1])) {
				player_Y --;
				//updatePlayer(g,player_X*tileSize + xOffset,player_Y*tileSize + yOffset);
				repaint();
			}
		}
		if(key == 115) {
			if (checkSolid(map[player_X][player_Y+1])) {
				player_Y ++;
				//updatePlayer(g,player_X*tileSize + xOffset,player_Y*tileSize + yOffset);
				repaint();
			}
			
		}
		if(key == 97) {
			
			System.out.println("Left");
			if (checkSolid(map[player_X-1][player_Y])) {
				player_X --;
				repaint();
			}
		}
		if(key == 100) {
			
			System.out.println("Right");
			if (checkSolid(map[player_X+1][player_Y])) {
				player_X ++;
				repaint();
			}
		}
		return true;
	}
	public boolean checkSolid(int tileNumber) {
		for(int i = 0;i < nonSolidBlock.length; i++) {
			if(nonSolidBlock[i] == tileNumber) {
				return true;
			}
		}
		
		return false;
		
	}
	
	

}


