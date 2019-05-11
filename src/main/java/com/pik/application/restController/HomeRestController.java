package com.pik.application.restController;

import com.pik.application.domain.User;
import com.pik.application.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;


@RestController
@RequestMapping("/api")
public class HomeRestController {
	@Autowired
	private UserRepository userRepository;
	private User userSaved;

	@RequestMapping("/user")
	public User user(Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		return userRepository.findOneByUsername(loggedUsername);
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password,
                                                     HttpServletResponse response) throws IOException {
		String token = null;
		User user = userRepository.findOneByUsername(username);
		userSaved = user;
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			token = Jwts.builder().setSubject(username).claim("roles", user.getRoles()).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey").compact();
			tokenMap.put("token", token);
			tokenMap.put("user", user);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
		} else {
			tokenMap.put("token", null);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
		}
	}

	public User getUser(){
		return userSaved;
	}
}
