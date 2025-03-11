package gestion.gui;

import gestion.dao.PatientDAO;
import gestion.entities.Patient;

import javax.swing.*;
import java.awt.event.*;

public class PatientForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codeInput;
    private JTextField nomInput;
    private JTextField prenomInput;
    private JTextField adresseInput;
    private JComboBox sexeInput;
    private Boolean isUpdate = false;

    public PatientForm(String mode, Patient patient) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Masculin");
        model.addElement("Féminin");

        sexeInput.setModel(model);

        if(mode.equals("update")){
            isUpdate = true;
            buttonOK.setText("Mettre à jour");
            codeInput.setText(patient.getCodePat());
            nomInput.setText(patient.getNom());
            prenomInput.setText(patient.getPrenom());
            adresseInput.setText(patient.getAdresse());
            sexeInput.setSelectedItem(patient.getSexe());
        }else{
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
        String adresse = adresseInput.getText();
        String code = codeInput.getText();
        String sexe = (String) sexeInput.getSelectedItem();

        PatientDAO patientDAO = new PatientDAO();
        Patient patient = new Patient(code, nom, prenom, sexe, adresse);

        if(isUpdate){
            System.out.println("update the outdate");
            patientDAO.update(patient);
        }else{
            patientDAO.save(patient);
            System.out.println("Patient ajouter");
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        PatientForm dialog = new PatientForm("add", null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
