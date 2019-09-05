package hello.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.model.Role;
import hello.model.User;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class APIRestController {

    private UserService userService;

    @Autowired
    public APIRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<User>> listUsers() {

        List<User> users = userService.listUsers();
//
//        for (User user : users){
//            System.out.println("USERLOGIN " + user.getLogin());
//            for (Role role : user.getRoles()){
//                System.out.println("ROLEROLLLE " + role.getRole());
//            }
//        }


        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        if (userService.getUserById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/findByUserLogin/{login}")
    public ResponseEntity<User> findByUserLogin(@PathVariable String login) {
        if (userService.findByUserLogin(login) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUserLogin(login);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> removeUser(@PathVariable int id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

// http://localhost:8080/login