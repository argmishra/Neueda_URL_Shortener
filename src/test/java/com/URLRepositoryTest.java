package com;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.neueda.url.shortner.NeuedaURLShortnerApplication;
import com.neueda.url.shortner.model.Url;
import com.neueda.url.shortner.repo.URLRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { NeuedaURLShortnerApplication.class })
@Transactional
public class URLRepositoryTest {

	// assert Values
	// change method name
	// move to package

	@Autowired
	URLRepository urlRepository;

	Url url;

	@BeforeEach
	public void setup() {
		url = new Url();
		url.setOriginalUrl("original");
		url.setShortUrl("short");
		urlRepository.save(url);
	}

	@Test
	public void findByShortUrl() {
		Optional<Url> url = urlRepository.findByShortUrl("short");
		System.out.print(url.get().getShortUrl());
	}

	@Test
	public void findAllByOriginalUrlIn() {
		List<Url> url = urlRepository.findAllByOriginalUrlIn(List.of("original"));
		System.out.print(url.size());

	}

}
