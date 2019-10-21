package com.yqvod.service;

import com.yqvod.common.ServerResponse;
import com.yqvod.pojo.Film;
import com.yqvod.vo.FilmDetailVo;

public interface IFilmService {
    ServerResponse saveOrUpdateFilm(Film film);
    ServerResponse<String> setSaleStatus(Integer filmId,Integer status);
    ServerResponse<FilmDetailVo> manageFilmDetail(Integer filmId);
    ServerResponse getFilmList(int pageNum,int pageSize);
}
