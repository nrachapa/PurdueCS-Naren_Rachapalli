package PJ03;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Project 3 - Artist.java
 * 
 * Artist class that describes the construction of a Artist object
 * @author Naren Rachapalli, lab sec 008 
 * @version October 31, 2020
 */
public class Artist extends Object {
	private Song[] appearsOnSongs;
	private String artistGenre;
	private final String artistName;
	private Song[] artistSongs;

	public Artist(String artistName, String artistGenre) {
		this.artistName = artistName;
		this.artistGenre = artistGenre;
		this.appearsOnSongs = new Song[5];
		this.artistSongs = new Song[5];
	}

	public Artist(String artistName) {
		this.artistName = artistName;
		this.artistGenre = "";
		this.appearsOnSongs = new Song[5];
		this.artistSongs = new Song[5];
	}

	public Artist(Artist copy) {
		this.appearsOnSongs = copy.getAppearsOnSongs();
		this.artistGenre = copy.getArtistGenre();
		this.artistName = copy.getArtistName();
		this.artistSongs = copy.getArtistSongs();
	}

	/**
	 * @return the appearsOnSongs
	 */
	public Song[] getAppearsOnSongs() {
		return appearsOnSongs;
	}

	/**
	 * @return the artistGenre
	 */
	public String getArtistGenre() {
		return artistGenre;
	}

	/**
	 * @return the artistName
	 */
	public String getArtistName() {
		return artistName;
	}

	/**
	 * @return the artistSongs
	 */
	public Song[] getArtistSongs() {
		return artistSongs;
	}

	public void recordFeaturedSong(Song song) {
		boolean artistAdded = false;
		for (int i = 0; i < this.appearsOnSongs.length; i++) {
			if (this.appearsOnSongs[i] != null) {
				if (this.appearsOnSongs[i].equals(song)) {
					artistAdded = true;
					calculateArtistGenre();
				}
			}
		}
		for (int i = 0; i < this.appearsOnSongs.length; i++) {
			if (!artistAdded) {
				if (this.appearsOnSongs[i] == null) {
					this.appearsOnSongs[i] = new Song(song);
					artistAdded = true;
					calculateArtistGenre();
				}
			}
		}
		for (int i = 0; i < this.appearsOnSongs.length; i++) {
			if (!artistAdded) {
				int newLength = this.artistSongs.length * 2;
				Song[] update = new Song[newLength];
				for (int j = 0; i < this.artistSongs.length; i++) {
					update[j] = this.artistSongs[j];
				}
				update[this.artistSongs.length] = song;
				this.artistSongs = update;
				calculateArtistGenre();
			}
		}
	}

	public void recordOwnSong(Song song) {
		boolean artistAdded = false;
		for (int i = 0; i < this.artistSongs.length; i++) {
			if (this.artistSongs[i] != null) {
				if (this.artistSongs[i].equals(song)) {
					artistAdded = true;
					calculateArtistGenre();
					return;
				}
			}
		}
		for (int i = 0; i < this.artistSongs.length; i++) {
			if (!artistAdded) {
				if (this.artistSongs[i] == null) {
					this.artistSongs[i] = new Song(song);
					artistAdded = true;
					calculateArtistGenre();
					return;
				}
			}
		}
		for (int i = 0; i < this.artistSongs.length; i++) {
			if (!artistAdded) {
				int newLength = this.artistSongs.length * 2;
				Song[] update = new Song[newLength];
				for (int j = 0; j < this.artistSongs.length; j++) {
					update[j] = this.artistSongs[j];
				}
				update[this.artistSongs.length] = new Song(song);
				this.artistSongs = update;
				calculateArtistGenre();
				return;
			}
		}
	}

	public void calculateArtistGenre() {
		int newLength = this.appearsOnSongs.length + this.artistSongs.length;
		String[] genres = new String[newLength];
		String genreString = "";
		int count = 0;
		int max = 0;
		for (int i = 0; i < this.appearsOnSongs.length; i++) {
			if (this.appearsOnSongs[i] != null) {
				if (!this.appearsOnSongs[i].getSongGenre().equals("")) {
					genres[i] = this.appearsOnSongs[i].getSongGenre();
				}
			}
		}
		for (int i = this.appearsOnSongs.length; i < newLength; i++) {
			if (this.artistSongs[i - this.appearsOnSongs.length] != null) {
				if (!this.artistSongs[i - this.appearsOnSongs.length].getSongGenre().equals("")) {
					genres[i] = this.artistSongs[i - this.appearsOnSongs.length].getSongGenre();
				}
			}
		}
		Arrays.sort(genres, Comparator.nullsLast(Comparator.naturalOrder()));
		if (genres[0] != null || !genres[0].equals("")) {
			genreString = genres[0];
		}
		for (int i = 1; i < genres.length; i++) {
			if (genres[i] != null && genres[i - 1] != null) {
				if (genres[i].equalsIgnoreCase(genres[i - 1])) {
					count += 1;
				} else if (genres[i].equalsIgnoreCase(genres[i - 1])) {
					if (count > max) {
						max = count;
						genreString = genres[i - 1];
					}
				}
			}
		}
		this.artistGenre = genreString;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		Artist artist = (Artist) o;
		if (artist.getArtistName().equals(artistName)) {
			if (artist.getArtistGenre().equals(artistGenre)) {
				if (artist.getArtistSongs().equals(artistSongs)) {
					if (artist.getAppearsOnSongs().equals(appearsOnSongs)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}

