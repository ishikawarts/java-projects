package jp.co.rootship.reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

/**
 * Reversiのウィンドウを作るクラス
 */
public class ReversiFrame extends JFrame {
	/** 盤面を描画するパネル */
	private ReversiPanel reversiPanel;
	/** 進行状況を表示するテキストフィールド */
	private JTextField messageArea;

	/**
	 * エントリポイント
	 * @param args 引数
	 */
	public static void main(String[] args) {

		var reversiFrame = new ReversiFrame();

		reversiFrame.setVisible(true);

	}

	/**
	 * コンストラクタ
	 */
	public ReversiFrame() {

		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		super.setSize(new Dimension(820, 885));

		this.messageArea = new JTextField();

		this.messageArea.setEnabled(false);

		this.messageArea.setDisabledTextColor(Color.BLACK);

		this.reversiPanel = new ReversiPanel(this.messageArea);

		super.add(this.reversiPanel, BorderLayout.CENTER);

		super.add(this.messageArea, BorderLayout.SOUTH);

		super.setJMenuBar(createMenuBar());

	}

	/**
	 * メニューバーを作成する
	 * @return メニューバー
	 */
	public JMenuBar createMenuBar() {
		var menuBar = new JMenuBar();

		var reset = new JButton("RESET");
		reset.addActionListener(e -> this.reversiPanel.reset());
		reset.setBackground(Color.WHITE);

//		var previous = new JButton("<");
//		previous.addActionListener(e -> this.reversiPanel.previous());
//		previous.setBackground(Color.WHITE);
//
//		var next = new JButton(">");
//		next.addActionListener(e -> this.reversiPanel.next());
//		next.setBackground(Color.WHITE);

		menuBar.add(reset);
//		menuBar.add(previous);
//		menuBar.add(next);

		return menuBar;

	}

}
