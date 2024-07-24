package com.example.mybatismysql;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CityMapper {

    /**
     * 没有设置类似hibernate的自增长，非空，所以需要直接设置id
     *
     * @param city
     */
    @Insert("INSERT INTO city (id,name, state, country) VALUES(#{id},#{name}, #{state}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(City city);

    @Select("SELECT id, name, state, country FROM city WHERE id = #{id}")
    City findById(long id);

}
