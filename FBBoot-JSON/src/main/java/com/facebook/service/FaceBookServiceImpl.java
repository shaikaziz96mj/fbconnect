package com.facebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FaceBookServiceImpl implements FaceBookService {
	
	private String accessToken;
	
	@Value("${spring.social.facebook.app-id}")
	private String facebookAppID;
	@Value("${spring.social.facebook.app-secret}")
	private String facebookSecret;
	
	private FacebookConnectionFactory createConnection() {
		return new FacebookConnectionFactory(facebookAppID, facebookSecret);
	}
	
	@Override
	public String generateFaceBookAuthorizeURL() {
		OAuth2Parameters params=new OAuth2Parameters();
		params.setRedirectUri("http://localhost:4041/facebook");
		params.setScope("email");
		return createConnection().getOAuthOperations().buildAuthenticateUrl(params);
	}
	
	@Override
	public void generateFaceBookAccessToken(String code) {
		accessToken=createConnection().getOAuthOperations().exchangeForAccess(code,"http://localhost:4041/facebook",null).getAccessToken();
	}
	
	@Override
	public String getUserData() {
		Facebook facebook=new FacebookTemplate(accessToken);
		String[] fields= {"id","first_name","name","email","birthday","gender","age_range","hometown","inspirational_people"};
		return facebook.fetchObject("me", String.class, fields);
	}
	
	@Override
	public PagedList<Post> getUserFeed() {
		return new FacebookTemplate(accessToken).feedOperations().getFeed();
	}
	
	@Override
	public PagedList<Album> getUserAlbum() {
		return new FacebookTemplate(accessToken).mediaOperations().getAlbums();
	}
	
	@Override
	public PagedList<Account> getUserAccounts() {
		return new FacebookTemplate(accessToken).pageOperations().getAccounts();
	}
	
	@Override
	public Account getUserAccount(String accountId) {
		return new FacebookTemplate(accessToken).pageOperations().getAccount(accountId);
	}
	
	@Override
	public PagedList<GroupMembership> getUserGroupMembership() {
		return new FacebookTemplate(accessToken).groupOperations().getMemberships();
	}
	
}