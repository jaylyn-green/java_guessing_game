package src.main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.Random;

public class App extends Application {
    private String[] songs;
    private String currentSong;
    private int score = 0;
    private Label scoreLabel;
    private TextField guessField;

    @Override
    public void start(Stage primaryStage) {
        loadSongs();
        Label label = new Label("Guess the Song!");
        Button playButton = new Button("Play Clip");
        playButton.setOnAction(e -> playRandomClip());
        
        guessField = new TextField();
        Button submitButton = new Button("Submit Guess");
        submitButton.setOnAction(e -> checkGuess());

        scoreLabel = new Label("Score: " + score);

        VBox layout = new VBox(10, label, playButton, guessField, submitButton, scoreLabel);
        Scene scene = new Scene(layout, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Music Guessing Game");
        primaryStage.show();
    }

    private void loadSongs() {
        File dir = new File("src/main/resources");
        songs = dir.list((dir1, name) -> name.endsWith(".wav"));
    }

    private void playRandomClip() {
        if (songs == null || songs.length == 0) {
            return;
        }
        Random rand = new Random();
        currentSong = songs[rand.nextInt(songs.length)];
        try {
            AudioPlayer.play("src/main/resources/" + currentSong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkGuess() {
        String guess = guessField.getText().trim();
        String correctAnswer = currentSong.replace(".wav", "");
        if (guess.equalsIgnoreCase(correctAnswer)) {
            score++;
        } else {
            score--;
        }
        scoreLabel.setText("Score: " + score);
        guessField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
