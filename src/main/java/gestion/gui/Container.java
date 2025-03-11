package gestion.gui;

import gestion.dao.MedecinDAO;
import gestion.dao.PatientDAO;
import gestion.dao.VisiterDAO;
import gestion.entities.Medecin;
import gestion.entities.Patient;
import gestion.entities.Visiter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.util.List;

public class Container {
    private JPanel panel1;
    private JButton VisiteBtn;
    private JButton PatientBtn;
    private JButton MedecinBtn;
    private JTable VisiteTable;
    private JTextField VisiteSearch;
    private JButton nouveauVisiteButton;
    private JPanel VisitePanel;
    private JPanel PatientPanel;
    private JPanel MedecinPanel;
    private JPanel parentPanel;
    private JTable patientTable;
    private JTable medecinTable;
    private JButton nouveauPatientButton;
    private JButton nouveauMedecinButton;
    private JTextField medecinSearch;
    private JTextField patientSearch;
    private JPopupMenu popupMenuMedecin = new JPopupMenu();
    private JPopupMenu popupMenuPatient = new JPopupMenu();
    private JPopupMenu popupMenuVisite = new JPopupMenu();

    private MedecinDAO medecinDAO = new MedecinDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private VisiterDAO visiterDAO = new VisiterDAO();

    public Container() {

//        card layout event
        VisiteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(VisitePanel);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
        PatientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(PatientPanel);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
        MedecinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(MedecinPanel);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
//        affichage table
        afficherMedecin();
        afficherPatient();
        afficherVisite();
//        recherche
        searchFunction();

//        update et delete button

        popupMenuSetup(medecinTable, "medecin");
        popupMenuSetup(patientTable, "patient");
        popupMenuSetup(VisiteTable, "visite");

        nouveauVisiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisiteForm dialog = new VisiteForm();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        nouveauPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatientForm dialog = new PatientForm("add", null);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                afficherPatient();
            }
        });
        nouveauMedecinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedecinForm dialog = new MedecinForm("add", null);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                afficherMedecin();
            }
        });

    }
    private void afficherMedecin(){
        List<Medecin> medecins = medecinDAO.findAll();

        String[] colonnes = {"Code", "Nom", "Prenom", "Grade"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        for(Medecin m: medecins){
            Object[] ligne = {m.getCodeMed(), m.getNom(), m.getPrenom(), m.getGrade()};
            model.addRow(ligne);
        }

        medecinTable.setModel(model);
    }
    private void afficherPatient(){
        List<Patient> patients = patientDAO.findAll();

        String[] colonnes = {"Code", "Nom", "Prenom", "Adresse", "Sexe"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        for(Patient p: patients){
            Object[] ligne = {p.getCodePat(), p.getNom(), p.getPrenom(), p.getAdresse(), p.getSexe()};
            model.addRow(ligne);
        }

        patientTable.setModel(model);
    }
    private void afficherVisite(){
        List<Visiter> visiteurs = visiterDAO.findAll();

        String[] colonnes = {"Date", "Medecin", "Patient"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        for(Visiter v: visiteurs){
            Object[] ligne = {v.getDateVisite(), v.getId().getCodeMed(), v.getId().getCodePat()};
            model.addRow(ligne);
        }

        VisiteTable.setModel(model);
    }
//    searching setup
    private void searchFilterSetup(JTextField searchField, JTable table){
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
                String searchedText = searchField.getText().trim();
                if(searchedText.isEmpty()){
                    sorter.setRowFilter(null);
                }else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ searchedText));
                }
            }
        });
    }

    private void searchFunction(){
        searchFilterSetup(medecinSearch, medecinTable);
        searchFilterSetup(patientSearch, patientTable);
        searchFilterSetup(VisiteSearch, VisiteTable);
    }
//    popup setup
    private void popupMenuSetup(JTable table, String tableName){
        JMenuItem editItem = new JMenuItem("Modifier");
        JMenuItem deleteItem = new JMenuItem("Supprimer");

        switch (tableName){
            case "medecin":
                table.setComponentPopupMenu(popupMenuMedecin);
                popupMenuMedecin.add(editItem);
                popupMenuMedecin.add(deleteItem);
                break;
            case "patient":
                table.setComponentPopupMenu(popupMenuPatient);
                popupMenuPatient.add(editItem);
                popupMenuPatient.add(deleteItem);
                break;
            case "visite":
                table.setComponentPopupMenu(popupMenuVisite);
                popupMenuVisite.add(editItem);
                popupMenuVisite.add(deleteItem);
                break;
        }

        editItem.addActionListener(e -> handleEdit(table, tableName));
        deleteItem.addActionListener(e -> handleDelete(table, tableName));
    }
    private void handleEdit(JTable table, String tableName){
        int selectedRow = table.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        if(selectedRow >= 0){
            switch (tableName){
                case "medecin":
                    System.out.println("Modifier Médecin : ");
                    String code = model.getValueAt(selectedRow, 0).toString();
                    String nom = model.getValueAt(selectedRow, 1).toString();
                    String prenom = model.getValueAt(selectedRow, 2).toString();
                    String grade = model.getValueAt(selectedRow, 3).toString();

                    System.out.println("Code: " + code + " | Nom: " + nom + " | Prénom: " + prenom + " | Grade: " + grade);
                    Medecin medecin = new Medecin(code, nom, prenom, grade);
                    MedecinForm dialog = new MedecinForm("update", medecin);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    afficherMedecin();
                    break;
                case "patient":
                    System.out.println("Modifier patient : ");
                    code = model.getValueAt(selectedRow, 0).toString();
                    nom = model.getValueAt(selectedRow, 1).toString();
                    prenom = model.getValueAt(selectedRow, 2).toString();
                    String adresse = model.getValueAt(selectedRow, 3).toString();
                    String sexe = model.getValueAt(selectedRow, 4).toString();

                    Patient patient = new Patient(code, nom, prenom, sexe, adresse);
                    PatientForm dialogPat = new PatientForm("update", patient);
                    dialogPat.setLocationRelativeTo(null);
                    dialogPat.setVisible(true);
                    afficherPatient();
                    break;
                case "visite":
                    System.out.println("Modifier Visite : ");
                    break;
            }
        }
    }
    private void handleDelete(JTable table, String tableName){
        int selectedRow = table.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        if(selectedRow >= 0){
            switch (tableName){
                case "medecin":
                    String code = model.getValueAt(selectedRow, 0).toString();
                    medecinDAO.delete(code);
                    break;
                case "patient":
                    code = model.getValueAt(selectedRow, 0).toString();
                    patientDAO.delete(code);
                    break;
            }
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Visite medical");
        frame.setContentPane(new Container().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}