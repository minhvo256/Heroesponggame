package pongGame_nReq;

import java.awt.Color;
/**
 * 
 * @author Khoa Dao
 *
 */
public class SettingsPanel {
	private String userName1, userName2;
	private Color backgroundColor, ballColor;
	private int paddleColor;
	private int ballNumber;
	private int iMaxPoint;
	public SettingsPanel(){	}
	
	public SettingsPanel(String userName1, String userName2, Color backgroundColor, int paddleColor, Color ballColor) {
		super();
		this.userName1 = userName1;
		this.userName2 = userName2;
		this.backgroundColor = backgroundColor;
		this.paddleColor = paddleColor;
		this.ballColor = ballColor;
	}

	public SettingsPanel(String u1, String u2) {
		userName1 = u1;
		userName2 = u2;
	}

	public int getBallNumber() {
		return ballNumber;
	}

	public void setBallNumber(int ballNumber) {
		this.ballNumber = ballNumber;
	}
	public int getMaxPoint() {
		return iMaxPoint;
	}

	public void setMaxPoint(int iMaxPoint) {
		this.iMaxPoint = iMaxPoint;
	}
	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getPaddleStyle() {
		return paddleColor;
	}

	public void setPaddleStyle(int paddleColor) {
		this.paddleColor = paddleColor;
	}

	public Color getBallColor() {
		return ballColor;
	}

	public void setBallColor(Color ballColor) {
		this.ballColor = ballColor;
	}

	public String getUserName1() {
		return userName1;
	}

	public void setUserName1(String uname) {
		userName1 = uname;
	}

}
