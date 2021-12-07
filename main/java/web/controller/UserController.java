package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDAO;
import web.model.MyUserDetails;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/admin/users/new")
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		return "new";
	}

	@PostMapping("/admin/users")
	public String addUser(@ModelAttribute("user") User user) {
		userService.addUser(user);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users")
	public String getAllUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "getAllUsers";
	}

	@PostMapping("/admin/users/{id}/delete")
	public String deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		model.addAttribute("user", userService.getUser(id));
		return "edit";
	}

	@PostMapping("/admin/users/{id}")
	public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
		userService.updateUser(id, user);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/{id}")
	public String getUser(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		return "getUser";
	}

	@RequestMapping(value = "/user/hello", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Authentication authentication) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("Welcome to your home page!");
		messages.add("Your name: " + authentication.getName());
		messages.add("Your role/s: " + authentication.getAuthorities());
		model.addAttribute("messages", messages);
		return "hello";
	}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

}