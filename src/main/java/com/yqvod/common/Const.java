package com.yqvod.common;/**
 * 项目名称：yqvod
 * 类 名 称：Const
 * 类 描 述：TODO
 * 创建时间：2019/10/15 10:19 PM
 * 创 建 人：fjw
 */

/**
 * @ClassName $ {NAME}
 * @Description TODO
 * @Author fjw
 * @Date 2019/10/15 10:19 PM
 * @Version 1.0
 **/
public class Const {
    public static final String CURRENT_USER="currentUser";
    public static final String EMAIL="email";
    public static final String USERNAME="username";
    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;   //管理员
    }

    public enum FilmStatusEnum{
        ON_SALE(1,"在线");
        private String value;
        private int code;
        FilmStatusEnum(int code,String value){
            this.code=code;
            this.value=value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
