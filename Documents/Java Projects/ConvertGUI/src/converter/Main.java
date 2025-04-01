package converter;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Metric Converter");

        // input text box thingy
        TextField inputField = new TextField();
        inputField.setMaxWidth(100);
        inputField.setMaxHeight(30);
        inputField.setPromptText("Enter a number");

        // dropdown box for each converesion
        ComboBox<String> convertUnit = new ComboBox<>();
        convertUnit.getItems().addAll("km to mi", "mi to km", "kg to lb", "lb to kg");
        convertUnit.setPromptText("Unit -> Unit");

        Button convertButton = new Button("Convert");

        Label resultLabel = new Label("Result: ");

        // conversion button logic
        // apparently the error handling doesn't actually work
        // still suck at doing that
        convertButton.setOnAction(e -> {
            try {
                double value = Double.parseDouble(inputField.getText());

                // parsing the selected conversion to grab the units
                String text = convertUnit.getValue();
                if (text == null) {
                    resultLabel.setText("Please pick a conversion.");
                    return;
                }

                // \\s+ looks for whitespaces according to google
                String[] words = text.split("\\s+");
                String fromUnit = words[0];
                String toUnit = words[2];

                String conversionType = convertUnit.getValue();

                double result = convert(value, conversionType);

                if (Double.isNaN(result)) {
                    resultLabel.setText("Invalid conversion type.");
                } else {
                    resultLabel.setText(String.format("%.2f %s = %.2f %s", value, fromUnit, result, toUnit));
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid number format!");
            }
        });

        // Actual UI
        VBox layout = new VBox(10, inputField, convertUnit, convertButton, resultLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(600, 500);

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static double convert(double value, String convertUnit) {
        switch (convertUnit) {
            case "km to mi":
                return value * 0.621371;
            case "mi to km":
                return value / 0.621371;
            case "kg to lb":
                return value * 2.20462;
            case "lb to kg":
                return value / 2.20462;
        }
        return Double.NaN;
    }

    public static void main(String[] args) {
        launch(args);
    }
}