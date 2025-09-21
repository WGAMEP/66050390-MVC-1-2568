// Main.java
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Controller.ApplicationController;
import Model.JobRepository;
import Model.CandidateRepository;
import Model.CompanyRepository;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            // ignore
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            JobRepository jobRepo = new JobRepository();
            CandidateRepository candidateRepo = new CandidateRepository();
            CompanyRepository companyRepo = new CompanyRepository();

            ApplicationController appController = new ApplicationController(jobRepo, candidateRepo, companyRepo);
            appController.showLogin();
        });
    }
}
