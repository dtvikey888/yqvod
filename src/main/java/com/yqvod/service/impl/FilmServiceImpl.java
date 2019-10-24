package com.yqvod.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yqvod.common.Const;
import com.yqvod.common.ResponseCode;
import com.yqvod.common.ServerResponse;
import com.yqvod.dao.CategoryMapper;
import com.yqvod.dao.FilmMapper;
import com.yqvod.pojo.Category;
import com.yqvod.pojo.Film;
import com.yqvod.service.ICategoryService;
import com.yqvod.service.IFilmService;
import com.yqvod.util.DateTimeUtil;
import com.yqvod.util.PropertiesUtil;
import com.yqvod.vo.FilmDetailVo;
import com.yqvod.vo.FilmListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ICategoryService iCategoryService;

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

    public ServerResponse getFilmList(int pageNum,int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<Film> filmList = filmMapper.selectList();

        List<FilmListVo> filmListVoList = Lists.newArrayList();
        for (Film filmItem:filmList) {
            FilmListVo filmListVo = assembleFilmListVo(filmItem);
            filmListVoList.add(filmListVo);
        }
        PageInfo pageResult = new PageInfo(filmList);
        pageResult.setList(filmListVoList);
        return ServerResponse.createBySuccess(pageResult);

    }

    private FilmListVo assembleFilmListVo(Film film){
        FilmListVo filmListVo = new FilmListVo();
        filmListVo.setId(film.getId());
        filmListVo.setCategoryId(film.getCategoryId());
        filmListVo.setName(film.getName());
        filmListVo.setSubtitle(film.getSubtitle());
        filmListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.yqvod.com/"));
        filmListVo.setMainImage(film.getMainImage());
        filmListVo.setFilmUrl(film.getFilmUrl());
        filmListVo.setStatus(film.getStatus());
        return filmListVo;
    }

    public ServerResponse<PageInfo> searchFilm(String filmName,Integer filmId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if (StringUtils.isNotBlank(filmName)){
            filmName = new StringBuilder().append("%").append(filmName).append("%").toString();
        }

        List<Film> filmList = filmMapper.selectByNameAndFilmId(filmName,filmId);
        List<FilmListVo> filmListVoList = Lists.newArrayList();
        for (Film filmItem:filmList) {
            FilmListVo filmListVo = assembleFilmListVo(filmItem);
            filmListVoList.add(filmListVo);
        }
        PageInfo pageResult = new PageInfo(filmList);
        pageResult.setList(filmListVoList);
        return ServerResponse.createBySuccess(pageResult);

    }

    public ServerResponse<FilmDetailVo> getFilmDetail(Integer filmId){
        if (filmId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Film film = filmMapper.selectByPrimaryKey(filmId);
        if (film == null){
            return ServerResponse.createByErrorMessage("影片已下架或者删除");
        }
        if (film.getStatus()!=Const.FilmStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("影片已下架或者删除");
        }
        FilmDetailVo filmDetailVo = assembleFilmDetailVo(film);
        return ServerResponse.createBySuccess(filmDetailVo);
    }

    public ServerResponse<PageInfo> getFilmByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy){
        if (StringUtils.isBlank(keyword)&&categoryId==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();

        if (categoryId!=null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category==null&&StringUtils.isBlank(keyword)){
                //没有该分类，并且还没有关键字，这个时候返回一个空的结果集，不报错。
                PageHelper.startPage(pageNum,pageSize);
                List<FilmListVo> filmListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(filmListVoList);
                return ServerResponse.createBySuccess(pageInfo);

            }

            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();

        }

         if (StringUtils.isNotBlank(keyword)){
             keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
         }

         PageHelper.startPage(pageNum,pageSize);
         //排序处理
         if (StringUtils.isNotBlank(orderBy)){
            if (Const.FilmListOrderBy.COUNT_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
         }

         List<Film> filmList = filmMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);
         List<FilmListVo> filmListVoList = Lists.newArrayList();
         for (Film film : filmList){
             FilmListVo filmListVo = assembleFilmListVo(film);
             filmListVoList.add(filmListVo);
         }

         PageInfo pageInfo = new PageInfo(filmList);
         pageInfo.setList(filmListVoList);
         return ServerResponse.createBySuccess(pageInfo);
    }
}
