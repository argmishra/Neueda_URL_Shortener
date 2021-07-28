package neueda.url.shortner.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@Autowired
	private URLRepository urlRepository;

	private Url url;

	private final String original = "https://www.google.com/";

	private final String small = "http://bit.ly/qqqqq";

	@BeforeEach
	public void setup() {
		url = new Url();
		url.setOriginalUrl(original);
		url.setShortUrl(small);
		urlRepository.save(url);
	}

	@Test
	public void findByShortUrl_success() {
		Optional<Url> url = urlRepository.findByShortUrl(small);
		assertEquals(small, url.get().getShortUrl());
	}

	@Test
	public void findAllByOriginalUrlIn_success() {
		List<Url> url = urlRepository.findAllByOriginalUrlIn(List.of(original));
		assertEquals(1, url.size());
	}

}
