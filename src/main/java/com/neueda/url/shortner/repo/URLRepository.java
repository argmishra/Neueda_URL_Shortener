package com.neueda.url.shortner.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neueda.url.shortner.model.Url;

@Repository
public interface URLRepository extends JpaRepository<Url, Long> {

	public List<Url> findAllByOriginalUrlIn(List<String> urls);

	public Optional<Url> findByShortUrl(String url);

}
