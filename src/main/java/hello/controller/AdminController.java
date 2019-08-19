package hello.controller;

import hello.model.User;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

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

    @GetMapping("/addd")
    public ModelAndView addPage(@ModelAttribute("message") String message) { //
        ModelAndView modelAndView = new ModelAndView();
        User userNew = new User();
        modelAndView.addObject("userNew", userNew);
        modelAndView.setViewName("adddUser");
        return modelAndView;
    }

    @PostMapping("/addd")
    public ModelAndView addUser(@ModelAttribute("userNew") User user) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.checkTitle(user.getLogin())) {
            modelAndView.setViewName("redirect:/admin");
            userService.addUser(user);
        } else {
            modelAndView.addObject("message",
                    "part with login \"" + user.getLogin() + "\" already exists");
            modelAndView.setViewName("redirect:/add");
        }
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView editPage(@PathVariable("id") int id,
                                 @ModelAttribute("message") String message) {
        User user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView editUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.checkTitle(user.getLogin()) || userService.getUserById(user.getId()).getLogin().equals(user.getLogin())) {
            modelAndView.setViewName("redirect:/admin");
            userService.updateUser(user);
        } else {
            modelAndView.addObject("message",
                    "part with login \"" + user.getLogin() + "\" already exists");
            modelAndView.setViewName("redirect:/edit/" +  + user.getId());
        }
        return modelAndView;
    }

    @GetMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
