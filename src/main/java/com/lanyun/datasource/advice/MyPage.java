package com.lanyun.datasource.advice;

/**
 * @email 352342845@qq.com
 * @date 2019-06-20 17:27
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-05-14 18:36
 */
public class MyPage<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = -3720998571176536865L;

    private List<T> content = new ArrayList<>();

    private long totalElements;

    private int pageNumber;

    private int pageSize;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public MyPage() {

    }

    //迭代器
    @Override
    public Iterator<T> iterator() {
        return getContent().iterator();
    }
}
