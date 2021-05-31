package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MemberLoginLogBean {
    private int mll_id;
    private int mll_success;
    private int mem_id;
    private String mll_userid;
    private Timestamp mll_datetime;
    private String mll_ip;
    private String mll_reason;
    private String mll_useragent;
    private String mll_url;
    private String mll_referer;
}
