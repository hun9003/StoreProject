package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PostBean {
    private int post_id;
    private int brd_id;
    private String post_title;
    private String post_content;
    private String post_category;
    private int mem_id;
    private String post_userid;
    private String post_nickname;
    private Timestamp post_datetime;
    private Timestamp post_updated_datetime;
    private int post_update_mem_id;
    private int post_comment_count;
    private Timestamp post_comment_updated_datetime;
    private int post_secret;
    private int post_notice;
    private int post_hit;
    private int post_like;
    private int post_dislike;
    private String post_ip;
    private int post_blame;
    private int post_device;
    private int post_file;
    private int post_image;
    private int post_del;
}
