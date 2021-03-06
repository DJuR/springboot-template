package com.jlearn.common;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author dingjuru
 * @date 2021/11/12
 */
@Setter
@Getter
public class BaseDTO implements Serializable {

    @Override
    public String toString() {

        ToStringBuilder builder = new ToStringBuilder(this);
        Field[] fields = this.getClass().getDeclaredFields();

        try{
            for (Field f : fields) {
                f.setAccessible(true);
                builder.append(f.getName(), f.get(this)).append("\n");
            }
        } catch (Exception e) {
            builder.append("toString builder encounter an error");
        }

        return builder.toString();
    }
}
