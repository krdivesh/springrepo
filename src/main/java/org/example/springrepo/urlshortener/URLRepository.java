package org.example.springrepo.urlshortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URLShortnerData, Long> {
    Optional<URLShortnerData> findByShortUrl(String shortUrl);
    Optional<URLShortnerData> findByLongUrl(String longUrl);
}
