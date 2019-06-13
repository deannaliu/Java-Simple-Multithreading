import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Example2 extends Application{
   static final ProgressBar bar = new ProgressBar();
   ProgressIndicator indicator;
    public static void main(String... args) {
        Task task = new Task() {
            @Override
            protected Integer call() throws Exception {
                int iterations;
                for (iterations = 0; iterations < 10000000; iterations++) {
                    updateProgress(iterations, 10000000);
                    System.out.println(iterations);
                }
                return iterations;
            }
        };
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        launch(args);
    }

        public void start(Stage primaryStage) throws Exception {
            VBox box     = new VBox();
            HBox toolbar = new HBox();
            toolbar.getChildren().add(bar);
            box.getChildren().add(toolbar);
            Scene scene = new Scene(box);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
