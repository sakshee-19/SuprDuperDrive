package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS where username=#{username}")
    Users findUser(String username);

    @Insert("INSERT into USERS (username,password,salt,firstname,lastname) values (#{username}, #{password}, #{salt}, #{firstname}, #{lastname})")
    @Options(keyProperty = "userId", useGeneratedKeys = true)
    int insert(Users users);

    @Select("DELETE FROM USERS where username=#{username}")
    void deleteUser(String username);

}
