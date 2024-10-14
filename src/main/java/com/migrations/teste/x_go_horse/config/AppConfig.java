package com.migrations.teste.x_go_horse.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AppConfig {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String migrationsPath;

    public AppConfig(String configFilePath) {
        loadConfig(configFilePath);
    }

    private void loadConfig(String configFilePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(configFilePath));
            for (String line : lines) {
                if (line.startsWith("db.url=")) {
                    dbUrl = line.split("=")[1];
                } else if (line.startsWith("db.user=")) {
                    dbUser = line.split("=")[1];
                } else if (line.startsWith("db.password=")) {
                    dbPassword = line.split("=")[1];
                } else if (line.startsWith("migrations.path=")) {
                    migrationsPath = line.split("=")[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getMigrationsPath() {
        return migrationsPath;
    }
}
