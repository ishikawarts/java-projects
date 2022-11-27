package jp.co.rootship.reversi.exception;

import java.text.MessageFormat;

import jp.co.rootship.reversi.board.Position;

/**
 * 盤外を参照しようとした際にスローする例外クラス
 */
public class OutsideBoardException extends RuntimeException {

	/**
	 * コンストラクタ
	 * @param position 盤上の位置
	 */
	public OutsideBoardException(Position position) {
		this(position.x(), position.y());
	}

	/**
	 * コンストラクタ
	 * @param x x座標
	 * @param y y座標
	 */
	public OutsideBoardException(int x, int y) {
		super(MessageFormat.format("(x, y) = ({0},{1}) is outside the board.", x, y));
	}

}
