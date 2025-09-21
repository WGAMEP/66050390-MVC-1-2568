package Controller;

import Model.CandidateRepository;
import Model.CompanyRepository;
import Model.JobRepository;
import View.AdminJobView;
import View.CandidateJobView;
import View.LoginView;

import javax.swing.SwingUtilities;

/**
 * ApplicationController - จัดการ navigation (Login -> Candidate / Admin)
 * ต้องแน่ใจว่าเมื่อสร้าง ApplicationController ให้ส่ง JobRepository, CandidateRepository, CompanyRepository
 */
public class ApplicationController {
    private final JobRepository jobRepo;
    private final CandidateRepository candidateRepo;
    private final CompanyRepository companyRepo;

    public ApplicationController(JobRepository jobRepo,
                                 CandidateRepository candidateRepo,
                                 CompanyRepository companyRepo) {
        this.jobRepo = jobRepo;
        this.candidateRepo = candidateRepo;
        this.companyRepo = companyRepo;
    }

    /**
     * แสดงหน้า Login (สร้าง LoginView + AuthController)
     */
    public void showLogin() {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            // AuthController(loginView, jobRepo, candidateRepo, this)
            new AuthController(loginView, jobRepo, candidateRepo, this);
        });
    }

    /**
     * แสดงหน้าสำหรับ candidate (ส่ง companyRepo ให้ controller เพื่อแสดงชื่อบริษัท)
     */
    public void showCandidateView(String candidateId) {
        SwingUtilities.invokeLater(() -> {
            CandidateJobView candidateView = new CandidateJobView();
            // CandidateJobController(view, jobRepo, candidateRepo, companyRepo, candidateId, this)
            new CandidateJobController(candidateView, jobRepo, candidateRepo, companyRepo, candidateId, this);
        });
    }

    /**
     * แสดงหน้าสำหรับ admin (ต้องส่ง candidateRepo และ companyRepo ให้ admin controller)
     */
    public void showAdminView() {
        SwingUtilities.invokeLater(() -> {
            AdminJobView adminView = new AdminJobView();
            // AdminJobController(view, jobRepo, candidateRepo, companyRepo, this)
            new AdminJobController(adminView, jobRepo, candidateRepo, companyRepo, this);
        });
    }
}
