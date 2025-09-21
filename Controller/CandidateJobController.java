package Controller;

import Model.Candidate;
import Model.CandidateRepository;
import Model.CompanyRepository;
import Model.Job;
import Model.JobRepository;
import View.CandidateJobView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * CandidateJobController - แสดงงาน (ชื่อบริษัท) และจัดการการสมัคร
 */
public class CandidateJobController {
    private final CandidateJobView view;
    private final JobRepository jobRepo;
    private final CandidateRepository candidateRepo;
    private final CompanyRepository companyRepo;
    private final String candidateId;
    private final ApplicationController appController;

    private final Path applicationsCsv = Paths.get("data", "applications.csv");
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Pattern EMAIL_RE = Pattern.compile("^[A-Za-z0-9+_.%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public CandidateJobController(CandidateJobView view,
                                  JobRepository jobRepo,
                                  CandidateRepository candidateRepo,
                                  CompanyRepository companyRepo,
                                  String candidateId,
                                  ApplicationController appController) {
        this.view = view;
        this.jobRepo = jobRepo;
        this.candidateRepo = candidateRepo;
        this.companyRepo = companyRepo;
        this.candidateId = candidateId;
        this.appController = appController;

        ensureApplicationsCsv();

        // listeners
        view.addApplyListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onApply();
            }
        });

        view.addLogoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.close();
                appController.showLogin();
            }
        });

        // initial load
        refreshJobList();
    }

    private void refreshJobList() {
        LocalDate today = LocalDate.now();
        List<Job> open = jobRepo.getOpenJobs().stream()
                .filter(j -> (j.getDeadline() == null) || !j.getDeadline().isBefore(today))
                .sorted(Comparator.comparing(Job::getDeadline))
                .collect(Collectors.toList());

        // ส่ง companyRepo เพื่อให้ view แสดงชื่อบริษัทแทน id
        view.displayJobs(open, companyRepo);
    }

    private void onApply() {
        String jobId = view.getSelectedJobId();
        if (jobId == null || jobId.trim().isEmpty()) {
            view.showMessage("Please select a job first.");
            return;
        }

        Candidate c = candidateRepo.findById(candidateId);
        if (c == null) {
            view.showMessage("Candidate not found.");
            return;
        }

        Job job = jobRepo.findById(jobId.trim());
        if (job == null) {
            view.showMessage("Selected job not found.");
            return;
        }

        if (!"OPEN".equalsIgnoreCase(job.getStatus())) {
            view.showMessage("This job is not open.");
            return;
        }

        LocalDate today = LocalDate.now();
        if (job.getDeadline() != null && job.getDeadline().isBefore(today)) {
            view.showMessage("Deadline has passed for this job.");
            return;
        }

        String jobType = job.getType() == null ? "" : job.getType().trim().toUpperCase();
        String candStatus = c.getStatus() == null ? "" : c.getStatus().trim().toUpperCase();

        if (jobType.equals("COOP") && !candStatus.equals("STUDENT")) {
            view.showMessage("Only STUDENT can apply for COOP.");
            return;
        }
        if (jobType.equals("NORMAL") && !candStatus.equals("GRAD")) {
            view.showMessage("Only GRAD can apply for NORMAL jobs.");
            return;
        }

        String email = c.getEmail();
        if (email == null || !EMAIL_RE.matcher(email).matches()) {
            view.showMessage("Invalid email format for candidate.");
            return;
        }

        if (job.getApplicantIds().contains(c.getId())) {
            view.showMessage("You already applied for this job.");
            return;
        }

        // register application
        job.addApplicant(c.getId());
        String timestamp = LocalDateTime.now().format(dtf);
        boolean saved = appendApplicationCsv(job.getId(), c.getId(), email, timestamp);
        if (saved) {
            view.showMessage("Applied successfully at " + timestamp);
        } else {
            view.showMessage("Applied but failed to save record.");
        }

        // refresh listing (and as requirement: return to job list)
        refreshJobList();
    }

    private void ensureApplicationsCsv() {
        try {
            if (!Files.exists(applicationsCsv.getParent())) {
                Files.createDirectories(applicationsCsv.getParent());
            }
            if (!Files.exists(applicationsCsv)) {
                Files.write(applicationsCsv,
                        ("jobId,candidateId,email,appliedAt" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
        } catch (IOException ex) {
            System.err.println("Cannot create applications CSV: " + ex.getMessage());
        }
    }

    private boolean appendApplicationCsv(String jobId, String candidateId, String email, String appliedAt) {
        String line = String.join(",", escapeCsv(jobId), escapeCsv(candidateId), escapeCsv(email), escapeCsv(appliedAt));
        try (BufferedWriter bw = Files.newBufferedWriter(applicationsCsv, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String escapeCsv(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            s = s.replace("\"", "\"\"");
            return "\"" + s + "\"";
        }
        return s;
    }
}
