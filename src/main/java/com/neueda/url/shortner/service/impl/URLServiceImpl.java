package com.neueda.url.shortner.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		List<Url> urlList = urlRepository.findAllByOriginalUrlIn(urls);
		List<String> originalUrlList = new ArrayList<>();
		for (Url url : urlList) {
			originalUrlList.add(url.getOriginalUrl());
		}
		urls.removeAll(originalUrlList);

		Url url = null;
		for (String u : urls) {
			url = new Url();
			url.setOriginalUrl(u);
			url.setShortUrl(PARTIAL_URL + RandomStringUtils.randomAlphanumeric(5).toLowerCase());
			urlList.add(url);
		}

		urlRepository.saveAll(urlList);
		return urlRepository.findAllByOriginalUrlIn(
				Stream.of(urls, originalUrlList).flatMap(Collection::stream).collect(Collectors.toList()));
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

}
