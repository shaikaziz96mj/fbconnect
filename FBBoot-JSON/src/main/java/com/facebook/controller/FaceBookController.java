package com.facebook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facebook.service.FaceBookService;

@RestController
@RequestMapping("/")
public class FaceBookController {
	
	@Autowired
	private FaceBookService facebookService;

	@GetMapping
	public List<String> welcome() {
		List<String> urls=new ArrayList<String>();
		urls.add("http://localhost:4041/generateFaceBookAuthorizeURL");
		urls.add("http://localhost:4041/getUserData");
		urls.add("http://localhost:4041/getUserFeed");
		urls.add("http://localhost:4041/getUserAlbum");
		urls.add("http://localhost:4041/getUserAccounts");
		urls.add("http://localhost:4041/getUserAccount");
		urls.add("http://localhost:4041/getUserGroupMembership");
		return urls;
	}
	
	@GetMapping("/generateFaceBookAuthorizeURL")
	public String generateFaceBookAuthorizeURL() {
		return facebookService.generateFaceBookAuthorizeURL();
	}
	
	@GetMapping("/facebook")
	public void generateFaceBookAccessToken(@RequestParam("code") String code) {
		facebookService.generateFaceBookAccessToken(code);
	}
	
	@GetMapping("/getUserData")
	public String getUserData(){
		return facebookService.getUserData();
	}
	
	@GetMapping("/getUserFeed")
	public PagedList<Post> getUserFeed(){
		return facebookService.getUserFeed();
	}
	
	@GetMapping("/getUserAlbum")
	public PagedList<Album> getUserAlbum(){
		return facebookService.getUserAlbum();
	}
	
	@GetMapping("/getUserAccounts")
	public PagedList<Account> getUserAccounts(){
		return facebookService.getUserAccounts();
	}
	
	@GetMapping("/getUserAccount/{accountId}")
	public Account getUserAccount(@PathVariable String accountId){
		return facebookService.getUserAccount(accountId);
	}
	
	@GetMapping("/getUserGroupMembership")
	public PagedList<GroupMembership> getUserGroupMembership(){
		return facebookService.getUserGroupMembership();
	}
	
}