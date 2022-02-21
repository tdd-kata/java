package com.markruler.legacy;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    String getUser();

}
