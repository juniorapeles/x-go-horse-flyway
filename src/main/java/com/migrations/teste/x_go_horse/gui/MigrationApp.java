package com.migrations.teste.x_go_horse.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.MigrateResult;
import org.flywaydb.core.api.output.MigrateOutput;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        String dbUrl = System.getenv().getOrDefault("DB_URL", "jdbc:sqlserver://localhost:1433;databaseName=master");
        String dbUser = System.getenv().getOrDefault("DB_USER", "sa");
        String dbPassword = System.getenv().getOrDefault("DB_PASSWORD", "YourStrong!Passw0rd");
        String migrationsPath = "filesystem:src/main/resources/db/migration/sqlserver"; // Ajuste conforme necessário

        // Configurar o Flyway
        Flyway flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword) // Configura o DataSource
                .locations(migrationsPath) // Localização das migrações
                .load();

        // Executar migrações e capturar o resultado
        try {
            MigrateResult migrateResult = flyway.migrate();

            // Construindo uma string detalhada sobre as migrações
            StringBuilder resultDetails = new StringBuilder();
            resultDetails.append("Migrations executed successfully:\n");
            resultDetails.append("Migrations applied: ").append(migrateResult.migrationsExecuted).append("\n");
            resultDetails.append("Current version: ").append(migrateResult.targetSchemaVersion).append("\n");

            // Iterar manualmente para evitar uso de lambda (compatível com Java 8)
            for (MigrateOutput migration : migrateResult.migrations) {
                resultDetails.append("Version: ").append(migration.version).append("\n");
                resultDetails.append("Description: ").append(migration.description).append("\n");
                resultDetails.append("Type: ").append(migration.type).append("\n");
                resultDetails.append("Execution time: ").append(migration.executionTime).append("ms\n\n");
            }

            outputArea.setText(resultDetails.toString());
        } catch (Exception e) {
                // Exibir erro detalhado no TextArea
                StringBuilder errorDetails = new StringBuilder();

                // Mensagem básica do erro
                errorDetails.append("Error running migrations: ").append(e.getMessage()).append("\n\n");

                // Capturar e exibir o stack trace completo
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                errorDetails.append("Stack Trace:\n").append(sw.toString()).append("\n");

                // Exibir o erro completo no TextArea
                outputArea.setText(errorDetails.toString());
            }
        }
    }
