package com.muesli.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MemberBean {

	private int mem_id;
	private String mem_userid;
	private	String mem_email;
	private String mem_password;
	private String mem_password2;
	private String mem_nickname;
	private int mem_level;
	private int mem_point;
	private String mem_phone;
	private int mem_gender;
	private int mem_receive_email;
	private int mem_receive_sms;
	private int mem_denied;
	private int mem_email_cert;
	private Timestamp mem_register_datetime;
	private String mem_register_ip;
	private Timestamp mem_lastlogin_datetime;
	private String mem_lastlogin_ip;
	private int mem_is_admin;
	private String mem_profile_content;
	private String mem_adminmemo;
	private String mem_photo;
}
