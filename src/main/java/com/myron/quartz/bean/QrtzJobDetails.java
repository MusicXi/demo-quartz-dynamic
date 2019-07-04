package com.myron.quartz.bean;

import org.springframework.util.StringUtils;

import java.io.Serializable;



public class QrtzJobDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String schedName;	
	private String jobName;	
	private String jobGroup;	
	private String description;	
	private String jobClassName;	
	private String isDurable;	
	private String isNonconcurrent;	
	private String isUpdateData;	
	private String requestsRecovery;	
	private String jobData;	
	
	//非持久化属性
	private String cronExpression;
	
	public QrtzJobDetails(){
		super();
	}
	
    public String getSchedName() {
        return StringUtils.isEmpty(schedName) ? schedName : schedName.trim();
    }
    
    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }
    
    public String getJobName() {
        return StringUtils.isEmpty(jobName) ? jobName : jobName.trim();
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getJobGroup() {
        return StringUtils.isEmpty(jobGroup) ? jobGroup : jobGroup.trim();
    }
    
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    
    public String getDescription() {
        return StringUtils.isEmpty(description) ? description : description.trim();
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getJobClassName() {
        return StringUtils.isEmpty(jobClassName) ? jobClassName : jobClassName.trim();
    }
    
    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }
    
    public String getIsDurable() {
        return StringUtils.isEmpty(isDurable) ? isDurable : isDurable.trim();
    }
    
    public void setIsDurable(String isDurable) {
        this.isDurable = isDurable;
    }
    
    public String getIsNonconcurrent() {
        return StringUtils.isEmpty(isNonconcurrent) ? isNonconcurrent : isNonconcurrent.trim();
    }
    
    public void setIsNonconcurrent(String isNonconcurrent) {
        this.isNonconcurrent = isNonconcurrent;
    }
    
    public String getIsUpdateData() {
        return StringUtils.isEmpty(isUpdateData) ? isUpdateData : isUpdateData.trim();
    }
    
    public void setIsUpdateData(String isUpdateData) {
        this.isUpdateData = isUpdateData;
    }
    
    public String getRequestsRecovery() {
        return StringUtils.isEmpty(requestsRecovery) ? requestsRecovery : requestsRecovery.trim();
    }
    
    public void setRequestsRecovery(String requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
    }
    
    public String getJobData() {
        return StringUtils.isEmpty(jobData) ? jobData : jobData.trim();
    }
    
    public void setJobData(String jobData) {
        this.jobData = jobData;
    }
    
	public String getCronExpression() {
		return StringUtils.isEmpty(cronExpression) ? cronExpression : cronExpression.trim();
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Override
	public String toString() {
		return "QrtzJobDetails ["
				+ "schedName = " + schedName 
				+ ", jobName = " + jobName 
				+ ", jobGroup = " + jobGroup 
				+ ", description = " + description 
				+ ", jobClassName = " + jobClassName 
				+ ", isDurable = " + isDurable 
				+ ", isNonconcurrent = " + isNonconcurrent 
				+ ", isUpdateData = " + isUpdateData 
				+ ", requestsRecovery = " + requestsRecovery 
				+ ", jobData = " + jobData 
				+ "]";
	}
	
}