package PJ03;

import java.io.*;
import java.util.*;
/**
 *
 * Project 3 - MusicAnalyzer.java
 * 
 * Analyzes music from artists into and from files. 
 * @author Naren Rachapalli, lab sec 008 
 * @version October 31, 2020
 */
// /Users/rain09/Workspace/Eclipse-ws/Tester/src/PJ03/SampleSong.song
// /Users/rain09/Workspace/Eclipse-ws/Tester/src/PJ03/Test.txt
public class MusicAnalyzer {
	private static Artist[] artists;
	private static MusicAnalyzer musicAnalyzer;
	private static Song[] songs;

	public MusicAnalyzer() {
		this.artists = new Artist[10];
		this.songs = new Song[10];
	}

	public static void showMenu() {
		System.out.println("****************");
		System.out.println("Music Analyzer");
		System.out.println("(1) List all songs by an artist");
		System.out.println("(2) List all features on a song");
		System.out.println("(3) List the main artist of a song");
		System.out.println("(4) How many songs does this artist have?");
		System.out.println("(5) What is the length of a specific song?");
		System.out.println("(6) What genre does this artist fall under?");
		System.out.println("(7) What are the songs which are by a specific artist featuring other specific artists?");
		System.out.println("(8) Export all artist data by artist");
		System.out.println("(9) Exit");
		System.out.println("****************");
	}

	public static void performInitialization(Scanner scanner) throws InvalidSongFormatException {
		System.out.println("What is the name of the song file you'd like to read?");
		String fileName = scanner.nextLine();
		File songFile = new File(fileName);
		if (!fileName.contains(".")) {
			throw new InvalidSongFormatException("Make sure you include the extension of your file!");
		}
		if (fileName.indexOf(".") == fileName.length() - 1) {
			throw new InvalidSongFormatException("Make sure you enter a valid file extension!");
		}
		if (!songFile.exists()) {
			throw new InvalidSongFormatException("The file you gave doesn't exist!");
		}
		processFile(fileName);
	}

	public static void processFile(String filename) throws InvalidSongFormatException {
		File f = new File(filename);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			try {
				line = br.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (line != null) {
				if (line.split(", ").length < 5) {
					throw new InvalidSongFormatException();
				}
				String accompanyArtists = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
				String others = line.substring(0, line.indexOf("[") - 2);
				String[] songInfo = others.split(", ");
				String[] accArtists = accompanyArtists.split(", ");  
				String songName = songInfo[0];
				String artistName = songInfo[1];
				String songGenre = songInfo[2];
				int songLength = 0;
				try {
					songLength = Integer.parseInt(songInfo[3]);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
				Artist mainArtist = new Artist(artistName, songGenre);
				Artist[] accompanyingArtists = new Artist[accArtists.length];
				if (!findArtistByName(artistName)) {
					musicAnalyzer.addArtist(mainArtist);
				} 
				for (int i = 0; i < accompanyingArtists.length; i++) {
					if (accArtists[i] != null && !accArtists[i].equals("")) {
						if (!findArtistByName(accArtists[i])) {
							Artist newArtist = new Artist(accArtists[i], songGenre);
							musicAnalyzer.addArtist(newArtist);
							accompanyingArtists[i] = newArtist;
						}
					}
				}
				Song newSong = new Song(songName, songLength, songGenre, mainArtist, accompanyingArtists);
				if (!findSongByName(songInfo[0])) {
					musicAnalyzer.addSong(newSong);
				}
				for (int i = 0; i < accompanyingArtists.length; i++) {
					if (accompanyingArtists[i] != null) {
						accompanyingArtists[i].recordFeaturedSong(newSong);
					}
				}
				mainArtist.recordOwnSong(newSong);	
				try {
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean findArtistByName(String name) {
		for (int i = 0; i < artists.length; i++) {
			if (artists[i] != null) {
				if (artists[i].getArtistName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean findSongByName(String name) {
		for (int i = 0; i < songs.length; i++) {
			if (songs[i] != null) {
				if (songs[i].getSongName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public int addArtist(Artist artist) {
		for (int i = 0; i < artists.length; i++) {
			if (artists[i] != null) {
				if (artists[i].equals(artist)) {
					return -1;
				}
			}
		}
		for (int i = 0; i < artists.length; i++) {
			if (artists[i] == null) {
				artists[i] = artist;
				return i;
			}
		}
		int newLength = artists.length * 2;
		Artist[] updated = Arrays.copyOf(artists, newLength);
		updated[artists.length] = artist;
		artists = updated;
		return (artists.length / 2);
	}

	public void addSong(Song song) {
		for (int i = 0; i < songs.length; i++) {
			if (songs[i] != null) {
				if (songs[i].equals(song)) {
					return;
				}
			}
		}
		for (int i = 0; i < songs.length; i++) {
			if (songs[i] == null) {
				songs[i] = song;
				return;
			}
		}
		int newLength = songs.length * 2;
		Song[] updated = Arrays.copyOf(songs, newLength);
		updated[songs.length] = song;
		songs = updated;
	}

	public void listSongsByArtist(String artistName) {
		boolean artistFound = false;
		int count = 0;
		for (Song s : songs) {
			if (s != null) {
				if (s.getMainArtist().getArtistName().equals(artistName)) {
					artistFound = true;
					count++;
					System.out.printf("(%d) %s\n", count, s.getSongName());
				}
			}
		} 
		if (!artistFound) {
			System.out.println("The artist you entered couldn't be found!");
		}
	}

	public void listFeaturesOnSong(String songName) {
		boolean songFound = false;
		int features = 0;
		for (Song s : songs) {
			if (s != null) {
				if (s.getSongName().equals(songName)) {
					for (Artist a : s.getAccompanyingArtists())
						if (a != null) {
							features++;
							songFound = true;
							System.out.printf("(%d) %s\n", features, a.getArtistName());
						}
				}
			}	
		}
		if (!songFound) {
			System.out.println("The song you entered couldn't be found!");
		}

		if (features == 0) {
			System.out.println("This song doesn't have any features!");
		}
	}

	public void findMainArtistOnSong(String songName) {
		boolean songFound = false;
		for (Song s : songs) {
			if (s != null) {
				if (s.getSongName().equals(songName)) {
					songFound = true;
					System.out.println("The song you entered was recorded by " + s.getMainArtist().getArtistName());
				}
			}
		}
		if (!songFound) {
			System.out.println("The song you entered couldn't be found!");
		}
	}

	public void countSongsByArtist(String artistName) {
		int count = 0;
		for (Artist a : artists) {
			if (a != null) {
				if (a.getArtistName().equals(artistName)) {
					for (int i = 0; i < a.getArtistSongs().length; i++) {
						if (a.getArtistSongs()[i] != null) {
							count++;
							System.out.println(i);
						}
					}
				}
			}
		}
		System.out.printf("The artist you entered has recorded %d songs\n", count);
	}

	public void findSongLength(String songName) {
		int minutes = 0;
		int seconds = 0;
		for (Song s : songs) {
			if (s != null) {
				if (s.getSongName().equals(songName)) {
					minutes = s.getSongLengthInSeconds() / 60;
					seconds = s.getSongLengthInSeconds() % 60;
					if (minutes < 10 && seconds < 10) {
						System.out.printf("0%d:0%d\n", minutes, seconds);
					} else if (minutes >= 10 && seconds < 10) {
						System.out.printf("%d:0%d\n", minutes, seconds);
					} else if (minutes < 10 && seconds >= 10) {
						System.out.printf("0%d:%d\n", minutes, seconds);
					} else {
						System.out.printf("%d:%d\n", minutes, seconds);
					}
					return;	
				}
			}
		}
		System.out.println("The song you entered couldn't be found!");
		return;
	}

	public void findArtistGenre(String artistName) {
		boolean artistFound = false;
		for (Artist i : artists) {
			if (i != null) {
				if (i.getArtistName().equals(artistName)) {
					artistFound = true;
					i.calculateArtistGenre();
					System.out.printf("The artist you entered has a genre of %s.\n",i.getArtistGenre());
					return;
				}

			}
		}
		if (!artistFound) {
			System.out.println("The artist you entered couldn't be found!");
			return;
		}
	}

	public void findArtistsAndFeatures(String artistName, String[] features) {
		boolean match = false;
		int count = 0;
		for (int i = 0; i < songs.length; i++) {
			if (songs[i] != null) {
				if (songs[i].getMainArtist().getArtistName().equals(artistName)) {
					count++;
					Artist[] matching = songs[i].getAccompanyingArtists();
					String[] names = new String[matching.length];
					for (int j = 0; j < matching.length; j++) {
						if (matching[j] != null) {
							names[j] = matching[j].getArtistName();
						}
					}
					if (Arrays.equals(features, names)) {
						match = true;
						System.out.println(songs[i].getSongName());
					}
				}
			}
		}
		if (count == 0) {
			System.out.println("The artist has no songs!");
			return;
		}
		if (!match) {
			System.out.println("The artist and features you entered couldn't be found!");
		}
	}


	public void exportByArtist(Scanner scanner, String filename) {
		File f = new File(filename);
		int input = 0;
		String sb = "";
		if (f.exists()) {
			System.out.println("The file you entered already exists. Continuing will overwrite it. Enter 1 if this is okay, or 2 to exit.");
			input = scanner.nextInt();
			while (input != 1 && input != 2) {
				System.out.println("Invalid choice. Try again.");
				input = scanner.nextInt();
			}
			if (input == 2) {
				return;
			}
			if (input == 1) {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(f, false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				PrintWriter pw = new PrintWriter(fos);
				for (Artist a : artists) {
					if (a != null) {
						sb = a.getArtistName() + "\t";
						for (int i = 0; i < a.getArtistSongs().length; i++) {
							if (a.getArtistSongs()[i] != null) {
								if (i == a.getArtistSongs().length - 1) {
									sb += a.getArtistSongs()[i].getSongName();
								} else {
									sb += a.getArtistSongs()[i].getSongName() + ", ";
								}
							}
						}
						pw.write(sb + "\n");
					}
					sb = "";
				}
				pw.close();
			}
		}

	}

	public static void main(String[] args) throws InvalidSongFormatException {
		Scanner scanner = new Scanner(System.in);
		musicAnalyzer = new MusicAnalyzer();
		int choice = 0;
		boolean loop = true;
		performInitialization(scanner);
		showMenu();
		while (loop) {
			try {
				choice = scanner.nextInt();
			} catch (NumberFormatException nfe) {
				System.out.println("You entered something other than a number, try again!");
				showMenu();
			}
			if (choice > 9 || choice < 1) {
				choice = 9;
			}
			switch (choice) {
			case 1:
				String artistName1 = "";
				System.out.println("What is the name of the artist you want all the songs by?");
				scanner.nextLine();
				artistName1 = scanner.nextLine();
				musicAnalyzer.listSongsByArtist(artistName1);
				break;
			case 2:
				String songFeaturesName = "";
				System.out.println("What is the name of the song you want the features of?");
				scanner.nextLine();
				songFeaturesName = scanner.nextLine();
				musicAnalyzer.listFeaturesOnSong(songFeaturesName);
				break;
			case 3:
				String songArtistName = "";
				System.out.println("What is the name of the song you want the main artist of?");
				scanner.nextLine();
				songArtistName = scanner.nextLine();
				musicAnalyzer.findMainArtistOnSong(songArtistName);
				break;
			case 4:
				String artistName = "";
				System.out.println("What is the name of the artist?");
				scanner.nextLine();
				artistName = scanner.nextLine();
				musicAnalyzer.countSongsByArtist(artistName);
				break;
			case 5:
				String songName = "";
				System.out.println("What is the name of the song?");
				scanner.nextLine();
				songName = scanner.nextLine();
				musicAnalyzer.findSongLength(songName);
				break;
			case 6:
				String artistNameGenre = "";
				System.out.println("What is the name of the artist?");
				scanner.nextLine();
				artistNameGenre = scanner.nextLine();
				musicAnalyzer.findArtistGenre(artistNameGenre);
				break;
			case 7:
				String artistMainName = "";
				int numOfFeatures = 0;
				String[] featuresStrings;
				System.out.println("What is the name of the main artist?");
				scanner.nextLine();
				artistMainName = scanner.nextLine();
				System.out.println("How many features are there?");
				numOfFeatures = scanner.nextInt();
				featuresStrings = new String[numOfFeatures];
				System.out.println("Enter the artist names, each one on its own line.");
				for (int i = 0; i < featuresStrings.length; i++) {
					scanner.nextLine();
					featuresStrings[i] = scanner.nextLine();
				}
				if (featuresStrings != null && artistMainName != null) {
					musicAnalyzer.findArtistsAndFeatures(artistMainName, featuresStrings);
				}
				break;
			case 8:
				String fileName = "";
				System.out.println("What is the filename you want to export to? Be sure to include the extension.");
				scanner.nextLine();
				fileName = scanner.nextLine();
				if (fileName != null) {
					musicAnalyzer.exportByArtist(scanner, fileName);
				}
				break;
			case 9:
				System.out.println("Exiting the Music Analyzer");
				loop = false;
				break;
			}
		}	
		scanner.close();
	}

}