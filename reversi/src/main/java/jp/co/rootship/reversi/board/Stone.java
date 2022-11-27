package jp.co.rootship.reversi.board;

/**
 * Reversiの石を表すクラス
 */
public class Stone {
	/** 石がない状態 */
	public static final int NONE = 0;
	/** 黒 */
	public static final int BLACK = 1;
	/** 白 */
	public static final int WHITE = 2;
	
	/**
	 * 石の反対の色を返す。<br>
	 * ただし、stoneがNONEの場合はNONEを返す。
	 * @param stone 石
	 * @return 石の反対の色を返す。
	 */
	public static int opposite(int stone) {
		
		return stone != NONE ? 3 - stone : NONE;

	}

}
