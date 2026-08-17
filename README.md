# KeyNotes: A Digital Piano Repertoire Tracker

### What will the application do?
This application will be used to record information about different piano pieces, allowing users to view and add the *name of a piece, composer, difficulty level from 1 to 5, genre, and number of pages*, to a personal digital repertoire. When working on a piece, the application will allow the user to fill out a practice log tracked by a daily streak to keep them motivated. Moreover, the user will be able to add each piece into either *"Not Started", "Learning",* and *"Mastered"*, to keep their repertoire organized. 

### Who will use this application?
This application can be used by beginner, intermediate, or advanced piano players looking to organize their repertoire list, and discover pieces similar to their favourite ones. This can also be used by piano players looking to track their practice, keep detailed notes after each session, and stay motivated with daily streaks.

### Why this project is of interest to me
I have been playing the piano for 10 years, and have relied on physical folders to track my entire repertoire, and paper logs for my practice, which became quite inefficient over time. Hence with this project, I will be able to streamline the process for myself, and for other piano students.

### User Stories
- As a user, I want to be able to add a piano piece to my repertoire list.
- As a user, I want to be able to view all the pieces listed in my repertoire.
- As a user, I want to be able to select one piece in my repertoire and view its name, the composer, genre, rating, number of total pages, and practice streak.
- As a user, I want to be able to add all pieces into either category: *“Not Started”, “Learning”, and “Mastered”.* 
- As a user, I want to be able to filter and sort a piece by its name, composer, genre, rating, and number of total pages.
- As a user, I want to be able to select a piece in my repertoire and rate it from 1 to 5 in terms of difficulty.
- As a user, I want to be able to track my practice streak and log in practice hours for each piece.
- As a user, when I selected the quit option from the application menu, I want to be given the option to save my repertoire file, and have the option not to do so.
- As a user, if I choose to save my file, I want to be given the option to retrieve my repertoire data from the file, and resume.

### Instructions for End User
- To locate the visual component, run the file and view the splash screen.
- To add a new piece in the repertoire, click on the "Add new piece" button, and fill in the details.
- To view all of your pieces in one place, click on the "View repertoire" button.
- To remove a piece from your repertoire, click on the "Remove a piece" button, and enter its name.
- To change the status of your piece, click on the "Change piece status button", and enter select a status from the drop down box.
- To view pieces in the *"Not Started"* category, click on the "View pieces in NOT STARTED" button.
- To view pieces in the *"Learning"* category, *log practice hours*, or *add to the daily practice streak*, click on the "View pieces in LEARNING" button.
- To log in practice hours, enter the name of the piece and the number of hours you have practiced.
- To add to the practice streak, enter the name of the piece.
- To view pieces in the *"Mastered"* category, or *add a difficulty rating*, click on the "View pieces in MASTERED" button.
- To add a difficulty rating, enter the name of the piece and select a number from the drop down box.
- To load data from the file, click on the "Load Data" button.
- To save data to the file, click on the "Save Data" button.

### Phase 4: Task 2
```
Thu Mar 27 22:22:51 PDT 2025
Nocturne has been added to the repertoire  
Thu Mar 27 22:22:59 PDT 2025  
Prelude has been added to the repertoire  
Thu Mar 27 22:23:16 PDT 2025  
Clair de Lune has been added to the repertoire  
Thu Mar 27 22:23:33 PDT 2025  
Waltz has been added to the repertoire  
Thu Mar 27 22:23:34 PDT 2025  
Viewed all pieces in the repertoire  
Thu Mar 27 22:23:39 PDT 2025  
Prelude has been removed from the repertoire  
Thu Mar 27 22:23:45 PDT 2025  
A piece has been updated to the LEARNING status  
Thu Mar 27 22:23:47 PDT 2025  
Pieces from NOT_STARTED have been retrieved.  
Thu Mar 27 22:23:48 PDT 2025  
Pieces from LEARNING have been retrieved.  
Thu Mar 27 22:23:51 PDT 2025  
Pieces from MASTERED have been retrieved.
```

### Phase 4: Task 3
#### Follow the Single Responsibility Principle for the RepertoireTracker class:

Based on the Single Responsibility Principle, the RepertoireTracker class should only be adding, removing, viewing, and changing the status of pieces. However, it also handles different methods that are only applicable to each status type, which makes it too complex. Instead, I could create 2 new classes each representative of the LEARNING and MASTERED status types, and handle its methods there. For example, a new class with the name 'LearningPieces' could be created, and include methods for logging practice, and increasing practice streak. Similarly, a class with the name 'MasteredPieces' would have methods to give the pieces a difficulty rating, and additional methods if need be. This would make the code more cohesive, reducing coupling, increase readability, and make it easy to test, use, and debug.


### Citations
- Video used to learn about Java Swing   
https://www.youtube.com/watch?v=Kmgo00avvEw&t=2449s

- Used to understand Splash Screen   
https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui

- Used to make the splash screen logo   
https://www.canva.com/

- Used to make the UML Diagram   
https://app.diagrams.net/
