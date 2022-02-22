package com.markruler.legacy.user.persistence;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    String getUser();

}
