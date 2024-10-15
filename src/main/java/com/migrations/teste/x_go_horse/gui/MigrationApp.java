package com.migrations.teste.x_go_horse.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

public class MigrationApp extends Application {
    private TextArea outputArea;

    public static void main(String[] args) {
        // Definindo a variável de ambiente DB_TYPE
        System.setProperty("DB_TYPE", "sqlserver"); // ou "postgres", dependendo do banco de dados
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
        // Defina suas credenciais de banco de dados aqui
        String dbUrl = "jdbc:sqlserver://<hostname>:<port>;databaseName=<dbname>"; // Substitua pelos valores corretos
        String dbUser = "<username>"; // Substitua pelo seu usuário
        String dbPassword = "<password>"; // Substitua pela sua senha
        String migrationsPath = "filesystem:src/main/resources/db/migration"; // Ajuste o caminho das migrações, se necessário

        // Configurar o Flyway
        Flyway flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword) // Configura o DataSource
                .locations(migrationsPath) // Localização das migrações
                .load();

        // Executar migrações e capturar o resultado
        try {
            MigrateResult migrateResult = flyway.migrate();
            outputArea.setText("Migrations executed successfully: " + migrateResult.getClass());
        } catch (Exception e) {
            outputArea.setText("Error running migrations: " + e.getMessage());
        }
    }
}
