package GameOfLife;

import java.awt.Color;


import javax.swing.JFrame;

public class GameOfLife extends JFrame{
		
	private static final long serialVersionUID = 1L;
		private final static int width = 700, height = 800;
		
		public GameOfLife() {
			setSize(width, height);
			setTitle("Welcome to Samwel Makyao Game of Life");
			setBackground(Color.WHITE);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			Component component = new Component();
			add(component);
			setVisible(true);
		}


	public static void main(String[] args) {
		new GameOfLife();

	}

}
