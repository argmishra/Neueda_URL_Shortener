package com.neueda.url.shortner.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.url.shortner.exception.MalformedURLException;
import com.neueda.url.shortner.exception.URLNotFoundException;
import com.neueda.url.shortner.model.Url;
import com.neueda.url.shortner.repo.URLRepository;
import com.neueda.url.shortner.service.URLService;

@Service
public class URLServiceImpl implements URLService {

	@Autowired
	private URLRepository urlRepository;

	public static final String PARTIAL_URL = "http://bit.ly/";

	@Override
	public List<Url> createShortUrl(List<String> urls) {
		this.validation(urls);
		List<String> originalUrlList = getUrlList(urls);

		List<Url> saveList = getSaveList(urls, originalUrlList);
		urlRepository.saveAll(saveList);

		return urlRepository.findAllByOriginalUrlIn(urls);
	}

	@Override
	public String getOriginalUrl(Url url) {
		Optional<Url> checkURL = urlRepository.findByShortUrl(url.getShortUrl());
		if (!checkURL.isPresent()) {
			throw new URLNotFoundException("Record not found");
		}
		return checkURL.get().getOriginalUrl();
	}

	private void validation(List<String> urls) {
		for (String url : urls) {
			try {
				new URL(url);
			} catch (Exception e) {
				throw new MalformedURLException("Malform URL");
			}
		}
	}

	private List<String> getUrlList(List<String> urls) {
		List<String> originalUrlList = new ArrayList<>();
		for (Url url : urlRepository.findAllByOriginalUrlIn(urls)) {
			originalUrlList.add(url.getOriginalUrl());
		}
		return originalUrlList;
	}

	private List<Url> getSaveList(List<String> urls, List<String> originalUrlList) {
		List<Url> saveList = new ArrayList<>();
		Url url = null;
		for (String u : urls.stream().filter(aObject -> !originalUrlList.contains(aObject))
				.collect(Collectors.toList())) {
			url = new Url();
			url.setOriginalUrl(u);
			url.setShortUrl(PARTIAL_URL + RandomStringUtils.randomAlphanumeric(5).toLowerCase());
			saveList.add(url);
		}
		return saveList;
	}

}
