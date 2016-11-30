package pongGame_nReq;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Khoa Dao
 *
 */
@SuppressWarnings("serial")
public class SettingsWindow extends JDialog{
	private JTextField txtUsername1;
	private JTextField txtUsername2;
	private ImageIcon imgBall1 = new ImageIcon("assets\\ball1.png");
	private ImageIcon imgBall2 = new ImageIcon("assets\\ball2.png");
	private ImageIcon imgBall3 = new ImageIcon("assets\\ball3.png");
	private ImageIcon imgBall1_pre = new ImageIcon("assets\\ball1_thumb.png");
	private ImageIcon imgBall2_pre = new ImageIcon("assets\\ball2_thumb.png");
	private ImageIcon imgBall3_pre = new ImageIcon("assets\\ball3_thumb.png");
	private JPanel pnlBall = new JPanel();
	private JPanel pnlPaddle = new JPanel();
	private JRadioButton optBall1 = new JRadioButton("Pokeball"),
			 optBall2 = new JRadioButton("Football"),
			 optBall3 = new JRadioButton("ChoiLoaBall"),
			 optBall4 = new JRadioButton("Solid color");
	private JRadioButton optPad1 = new JRadioButton(),
			 optPad2 = new JRadioButton(),
			 optPad3 = new JRadioButton(),
			 optPad4 = new JRadioButton();
	private	ButtonGroup btgBall = new ButtonGroup();
	private	ButtonGroup btgPaddle = new ButtonGroup();
	private Color clBackground;
	//Xem khai bao MyDialogResult o cuoi class nay
	public MyDialogResult dialogResult;
	
	public SettingsWindow() {
		setPreferredSize(new Dimension(500, 500));
		getContentPane().setLayout(null);
		setModal(true);
		this.setFocusable(true);
		this.requestFocus();
		JLabel lbl2ImageBall1 = new JLabel(imgBall1_pre);
		getContentPane().add(lbl2ImageBall1);
		lbl2ImageBall1.setBounds(145, 180, 45, 45);
		JLabel lbl2ImageBall2 = new JLabel(imgBall2_pre);
		getContentPane().add(lbl2ImageBall2);
		lbl2ImageBall2.setBounds(210, 180, 45, 45);
		JLabel lbl2ImageBall3 = new JLabel(imgBall3_pre);
		getContentPane().add(lbl2ImageBall3);
		lbl2ImageBall3.setBounds(275, 180, 45, 45);
		dialogResult = MyDialogResult.DEFAULT;
		txtUsername1 = new JTextField(10);
		txtUsername2 = new JTextField(10);
		getContentPane().add(txtUsername1);
		getContentPane().add(txtUsername2);
		getContentPane().setBackground(Color.BLACK);
		txtUsername1.setBounds(90, 65, 120, 25);
		txtUsername2.setBounds(90, 107, 120, 25);
		txtUsername1.requestFocus(false);
		txtUsername1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		txtUsername2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		txtUsername1.setBackground(Color.BLACK);
		txtUsername2.setBackground(Color.BLACK);
		txtUsername1.setCaretColor(Color.LIGHT_GRAY);
		txtUsername2.setCaretColor(Color.LIGHT_GRAY);
		txtUsername1.setForeground(Color.LIGHT_GRAY);
		txtUsername2.setForeground(Color.LIGHT_GRAY);
		txtUsername1.setText("Player 1");
		txtUsername2.setText("Player 2");
		// Limit user name only containt 15 digit
		txtUsername1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtUsername1.getText().length() >= 15) {
					e.consume();
				}
			}
		});
		txtUsername2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtUsername1.getText().length() >= 15) {
					e.consume();
				}
			}
		});
		// Descriptions
		JLabel lblUser_1 = new JLabel("Player 1: ");
		lblUser_1.setBounds(10, 70, 71, 14);
		lblUser_1.setForeground(Color.WHITE);
		getContentPane().add(lblUser_1);
		
		JLabel lblUser_2 = new JLabel("Player 2:");
		lblUser_2.setBounds(10, 112, 71, 14);
		lblUser_2.setForeground(Color.WHITE);
		getContentPane().add(lblUser_2);
		
		JLabel lblTitle = new JLabel("Settings");
		lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(10, 10, 150, 36);
		getContentPane().add(lblTitle);
		
		// Allow user can choose the background
		JLabel lblsetBG = new JLabel("Choose the background: ");
		lblsetBG.setBounds(10, 310, 160, 25);
		lblsetBG.setForeground(Color.WHITE);
		getContentPane().add(lblsetBG);
		JButton btnBackground = new JButton("...");
		btnBackground.setBounds(175, 313, 50, 23);
		btnBackground.setForeground(Color.WHITE);
		btnBackground.setFocusPainted(false);
		btnBackground.setBackground(Color.BLACK);
		getContentPane().add(btnBackground);
		btnBackground.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chooseColor();
			}
		});
		// Allow user can choose the paddle
		JLabel lblsetPaddle = new JLabel("Choose the paddle: ");
		lblsetPaddle.setBounds(10, 230, 155, 25);
		lblsetPaddle.setForeground(Color.WHITE);
		getContentPane().add(lblsetPaddle);
		// Paddle panel
		btgPaddle.add(optPad1);
		btgPaddle.add(optPad2);
		btgPaddle.add(optPad3);
		btgPaddle.add(optPad4);
		pnlPaddle.setLayout(null);
		pnlPaddle.add(optPad1);
		pnlPaddle.add(optPad2);
		pnlPaddle.add(optPad3);
		pnlPaddle.add(optPad4);
		pnlPaddle.setBounds(125, 230, 350, 25);
		pnlPaddle.setBackground(Color.BLACK);
		optPad1.setBounds(30, 3, 20, 20);
		optPad1.setFocusPainted(false);
		optPad1.setBackground(Color.BLACK);
		optPad1.setForeground(Color.WHITE);
		optPad2.setBounds(95, 3, 20, 20);
		optPad2.setFocusPainted(false);
		optPad2.setBackground(Color.BLACK);
		optPad2.setForeground(Color.WHITE);
		optPad3.setBounds(160, 3, 20, 20);
		optPad3.setFocusPainted(false);
		optPad3.setBackground(Color.BLACK);
		optPad3.setForeground(Color.WHITE);
		optPad4.setBounds(225, 3, 20, 20);
		optPad4.setFocusPainted(false);
		optPad4.setBackground(Color.BLACK);
		optPad4.setForeground(Color.WHITE);
		getContentPane().add(pnlPaddle);
		// Allow user can choose the ball
		JLabel lblsetBall = new JLabel("Choose the ball: ");
		lblsetBall.setBounds(10, 145, 150, 25);
		lblsetBall.setForeground(Color.WHITE);
		getContentPane().add(lblsetBall);
		// Ball panel
		btgBall.add(optBall1);
		btgBall.add(optBall2);
		btgBall.add(optBall3);
		btgBall.add(optBall4);
		pnlBall.setLayout(null);
		pnlBall.add(optBall1);
		pnlBall.add(optBall2);
		pnlBall.add(optBall3);
		pnlBall.add(optBall4);
		pnlBall.setBounds(125, 145, 350, 25);
		pnlBall.setBackground(Color.BLACK);
		optBall1.setBounds(30, 3, 20, 20);
		optBall1.setFocusPainted(false);
		optBall1.setBackground(Color.BLACK);
		optBall1.setForeground(Color.WHITE);
		optBall2.setBounds(95, 3, 20, 20);
		optBall2.setFocusPainted(false);
		optBall2.setBackground(Color.BLACK);
		optBall2.setForeground(Color.WHITE);
		optBall3.setBounds(160, 3, 20, 20);
		optBall3.setFocusPainted(false);
		optBall3.setBackground(Color.BLACK);
		optBall3.setForeground(Color.WHITE);
		optBall4.setBounds(225, 3, 20, 20);
		optBall4.setFocusPainted(false);
		optBall4.setBackground(Color.BLACK);
		optBall4.setForeground(Color.WHITE);
		getContentPane().add(pnlBall);
		// Declare and add function to buttons
		JButton btnSave = new JButton("SAVE");
		JButton btnCancel = new JButton("CANCEL");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogResult = MyDialogResult.YES;
				setVisible(false);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogResult = MyDialogResult.CANCEL;
				setVisible(false);
			}
		});
		btnSave.setBounds(280, 420, 89, 23);
		getContentPane().add(btnSave);
		btnSave.setBackground(Color.BLACK);
		btnSave.setForeground(Color.WHITE);
		btnSave.setFocusPainted(false);
		btnCancel.setBounds(380, 420, 89, 23);
		btnCancel.setBackground(Color.black);
		btnCancel.setForeground(Color.white);
		btnCancel.setFocusPainted(false);
		getContentPane().add(btnCancel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int result = JOptionPane.showConfirmDialog(SettingsWindow.this, "Exit?");
				if(result == JOptionPane.YES_OPTION){
					setVisible(false);
				}				
			}
		});
	}
	public void chooseColor() {
		clBackground = JColorChooser.showDialog(this, "Choose background color", Color.BLACK);
	}
	public SettingsPanel getSetings(){
		SettingsPanel st = new SettingsPanel();
		st.setUserName1(txtUsername1.getText());
		st.setUserName2(txtUsername2.getText());
		if (optBall1.isSelected()) {
			st.setBallNumber(1);
		}else if (optBall2.isSelected()){
			st.setBallNumber(2);
		}else if (optBall3.isSelected()){
			st.setBallNumber(3);
		}else if (optBall4.isSelected()){
			st.setBallNumber(4);
		}
		if (optPad1.isSelected()) {
			st.setPaddleStyle(1);
		}else if (optPad2.isSelected()) {
			st.setPaddleStyle(2);
		}else if (optPad3.isSelected()) {
			st.setPaddleStyle(3);
		}else if (optPad4.isSelected()) {
			st.setPaddleStyle(4);
		}
		return st;
	}
}



