package com.recipes.demo.persistence;

import com.recipes.demo.businesslayer.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
//    final private Map<String, User> users = new ConcurrentHashMap<>();
//
//    public boolean isPresent(String email) {
//        if (users.containsKey(email)) {
//            return true;
//        }
//        return false;
//    }
//    public User findUserByEmail(String email) {
//        return users.get(email);
//    }
//
//    public void save(User user) {
//        users.put(user.getEmail(), user);
//    }
}