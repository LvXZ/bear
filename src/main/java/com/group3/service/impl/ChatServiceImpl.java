package com.group3.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.dao.ChatDAO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Chat;
import com.group3.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatDAO chatDao;
	
	
	@Override
	public ResponseInfoDTO<List<Chat>> getUnRead(String producerId,String subscriberId) {
		// TODO Auto-generated method stub
		System.out.println(producerId+"   2  "+subscriberId);
		List<Chat> chats = chatDao.getUnRead(producerId,subscriberId);
		chatDao.updateSign(producerId,subscriberId);
		return ResponseInfoDTO.success(chats);
	}

	@Override
	public int produceRead(String producerId, String subscriberId, Timestamp timestamp, String context, int sign) {
		// TODO Auto-generated method stub
		Chat chat = new Chat();
		chat.setProducerId(producerId);
		chat.setSubscriberId(subscriberId);
		chat.setTimestamp(timestamp);
		chat.setContext(context);
		chat.setSign(sign);
		
		chatDao.produceRead(chat);
		return 0;
	}

	@Override
	public ResponseInfoDTO<List<Chat>> getHistoryInfo(String producerId, String subscriberId, String startTime,String endTime) {
		// TODO Auto-generated method stub
		List<Chat> chats = chatDao.getHistoryInfo(producerId, subscriberId, startTime,endTime);
		return ResponseInfoDTO.success(chats);
	}

}
