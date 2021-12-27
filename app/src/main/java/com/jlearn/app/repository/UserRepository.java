package com.jlearn.app.repository;

import com.jlearn.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dingjuru
 * @date 2021/11/17
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 通过用户名查询
     *
     * @param username
     * @return
     */
    User findByUsername(String username);


}
