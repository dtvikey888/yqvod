package com.yqvod.util;

import java.sql.ResultSet;
import java.util.Scanner;

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
            MysqlDB2 db2 = new MysqlDB2();
            //SELECT film.typeid as category_id,film.title as name,film.title as subtitle,film.images as main_image,film.images as sub_images, url.url as film_url ,film.content as detail,film.ly as source,film.hits as count,film.dateandtime as create_time,film.dateandtime as update_time FROM  url,film WHERE url.filmid = film.id and film.typeid=1
            String sql = "SELECT " +
                    "film.typeid as category_id," +
                    "film.title as name," +
                    "film.title as subtitle," +
                    "replace(film.images,'/Yqvod/UPloadfiles/images/','') as main_image," +
                    "replace(film.images,'/Yqvod/UPloadfiles/images/','') as sub_images," +
                    "replace(replace(replace(url.url,'/Yqvod/',''),'Video//',''),'Video/','') as film_url," +
                    "film.content as detail," +
                    "film.ly as source," +
                    "film.hits as count," +
                    "film.dateandtime as create_time," +
                    "film.dateandtime as update_time " +
                    "FROM  url,film WHERE url.filmid = film.id and film.typeid=54 ";

            int category_id=8;
            int status=1;
            String sub_images="";
            String sql2="";
            String sql3="";
            ResultSet rs = null;
            ResultSet rs2 =null;
            rs = db.executeQuery(sql);
            while (rs.next()){

                System.out.println(rs.getInt(1)
                        +" "+rs.getString(2)
                        +" "+rs.getString(3)
                        +" "+rs.getString(4)
                        +" "+rs.getString(5)
                        +" "+rs.getString(6)
                        +" "+rs.getString(7)
                        +" "+rs.getString(8)
                        +" "+rs.getInt(9)
                        +" "+rs.getString(10)
                        +" "+rs.getString(11));

                sub_images = rs.getString(5)+","+rs.getString(5)+","+rs.getString(5)+","+rs.getString(5);
                System.out.println(sub_images);
                System.out.println(status);

                sql2="select * from yqvod_film where name='"+rs.getString(2)+"'";
                rs2 = db2.executeQuery(sql2);

                if (!rs2.next()) {

                    sql3="insert into yqvod_film(" +
                            "category_id," +
                            "name," +
                            "subtitle," +
                            "main_image," +
                            "sub_images," +
                            "film_url," +
                            "detail," +
                            "source," +
                            "count," +
                            "status," +
                            "create_time," +
                            "update_time) values(" +
                            ""+category_id+"," +
                            "'"+rs.getString(2)+"'," +
                            "'"+rs.getString(3)+"'," +
                            "'"+rs.getString(4)+"'," +
                            "'"+sub_images+"'," +
                            "'"+rs.getString(6)+"'," +
                            "'"+rs.getString(7)+"'," +
                            "'"+rs.getString(8)+"'," +
                            "'"+rs.getString(9)+"'," +
                            ""+status+"," +
                            "'"+rs.getString(10)+"'," +
                            "'"+rs.getString(11)+"')";

                    db2.executeInsert(sql3);
                }


                //db.closeStmt();
                //db.closeConn();
                //db2.closeStmt();
                //db2.closeConn();
               // rs.close();
                //rs2.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
