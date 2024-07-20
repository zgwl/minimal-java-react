package com.example.api.mappers;

import java.util.UUID;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.api.daos.chat.ChatRecord;

@Mapper
public interface UserChatMapper {

  @Select("SELECT id AS id, user_id AS userId, session_id AS sessionId, updated_at AS updatedAt, messages AS messages "
      +
      "FROM chats WHERE user_id = #{userId} ORDER BY updated_at DESC LIMIT 1")
  ChatRecord findLatestByUserId(@Param("userId") Long userId);

  @Select("SELECT id AS id, user_id AS userId, session_id AS sessionId, updated_at AS updatedAt, messages AS messages "
      +
      "FROM chats WHERE user_id = #{userId} AND session_id = #{sessionId}")
  ChatRecord findByUserIdAndSessionId(@Param("userId") Long userId, @Param("sessionId") UUID sessionId);

  @Insert("INSERT INTO chats (user_id, session_id, messages, updated_at) " +
      "VALUES (#{userId}, #{sessionId}, CAST(#{messages} AS JSONB), now())")
  void insertChat(Long userId, UUID sessionId, String messages);

  @Update("UPDATE chats SET messages = CAST(#{messages} AS JSONB), updated_at = now() " +
      "WHERE user_id = #{userId} AND session_id = #{sessionId}")
  void updateChat(Long userId, UUID sessionId, String messages);
}
