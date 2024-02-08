package GameOfLife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GameOfLife.Component.SquareID;

public class Square {
		static final int size = 7;
		private int x,y;
		SquareID ID;
		
		
		public Square(SquareID ID, int x, int y) {
			this.ID = ID;
			this.x = x;
			this.y = y;
		}
		
		public void paint(Graphics g) {
			
			if(ID == SquareID.ALIVE) {
				g.setColor(Color.BLACK);
			}else if(ID == SquareID.DEAD) {
				g.setColor(Color.WHITE);
			}
			g.drawRect(x, y, size, size);
			g.fillRect(x, y, size, size);
		}
		
		public Rectangle getBounds() {
			return new Rectangle(x,y,size,size);
		}
	}
