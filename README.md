# Film App
===================================

One of the final projects produced for the Udacity Android Advanced Nanodegree.

[Android Development Advanced Apps](https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df)

# Project Overview
------------------------

Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing.

Present the user with a grid arrangement of movie posters upon launch.
Allow your user to change sort order via a setting:
* Popular Movies
* Highest Rated
* Favourites

Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
* original title
* movie poster image thumbnail
* A plot synopsis (called overview in the api)
* user rating (called vote_average in the api)
  release date.
  
Allow users to view and play trailers in the youtube app or a web browser.
Allow users to read reviews of a selected movie.
Allow users to mark a movie as a favorite in the details view by tapping a button(star).
Create a database and content provider to store the names and ids of the user's favorite movies.
 
# Skills Applied
---------------------------------

* Build a UI layout for multiple Activities.
* Launch these Activities via Intent.
* Fetch data from themovieDB API.

# Specifications:
---------------------------------

* App is written solely in the Java Programming Language.
* Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
* UI contains a settings menu to toggle the sort order of the movies by: most popular, highest rated, favourites.
* UI contains a screen for displaying the details for a selected movie.
* Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
* When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.
* When a movie poster thumbnail is selected, the movie details screen is launched.
* The titles and IDs of the user’s favorite movies are stored in a native SQLite database and are exposed via a ContentProvider. This ContentProvider is updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.
* When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the ContentProvider.

# Screen Shots:
---------------------------------

![Popular Movies Main](https://cherudek.github.io/FilmApp/film_app_screenshot3.png "Popular Movies Main Screenshot")

![Movie Posters with Settings Menu](https://cherudek.github.io/FilmApp/film_app_screenshot4png.png "Main Activity plus Settings Menu Screenshot")

![Movie Details](https://cherudek.github.io/FilmApp/film_app_screenshot.png "Main Details Screenshot")

![Popular Movies Landscape](https://cherudek.github.io/FilmApp/film_app_screenshot2.png "Popular Movies Landscape")









