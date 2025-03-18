package gestion.gui;

import gestion.dao.MedecinDAO;
import gestion.entities.Medecin;

import javax.swing.*;
import java.awt.event.*;

public class MedecinForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codeInput;
    private JTextField nomInput;
    private JTextField prenomInput;
    private JTextField gradeInput;
    private boolean isUpdate = false;

    public MedecinForm(String mode, Medecin medecin) {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        if (mode.equals("update")) {
            isUpdate = true;
            buttonOK.setText("Mettre à jour");
            nomInput.setText(medecin.getNom());
            prenomInput.setText(medecin.getPrenom());
            gradeInput.setText(medecin.getGrade());
            codeInput.setText(medecin.getCodeMed());
        } else {
            buttonOK.setText("Ajouter");
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String nom = nomInput.getText();
        String prenom = prenomInput.getText();
        String grade = gradeInput.getText();
        String code = codeInput.getText();

        MedecinDAO medecinDAO = new MedecinDAO();
        Medecin medecin = new Medecin(code, nom, prenom, grade);

        if(isUpdate){
            System.out.println("update the outdate");
            medecinDAO.update(medecin);
            JOptionPane.showMessageDialog(this,
                    "Modification(s) enregistrée(s) avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            medecinDAO.save(medecin);
            JOptionPane.showMessageDialog(this,
                    "Medecin enregistrée avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MedecinForm dialog = new MedecinForm("Save", null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
