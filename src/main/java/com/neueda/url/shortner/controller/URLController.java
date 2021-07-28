package com.neueda.url.shortner.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.url.shortner.model.Url;
import com.neueda.url.shortner.service.URLService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("url")
@Slf4j
public class URLController {

	@Autowired
	private URLService urlService;

	@PostMapping(value = "/short")
	public List<Url> createShortUrl(@Valid @RequestBody List<String> urls) {
		log.info("Create short url");
		return urlService.createShortUrl(urls);
	}

	@GetMapping(value = "/original")
	public void getOriginalUrl(@Valid @RequestBody Url url, HttpServletResponse response) throws IOException {
		log.info("Get original url");
		response.sendRedirect(urlService.getOriginalUrl(url));
	}

}
