package com.yqvod.dao;

import com.google.common.collect.Lists;
import com.yqvod.pojo.Film;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Film record);

    int insertSelective(Film record);

    Film selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Film record);

    int updateByPrimaryKey(Film record);

    List<Film> selectList();

    List<Film> selectByNameAndFilmId(@Param("filmName") String filmName,@Param("filmId") Integer filmId);
}