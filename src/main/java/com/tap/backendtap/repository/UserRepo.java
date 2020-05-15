package com.tap.backendtap.repository;

import com.tap.backendtap.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Florina on 5/6/2020
 */
@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findUserByEmail(String email);

    Boolean existsUserByEmail(String email);
    
    @Query("Select u.idUser from User u where u.email=:email")
    Long getUserIdByEmail(@Param("email") String email);
}
