package com.ishikawarts.foundation.exception;

import java.util.LinkedHashMap;

/**
 * アプリ側で発生が想定される例外情報を保持するクラス
 */
public class ApplicationException extends Exception {

	private LinkedHashMap<String, String> detailInfoMap = new LinkedHashMap<>();

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, LinkedHashMap<String, String> detailInfoMap) {
		super(message);
		this.detailInfoMap = detailInfoMap;
	}

	public LinkedHashMap<String, String> getDetailInfoMap() {
		return this.detailInfoMap;
	}

}
