package com.ishikawarts.foundation.exception;

/**
 * アプリ側でハンドリングしていない例外情報を保持するクラス
 */
public class SystemException extends Exception {

	public SystemException(Exception e) {
		super(e);
	}
	
	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable t) {
		super(t);
	}

}
