package com.jlearn.auth.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjuru
 * @date 2021/11/22
 */
@Getter
@Setter
public class RouterDTO {

    private String method;

    private String router;

    private String summary;

    private String description;

}
