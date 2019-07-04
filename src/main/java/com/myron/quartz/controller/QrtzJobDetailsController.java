package com.myron.quartz.controller;


import com.github.pagehelper.Page;
import com.myron.quartz.bean.QrtzJobDetails;
import com.myron.quartz.service.QrtzJobDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins={"*"})
@Controller
@RequestMapping("/qrtzJobDetails")
public class QrtzJobDetailsController {

	@Autowired
	private QrtzJobDetailsService qrtzJobDetailsService;


	/**
	 * 查询定时任务
	 */
	@RequestMapping(value = "/listByPage", method=RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object> listByPage(QrtzJobDetails qrtzJobDetails, Page<Map<String, Object>> page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		page = this.qrtzJobDetailsService.findMapListByPage(qrtzJobDetails, page);
		map.put("data", page);
	    map.put("total", page.getTotal());
		return map;
	}
	
	//动态添加定时任务"
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addQrtzJobDetails(@RequestBody QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();

		map = this.qrtzJobDetailsService.createQrtzJobDetails(qrtzJobDetails);
		map.put("success", true);
		map.put("msg", "定时任务添加成功");
		return map;
	}
	
	//动态修改定时任务
	@RequestMapping(value = "/edit", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateQrtzJobDetails(@RequestBody QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.updateQrtzJobDetails(qrtzJobDetails);
		return map;
	}
	
	//动态删除定时任务,先暂停再删除
	@RequestMapping(value = "/delete",  method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteQrtzJobDetails(@RequestBody QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.deleteQrtzJobDetails(qrtzJobDetails);
		return map;
	}
	
	//暂停定时任务
	@RequestMapping(value = "/pause", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pauseJob(@RequestBody QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.pauseJob(qrtzJobDetails);
		return map;
	}
	
	//恢复暂停的定时任务
	@RequestMapping(value = "/resume", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resumeJob(@RequestBody QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.resumeJob(qrtzJobDetails);
		return map;
	}
	
}
