package com.group3.dao;

import com.group3.entity.Chat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatDAO {
    List<Chat> getUnRead(@Param("producerId")String producerId, @Param("subscriberId")String subscriberId);

    int produceRead(Chat chat);

    int updateSign(@Param("producerId")String producerId, @Param("subscriberId")String subscriberId);

    List<Chat> getHistoryInfo(@Param("producerId")String producerId, @Param("subscriberId")String subscriberId, @Param("startTime")String startTime, @Param("endTime")String endTime);
//	Chat getMoreChat();
}