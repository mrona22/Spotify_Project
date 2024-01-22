# Spotify_Project


## Overview

This project implements a Music Playback System in Java, consisting of three main classes: **PlayableItem**, **MusicDatabase**, and **Playlist**. The system provides a robust framework for managing and enjoying music, with features such as song playback, playlist creation, and dynamic navigation.

## Classes

1. **Playable item**
- Represents an individual song with attributes like song name, artist, popularity, length, and URL.
- Methods include playback control, popularity management, completeness check, comparison, and more.

 
2. **MusicDatabase**
- Manages music-related information with data structures like Hashtable and TreeMap.
- Supports operations such as adding songs, searching, and sorting.
- Efficiently handles file input for adding songs to the database.
  
  
3. **Playlist**
- Represents a playlist with support for three playback modes: normal, random, and most frequently listened.
- Methods include playlist management, playback control, mode switching, and status display.
- Empowers users with flexibility and control over their music listening experience.


## Usage

1. **PlayableItem**
- Create instances using the constructor `new PlayableItem(songName, artist, popularity, lastTime, length, url)`.
- Use methods like `getArtist()`, `getSongName()`, `atomicPlay()`, etc.
2. **MusicDatabase**
- Create an instance using new MusicDatabase().
- Add songs using `addSong(name, artist, popularity, duration, url)`.
- Use methods like `size()`, `partialSearchBySongName(name)`, etc.
3. **Playlist**
- Create an instance using new `Playlist()`.
- Add songs using `addPlayableItem(newItem)` or remove using `removePlayableItem(number)`.
- Switch playback modes with `switchPlayingMode(newMode)`.

## *License*

*University of California, San Diego (UCSD) - Coursework Project*


