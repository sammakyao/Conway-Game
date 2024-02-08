package GameOfLife;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.Timer;

	public class Component extends JComponent implements MouseListener, ActionListener{
		private static final long serialVersionUID = 1L;
		enum SquareID {ALIVE, DEAD};
		int row = 100, col = 100;
		private int[][] current = new int[100][100];
		private int[][] sparse = new int[][]{{100, 100, 32}, {42, 45, 1}, {42, 46, 1}, {43, 45, 1}, {43, 46, 1}, {52, 45, 1}, {52, 46, 1}, {52, 47, 1}, {53, 44, 1}, {53, 48, 1}, {54, 43, 1}, {54, 49, 1}, {55, 43, 1}, {55, 49, 1}, {56, 46, 1}, {57, 44, 1}, {57, 48, 1}, {58, 45, 1}, {58, 46, 1}, {58, 47, 1}, {59, 46, 1}, {62, 43, 1}, {62, 44, 1}, {62, 45, 1}, {63, 43, 1}, {63, 44, 1}, {63, 45, 1}, {64, 42, 1}, {64, 46, 1}, {66, 41, 1}, {66, 42, 1}, {66, 46, 1}, {66, 47, 1}};
		private int[][] future = new int[row][col];
		private Square[][] squares  = new Square[row][col];
		
		Timer timer;
		Rectangle start = new Rectangle(300, 710, 100, 40);
		int generationCount = 0;

		public Component() {
			setBackground(Color.WHITE);
			timer = new Timer(100, this);
			sparseToCurrent();
			buildSquares();
			addMouseListener(this);
		}

		private void sparseToCurrent() {
			for(int i=1;i<sparse.length;i++){
				current[sparse[i][0]][sparse[i][1]]=sparse[i][2];
			}
			
		}

		private void nextGeneration() {
			for (int i = 0; i < row; i++)
			{
				for (int j = 0; j < col; j++)
				{
					int aliveNeighbours = 0;
					for (int p = -1; p <= 1; p++)
						for (int q = -1; q <= 1; q++)
							if ((i+p>=0 && i+p<row) && (j+q>=0 && j+q<col))
								aliveNeighbours += current[i + p][j + q];

					aliveNeighbours -= current[i][j];

					/* 
					 * Conway Game of Life Rules
					 * Any live cell with fewer than two live neighbors dies, as if by underpopulation.
					Any live cell with two or three live neighbors lives on to the next generation.
					Any live cell with more than three live neighbors dies, as if by overpopulation.
					Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
					*/

					if ((current[i][j] == 1) && (aliveNeighbours < 2 || aliveNeighbours>3))
						future[i][j] = 0;

					// A new cell is born
					else if ((current[i][j] == 0) && (aliveNeighbours == 3))
						future[i][j] = 1;

					else
						future[i][j] = current[i][j];
				}
			}
			generationCount++;
		}

		void buildSquares() {
			for (int i = 0; i < row; i++)
			{
				for (int j = 0; j < col; j++)
				{
					if(current[i][j]==0) {
						squares[i][j] = new Square(SquareID.DEAD, i*Square.size, j*Square.size);
					}else if(current[i][j]==1) {
						squares[i][j] = new Square(SquareID.ALIVE, i*Square.size, j*Square.size);
					}     
				}
			}
		}

		public void paintComponent(Graphics g) {
			for (int i = 0; i < row; i++)
			{
				for (int j = 0; j < col; j++)
				{
					squares[i][j].paint(g);
				}
			}
			g.setColor(Color.BLACK);
			g.setFont(new Font("Dialog",Font.BOLD, 20));
			g.fillRect(300, 710, 100, 40);
			g.drawRect(300, 710, 100, 40);
			g.setColor(Color.WHITE);
			g.drawString("ENTER", 315, 739);
			g.setFont(new Font("Dialog",Font.BOLD, 20));
			g.setColor(Color.BLACK);
			g.drawString("GENERATION: "+ generationCount, 30, 729);
		}

		void initialSquareToCurrent() {
			for (int i = 0; i < row; i++)
			{
				for (int j = 0; j < col; j++)
				{
					Square s = squares[i][j];
					if(s.ID == SquareID.DEAD) {
						current[i][j]=0;
					}else if(s.ID == SquareID.ALIVE) {
						current[i][j]=1;
					}
				}
			}
		}

		private void update() {
			timer.start();
			nextGeneration();
			buildSquares();
			current = future;
			future = new int[row][col];
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX(), y =e.getY();
			for(int w = 0; w < row; w++) {
				for(int h = 0; h < col; h++) {
					Rectangle r = squares[w][h].getBounds();
					Square s = squares[w][h];
					if(r.contains(x,y)) {
						if(s.ID == SquareID.DEAD) {
							s.ID = SquareID.ALIVE;
						}else {
							s.ID = SquareID.DEAD;
						}
					}
				}
			}

			this.repaint();
			if(start.contains(x,y)) {
				initialSquareToCurrent();
				update();

			}	
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			update();
			repaint();

		}
	}
