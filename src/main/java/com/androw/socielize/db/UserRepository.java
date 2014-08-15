package com.androw.socielize.db;

import com.androw.socielize.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Androw on 14/08/2014.
 */
public interface UserRepository extends MongoRepository<User, Long> {
    public User findByEmail(String email);
}
