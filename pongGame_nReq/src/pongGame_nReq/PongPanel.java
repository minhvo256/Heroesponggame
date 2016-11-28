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
	JMenuBar mnBar;
	JMenu mnOption, mnHelp;
	JMenuItem mniSettings, mniAbout;
	private JDialog dlg;
	/** Background. */
	private Color backgroundColor = Color.BLACK;
	private ImageIcon img2 = new ImageIcon("image\\main_bg.png");
	private ImageIcon title = new ImageIcon("image\\title.png");
	private ImageIcon img3 = new ImageIcon("image\\background.jpg");
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
	private ImageIcon img6 = new ImageIcon("image\\ball.png");
	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 80;
	private ImageIcon img1 = new ImageIcon("image\\Paddle_1.png");

	/** Player 2's paddle: position and size */
	private int playerTwoX = 473;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 80;
	private ImageIcon img = new ImageIcon("image\\Paddle_2.png");
	

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

		// call step() 60 fps
		Timer timer = new Timer(1000 / 60, this);
		timer.start();
	}
	//public void initializeMenu() {
	//	mnBar = new JMenuBar();
	//	mnOption = new JMenu("Mode");
	//	mnHelp = new JMenu("Help");
	//	mniSettings = new JMenuItem("Settings");
	//	mniAbout = new JMenuItem("About");
		// add submn to ViewOption
	//	mnOption.add(mniSettings);
		// add submn to HelpOption
	//	mnHelp.add(mniAbout);
		// add menu to Menu bar
	//	mnBar.add(mnOption);
	//	mnBar.add(mnHelp);
		// Set hotkey for MenuItem
	//	mniSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		// Set icon for MenuItem
		// mniStand.setIcon(imCaculator);
		// mniAbout.setIcon(imAbout);
	//	setJMenuBar(mnBar);
	//}
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
				try {
					f.playSound("sound\\ping.wav");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ballDeltaY *= -1;
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				try {
					f.playSound("sound\\ping.wav");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
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
				try {
					f.playSound("sound\\ping.wav");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
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
			g.drawImage(img2.getImage(), 0, 0, 500,500,null);		
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
			g.drawImage(img3.getImage(), 0, 0, 500, 500, null);
			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			g.setColor(Color.darkGray);
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(240, lineY, 240, lineY + 25);
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
			g.setColor(Color.RED);
			//g.fillOval(ballX, ballY, diameter, diameter);
			g.drawImage(img6.getImage(), ballX, ballY, diameter, diameter,null);
			Random rand = new Random();
			// draw the paddles
			g.drawImage(img.getImage(),playerTwoX-1, playerTwoY, playerTwoWidth+3, playerTwoHeight,null);
			g.drawImage(img1.getImage(),playerOneX-2, playerOneY, playerOneWidth+3, playerOneHeight,null);
			
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
				SettingsWindow w = new SettingsWindow();
				w.setLocationRelativeTo(PongPanel.this);
				w.setVisible(true);
				Settings = w.getSetings();
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