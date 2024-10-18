package com.migrations.teste.x_go_horse.entities;

public class ConexaoDb {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String migrationsPath;

    public ConexaoDb() {
    }

    public ConexaoDb(String dbUrl, String dbUser, String dbPassword, String migrationsPath) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.migrationsPath = migrationsPath;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getMigrationsPath() {
        return migrationsPath;
    }

    public void setMigrationsPath(String migrationsPath) {
        this.migrationsPath = migrationsPath;
    }
}



