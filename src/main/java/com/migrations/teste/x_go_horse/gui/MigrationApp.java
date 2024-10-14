package com.migrations.teste.x_go_horse.gui;

import com.migrations.teste.x_go_horse.config.AppConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MigrationApp extends Application {
    private TextArea outputArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Migration Runner");

        Button runMigrationsButton = new Button("Run Migrations");
        outputArea = new TextArea();
        outputArea.setEditable(false);

        runMigrationsButton.setOnAction(e -> runMigrations());

        VBox vbox = new VBox(10, runMigrationsButton, outputArea);
        vbox.setPrefWidth(400);
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void runMigrations() {
        AppConfig config = new AppConfig("src/main/resources/config.conf");

        // Configurar o Flyway
        Flyway flyway = Flyway.configure()
                .dataSource(config.getDbUrl(), config.getDbUser(), config.getDbPassword())
                .locations(config.getMigrationsPath()) // Localização das migrações
                .load();

        // Executar migrações e capturar o resultado
        try {
            MigrateResult migrationsCount = flyway.migrate();
            outputArea.setText("Migrations executed successfully: " + migrationsCount);
        } catch (Exception e) {
            outputArea.setText("Error running migrations: " + e.getMessage());
        }
    }
}
