package Controller;

import Model.Candidate;
import Model.CandidateRepository;
import Model.JobRepository;
import View.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AuthController - ตรวจสอบการล็อกอินแบบง่าย ๆ
 * - ถ้ากรอก "admin" (คำว่า admin) -> ไปหน้า admin
 * - มิฉะนั้นค้นหา candidate โดย id (ต้องอยู่ใน CandidateRepository)
 * - ถ้าเจอ -> ไปหน้า candidate
 * - ถ้าไม่เจอ -> แสดงข้อความผิดพลาดบน LoginView
 */
public class AuthController {
    private final LoginView loginView;
    private final JobRepository jobRepo;
    private final CandidateRepository candidateRepo;
    private final ApplicationController appController;

    public AuthController(LoginView loginView,
                          JobRepository jobRepo,
                          CandidateRepository candidateRepo,
                          ApplicationController appController) {
        this.loginView = loginView;
        this.jobRepo = jobRepo;
        this.candidateRepo = candidateRepo;
        this.appController = appController;

        // bind listener
        this.loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String userId = loginView.getUserId();
        if (userId == null || userId.trim().isEmpty()) {
            loginView.setMessage("Please enter user ID.");
            return;
        }

        // simple admin check: literal "admin" (case-insensitive)
        if ("admin".equalsIgnoreCase(userId.trim())) {
            loginView.setMessage("Login as admin.");
            loginView.close();
            appController.showAdminView();
            return;
        }

        // check candidate id (must be present in repository)
        Candidate c = candidateRepo.findById(userId.trim());
        if (c == null) {
            loginView.setMessage("User not found. Use a valid Candidate ID or 'admin'.");
            return;
        }

        // success -> open candidate view
        loginView.setMessage("Login successful: " + c.getFirstName() + " " + c.getLastName());
        loginView.close();
        appController.showCandidateView(c.getId());
    }
}
