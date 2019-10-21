package com.yqvod.service.impl;

import com.yqvod.common.ResponseCode;
import com.yqvod.common.ServerResponse;
import com.yqvod.dao.CategoryMapper;
import com.yqvod.dao.FilmMapper;
import com.yqvod.pojo.Category;
import com.yqvod.pojo.Film;
import com.yqvod.service.IFilmService;
import com.yqvod.util.DateTimeUtil;
import com.yqvod.util.PropertiesUtil;
import com.yqvod.vo.FilmDetailVo;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName $ {NAME}
 * @Description TODO
 * @Author fjw
 * @Date 2019/10/21 2:55 PM
 * @Version 1.0
 **/
@Service("iFilmService")
public class FilmServiceImpl implements IFilmService {
    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse saveOrUpdateFilm(Film film){
        if (film !=null){
            if (StringUtils.isNotBlank(film.getSubImages())){
                String[] subImageArray = film.getSubImages().split(",");
                if (subImageArray.length>0){
                    film.setMainImage(subImageArray[0]);
                }
            }

            if (film.getId()!=null){
                int rowCount = filmMapper.updateByPrimaryKey(film);
                if (rowCount>0){
                    return ServerResponse.createBySuccess("更新影片成功");
                }
                return ServerResponse.createByErrorMessage("更新影片失败");
            }else{
                int rowCount = filmMapper.insert(film);
                if (rowCount>0) {
                    return ServerResponse.createBySuccess("新增影片成功");
                }
                return ServerResponse.createBySuccess("新增影片失败");
            }
        }

        return ServerResponse.createByErrorMessage("新增或更新影片参数不正确");
    }

    public ServerResponse<String> setSaleStatus(Integer filmId,Integer status){
        if (filmId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Film film = new Film();
        film.setId(filmId);
        film.setStatus(status);
        int rowCount = filmMapper.updateByPrimaryKeySelective(film);
        if (rowCount>0){
            return ServerResponse.createBySuccess("修改影片销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改影片销售状态失败");
    }

    public ServerResponse<FilmDetailVo> manageFilmDetail(Integer filmId){
        if (filmId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Film film = filmMapper.selectByPrimaryKey(filmId);
        if (film == null){
            return ServerResponse.createByErrorMessage("影片已下架或者删除");
        }
        FilmDetailVo filmDetailVo = assembleFilmDetailVo(film);
        return ServerResponse.createBySuccess(filmDetailVo);

    }

    private FilmDetailVo assembleFilmDetailVo(Film film){
        FilmDetailVo filmDetailVo = new FilmDetailVo();
        filmDetailVo.setId(film.getId());
        filmDetailVo.setCategoryId(film.getCategoryId());
        filmDetailVo.setName(film.getName());
        filmDetailVo.setSubtitle(film.getSubtitle());
        filmDetailVo.setMainImage(film.getMainImage());
        filmDetailVo.setSubImages(film.getSubImages());
        filmDetailVo.setFilmUrl(film.getFilmUrl());
        filmDetailVo.setDetail(film.getDetail());
        filmDetailVo.setSource(film.getSource());
        filmDetailVo.setCount(film.getCount());
        filmDetailVo.setStatus(film.getStatus());

        filmDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.yqvod.com/"));
        Category category = categoryMapper.selectByPrimaryKey(film.getCategoryId());
        if (category == null){
            filmDetailVo.setParentCategoryId(0); //默认根节点
        }else {
            filmDetailVo.setParentCategoryId(category.getParentId());
        }

        filmDetailVo.setCreateTime(DateTimeUtil.dateToStr(film.getCreateTime()));
        filmDetailVo.setUpdateTime(DateTimeUtil.dateToStr(film.getUpdateTime()));

        return filmDetailVo;

    }

}
