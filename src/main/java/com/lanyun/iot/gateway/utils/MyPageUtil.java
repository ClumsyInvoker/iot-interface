package com.lanyun.iot.gateway.utils;

import com.lanyun.iot.gateway.controller.advice.MyPage;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-06-20 17:45
 */
public class MyPageUtil {

    public static <T> MyPage<T> pageToMyPage(Page<T> page) {
        MyPage<T> myPage = new MyPage<>();
        myPage.setContent(page.getContent());
        myPage.setPageNumber(page.getNumber() + 1);
        myPage.setPageSize(page.getSize());
        myPage.setTotalElements(page.getTotalElements());
        return myPage;
    }


    public static <T> MyPage<T> convert(MyPage page, List<T> list) {
        MyPage<T> myPage = new MyPage<>();
        myPage.setContent(list);
        myPage.setPageNumber(page.getPageNumber());
        myPage.setPageSize(page.getPageSize());
        myPage.setTotalElements(page.getTotalElements());
        return myPage;
    }
}
