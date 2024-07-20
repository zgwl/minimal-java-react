package com.example.api.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.api.daos.chat.ChatRecord;

@Mapper
public interface UserChatMapper {

  @Select("SELECT id AS id, user_id AS userId, updated_at AS updatedAt, messages AS messages " +
      "FROM chats WHERE user_id = #{userId}")
  ChatRecord findByUserId(@Param("userId") Long userId);

  @Insert("INSERT INTO chats (user_id, messages, updated_at) " +
      "VALUES (#{userId}, CAST(#{messages} AS JSONB), now()) " +
      "ON CONFLICT (user_id) DO UPDATE SET messages = CAST(excluded.messages AS JSONB), updated_at = now()")
  void upsertChat(Long userId, String messages);
}
