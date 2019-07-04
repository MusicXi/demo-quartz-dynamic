package com.myron.quartz.service;

import com.github.pagehelper.Page;
import com.myron.quartz.bean.QrtzJobDetails;

import java.util.List;
import java.util.Map;


/**
 *  动态定时器管理服务
 * @author linrx1
 */
public interface QrtzJobDetailsService {

	Map<String, Object> createQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception;

	QrtzJobDetails findQrtzJobDetailsByPrimaryKey(String id);
	Page<QrtzJobDetails> findListByPage(QrtzJobDetails qrtzJobDetails, Page<QrtzJobDetails> page);
	Page<Map<String, Object>> findMapListByPage(QrtzJobDetails qrtzJobDetails, Page<Map<String, Object>> page);
	List<Map<String, Object>> findMapList(QrtzJobDetails qrtzJobDetails);
	List<QrtzJobDetails> findList(QrtzJobDetails qrtzJobDetails);
	
	Map<String, Object> pauseJob(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> resumeJob(QrtzJobDetails qrtzJobDetails) throws Exception;
	
	
}
