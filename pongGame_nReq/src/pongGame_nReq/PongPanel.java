/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  Version: 0.5
 */
package pongGame_nReq;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SecondaryLoop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -1097341635155021546L;
	PlaySound f = new PlaySound();
	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;
	private boolean settings;
	private SettingsPanel Settings;
	private SettingsWindow w = new SettingsWindow();
	private JDialog dlg;
	/** Background. */
	private Color backgroundColor = Color.BLACK;
	private ImageIcon imgBackground = new ImageIcon("assets\\main_bg.png");
	private ImageIcon title = new ImageIcon("assets\\title.png");
	private ImageIcon imgPlayingBackground = new ImageIcon("assets\\background.jpg");
	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 350;
	private int ballY = 350;
	private int diameter = 30;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;
	private ImageIcon imgBall1 = new ImageIcon("assets\\ball1.png");
	private ImageIcon imgBall2 = new ImageIcon("assets\\ball2.png");
	private ImageIcon imgBall3 = new ImageIcon("assets\\ball3.png");
	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 80;
	private ImageIcon imgPad1_1 = new ImageIcon("assets\\paddle1_1.png");
	private ImageIcon imgPad2_1 = new ImageIcon("assets\\paddle2_1.png");
	/** Player 2's paddle: position and size */
	private int playerTwoX = 473;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 80;
	private ImageIcon imgPad1_2 = new ImageIcon("assets\\paddle1_2.png");
	private ImageIcon imgPad2_2 = new ImageIcon("assets\\paddle2_2.png");

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;

	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);
		// listen to key presses
		setFocusable(true);
		addKeyListener(this);
		f.playSound("sound\\start.wav");
		// call step() 60 fps
		Timer timer = new Timer(1000 / 60,this);
		timer.start();
	}
	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();		
	}
	
	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY +diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				f.playSound("sound\\ping.wav");
				ballDeltaY *= -1;
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				if (nextBallTop < playerOneBottom && nextBallBottom > playerOneTop){
					f.playSound("sound\\ping.wav");	
				}
				
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
					f.playSound("sound\\score.wav");	
					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == Settings.getMaxPoint()) {
						playing = false;
						gameOver = true;
					}
					ballX = 215;
					ballY = 215;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				if (nextBallTop < playerTwoBottom && nextBallBottom > playerTwoTop){
					f.playSound("sound\\ping.wav");
				}
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
					f.playSound("sound\\score.wav");
					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == Settings.getMaxPoint()) {
						playing = false;
						gameOver = true;
					}
					ballX = 215;
					ballY = 215;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {
			
			/* Show welcome screen */
			g.drawImage(imgBackground.getImage(), 0, 0, 500,500,null);		
			// Draw game title and start message
			g.setColor(Color.WHITE);
			g.drawImage(title.getImage(), 90, 60, 300, 80,null);
			// FIXME Wellcome message below show smaller than game title
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			
			g.drawString("Press 'P' to play.", 155, 400);
			g.drawString("Press 'S' to settings.", 155, 425);
			
			//Reset score
			playerOneScore = 0;
			playerTwoScore = 0;

		} else if (playing) {

			/* Game is playing */
			Settings = w.getSetings();
			g.drawImage(imgPlayingBackground.getImage(), 0, 0, 500, 500, null);
			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			g.setColor(Color.WHITE);
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(245, lineY, 245, lineY + 25);
			}
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());
			// draw player name
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			g.drawString(Settings.getUserName1(), 80, 160);
			g.drawString(Settings.getUserName2(), 380, 160);
			// draw the scores
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 400, 100); // Player 2
			
			// draw the ball
			if (Settings.getBallNumber() == 1) {
				g.drawImage(imgBall1.getImage(), ballX, ballY, diameter, diameter,null);
			}else if (Settings.getBallNumber() == 2) {
				g.drawImage(imgBall2.getImage(), ballX, ballY, diameter, diameter,null);
			}else if (Settings.getBallNumber() == 3) {
				g.drawImage(imgBall3.getImage(), ballX, ballY, diameter, diameter,null);
			}else if (Settings.getBallNumber() == 4) {
				g.setColor(Color.RED);
				g.fillOval( ballX, ballY, diameter, diameter);
			}
			Random rand = new Random();
			// draw the paddles
			if (Settings.getPaddleStyle() == 1) {
				g.drawImage(imgPad1_1.getImage(),playerTwoX-1, playerTwoY, playerTwoWidth+3, playerTwoHeight,null);
				g.drawImage(imgPad1_2.getImage(),playerOneX-2, playerOneY, playerOneWidth+3, playerOneHeight,null);
			}else if (Settings.getPaddleStyle() == 2) {
				g.drawImage(imgPad2_1.getImage(),playerTwoX-1, playerTwoY, playerTwoWidth+3, playerTwoHeight,null);
				g.drawImage(imgPad2_2.getImage(),playerOneX-2, playerOneY, playerOneWidth+3, playerOneHeight,null);
			}else if (Settings.getPaddleStyle() == 3) {
				g.setColor(Color.BLUE);
				g.fillRect(playerTwoX-1, playerTwoY, playerTwoWidth+3, playerTwoHeight);
				g.setColor(Color.RED);
				g.fillRect(playerOneX-2, playerOneY, playerOneWidth+3, playerOneHeight);
			}else if (Settings.getPaddleStyle() == 4) {
				g.setColor(Color.MAGENTA);
				g.fillRect(playerTwoX-1, playerTwoY, playerTwoWidth+3, playerTwoHeight);
				g.setColor(Color.CYAN);
				g.fillRect(playerOneX-2, playerOneY, playerOneWidth+3, playerOneHeight);
			}
			
			
		} else if (gameOver) {
			/* Show End game screen with winner name and score */
			// Draw scores
			// TODO Set Blue color
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 400, 100);

			// Draw the winner name
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.drawString(Settings.getUserName1() +" Wins!", 165, 200);
			} else {
				g.drawString(Settings.getUserName2() + " Wins!", 165, 200);
			}

			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
			g.drawString("Press 'Spacebar' to restart", 175, 400);
			//f.playSound("sound\\end.wav");
			// TODO Draw a restart message
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				showTitleScreen = false;
				settings = false;
				playing = true;
			}else if (e.getKeyCode() == KeyEvent.VK_S) {
				w.setLocationRelativeTo(PongPanel.this);
				w.setVisible(true);
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 250;
			playerTwoY = 250;
			ballX = 250;
			ballY = 250;
			
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

}