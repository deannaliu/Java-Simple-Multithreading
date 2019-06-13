import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Example extends Application {
    static final ProgressBar bar = new ProgressBar(0);
    static double max = 1000000.0;

    public static void main(String... args) {
        new Thread(() -> {
            for (int i = 0; i < max; i++) {
                final int counter = i;
                Platform.runLater(() -> bar.setProgress(counter / max));
                System.out.println(i);
            }
        }).start(); //this starts the thread

       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox();
        HBox toolbar = new HBox();
        toolbar.getChildren().add(bar);
        box.getChildren().add(toolbar);
        Scene scene = new Scene(box);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}