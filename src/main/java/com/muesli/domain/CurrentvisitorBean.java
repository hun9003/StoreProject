package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CurrentvisitorBean {
    private String cur_ip;
    private int mem_id;
    private String cur_mem_name;
    private Timestamp cur_datetime;
    private String cur_page;
    private String cur_url;
    private String cur_referer;
    private String cur_useragent;
}
