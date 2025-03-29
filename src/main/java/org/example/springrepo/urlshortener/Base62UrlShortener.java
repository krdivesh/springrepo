package org.example.springrepo.urlshortener;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class Base62UrlShortener implements URLShortener {
    public static final int LENGTH_OF_SHORT_URL = 7;
    private static final AtomicLong longCounter = new AtomicLong();
    public static final ArrayList<Character> base62CharList = new ArrayList<>();

    static {
        for (char ch='a'; ch<='z'; ch++) {
            base62CharList.add(ch);
        }

        for (char ch='A'; ch<='Z'; ch++) {
            base62CharList.add(ch);
        }

        for (char ch='0'; ch<='9'; ch++) {
            base62CharList.add(ch);
        }
    }

    @Override
    public String shortenURL(String longUrl) {
        long count = longCounter.incrementAndGet();
        count = Long.MAX_VALUE - count;
        if (count < 0) {
            count = Long.MAX_VALUE+count;
        }
        return encodeBase62(count);

    }

    private String encodeBase62(long count) {
        if (count < 62) {
            return String.valueOf(base62CharList.get((int) count));
        }
        return base62CharList.get((int) (count%62))+ encodeBase62(count/62);
    }
}
