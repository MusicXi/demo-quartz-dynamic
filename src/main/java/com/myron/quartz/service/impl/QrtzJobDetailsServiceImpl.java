package com.myron.quartz.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myron.quartz.bean.QrtzJobDetails;
import com.myron.quartz.dao.QrtzJobDetailsDao;
import com.myron.quartz.exception.DynamicQuartzException;
import com.myron.quartz.job.DynamicQuartzJob;
import com.myron.quartz.service.QrtzJobDetailsService;
import com.myron.quartz.util.QuartzUtil;
import com.myron.quartz.util.SpringContextHolder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("qrtzJobDetailsService")
@Transactional(rollbackFor=Exception.class)
public class QrtzJobDetailsServiceImpl  implements QrtzJobDetailsService {
	private static final Logger LOGGER=LoggerFactory.getLogger(QrtzJobDetailsServiceImpl.class);

	/** triggerName 前缀*/
	private static final String TRIGGER_NAME_PREFIX = "triggerName.";
	/** jobName/triggerName 默认组 */
	private static final String GROUP_DEFAULT = "DEFAULT";
	
	@Autowired
	private QrtzJobDetailsDao qrtzJobDetailsDao;

	@Autowired
	private Scheduler scheduler;
	
	@Override
	public Map<String, Object> createQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		
		// 非空校验
		if (qrtzJobDetails == null) {
			throw new Exception("qrtzJobDetails 为空");
		}
		if (StringUtils.isEmpty(qrtzJobDetails.getJobName())) {
			throw new Exception("qrtzJobDetails serviceInfo 为空");
		}

		// 定时服务有效性校验 (校验是否存在对应的servcie.method )
		this.checkServiceAndMethod(qrtzJobDetails.getJobName());

		// 唯一性校验
		String jobName = qrtzJobDetails.getJobName();
		String triggerName = TRIGGER_NAME_PREFIX + qrtzJobDetails.getJobName();
		String jobGroup = StringUtils.isEmpty(qrtzJobDetails.getJobGroup())? GROUP_DEFAULT : qrtzJobDetails.getJobGroup();
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		if (scheduler.checkExists(jobKey)) {
			throw new DynamicQuartzException(qrtzJobDetails.getJobName() + "服务方法对应定时任务已经存在!");
		}

		// 构建job信息
		JobDetail job = JobBuilder.newJob(DynamicQuartzJob.class).withIdentity(jobKey).withDescription(qrtzJobDetails.getDescription()).build();
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, jobGroup);

        // 构建job的触发规则 cronExpression
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()  
        		.withSchedule(CronScheduleBuilder.cronSchedule(qrtzJobDetails.getCronExpression())).build();

		// 注册job和trigger信息
        scheduler.scheduleJob(job, trigger);  
		
		resultMap.put("success", true);
		resultMap.put("msg", "创建QrtzJobDetails 成功!");
		return resultMap;
	}

	
	@Override
	public Map<String, Object> updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		JobKey jobKey = JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup());
		TriggerKey triggerKey = null;
		List<? extends Trigger> list = scheduler.getTriggersOfJob(jobKey);
		if (list == null || list.size() != 1) {
			return resultMap;
		}
		for (Trigger trigger : list) {
			//暂停触发器
			scheduler.pauseTrigger(trigger.getKey());
			triggerKey = trigger.getKey();
		}
		Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()  
              .withSchedule(CronScheduleBuilder.cronSchedule(qrtzJobDetails.getCronExpression())).build();
		scheduler.rescheduleJob(newTrigger.getKey(), newTrigger);
		LOGGER.info("update job name:{} success", qrtzJobDetails.getJobName());
		resultMap.put("success", true);
		resultMap.put("msg", "update job success");
		return resultMap;
	}


	@Override
	public Map<String, Object> deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		JobKey jobKey = JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup());
		QuartzUtil.deleteJob(scheduler, jobKey);
		LOGGER.info("delete job name:{} success", qrtzJobDetails.getJobName());
		resultMap.put("success", true);
		resultMap.put("msg", "delete job success");
		return resultMap;
	}
	
	@Override
	public QrtzJobDetails findQrtzJobDetailsByPrimaryKey(String id) {
		return this.qrtzJobDetailsDao.selectByPrimaryKey(id);
	}

	@Override
	public Page<Map<String, Object>> findMapListByPage(QrtzJobDetails qrtzJobDetails, Page<Map<String, Object>> page) {
		page = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		this.qrtzJobDetailsDao.selectMapList(qrtzJobDetails);
		return page;
	}
	
	@Override
	public Page<QrtzJobDetails> findListByPage(QrtzJobDetails qrtzJobDetails, Page<QrtzJobDetails> page) {
		page = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		this.qrtzJobDetailsDao.selectList(qrtzJobDetails);
		return page;

	}

	@Override
	public List<Map<String, Object>> findMapList(QrtzJobDetails qrtzJobDetails) {
		return this.qrtzJobDetailsDao.selectMapList(qrtzJobDetails);
	}
	
	@Override
	public List<QrtzJobDetails> findList(QrtzJobDetails qrtzJobDetails){
		return this.qrtzJobDetailsDao.selectList(qrtzJobDetails);
	}

	@Override
	public Map<String, Object> pauseJob(QrtzJobDetails qrtzJobDetails)
			throws Exception {
		scheduler.pauseJob(JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup()));
		LOGGER.info("pause job name:{} success", qrtzJobDetails.getJobName());
		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("msg", "pause job success!");
		return result;
	}

	@Override
	public Map<String, Object> resumeJob(QrtzJobDetails qrtzJobDetails)
			throws Exception {
		scheduler.resumeJob(JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup()));
		LOGGER.info("resume job name:{} success", qrtzJobDetails.getJobName());
		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("msg", "resume job success!");
		return result;
	}


	/**
	 * <li>校验服务和方法是否存在</li>
	 * @param jobName
	 * @throws DynamicQuartzException
	 */
	private void checkServiceAndMethod(String jobName) throws DynamicQuartzException {
		String[] serviceInfo = jobName.split("\\.");
		String beanName = serviceInfo[0];
		String methodName = serviceInfo[1];
		if (! SpringContextHolder.existBean(beanName)) {
			throw new DynamicQuartzException("找不到对应服务");
		}
		if (! SpringContextHolder.existBeanAndMethod(beanName, methodName, null)) {
			throw new DynamicQuartzException("服务方法不存在");
		}
		

	}
	


}
