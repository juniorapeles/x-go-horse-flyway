package com.migrations.teste.x_go_horse.ui;

public class MigrationApp {

    private JTextField urlField;
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField migrationPathField;
    private JButton migrateButton;
    private JLabel resultLabel;

    public MigrationApp() {
        // Define o layout e os componentes da interface
        setTitle("Flyway Migration App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        urlField = new JTextField("jdbc:mysql://localhost:3306/seubanco", 20);
        userField = new JTextField("root", 20);
        passwordField = new JPasswordField(20);
        migrationPathField = new JTextField("filesystem:/caminho/para/migrations", 20);
        migrateButton = new JButton("Rodar Migração");
        resultLabel = new JLabel("");

        JPanel panel = new JPanel();
        panel.add(new JLabel("URL do Banco:"));
        panel.add(urlField);
        panel.add(new JLabel("Usuário:"));
        panel.add(userField);
        panel.add(new JLabel("Senha:"));
        panel.add(passwordField);
        panel.add(new JLabel("Caminho Migrations:"));
        panel.add(migrationPathField);
        panel.add(migrateButton);
        panel.add(resultLabel);

        add(panel);

        // Ação ao clicar no botão de migração
        migrateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlField.getText();
                String user = userField.getText();
                String password = new String(passwordField.getPassword());
                String migrationPath = migrationPathField.getText();

                ConexaoDb conexaoDb = new ConexaoDb(url, user, password, migrationPath);

                try {
                    // Configura e executa o Flyway com as informações fornecidas
                    Flyway flyway = Flyway.configure()
                            .dataSource(conexaoDb.getDbUrl(), conexaoDb.getDbUser(), conexaoDb.getDbPassword())
                            .locations(conexaoDb.getMigrationsPath())
                            .load();

                    flyway.migrate();
                    resultLabel.setText("Migração realizada com sucesso!");

                } catch (Exception ex) {
                    resultLabel.setText("Erro: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
}