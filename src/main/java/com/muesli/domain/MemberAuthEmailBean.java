package com.muesli.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAuthEmailBean {
	int mae_id;
	int mem_id;
	String mae_key;
	int mae_type;
	Timestamp mae_generate_datetime;
	Timestamp mae_use_datetime;
	int mae_expired;
}
