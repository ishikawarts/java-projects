package jp.co.rootship.reversi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.JTextField;

import jp.co.rootship.reversi.board.Board;
import jp.co.rootship.reversi.board.Position;
import jp.co.rootship.reversi.board.Stone;

/**
 * Reversiの盤面を描画するパネルクラス
 */
public class ReversiPanel extends JPanel implements MouseListener {
	/** 1マスの大きさ */
	private static int CELL_SIZE = Environment.BOARD_SIZE / 8;

	/** 石の大きさ */
	private static int STONE_SIZE = Environment.BOARD_SIZE / 8 - 4;

	/** 進行状況を表示するテキストフィールド */
	private JTextField messageField = new JTextField();

	/** 盤面 */
	private Board board = new Board();

	/** ターンプレイヤー */
	private int turnPlayer = Stone.BLACK;

	/** プレイ履歴 */
	private List<PlayLog> history = new ArrayList<>();

	/** 参照している履歴の要素番号 */
	private int historyIndex = 0;

	/** ゲームが終了している場合にtrue */
	private boolean isFinished = false;

	/**
	 * コンストラクタ
	 * @param messageField 進行状況を表示するテキストフィールド
	 */
	public ReversiPanel(JTextField messageField) {

		super.setBackground(Color.LIGHT_GRAY);

		super.addMouseListener(this);

		this.messageField = messageField;

		this.messageField.setText(String.join(" ", getCountStoneText(), getTurnPlayerText()));

		this.history.add(new PlayLog(this.board.getBoard(), this.turnPlayer));

		this.historyIndex = 0;
	}

	/**
	 * 進行状況をリセットする
	 */
	public void resetGame() {
		this.board = new Board();
		repaint();
	}

	/**
	 * 盤面を描画する
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(new Color(47, 130, 80));
		g2.fillRect(0, 0, Environment.BOARD_SIZE, Environment.BOARD_SIZE);

		g2.setColor(Color.BLACK);

		IntStream.rangeClosed(0, 8).forEach(i -> {
			g2.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, Environment.BOARD_SIZE);
			g2.drawLine(0, i * CELL_SIZE, Environment.BOARD_SIZE, i * CELL_SIZE);
		});

		g2.setColor(new Color(255, 255, 100));

		board.getEnablePositions(this.turnPlayer)
				.forEach(position -> {
					g2.fillRect(position.x() * CELL_SIZE + 1, position.y() * CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);

				});

		IntStream.rangeClosed(0, 63)
				.mapToObj(i -> new Position(i / 8, i % 8))
				.filter(position -> !this.board.isEmptyPosition(position))
				.forEach(position -> {
					var stoneColor = this.board.lookStone(position) == Stone.BLACK ? Color.BLACK : Color.WHITE;
					g2.setColor(stoneColor);
					g2.fillOval(position.x() * CELL_SIZE + 2, position.y() * CELL_SIZE + 2, STONE_SIZE, STONE_SIZE);
				});

	}

	/**
	 * マウスクリックされた際に実行される
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		if (isFinished)
			return;

		int x = e.getPoint().x / CELL_SIZE;
		int y = e.getPoint().y / CELL_SIZE;

		if (this.board.putStone(x, y, this.turnPlayer)) {

			nextTurn();

		} else {
			this.messageField.setText(String.join(" ", getCountStoneText(), getTurnPlayerText(), "そこに石を置くことはできません。"));
		}

		repaint();

	}

	/**
	 * 次のターンへ移行する
	 */
	private void nextTurn() {

		judge();

		if (isFinished)
			return;

		int nextTurnPlayer = Stone.opposite(this.turnPlayer);

		if (this.board.hasEnablePosition(nextTurnPlayer)) {
			this.turnPlayer = nextTurnPlayer;
		}

		if (this.historyIndex < this.history.size())
			this.history = this.history.stream().limit(this.historyIndex).collect(Collectors.toList());

		this.history.add(new PlayLog(this.board.getBoard(), this.turnPlayer));

		this.historyIndex++;

		this.messageField.setText(String.join(" ", getCountStoneText(), getTurnPlayerText()));

	}

	/**
	 * 勝敗判定する
	 */
	private void judge() {

		boolean canPutBlack = this.board.hasEnablePosition(Stone.BLACK);

		boolean canPutWhite = this.board.hasEnablePosition(Stone.WHITE);

		if (!canPutBlack && !canPutWhite) {

			long countBlack = this.board.countBlack();

			long countWhite = this.board.countWhite();

			String result;
			if (countBlack > countWhite)
				result = "黒の勝利!!";
			else if (countBlack < countWhite)
				result = "白の勝利!!";
			else
				result = "引き分け";

			this.messageField.setText(String.join(" ", getCountStoneText(), result));

			this.isFinished = true;

		}
	}

	/**
	 * 石の数が書かれたテキストを返す
	 * @return 石の数が書かれたテキスト
	 */
	private String getCountStoneText() {

		return MessageFormat.format("黒 : {0} 白 : {1}", this.board.countBlack(), this.board.countWhite());
	}

	/**
	 * ターンプレイヤーが書かれたテキストを返す
	 * @return ターンプレイヤーが書かれたテキスト
	 */
	private String getTurnPlayerText() {
		String turnColor = turnPlayer == Stone.BLACK ? "黒" : "白";

		return MessageFormat.format("現在 {0} のターンです。 ", turnColor);
	}

	/**
	 * 進行状況をリセットする
	 */
	public void reset() {

		this.isFinished = false;

		this.board = new Board();

		this.turnPlayer = Stone.BLACK;

		messageField.setText(String.join(" ", getCountStoneText(), getTurnPlayerText()));

		this.history.clear();

		this.history.add(new PlayLog(this.board.getBoard(), this.turnPlayer));

		this.historyIndex = 0;

		repaint();

	}

	/**
	 * 一手前の状態を復元する
	 */
	public void previous() {

		if (this.historyIndex > 0)
			this.historyIndex--;

		restore();

	}

	/**
	 * 一手後の状態を復元する
	 */
	public void next() {

		if (this.historyIndex < this.history.size())
			this.historyIndex++;

		restore();
	}

	/**
	 * 盤面を履歴から復元する
	 */
	private void restore() {
		PlayLog log = this.history.get(this.historyIndex);

		this.board.setBoard(log.board());

		this.turnPlayer = log.turnPlayer();

		this.messageField.setText(getTurnPlayerText());

		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 未使用
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// 未使用
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 未使用
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 未使用
	}

}
