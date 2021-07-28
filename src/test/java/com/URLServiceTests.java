package com;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.neueda.url.shortner.NeuedaURLShortnerApplication;
import com.neueda.url.shortner.exception.URLNotFoundException;
import com.neueda.url.shortner.model.Url;
import com.neueda.url.shortner.repo.URLRepository;
import com.neueda.url.shortner.service.URLService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { NeuedaURLShortnerApplication.class })
@Transactional
public class URLServiceTests {

	// assert Values
	// change method name
	// add test
	// move to package

	@MockBean
	private URLRepository urlRepository;

	@Autowired
	private URLService urlService;

	Url url;

	String original = "https://www.google.com/";

	String small = "http://bit.ly/qqqqq";

	@BeforeEach
	public void setup() {
		url = new Url();
		url.setOriginalUrl(original);
		url.setShortUrl(small);
		Mockito.when(urlRepository.findByShortUrl(small)).thenReturn(Optional.of(url));
	}

	@Test
	public void findByShortUrl() {
		urlService.getOriginalUrl(url);
	}

	@Test
	public void fail() {
		Mockito.when(urlRepository.findByShortUrl(small)).thenReturn(Optional.empty());

		assertThrows(URLNotFoundException.class, () -> {
			urlService.getOriginalUrl(url);
		});
	}

}
