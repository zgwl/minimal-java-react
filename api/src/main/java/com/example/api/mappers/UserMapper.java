package com.example.api.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.api.daos.User;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM users")
  List<User> findAll();

  @Select("SELECT * FROM users WHERE id = #{id}")
  User findById(Long id);

  @Select("SELECT * FROM users WHERE email = #{email}")
  User findByEmail(String email);

  @Insert("INSERT INTO users(name, email, password) VALUES(#{name}, #{email}, #{password})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(User user);

  @Update("UPDATE users SET name = #{name}, email = #{email}, password = #{password} WHERE id = #{id}")
  void update(User user);

  @Delete("DELETE FROM users WHERE id = #{id}")
  void delete(Long id);
}
