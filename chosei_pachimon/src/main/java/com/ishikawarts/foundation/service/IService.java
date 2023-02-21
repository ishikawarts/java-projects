package com.ishikawarts.foundation.service;

import com.ishikawarts.foundation.exception.SystemException;
import com.ishikawarts.foundation.service.dto.IParameter;
import com.ishikawarts.foundation.service.dto.IResult;

public interface IService<P extends IParameter, R extends IResult>{
	
	R execute(P param) throws SystemException;

}
