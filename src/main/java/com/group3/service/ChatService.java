package com.group3.service;

import java.sql.Timestamp;
import java.util.List;

import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Chat;



public interface ChatService {
	ResponseInfoDTO<List<Chat>>  getUnRead(String producerid, String subscriberid);
	
	int produceRead(String produceid, String subscriberid, Timestamp timestamp, String context, int sign);
	
	ResponseInfoDTO<List<Chat>> getHistoryInfo(String producerid, String subscriberid, String startTime, String endTime);
}
