# PhilosoFill
Login Interface<br>
<img width="598" alt="Screenshot 2024-05-27 at 9 53 26 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/1c29b543-b8f4-429f-9f48-a6eb090092b0">

Create a User<br>
<img width="601" alt="Screenshot 2024-06-04 at 9 10 28 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/ca472d32-873b-4c2e-9a1d-2f557affc941">

Main Menu<br>
<img width="599" alt="Screenshot 2024-05-27 at 9 54 04 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/576b9d99-ab2f-45f8-b0a6-acc8f65273c4">

Level Selection<br>
<img width="599" alt="Screenshot 2024-06-04 at 9 13 21 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/9f7e30cc-8084-4259-8982-492bd7d2ceb6">


Game Interface<br>
<img width="594" alt="Screenshot 2024-05-27 at 9 54 54 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/4b109aa5-39ed-42a3-8d50-fb0852c26adc">

Settlement Interface<br>
<img width="599" alt="Screenshot 2024-06-04 at 9 20 30 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/ea14607e-7bfc-49ab-8c3c-5ce7062bdf4b">

High Score List<br>
<img width="597" alt="Screenshot 2024-05-27 at 9 55 59 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/22ec6f5c-f350-4693-b0c0-e951bc3a7096">


Saved Game Interface<br>
<img width="600" alt="Screenshot 2024-05-27 at 10 02 11 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/0e892a3b-2c74-43f1-aa97-785bb94fe928">


Collection Interface<br>
<img width="598" alt="Screenshot 2024-05-27 at 10 00 36 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/f39e39c6-92bc-4438-9609-1bf2e11d4a1a">

Instructor Tracking Panel<br>
<img width="600" alt="Screenshot 2024-06-04 at 9 15 02 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/bb80cae4-0d59-44f8-9b3c-f1723f954465">

Debug Mode<br>
<img width="598" alt="Screenshot 2024-06-04 at 9 16 23 PM" src="https://github.com/wxi003/PhilosoFill/assets/122643336/4005ed1b-63ac-4bfa-ae16-f9ee6ef0ad22">


## Description
This desktop application is an immersive educational puzzle game designed for teenagers. It transports players to a mystical world of philosophical discovery, where they are challenged to complete famous philosophical quotes by filling in missing words. The game combines the thrill of puzzle-solving with the discovery of ancient wisdom in an engaging, educational format.
## System Requirements
- JDK 19: Ensure that JDK 19 is installed on your system to compile and run the application.
- Maven (3.0 or higher): This project uses Maven for dependency management and building. You can use the Maven plugin in IntelliJ IDEA or have Maven installed on your computer.
## Dependencies and 3rd party software
1. javafx-controls version 21.0.2
2. javafx-fxml version 21.0.2
3. junit-jupiter-api version 5.10.0
4. junit-jupiter-engine version 5.10.0
5. gson version 2.10.1
6. maven-compiler-plugin version 3.11.0
7. maven-jar-plugin version 2.4
8. maven-shade-plugin version 3.2.4
9. javafx-maven-plugin version 0.0.8
## How to build
1. Clone/Download the project into IntelliJ IDEA.
2. Open the project.
3. A maven reload option will appear, click on it. This will obtain any dependencies automatically. No reload option means that maven already installed all the dependencies.
4. Click on the clean option (equivalent to 'mvn clean' if maven is installed in the computer) and Plugins -> javafx -> javafx:jlink(equivalent to 'mvn javafx:jlink' if maven is installed in the computer) from the maven menu.
5. The resulting zip file which contains executable file (app.bat on Windows / app on Mac) will be in the project folder -> target -> appZip.zip -> bin -> app.bat / app.
## How to run
1. Extract All for appZip.zip folder. Go to bin folder and find app.bat / app.
   To run the project correctly on Windows, make sure you also put puzzle.json, saved_game_entries.json, score_entries.json and user.json to the bin folder. For Mac user, move the extracted appZip folder to the home directory and put .json files to the path /Users/yourUsername.
2. If app.bat / app is configured correctly you should be able to just double-click the executable file and run it.
3. Otherwise, from the maven menu, click the Plugins -> javafx -> javafx:run(equivalent to 'mvn javafx:run' if maven is installed in the computer).

## User guide
Profile Interface: choose an existing player or create a new one -> Main Menu: choose Start a new game / Load a saved game / View high scores list / Access tutorial / View collections.
If you find the game window too small, you can adjust the display resolution to make it larger, as our game window size is fixed.

## Instructor Dashboard
1. Click on the play button for the teacher profile.
2. Go to the main menu and enter the password 123456.
3. The Teacher Tracking Interface / Instructor Dashboard will be displayed

## Debug mode
1. Click on the play button for the tester profile.
2. Go to the main menu and enter the password 987654.
3. Debug mode will be activated.

## Contributors
CS2212B Winter 2024: Xi Wang, Chao Zhang, Enqin Liu, Xikai Lin, Yuqian Sun 
