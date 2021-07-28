package neueda.url.shortner.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.neueda.url.shortner.controller.URLController;
import com.neueda.url.shortner.model.Url;
import com.neueda.url.shortner.service.URLService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { NeuedaURLShortnerApplication.class })
@Transactional
public class URLControllerTests {

	@MockBean
	private URLService urlService;

	@MockBean
	private HttpServletResponse response;

	@Autowired
	private URLController urlController;

	private Url url;

	private final String original = "https://www.google.com/";

	private final String small = "http://bit.ly/qqqqq";

	private List<String> urls;

	@BeforeEach
	public void setup() throws IOException {
		url = new Url();
		url.setShortUrl(small);
		url.setOriginalUrl(original);
		urls = new ArrayList();
		urls.add(original);
	}

	@Test
	public void getOriginalUrl_success() throws IOException {
		Mockito.when(urlService.getOriginalUrl(url)).thenReturn(original);
		doNothing().when(response).sendRedirect(original);

		urlController.getOriginalUrl(url, response);

		verify(urlService, times(1)).getOriginalUrl(url);
		verify(response, times(1)).sendRedirect(original);
	}

	@Test
	public void createShortUrl_success() throws IOException {
		Mockito.when(urlService.createShortUrl(urls)).thenReturn(List.of(url));

		List<Url> urlList = urlController.createShortUrl(urls);

		assertEquals(1, urlList.size());
	}

}
