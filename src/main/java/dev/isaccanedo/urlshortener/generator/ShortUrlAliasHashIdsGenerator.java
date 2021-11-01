package dev.isaccanedo.urlshortener.generator;

import org.hashids.Hashids;

/**
 * Generates short URL aliases using hashids library <a href="https://hashids.org/java/" target="_blank">https://hashids.org/java/</a>
 * 
 * <p>
 * Short URL alias should be:
 * <ul>
 * 	<li>Short length</li>
 * 	<li>User friendly (avoid curse words)</li>
 * 	<li>Unique</li>
 * 	<li>Fast to generate</li>
 * 	<li>Easy to encode and decode</li>
 * </ul>
 * 
 * <p> hashids fills those requirements and is a mature and widely used library for that purpose.
 * 
 */
public class ShortUrlAliasHashIdsGenerator implements ShortUrlAliasGenerator {
	@Override
	public String generate(String longUrl) {
		return new Hashids(longUrl).encode(1, 2, 3);
	}
}
