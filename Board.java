package com.ubs.opsit.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	
	private final int B_WIDTH = 300;
	private final int B_HEIGHT = 300;
	private final int DOT_SIZE = 10;
	private final int ALL_DOTS = 900;
	private final int RAND_POS = 29;
	private final int DELAY = 20;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	
	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;
	private int count = 0;
	
	public Board() {
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}
	
	private void loadImages() {
		ImageIcon iid = new ImageIcon(getClass().getResource("dot.png"));
		ball = iid.getImage();
		
		ImageIcon iia = new ImageIcon(getClass().getResource("apple.png"));
		apple = iia.getImage();
		
		ImageIcon iih = new ImageIcon(getClass().getResource("head.png"));
		head = iih.getImage();
	}
	
	//Program starts here
	private void initGame() {
		dots = 3;
		
		for(int z=0;z<dots;z++) {
			x[z] = 50 - z * 10;
			y[z] = 50;
		}
		
		locateApple();
		
		// we use a timer to call action performed method at fixed delay
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private void doDrawing(Graphics g) {
		if(inGame) {
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int z=0;z<dots;z++) {
				if (z ==0) {
					g.drawImage(head, x[z], y[z], this);
				}else {
					g.drawImage(ball, x[z], y[z], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}else {
			gameOver(g);
		}
	}
	
	private void gameOver(Graphics g) {
		String msg = "Game Over "+dots;
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}
	
	private void checkApple() {
		if((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			locateApple();
		}
	}
	
	private int change_y = 0;
	private boolean go_up = false;
	
	private void move() {
		//check collision
		for(int z = dots; z>0;z--) {
			if(z>4 && x[0]==x[z] && y[0]==y[z]) {
				inGame = false;
			}
		}
		
		if(y[0] >= B_HEIGHT)
			inGame = false;
		if(y[0] < 0)
			inGame = false;
		if(x[0] >= B_WIDTH)
			inGame = false;
		if(x[0] < 0)
			inGame = false;
		
		if(!inGame) {
			System.out.println("Exit From Game ----------------------------dots size = "+dots);
			timer.stop();
		}
		
		//move
		for(int z = dots; z>0;z--) {
			x[z] = x[z-1];
			y[z] = y[z-1];
		}
		
		for(int i=0; i<1;i++) {
			if(apple_y > y[0]) {
				go_up = true;
			}else if(apple_y < y[0]) {
				go_up = false;
			}
			if(x[0] < 290 && change_y == 0) {
				x[0] += DOT_SIZE;
				if(x[0] == 290) change_y = 1;
				break;
			}
			if(go_up) {
				if(change_y == 1) {
					y[0] += DOT_SIZE;
					if(apple_y == y[0])change_y = 2;
					break;
				}
				if(change_y == 2) {
					x[0] -= DOT_SIZE;
					if(x[0] == 0)  change_y = 3;
					break;
				}
				if(change_y == 3) {
					y[0] += DOT_SIZE;
					change_y = 4;
					break;
				}
				if(change_y == 4) {
					x[0] += DOT_SIZE;
					change_y = 0;
					break;
				}
			}
			if(!go_up) {
				if(change_y == 1) {
					y[0] -= DOT_SIZE;
					if(apple_y == y[0])change_y = 2;
					break;
				}
				if(change_y == 2) {
					x[0] -= DOT_SIZE;
					if(x[0] == 0)  change_y = 3;
					break;
				}
				if(change_y == 3) {
					y[0] -= DOT_SIZE;
					change_y = 4;
					break;
				}
				if(change_y == 4) {
					x[0] += DOT_SIZE;
					change_y = 0;
					break;
				}
			}
		}
	}
	
	/*private void move() {
		//check collision
		for(int z = dots; z>0;z--) {
			if(z>4 && x[0]==x[z] && y[0]==y[z]) {
				inGame = false;
			}
		}
		
		if(y[0] >= B_HEIGHT)
			inGame = false;
		if(y[0] < 0)
			inGame = false;
		if(x[0] >= B_WIDTH)
			inGame = false;
		if(x[0] < 0)
			inGame = false;
		
		if(!inGame) {
			System.out.println("Exit From Game ----------------------------dots size = "+dots);
			timer.stop();
		}
		
		//move
		for(int z = dots; z>0;z--) {
			x[z] = x[z-1];
			y[z] = y[z-1];
		}
		
		int distance_x = apple_x - x[0];
		int distance_y = apple_y - y[0];
		distance_x = distance_x > 0 ? distance_x : (distance_x * -1);
		distance_y = distance_y > 0 ? distance_y : (distance_y * -1);
		boolean go_towards_x = distance_x >= distance_y ? true : false;
		
		for(int i=0; i<1;i++) {
			if(apple_x < x[0]) {
				x[0] -= DOT_SIZE;
				break;
			}
			if(apple_x > x[0]) {
				x[0] += DOT_SIZE;
				break;
			}
			
			if(apple_y < y[0]) {
				y[0] -= DOT_SIZE;
				break;
			}
			
			if(apple_y > y[0]) {
				y[0] += DOT_SIZE;
				break;
			}
			if(apple_y > y[0]) {
				go_up = true;
			}else if(apple_y < y[0]) {
				go_up = false;
			}
			if(x[0] < 290 && change_y == 0) {
				x[0] += DOT_SIZE;
				if(x[0] == 290) change_y = 1;
				break;
			}
			if(go_up) {
				if(change_y == 1) {
					y[0] += DOT_SIZE;
					change_y = 2;
					break;
				}
				if(change_y == 2) {
					x[0] -= DOT_SIZE;
					if(x[0] == 0)  change_y = 3;
					break;
				}
				if(change_y == 3) {
					y[0] += DOT_SIZE;
					change_y = 4;
					break;
				}
				if(change_y == 4) {
					x[0] += DOT_SIZE;
					change_y = 0;
					break;
				}
			}
			if(!go_up) {
				if(change_y == 1) {
					y[0] -= DOT_SIZE;
					change_y = 2;
					break;
				}
				if(change_y == 2) {
					x[0] -= DOT_SIZE;
					if(x[0] == 0)  change_y = 3;
					break;
				}
				if(change_y == 3) {
					y[0] -= DOT_SIZE;
					change_y = 4;
					break;
				}
				if(change_y == 4) {
					x[0] += DOT_SIZE;
					change_y = 0;
					break;
				}
			}
		}
		
		
		
		if(leftDirection) {
			x[0] -= DOT_SIZE;
		}
		
		if(rightDirection) {
			x[0] += DOT_SIZE;
		}
		
		if(upDirection) {
			y[0] -= DOT_SIZE;
		}
		
		if(downDirection) {
			y[0] += DOT_SIZE;
		}
	}*/
	
	/*private void checkCollision() {
		for(int z = dots; z>0;z--) {
			if(z>4 && x[0]==x[z] && y[0]==y[z]) {
				inGame = false;
			}
		}
		
		if(y[0] >= B_HEIGHT)
			inGame = false;
		if(y[0] < 0)
			inGame = false;
		if(x[0] >= B_WIDTH)
			inGame = false;
		if(x[0] < 0)
			inGame = false;
		
		if(!inGame) {
			System.out.println("Exit From Game ----------------------------dots size = "+dots);
			timer.stop();
		}
	}*/
	
	private void locateApple() {
		int r = (int) (Math.random() * RAND_POS);
		apple_x = r * DOT_SIZE;
		
		r = (int) (Math.random() * RAND_POS);
		apple_y = r * DOT_SIZE;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(inGame) {
			checkApple();
			//checkCollision();
			move();
		}
		
		repaint();
				
	}
	
	private class TAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT && !rightDirection) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if(key == KeyEvent.VK_RIGHT && !leftDirection) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if(key == KeyEvent.VK_UP && !downDirection) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
			
			if(key == KeyEvent.VK_DOWN && !upDirection) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
	}
	
}
