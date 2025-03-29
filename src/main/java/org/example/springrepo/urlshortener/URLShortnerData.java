package org.example.springrepo.urlshortener;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(
        name = "shortened_url",
        indexes = @Index(name = "idx_short_url", columnList = "short_url")
)
@RequiredArgsConstructor
@NoArgsConstructor
public class URLShortnerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 40)
    @NonNull
    private String shortUrl;

    @Column(nullable = false)
    @NonNull
    private String longUrl;
}
