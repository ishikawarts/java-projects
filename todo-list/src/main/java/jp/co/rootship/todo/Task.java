package jp.co.rootship.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * タスク1件のデータを保持するクラス
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
	/**  ID*/
	private int id;
	/** タスク名称 */
	private String name;
	/** 完了フラグ */
	private boolean isCompleted;
	/** 削除フラグ */
	private boolean isDeleted;

}
