package com.yqvod.util;

import java.sql.ResultSet;

/**
 * @ClassName $ {NAME}
 * @Description 老得数据库转到新的来。
 * @Author fjw
 * @Date 2019/10/27 9:18 PM
 * @Version 1.0
 **/
public class TranceData {
    public static void main(String[] args) {

        try {
            MysqlDB db = new MysqlDB();
            //SELECT film.ly as ly2 ,film.typeid as lx, url.url as url2 ,film.dateandtime as dateandtime2,film.title as title2,film.content as content2,film.hits as hits2 FROM  url,film WHERE url.filmid = film.id and film.typeid=1
            String sql = "select typeid,title,images from film where typeid = 1";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString(1)
                        +" "+rs.getString(2)
                        +" "+rs.getString(3));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
