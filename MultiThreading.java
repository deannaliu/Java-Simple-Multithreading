import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.concurrent.locks.ReentrantLock;
import javafx.stage.Stage;

public class MultiThreading extends Application {

    ProgressBar bar;
    ProgressIndicator indicator;
    Button button;
    Label processLabel;

    int numTasks = 0;

    private ReentrantLock threadLock = new ReentrantLock();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box     = new VBox();
        HBox toolbar = new HBox();
        bar = new ProgressBar(0);
        indicator = new ProgressIndicator(0);
        indicator.setStyle("font-size: 16pt");
        toolbar.getChildren().add(bar);
        toolbar.getChildren().add(indicator);
        button = new Button("Restart");
        processLabel = new Label();
        processLabel.setFont(new Font("Serif", 16));
        box.getChildren().add(toolbar);
        box.getChildren().add(button);
        box.getChildren().add(processLabel);
        Scene scene = new Scene(box);
        primaryStage.setScene(scene);

        button.setOnAction(e -> {
            Task task = new Task() {
                int task = numTasks++;
                double max = 200;
                double perc;

                @Override
                protected Void call() throws Exception {
                    threadLock.lock();
                    try{
                    for (int i = 0; i < 200; i++) {
                        System.out.println(i);
                        perc = i / max;
                        Platform.runLater(() -> {
                            bar.setProgress(perc);
                            indicator.setProgress(perc);
                            processLabel.setText("Task number " + task);
                        });

                        // sleep each frame
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    }
                }
                        finally{
                        threadLock.unlock();
                        return null;
                    }

                    } //closes call
            }; //closes the task
            Thread thread = new Thread(task);
            thread.start();
        }); // end of button
        primaryStage.show();
    }
}