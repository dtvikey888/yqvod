package com.yqvod.controller.portal;

import com.yqvod.common.ServerResponse;
import com.yqvod.service.IFilmService;
import com.yqvod.vo.FilmDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: dtvikey
 * @Date: 23/10/19 下午 05:11
 * @Version 1.0
 */
@Controller
@RequestMapping("/film/")
public class FilmController {

    @Autowired
    IFilmService iFilmService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<FilmDetailVo> detail(Integer filmId){
        return iFilmService.getFilmDetail(filmId);
    }


}
