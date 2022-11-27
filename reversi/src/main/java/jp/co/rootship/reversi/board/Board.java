package jp.co.rootship.reversi.board;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jp.co.rootship.reversi.exception.OutsideBoardException;

/**
 * 盤面を表すクラス
 */
public class Board implements Cloneable {

	/** 周囲8方向を表す基準点からの距離 */
	private static final int[][] ARROUND_OFFSET = {
			{ 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }
	};

	/** 盤面 */
	private int[][] board = {
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 2, 0, 0, 0 },
			{ 0, 0, 0, 2, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
	};

	/**
	 * コンストラクタ
	 */
	public Board() {

	}

	/**
	 * コンストラクタ
	 * @param board 盤面
	 */
	public Board(int[][] board) {
		this.board = board;
	}

	/**
	 * positionが盤外の場合にtrueを返す
	 * @param position 盤上の位置
	 * @return true：盤外、false：盤内
	 */
	private static boolean isOutsideBoard(Position position) {
		return isOutsideBoard(position.x(), position.y());
	}

	/**
	 * 座標x,yが盤外の場合にtrueを返す
	 * @param x x座標
	 * @param y y座標
	 * @return true：盤外、false：盤内
	 */
	private static boolean isOutsideBoard(int x, int y) {
		return x < 0 || x > 7 || y < 0 || y > 7;
	}

	/**
	 * 座標x,yが盤外の場合にOutsideBoardExceptionをスローする
	 * @param x x座標
	 * @param y y座標
	 * @throws OutsideBoardException x,yが盤外の場合にスローされる
	 */
	private static void throwExceptionIfPositionIsOutsideBoard(int x, int y) throws OutsideBoardException {
		if (isOutsideBoard(x, y)) {
			throw new OutsideBoardException(x, y);
		}
	}

	/**
	 * 指定した位置にある石を取得する
	 * @param position 盤上の位置
	 * @return 指定した位置の石
	 * @throws OutsideBoardException x,yが盤外の場合にスローされる
	 */
	public int lookStone(Position position) throws OutsideBoardException {
		return lookStone(position.x(), position.y());
	}

	/**
	 * 指定した位置にある石を取得する
	 * @param x x座標
	 * @param y y座標
	 * @return 指定した位置の石
	 * @throws OutsideBoardException x,yが盤外の場合にスローされる
	 */
	public int lookStone(int x, int y) throws OutsideBoardException {

		throwExceptionIfPositionIsOutsideBoard(x, y);

		return this.board[x][y];
	}

	/**
	 * 石を置ける場所がある場合にtrueを返す
	 * @param mine 自分の石
	 * @return true：石を置ける場所がある、false：石を置ける場所がない
	 */
	public boolean hasEnablePosition(int mine) {
		return getEnablePositions(mine).count() > 0;
	}

	/**
	 * 石を置ける場所のStreamを返す
	 * @param mine 自分の石
	 * @return　石を置ける場所のStream
	 */
	public Stream<Position> getEnablePositions(int mine) {
		return IntStream.rangeClosed(0, 63)
				.mapToObj(i -> new Position(i / 8, i % 8))
				.filter(position -> isEnablePosition(position, mine));
	}

	/**
	 * 指定した位置に石を置ける場合にtrueを返す
	 * @param position 盤上の位置
	 * @param mine 自分の石
	 * @return true:指定した位置に石を置ける、false：指定した位置に石を置けない
	 */
	public boolean isEnablePosition(Position position, int mine) {
		return isEnablePosition(position.x(), position.y(), mine);
	}

	/**
	 * 指定した位置に石を置ける場合にtrueを返す
	 * @param x x座標
	 * @param y y座標
	 * @param mine 自分の石
	 * @return true:指定した位置に石を置ける、false：指定した位置に石を置けない
	 */
	private boolean isEnablePosition(int x, int y, int mine) {

		if (!isEmptyPosition(x, y))
			return false;

		return getDirectionsThatCanReversed(x, y, mine).count() > 0;
	}

	/**
	 * 指定した位置に石を置いた場合に裏返せる石のある方向のStreamを返す
	 * @param position 盤上の位置
	 * @param mine 自分の石
	 * @return 石を置いた際に裏返せる石のある方向のStream
	 */
	public Stream<Position> getDirectionsThatCanReversed(Position position, int mine) {
		return getDirectionsThatCanReversed(position.x(), position.y(), mine);
	}

	/**
	 * 指定した位置に石を置いた場合に裏返せる石のある方向のStreamを返す
	 * @param x x座標
	 * @param y y座標
	 * @param mine 自分の石
	 * @return 石を置いた際に裏返せる石のある方向のStream
	 */
	public Stream<Position> getDirectionsThatCanReversed(int x, int y, int mine) {
		return Stream.of(ARROUND_OFFSET)
				.map(offset -> new Position(offset[0], offset[1]))
				.filter(offset -> !isOutsideBoard(x + offset.x(), y + offset.y()))
				.filter(offset -> isOpponentsStone(x + offset.x(), y + offset.y(), mine))
				.filter(offset -> {
					return IntStream.rangeClosed(2, 7)
							.mapToObj(i -> new Position(x + offset.x() * i, y + offset.y() * i))
							.filter(position -> !isOutsideBoard(position))
							.anyMatch(position -> this.board[position.x()][position.y()] == mine);
				});
	}

	/**
	 * 指定した位置に石を置く
	 * @param position 盤上の位置
	 * @param mine 自分の石
	 * @return true：指定した位置に石を置けた、false：指定した位置に石を置けなかった
	 */
	public boolean putStone(Position position, int mine) {
		return putStone(position.x(), position.y(), mine);
	}

	/**
	 * 指定した位置に石を置く
	 * @param x x座標
	 * @param y y座標
	 * @param mine 自分の石
	 * @return true：指定した位置に石を置けた、false：指定した位置に石を置けなかった
	 */
	public boolean putStone(int x, int y, int mine) {

		if (isOutsideBoard(x, y))
			return false;

		if (isEnablePosition(x, y, mine)) {
			this.board[x][y] = mine;
			reverse(x, y, mine);
			return true;
		}

		return false;
	}

	/**
	 * 石を裏返す
	 * @param x x座標
	 * @param y y座標
	 * @param mine 自分の石
	 */
	private void reverse(int x, int y, int mine) {
		this.getDirectionsThatCanReversed(x, y, mine)
				.forEach(offset -> {
					List<Position> positions = IntStream.rangeClosed(1, 7)
							.mapToObj(i -> new Position(x + offset.x() * i, y + offset.y() * i))
							.filter(position -> !isOutsideBoard(position))
							.toList();

					int minePositionIndex = positions.stream()
							.map(position -> this.board[position.x()][position.y()])
							.toList()
							.indexOf(mine);

					positions.stream()
							.limit(minePositionIndex)
							.forEach(position -> this.board[position.x()][position.y()] = mine);

				});
	}

	/**
	 * 指定した位置に石がない場合にtrueを返す
	 * @param position 盤上の位置
	 * @return true:指定した位置に石がない、false：指定した位置に石がある
	 */
	public boolean isEmptyPosition(Position position) {
		return isEmptyPosition(position.x(), position.y());
	}

	/**
	 * 指定した位置に石がない場合にtrueを返す
	 * @param x x座標
	 * @param y y座標
	 * @return true:指定した位置に石がない、false：指定した位置に石がある
	 */
	public boolean isEmptyPosition(int x, int y) {
		return this.board[x][y] == Stone.NONE;
	}

	/**
	 * 指定した位置に自分の石がある場合にtrueを返す。
	 * @param position 盤上の位置
	 * @param mine 自分の石
	 * @return true：指定した位置に自分の石がある、false：指定した位置に自分の石がない
	 */
	public boolean isMyStone(Position position, int mine) {
		return isMyStone(position.x(), position.y(), mine);
	}

	/**
	 * 指定した位置に自分の石がある場合にtrueを返す。
	 * @param x x座標
	 * @param y y座標
	 * @param mine 自分の石
	 * @return true：指定した位置に自分の石がある、false：指定した位置に自分の石がない
	 */
	public boolean isMyStone(int x, int y, int mine) {
		throwExceptionIfPositionIsOutsideBoard(x, y);
		return this.board[x][y] == mine;
	}

	/**
	 * 指定した位置に相手の石がある場合にtrueを返す。
	 * @param position 盤上の位置
	 * @param mine 自分の石
	 * @return true：指定した位置に相手の石がある、false：指定した位置に相手の石がない
	 */
	public boolean isOpponentsStone(Position position, int mine) {
		return isOpponentsStone(position.x(), position.y(), mine);
	}

	/**
	 * 指定した位置に相手の石がある場合にtrueを返す。
	 * @param x x座標
	 * @param y y座標
	 * @param mine 自分の石
	 * @return true：指定した位置に相手の石がある、false：指定した位置に相手の石がない
	 */
	public boolean isOpponentsStone(int x, int y, int mine) {
		return isMyStone(x, y, Stone.opposite(mine));
	}

	/**
	 * 盤面のコピーをint型二次元配列で取得する
	 * @return int型二次元配列の盤面のコピー
	 */
	public int[][] getBoard() {

		int[][] board = new int[8][8];

		copyBoard(this.board, board);

		return board;
	}

	/**
	 * 盤面を設定する
	 * @param board 盤面
	 */
	public void setBoard(int[][] board) {
		copyBoard(board, this.board);
	}

	/**
	 * 黒の数を返す
	 * @return 黒の数
	 */
	public long countBlack() {
		return countStone(Stone.BLACK);
	}

	/**
	 * 白の数を返す
	 * @return 白の数
	 */
	public long countWhite() {
		return countStone(Stone.WHITE);
	}

	/**
	 * 指定した種類の石の数を返す
	 * @param stone 石
	 * @return 指定した種類の石の数
	 */
	public long countStone(int stone) {
		return IntStream.rangeClosed(0, 63).filter(i -> this.board[i / 8][i % 8] == stone).count();

	}

	/**
	 * このインスタンスの複製を返す
	 */
	@Override
	protected Object clone() {

		int[][] board = new int[8][8];

		copyBoard(this.board, board);

		return new Board(board);
	}

	/**
	 * 盤面をコピーする
	 * @param from コピー元の盤面
	 * @param to コピー先の盤面
	 */
	private static void copyBoard(int[][] from, int[][] to) {

		IntStream.rangeClosed(0, 63)
				.mapToObj(i -> new Position(i / 8, i % 8))
				.forEach(position -> to[position.x()][position.y()] = from[position.x()][position.y()]);

	}
}
