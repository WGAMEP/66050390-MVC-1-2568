package Controller;

import Model.Candidate;
import Model.CandidateRepository;
import Model.CompanyRepository;
import Model.Job;
import Model.JobRepository;
import View.AdminJobView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AdminJobController - ปรับให้ยอมรับเฉพาะเกรดตัวอักษรชุดที่กำหนด
 */
public class AdminJobController {
    private final AdminJobView view;
    private final JobRepository jobRepo;
    private final CandidateRepository candidateRepo;
    private final CompanyRepository companyRepo;
    private final ApplicationController appController;

    // ชุดเกรดที่ยอมรับ (ตัวใหญ่เท่านั้น)
    private static final Set<String> ALLOWED_GRADES = new HashSet<>(Arrays.asList(
            "A","B+","B","C+","C","D+","D","F"
    ));

    public AdminJobController(AdminJobView view,
                              JobRepository jobRepo,
                              CandidateRepository candidateRepo,
                              CompanyRepository companyRepo,
                              ApplicationController appController) {
        this.view = view;
        this.jobRepo = jobRepo;
        this.candidateRepo = candidateRepo;
        this.companyRepo = companyRepo;
        this.appController = appController;

        refreshJobsAndCandidates();

        view.addUpdateGradeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateGrade();
            }
        });

        view.addLogoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.close();
                appController.showLogin();
            }
        });
    }

    private void refreshJobsAndCandidates() {
        List<Job> allJobs = jobRepo.getAllJobs();
        view.displayJobs(allJobs, companyId -> {
            try {
                return companyRepo.findById(companyId).getName();
            } catch (Exception ex) {
                return companyId;
            }
        });

        List<Candidate> allCands = candidateRepo.getAllCandidates();
        view.displayCandidates(allCands);
    }

    private void handleUpdateGrade() {
        String candId = view.getCandidateIdInput();
        String gradeStr = view.getGradeInput();

        if (candId == null || candId.isEmpty()) {
            view.showMessage("Candidate ID");
            return;
        }
        if (gradeStr == null || gradeStr.isEmpty()) {
            view.showMessage("Grade (A, B+, B, C+, C, D+, D, F)");
            return;
        }

        // Normalize: uppercase, trim, remove extra spaces
        String normalized = gradeStr.trim().toUpperCase().replaceAll("\\s+", "");
        if (!ALLOWED_GRADES.contains(normalized)) {
            view.showMessage("A, B+, B, C+, C, D+, D, F");
            return;
        }

        Candidate c = candidateRepo.findById(candId);
        if (c == null) {
            view.showMessage("Not found Candidate ID: " + candId);
            return;
        }

        // บันทึกเกรดเป็นตัวอักษร
        c.setGrade(normalized);
        view.showMessage("Update: " + c.getId() + " = " + normalized);

        view.clearInputs();
        refreshJobsAndCandidates();
    }
}
