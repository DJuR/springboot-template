package com.jlearn.es.jest;

import lombok.Data;

import java.util.List;

/**
 * @author dingjuru
 * @date 2021/12/23
 */
@Data
public class AppJestIndex {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private List<String> books;
}
