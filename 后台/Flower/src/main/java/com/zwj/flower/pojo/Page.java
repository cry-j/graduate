package com.zwj.flower.pojo;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/14 16:10
 * @Description: page基类
 */
public class Page<T> {
    private Integer start;//首页
    private Integer end;//尾页
    private Integer currentPage;//当前页
    private long total;//总记录数
    private List<T> list;//记录的集合

    public Page() {
    }

    public Page(Integer start, Integer end, Integer currentPage, long total, List<T> list) {
        this.start = start;
        this.end = end;
        this.currentPage = currentPage;
        this.total = total;
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", end=" + end +
                ", currentPage=" + currentPage +
                ", total=" + total +
                ", list=" + list +
                '}';
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
