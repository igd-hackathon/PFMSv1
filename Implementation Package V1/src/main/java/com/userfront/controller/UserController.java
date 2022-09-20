package com.userfront.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.User;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Principal principal, Model model) {
	User user = userService.findByUsername(principal.getName());
	model.addAttribute("user", user);
	return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profilePost(@ModelAttribute("user") User newUser, Model model) {
	User user = userService.findByUsername(newUser.getUsername());
	user.setUsername(newUser.getUsername());
	user.setFirstName(newUser.getFirstName());
	user.setLastName(newUser.getLastName());
	user.setEmail(newUser.getEmail());
	user.setPhone(newUser.getPhone());
	model.addAttribute("user", user);
	userService.saveUser(user);
	return "profile";
    }

    @GetMapping(path = "/download/code")
    public ResponseEntity<Resource> downloadsource() throws IOException {
	File file = new File("/Users/sundaravelpalanivel/Desktop/tes/source.zip");
	Path path = Paths.get(file.getAbsolutePath());
	ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
	return ResponseEntity.ok().headers(this.headers("source.zip")).contentLength(file.length())
		.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    @GetMapping(path = "/download/application")
    public ResponseEntity<Resource> downloadApplication() throws IOException {
	File file = new File("/Users/sundaravelpalanivel/Desktop/tes/application.zip");
	Path path = Paths.get(file.getAbsolutePath());
	ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
	return ResponseEntity.ok().headers(this.headers("application.zip")).contentLength(file.length())
		.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    @GetMapping(path = "/user/addfields")
    public String addFieldsUser() throws IOException {
	return "customerAddField";
    }

    @RequestMapping(value = "/user/service", method = RequestMethod.POST)
    public String userDownload() {
	return "customerDownloadOptions";
    }

    @GetMapping(path = "/loan/addfields")
    public String addFieldsloan() throws IOException {
	return "loamAddnield";
    }

    @RequestMapping(value = "/loan/service", method = RequestMethod.POST)
    public String loanDownload() {
	return "loanDownloadOptions";
    }

    @GetMapping(path = "/account/addfields")
    public String addFieldsAccount() throws IOException {
	return "accountAddField";
    }

    @RequestMapping(value = "/account/service", method = RequestMethod.POST)
    public String AccountDownload() {
	return "accountDownloadOptions";
    }

    @RequestMapping(value = "/loan/application", method = RequestMethod.GET)
    public String account() {
	return "loanApplication";

    }

    private HttpHeaders headers(String name) {

	HttpHeaders header = new HttpHeaders();
	header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
	header.add("Cache-Control", "no-cache, no-store," + " must-revalidate");
	header.add("Pragma", "no-cache");
	header.add("Expires", "0");
	return header;

    }
}
