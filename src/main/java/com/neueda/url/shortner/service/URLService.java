package com.neueda.url.shortner.service;

import java.util.List;

import com.neueda.url.shortner.model.Url;

public interface URLService {

	public List<Url> createShortUrl(List<String> urls);

	public String getOriginalUrl(Url url);

}
