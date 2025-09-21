package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobRepository {
    private List<Job> jobs;

    public JobRepository(){
        jobs = new ArrayList<>();
        // ตัวอย่าง >=10 ตำแหน่ง ครอบคลุม >=2 บริษัท
        jobs.add(new Job("20000001","Software Engineer","Develop backend","10000001", LocalDate.of(2025,12,31),"OPEN","NORMAL"));
        jobs.add(new Job("20000002","Mobile Developer","Develop Android app","10000001", LocalDate.of(2025,11,30),"OPEN","NORMAL"));
        jobs.add(new Job("20000003","Data Analyst","Analyze datasets","10000001", LocalDate.of(2025,10,31),"OPEN","NORMAL"));
        jobs.add(new Job("20000004","IT Intern","Assist IT team","10000001", LocalDate.of(2025,9,30),"OPEN","COOP"));
        jobs.add(new Job("20000005","Web Developer","Frontend & Backend","10000002", LocalDate.of(2025,12,1),"OPEN","NORMAL"));
        jobs.add(new Job("20000006","UI/UX Designer","Design pages","10000002", LocalDate.of(2025,10,15),"OPEN","NORMAL"));
        jobs.add(new Job("20000007","Network Engineer","Manage network infra","10000002", LocalDate.of(2025,11,10),"OPEN","NORMAL"));
        jobs.add(new Job("20000008","Co-op Research","Research tasks","10000002", LocalDate.of(2025,9,25),"OPEN","COOP"));
        jobs.add(new Job("20000009","DevOps Engineer","CI/CD pipelines","10000001", LocalDate.of(2025,12,31),"OPEN","NORMAL"));
        jobs.add(new Job("20000010","Security Analyst","Monitoring","10000002", LocalDate.of(2025,10,5),"OPEN","NORMAL"));
    }

    public List<Job> getAllJobs(){ return new ArrayList<>(jobs); }

    public Job findById(String id){
        for(Job j: jobs){
            if(j.getId().equals(id)) return j;
        }
        return null;
    }

    public void addJob(Job job){ jobs.add(job); }

    // คืนเฉพาะ job ที่ status=OPEN
    public List<Job> getOpenJobs(){
        List<Job> list = new ArrayList<>();
        for(Job j: jobs){
            if(j.getStatus().equalsIgnoreCase("OPEN")) list.add(j);
        }
        return list;
    }
}
