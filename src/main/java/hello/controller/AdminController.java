package hello.controller;

import hello.model.Role;
import hello.model.User;
import hello.repository.RoleRepository;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView allUsers() {
        List<User> users = userService.listUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("usersList", users);
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView addUser(User user, @RequestParam("roleseee") String[] roles) {
        Role role = new Role(roles[0]);
        Set<Role> roleses = new HashSet<>();
        roleses.add(role);
        user.setRoles(roleses);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.addUser(user);
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    public ModelAndView editUser(User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.updateUser(user);
        return modelAndView;
    }

    @GetMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

}
