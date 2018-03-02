package geometry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Random;

public class Geometry extends Application{

    private static final double WIDTH = 800;               // Ширина окна
    private static final double HEIGHT = 600;               // Высота окна
    private static final double BUTTON_WIDTH = 100;         // Ширина кнопки
    private static final double BUTTON_HEIGHT = 20;         // Высота кнопки
    private static final int COLOR_SPAN = 200;              // Диапазон 0-255 умылшенно срезан для получения более тёмных цветов
    static Random rnd = new Random();

    private static Shapes<Rectang> rectangles = new Shapes<>();

    public static void main(String[] args) {
        launch(args);
    }

    void windowSetup(Stage primaryStage) {
        primaryStage.setTitle("Geometry");
//        Image icon = new Image(getClass().getResourceAsStream("images/snowman.png"));
//        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
//        primaryStage.setWidth(WIDTH);
//        primaryStage.setHeight(HEIGHT);
    }

    Button button(String text, double x, double y) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        return button;
    } // Создаём Button

    private Paint generateColor() {
        int red = rnd.nextInt(COLOR_SPAN);
        int green = rnd.nextInt(COLOR_SPAN);
        int blue = rnd.nextInt(COLOR_SPAN);
        return Color.rgb(red, green, blue);
    }

    void controls(Pane root, Pane pane) {

        Button btn1 = button("Multy Threads", 10, 10);
        btn1.setOnAction(event -> {
            int rectNum = 3 + rnd.nextInt(7);
            for (int i = 0; i <rectNum; i++){
                Paint color = generateColor();
                double width = 50 + rnd.nextInt(100);
                double height = 50 + rnd.nextInt(100);
                double x = rnd.nextInt(1000);
                double y = rnd.nextInt(800);
                Rectang rectang = new Rectang(x, y, width, height, color);
                rectangles.set.add(rectang);
                pane.getChildren().add(rectang.rectangle);
                new Thread(() -> {
                    try {
                        animate(rectang);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        root.getChildren().addAll(btn1);
    }

    static void animate(Rectang rectang) throws InterruptedException {
        double inc = 5;
        int mills = 200;

        while (true) {
            double x = rectang.rectangle.getX();
            double y = rectang.rectangle.getY();
            double width = rectang.rectangle.getWidth();
            double height = rectang.rectangle.getHeight();
            if (rectang.xDn) x += inc;
            else x -= inc;
            if (rectang.yRt) y += inc;
            else y -= inc;
            if (x < 0){
                x = 0;
                rectang.xDn = true;
            }
            if (y < 0) {
                y = 0;
                rectang.yRt = true;
            }
            if (x + width > WIDTH - 1){
                x = WIDTH - 1 - width;
                rectang.xDn = false;
            }
            if (y + height > HEIGHT - 1){
                y = HEIGHT - 1 - height;
                rectang.yRt = false;
            }
            rectang.rectangle.setX(x);
            rectang.rectangle.setY(y);
            Thread.sleep(mills);
        }
    }

        @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Pane pane = new Pane();
        root.getChildren().addAll(pane);

        windowSetup(primaryStage);                      // Инициализация окна
        controls(root, pane);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
