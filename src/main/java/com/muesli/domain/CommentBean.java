package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentBean {
    private int cmt_id;
    private int post_id;
    private int brd_id;
    private int cmt_num;
    private int cmt_reply;
    private String cmt_content;
    private int mem_id;
    private Timestamp cmt_datetime;
    private Timestamp cmt_updated_datetime;
    private String cmt_ip;
    private int cmt_like;
    private int cmt_dislike;
    private int cmt_blame;
    private int cmt_device;
    private int cmt_del;

    private String mem_nickname;
}
