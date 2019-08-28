package com.example.productivity.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;


import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getOneUser(@PathVariable long id) {
        return userRepository.findById(id);
    }

    @PostMapping
    public User addOneUser(@RequestBody User newUser) {
          return userRepository.save(newUser);
    }

    @PatchMapping("/{id}")
    public User updateOneUser(@RequestBody User updatedUser, @PathVariable long id) {
        User user = userRepository.getOne(id);
        System.out.println("LOOKKKKKKK" + updatedUser);
        user.setId(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        System.out.println("NEW USER HERE " + user);
        if (updatedUser.getPassword() != null) {
            user.setPassword(updatedUser.getPassword());
        }
        userRepository.save(user);
        System.out.println(user);
        return user;
    }

    @PostMapping("/authenticate")
    public User authenticateUser(@RequestBody User searchUser) {

        User userFound = userRepository.findByEmail(searchUser.getEmail());

        HashMap<Object, Object> parentHash = new HashMap<>();
        HashMap<Object, Object> user = new HashMap<>();
        HashMap<Object, Object> authBoolean = new HashMap<>();
        if(userFound.getPassword().equals(searchUser.getPassword())){
            System.out.println("I ran!!!!!!!");
            user.put("authenticated", true);
            authBoolean.put("user", userFound);
            //authBoolean.put(true, apple);
            parentHash.put("authenticated",true);
            parentHash.put("user", userFound);
            System.out.println(userFound);
            return userFound;
        }
        else{
            parentHash.put("authenticated", false);
            return userFound;
        }
    }

    @DeleteMapping("/{id}")
    public User deleteOneUser(@PathVariable long id) {
        User removedUser = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return removedUser;
    }

    static class UserRequest {
        private final User user;

        public UserRequest(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        @Override
        public String toString() {
            return "UsersRequest{" +
                    "users=" + user +
                    '}';
        }
    }
}
