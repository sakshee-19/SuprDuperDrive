package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * from Credentials")
    public List<Credentials> findAllCredentials();

    @Insert("INSERT into Credentials (url, username, key, password ,userid) values (#{url}, #{username}, #{key}, #{password} ,#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public int uploadCredentials(Credentials credentials);

    @Delete("DELETE FROM Credentials where credentialid=#{credentialId}")
    public void deleteCredentials(int credentialId);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialid=#{credentialId}")
    public void updateCredentials(Credentials credentials);

    @Select("SELECT * from Credentials WHERE credentialid=#{credentialId}")
    Credentials getCredentialsById(Integer credentialId);
}
