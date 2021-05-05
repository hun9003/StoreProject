package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageBean {
    private int count;
    private int pageSize;
    private String pageNum;
    private int currentPage;
    private int startRow;

    private int pageBlock;
    private int startPage;
    private int endPage;
    private int pageCount;

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
        init();
    }
    public void init() {
        pageBlock=10;
        startPage=(currentPage-1)/pageBlock*pageBlock+1;

        pageCount = count/pageSize +(count%pageSize==0?0:1);

        endPage=startPage+pageBlock-1;
        if(endPage > pageCount){
            endPage=pageCount;
        }
    }



}