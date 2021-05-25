package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikedBean {
    private int lik_id;
    private int target_id;
    private int target_type;
    private int brd_id;
    private int mem_id;
    private int target_mem_id;
    private int lik_type;
    private int lik_ip;
}
