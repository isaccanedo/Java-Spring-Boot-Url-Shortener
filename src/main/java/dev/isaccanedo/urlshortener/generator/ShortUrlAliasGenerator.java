package dev.isaccanedo.urlshortener.generator;

/**
 * Responsible for the generation of short URL aliases from long URLs
 */
public interface ShortUrlAliasGenerator {
	/**
	 * Generates a new short URL alias based on a given long URL
	 * 
	 * @param longUrl long URL
	 * 
	 * @return generated alias from the given long URL
	 */
	String generate(String longUrl);
}
