package org.example.springrepo.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class URLShortenerController {
    @Autowired
    URLShortener urlShortener;

    @Autowired
    URLRepository urlRepository;

    public static final String HTTPS = "https://";
    public static final String HTTP = "http://";

    @GetMapping("/shortenUrl/{longUrl}")
    public ResponseEntity<String> shortenUrl(@PathVariable String longUrl) {
        Optional<URLShortnerData> searchByLongUrl = urlRepository.findByLongUrl(longUrl);
        if (searchByLongUrl.isPresent()) {
            return ResponseEntity.status(OK).body(searchByLongUrl.get().getShortUrl() );
        }
        String shortStr = urlShortener.shortenURL(longUrl);
        urlRepository.save(new URLShortnerData(shortStr, longUrl));
        String shortUrl = HTTP+"localhost:9090/"+ shortStr +".com";
        return new ResponseEntity<>(shortUrl, OK);
    }

    @GetMapping("/accessUrl/{shortUrl}")
    public ResponseEntity<String> accessUrl(@PathVariable String shortUrl) {
        Optional<URLShortnerData> shortnedUrlData = urlRepository.findByShortUrl(shortUrl.split(".com")[0]);
        if (shortnedUrlData.isPresent()) {
            return new ResponseEntity<>(HTTPS + shortnedUrlData.get().getLongUrl() + ".com", HttpStatus.TEMPORARY_REDIRECT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
