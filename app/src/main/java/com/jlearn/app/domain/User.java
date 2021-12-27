package  com.jlearn.app.domain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.jlearn.auth.service.AuthUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dingjuru
 * @date 2021/11/17
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends AuthUser {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    @JSONField(serialize = false)
    private String password;

    private Boolean enabled;

    private String sex = "男";

    private String nickname;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);

    }
}
