package jp.co.rootship.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 電卓クラス
 */
public class Calculator extends JFrame {

	/** 表示エリアの値を保存する変数 */
	private double memory = 0;

	/** 入力された演算子を保存する変数 */
	private String operator = "";

	/** 表示エリアの上書き可否を制御する変数 */
	private boolean canOverwrite = true;

	/**
	 * コンストラクタ
	 */
	public Calculator() {

		// ウィンドウサイズを300x400に指定
		super.setSize(new Dimension(300, 400));

		// ウィンドウ右上の×ボタンを押したときアプリを終了するよう設定
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 電卓の画面に設置用のパネルを設置
		super.add(createMainPanel(), BorderLayout.CENTER);

	}

	/**
	 * メインパネルオブジェクトを作成する
	 * @return パネル
	 */
	private Component createMainPanel() {
		// メインパネルを設置
		JPanel mainPanel = new JPanel();

		// メインパネルのレイアウト設定 (縦方向)
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// 画面表示用のテキストエリアを作成
		JTextArea textArea = createDisplayArea();
		
		// メインパネルに表示エリアを設置
		mainPanel.add(textArea);

		// メインパネルにボタンエリアを設置
		mainPanel.add(createButtonArea(textArea));

		return mainPanel;
	}

	/**
	 * 画面表示用のテキストエリアを作成する
	 * @return 画面表示用のテキストエリア
	 */
	private JTextArea createDisplayArea() {
		// テキストエリアのインスタンスを生成
		JTextArea textArea = new JTextArea("0", 3, 10);
		// 最大サイズを設定
		textArea.setMaximumSize(new Dimension(300, 100));
		// 右端で折り返す
		textArea.setLineWrap(true);
		// 入力を無効化
		textArea.setEnabled(false);
		// 入力無効化時のテキストカラーを黒に変更
		textArea.setDisabledTextColor(Color.BLACK);
		// フォントを指定
		textArea.setFont(new Font("Arial", Font.PLAIN, 16));

		return textArea;
	}

	/**
	 * ボタンを設置したパネルを作成する
	 * @param textArea 画面表示用のテキストエリア
	 * @return ボタンを設置したパネル
	 */
	private JPanel createButtonArea(JTextArea textArea) {
		// ボタンパネルのインスタンスを生成
		JPanel buttonPanel = new JPanel();

		// ボタンパネルのレイアウトを設定（4x4のグリッド）
		buttonPanel.setLayout(new GridLayout(4, 4));

		// 表示エリアにボタンのテキストを追加するリスナーを作成
		ActionListener buttonListener = createButtonListener(textArea);

		// ボタンテキストのリスト（表示順）
		List<String> buttonLabels = List.of(
				"7", "8", "9", "/",
				"4", "5", "6", "x",
				"1", "2", "3", "-",
				"0", "C", "=", "+");
		
		// 文字列のリストの各要素をJButtonに変換してボタンパネルに登録
		buttonLabels.stream()
				.map(text -> new JButton(text))
				.forEach(button -> {
					//ボタンを押したときの処理を登録
					button.addActionListener(buttonListener);
					// ボタンパネルにボタンを設置
					buttonPanel.add(button);
				});

		return buttonPanel;
	}
	
	/**
	 * ボタンがクリックされた際のアクションリスナーを作成する
	 * @param textArea 画面表示用のテキストエリア
	 * @return ボタンがクリックされた際のアクションリスナー
	 */
	private ActionListener createButtonListener(JTextArea textArea) {
		return e -> {
			// イベントオブジェクトからボタンテキストを取得
			String buttonText = e.getActionCommand();

			switch (buttonText) {
			case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".":
				appendNumber(textArea, buttonText);
				break;

			case "/", "x", "-", "+", "=":
				calculate(textArea, buttonText);
				break;

			case "C":
				clear(textArea);
				break;
			}
		};

	}
	
	/**
	 * 画面表示用のテキストエリアにクリックされたボタンテキストを設定する
	 * @param textArea 画面表示用のテキストエリア
	 * @param buttonText ボタンテキスト
	 */
	private void appendNumber(JTextArea textArea, String buttonText) {
		// 表示エリアを上書きする場合
		if (this.canOverwrite) {
			// 内容をクリア
			textArea.setText("");
			// 上書き不可に変更
			this.canOverwrite = false;
		}

		// ボタンテキスト（0~9）を表示エリアに追加
		textArea.append(buttonText);
	}
	
	/**
	 * 計算して画面表示用のテキストエリアに反映する
	 * @param textArea 画面表示用のテキストエリア
	 * @param buttonText ボタンテキスト
	 */
	private void calculate(JTextArea textArea, String buttonText) {
		// 画面表示用のテキストエリアの値
		double input = Double.parseDouble(textArea.getText());

		// 演算子別に計算を行いメモリーに保存
		this.memory = switch (this.operator) {
		case "/" -> input == 0 ? 0 : this.memory / input;
		case "x" -> this.memory * input;
		case "-" -> this.memory - input;
		case "+" -> this.memory + input;
		default -> input;
		};

		// 計算結果を表示エリアに設定
		textArea.setText(("" + this.memory).replaceAll("\\.0$", ""));

		// クリックされた演算子を保存
		this.operator = buttonText;

		// 表示エリアを上書き可能に変更
		this.canOverwrite = true;

	}
	
	/**
	 * 計算中の情報をクリアする
	 * @param textArea 画面表示用のテキストエリア
	 */
	private void clear(JTextArea textArea) {
		// 表示エリアを0で上書き
		textArea.setText("0");
		// 表示エリアを上書き可能に変更
		this.canOverwrite = true;
		// メモリーの値をクリア
		this.memory = 0;
		// 演算子をクリア
		this.operator = "";
	}

	/**
	 * エントリポイント
	 * @param args 引数（未使用）
	 */
	public static void main(String[] args) {

		// 電卓クラスのインスタンスを生成
		Calculator calculator = new Calculator();
		// 電卓を可視化する
		calculator.setVisible(true);

	}

}
