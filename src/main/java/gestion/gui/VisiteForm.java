package gestion.gui;

import gestion.dao.MedecinDAO;
import gestion.dao.PatientDAO;
import gestion.dao.VisiterDAO;
import gestion.entities.Medecin;
import gestion.entities.Patient;
import gestion.entities.Visiter;
import gestion.entities.VisiterId;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

public class VisiteForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton patientbtn;
    private JComboBox medecinComboBox;
    private JComboBox patientComboBox;
    private boolean isUpdate = false;

    public VisiteForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initializeComboBox();

        AutoCompleteDecorator.decorate(medecinComboBox);
        AutoCompleteDecorator.decorate(patientComboBox);

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
        patientbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatientForm dialog = new PatientForm("add", null);
                dialog.setLocationRelativeTo(null);
                dialog.setSize(500, 250);
                dialog.setVisible(true);
                initializeComboBox();
            }
        });
    }

    private void initializeComboBox(){
        List<Medecin> medecins = new MedecinDAO().findAll();
        List<Patient> patients = new PatientDAO().findAll();

        DefaultComboBoxModel<String> modelMedecin = new DefaultComboBoxModel<>();
        medecins.forEach(m -> modelMedecin.addElement(m.getNom() + " - " + m.getCodeMed()));
        medecinComboBox.setModel(modelMedecin);

        DefaultComboBoxModel<String> modelPatient = new DefaultComboBoxModel<>();
        patients.forEach(p -> modelPatient.addElement(p.getNom() + " - " + p.getPrenom() + " - " + p.getCodePat()));
        patientComboBox.setModel(modelPatient);

    }

    private void onOK() {
        String selectedMed = (String) medecinComboBox.getSelectedItem();
        String selectedPat = (String) patientComboBox.getSelectedItem();
        Date currentDate = new Date();

        if(selectedMed == null || selectedPat == null) {
            JOptionPane.showMessageDialog(this,
                    "Sélectionnez un médecin et un patient !",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Extraction des codes
        String codeMed = selectedMed.split(" - ")[1];
        String codePat = selectedPat.split(" - ")[2];

        PatientDAO patientDAO = new PatientDAO();
        MedecinDAO medecinDAO = new MedecinDAO();
        VisiterDAO visiterDAO = new VisiterDAO();

        Patient patient = patientDAO.findByCode(codePat);
        Medecin medecin = medecinDAO.findByCode(codeMed);
        VisiterId id = new VisiterId(medecin, patient, currentDate);

        // 5. Vérification des objets trouvés
        if(medecin == null || patient == null) {
            JOptionPane.showMessageDialog(this,
                    "Sélection invalide !",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Visiter visite = new Visiter(id);
        visiterDAO.save(visite);

        JOptionPane.showMessageDialog(this,
                "Visite enregistrée avec succès !",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        VisiteForm dialog = new VisiteForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
