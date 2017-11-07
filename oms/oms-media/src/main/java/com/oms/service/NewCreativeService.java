package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.NewCreative;

@Service
public interface NewCreativeService {

	public NewCreative addCreative(NewCreative creativeDTO, Long userId)
			throws IllegalAccessException, InvocationTargetException, Exception;

	public List<NewCreative> getAllNewCreatives();

	public NewCreative getNewCreativeById(Long id)
			throws OMSSystemException, IllegalAccessException, InvocationTargetException;

}
