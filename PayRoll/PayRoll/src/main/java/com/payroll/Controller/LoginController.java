	package com.payroll.Controller;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.payroll.service.CustomUserDetailsService;
	
	@Controller
	public class LoginController {
		@Autowired
	    private CustomUserDetailsService userDetailsService;
	
	    @GetMapping("/login")
	    public String login() {
	        return "login";
	    }
	
	    @PostMapping("/login")
	    public String login(@RequestParam("username") String username,
	                        @RequestParam("password") String password,
	                        RedirectAttributes redirectAttributes) {
	        // Authenticate user using CustomUserDetailsService
	        if (userDetailsService.authenticateUser(username, password)) {
	            return "redirect:/dashboard"; // Redirect to dashboard if authentication is successful
	        } else {
	            // Add error message and redirect back to login page
	            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
	            return "redirect:/login";
	        }
	    }
	
	    @GetMapping("/secured/admin")
	    public String adminPage() {
	        return "admin";
	    }
	}
	
