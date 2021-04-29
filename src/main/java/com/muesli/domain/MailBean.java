package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailBean {
	
	private String from;
	private String to;
	private String subject;
	private String contents;
}
