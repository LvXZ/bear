package com.group3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group3.util.ParamsJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Chat;
import com.group3.service.ChatService;


@RestController
@RequestMapping("/chat")
public class ChatController {
	
	
	@Autowired
	private ChatService chatService;

	@PostMapping(value = "/get")
	public ResponseInfoDTO<List<Chat>> getUnread(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) throws IOException{

		String producerid = ParamsJSONUtil.getString(params, "producerid");
		String subscriberid = ParamsJSONUtil.getString(params, "subscriberid");

		System.out.println(producerid+"   "+subscriberid);

		ResponseInfoDTO<List<Chat>> res = chatService.getUnRead(producerid,subscriberid);
		return res;
	}
	
	
	@PostMapping(value = "/gethistory")
	public ResponseInfoDTO<List<Chat>> getHistory(@RequestBody String params,HttpServletRequest request,HttpServletResponse response) throws IOException{

		String producerid = ParamsJSONUtil.getString(params, "producerid");
		String subscriberid = ParamsJSONUtil.getString(params, "subscriberid");
		String startTime = ParamsJSONUtil.getString(params, "startTime");
		String endTime = ParamsJSONUtil.getString(params, "endTime");

		ResponseInfoDTO<List<Chat>> res = chatService.getHistoryInfo(producerid, subscriberid, startTime,endTime);

		return res;
	}
	
	
}
