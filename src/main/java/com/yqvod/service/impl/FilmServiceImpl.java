package com.yqvod.service.impl;

import com.yqvod.common.ResponseCode;
import com.yqvod.common.ServerResponse;
import com.yqvod.dao.FilmMapper;
import com.yqvod.pojo.Film;
import com.yqvod.service.IFilmService;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
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

    private FilmMapper filmMapper;

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
                return ServerResponse.createBySuccess("更新影片失败");
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
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
    }

}
