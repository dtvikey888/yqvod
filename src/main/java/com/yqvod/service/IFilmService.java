package com.yqvod.service;

import com.yqvod.common.ServerResponse;
import com.yqvod.pojo.Film;

public interface IFilmService {
    ServerResponse saveOrUpdateFilm(Film film);
    ServerResponse<String> setSaleStatus(Integer filmId,Integer status);
}
