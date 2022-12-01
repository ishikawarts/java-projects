package jp.co.rootship.todo;

import java.util.List;

import javax.faces.bean.ManagedBean;

import lombok.Getter;
import lombok.Setter;

/**
 * ToDoリストページにのバッキングBeanクラス
 */
@ManagedBean
@Getter
@Setter
public class TodoList {

	/** 新規タスク */
	private Task newTask;

	/** タスク一覧 */
	private List<Task> tasks;

	/** コンストラクタ */
	public TodoList() {

		this.newTask = new Task();

		this.tasks = TaskTableAccess.selectAll();

	}

	/**
	 * タスクを追加する
	 * @return 遷移先
	 */
	public String addTask() {

		TaskTableAccess.insert(newTask);

		for (Task task : tasks) {
			TaskTableAccess.update(task);
		}

		this.tasks = TaskTableAccess.selectAll();

		this.newTask = new Task();

		return "todo-list";
	}

	/**
	 * 完了しているタスク削除するアクション
	 * @return 遷移先
	 */
	public String deleteTask() {

		for (Task task : tasks) {
			if (task.getIsCompleted() == true) {
				task.setIsDeleted(true);
				TaskTableAccess.update(task);
			}
		}

		this.tasks = TaskTableAccess.selectAll();

		return "todo-list";
	}

	/**
	 * タスク完了を保存する
	 * @return 遷移先
	 */
	public String completeTask() {

		for (Task task : tasks) {
			TaskTableAccess.update(task);
		}

		this.tasks = TaskTableAccess.selectAll();

		return "todo-list";
	}

}
