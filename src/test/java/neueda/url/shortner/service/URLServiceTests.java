package neueda.url.shortner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
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
import com.neueda.url.shortner.exception.MalformedURLException;
import com.neueda.url.shortner.exception.URLNotFoundException;
import com.neueda.url.shortner.model.Url;
import com.neueda.url.shortner.repo.URLRepository;
import com.neueda.url.shortner.service.URLService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { NeuedaURLShortnerApplication.class })
@Transactional
public class URLServiceTests {

	@MockBean
	private URLRepository urlRepository;

	@Autowired
	private URLService urlService;

	private Url url;

	private final String original = "https://www.google.com/";

	private final String small = "http://bit.ly/qqqqq";

	private List<String> urls;

	@BeforeEach
	public void setup() {
		url = new Url();
		url.setOriginalUrl(original);
		url.setShortUrl(small);
		urls = new ArrayList<>();
		urls.add(original);
	}

	@Test
	public void getOriginalUrl_success() {
		Mockito.when(urlRepository.findByShortUrl(small)).thenReturn(Optional.of(url));

		String actualUrl = urlService.getOriginalUrl(url);

		assertEquals(original, actualUrl);
	}

	@Test
	public void getOriginalUrl_fail() {
		Mockito.when(urlRepository.findByShortUrl(small)).thenReturn(Optional.empty());

		assertThrows(URLNotFoundException.class, () -> {
			urlService.getOriginalUrl(url);
		});
	}

	@Test
	public void createShortUrl_success_recond_present() {
		Mockito.when(urlRepository.findAllByOriginalUrlIn(urls)).thenReturn(List.of(url));

		List<Url> urlList = urlService.createShortUrl(urls);
		assertEquals(1, urlList.size());
	}

	@Test
	public void createShortUrl_success_recond_not_present() {
		Url url2 = new Url();
		url2.setOriginalUrl("https://www.facebook.com/");
		url2.setShortUrl("http://bit.ly/fffff");

		Mockito.when(
				urlRepository.findAllByOriginalUrlIn(List.of("https://www.google.com/", "https://www.facebook.com/")))
				.thenReturn(List.of(url)).thenReturn(List.of(url, url2));

		List<Url> urlList = urlService.createShortUrl(List.of("https://www.google.com/", "https://www.facebook.com/"));
		assertEquals(2, urlList.size());
	}

	@Test
	public void createShortUrl_fail_invalid_url() {
		assertThrows(MalformedURLException.class, () -> {
			urlService.createShortUrl(List.of("anannaaa"));
		});
	}

}
