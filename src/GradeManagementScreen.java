import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class GradeManagementScreen extends JFrame {
    private JTextField cedulaField, nombreField;
    private JTextField[] gradeFields;

    public GradeManagementScreen() {
        setTitle("Gestión de Calificaciones");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        add(panel);

        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaField = new JTextField();

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField();

        gradeFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            panel.add(new JLabel("Calificación " + (i + 1) + ":"));
            gradeFields[i] = new JTextField();
            panel.add(gradeFields[i]);
        }

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGrades();
            }
        });

        panel.add(cedulaLabel);
        panel.add(cedulaField);
        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(saveButton);

        setVisible(true);
    }

    private void saveGrades() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO estudiantes (cedula, nombre, materia1, materia2, materia3, materia4, materia5) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, cedulaField.getText());
            stmt.setString(2, nombreField.getText());
            for (int i = 0; i < gradeFields.length; i++) {
                double grade = Double.parseDouble(gradeFields[i].getText());
                if (grade < 0 || grade > 20) {
                    JOptionPane.showMessageDialog(this, "Calificación inválida: " + grade, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                stmt.setDouble(3 + i, grade);
            }

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Datos guardados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
