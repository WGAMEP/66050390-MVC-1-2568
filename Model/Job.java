package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Job {
    private String id;
    private String title;
    private String description;
    private String companyId;
    private LocalDate deadline;
    private String status; // OPEN / CLOSED
    private String type;   // NORMAL / COOP
    private List<String> applicantIds; // id ของผู้สมัคร

    public Job(String id, String title, String description, String companyId,
               LocalDate deadline, String status, String type){
        this.id = id;
        this.title = title;
        this.description = description;
        this.companyId = companyId;
        this.deadline = deadline;
        this.status = status;
        this.type = type;
        this.applicantIds = new ArrayList<>();
    }

    public String getId(){ return id; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public String getCompanyId(){ return companyId; }
    public LocalDate getDeadline(){ return deadline; }
    public String getStatus(){ return status; }
    public String getType(){ return type; }
    public List<String> getApplicantIds(){ return applicantIds; }

    public void addApplicant(String candidateId){
        if(!applicantIds.contains(candidateId)){
            applicantIds.add(candidateId);
        }
    }

    public void setStatus(String status){ this.status = status; }

    public void setApplyDate(LocalDate now) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setApplyDate'");
    }
}
