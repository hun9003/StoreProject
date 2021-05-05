package com.muesli.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuBean {
    private int men_id;
    private String men_parent;
    private String men_name;
    private String men_link;
    private String men_target;
    private int men_desktop;
    private int men_mobile;
    private String men_custom;
    private int men_order;
}
