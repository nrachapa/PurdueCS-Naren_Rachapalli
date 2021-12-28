package PJ03;

/**
 * Project 3 - Song.java
 * 
 * Song class that describes the construction of a SOng object
 * 
 * @author Naren Rachapalli, lab sec 008
 * @version October 31, 2020
 */
public class Song extends Object {
	private final String songName;
	private final int songLengthInSeconds;
	private final String songGenre;
	private final Artist mainArtist;
	private final Artist[] accompanyingArtists;

	public Song(String songName, int songLengthInSeconds, String songGenre, Artist mainArtist,
			Artist[] accompanyingArtists) {
		this.songName = songName;
		this.songLengthInSeconds = songLengthInSeconds;
		this.songGenre = songGenre;
		this.mainArtist = mainArtist;
		this.accompanyingArtists = accompanyingArtists;
	}

	public Song() {
		this.mainArtist = null;
		this.accompanyingArtists = null;
		this.songName = "NO NAME SONG";
		this.songLengthInSeconds = -1;
		this.songGenre = "Unknown";
	}

	public Song(Song copy) {
		this.songName = copy.getSongName();
		this.songLengthInSeconds = copy.getSongLengthInSeconds();
		this.songGenre = copy.getSongGenre();
		this.mainArtist = copy.getMainArtist();
		this.accompanyingArtists = copy.getAccompanyingArtists();
	}

	/**
	 * @return the songName
	 */
	public String getSongName() {
		return songName;
	}

	/**
	 * @return the songLengthInSeconds
	 */
	public int getSongLengthInSeconds() {
		return songLengthInSeconds;
	}

	/**
	 * @return the songGenre
	 */
	public String getSongGenre() {
		return songGenre;
	}

	/**
	 * @return the mainArtist
	 */
	public Artist getMainArtist() {
		return mainArtist;
	}

	/**
	 * @return the accompanyingArtists
	 */
	public Artist[] getAccompanyingArtists() {
		return accompanyingArtists;
	}

	public String getSongLengthInMinutesAndSeconds() {
		int minutes = 0;
		int seconds = 0;
		String line = "";
		if (this.songLengthInSeconds == -1) {
			return line;
		}
		minutes = this.songLengthInSeconds / 60;
		seconds = this.songLengthInSeconds % 60;
		if (minutes < 10 && seconds < 10) {
			line = String.format("0%d:0%d", minutes, seconds);
		} else if (minutes >= 10 && seconds < 10) {
			line = String.format("%d:0%d", minutes, seconds);
		} else if (minutes < 10 && seconds >= 10) {
			line = String.format("0%d:%d", minutes, seconds);
		} else {
			line = String.format("%d:%d", minutes, seconds);
		}
		return line;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		Song song = (Song) o;
		if (song.getSongLengthInSeconds() == songLengthInSeconds) {
			if (song.getSongGenre().equals(songGenre)) {
				if (song.getSongName().equals(songName)) {
					return true;
				}
			}

		}
		return false;
	}
}
