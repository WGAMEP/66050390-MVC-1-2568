package Model;

import java.util.ArrayList;
import java.util.List;

public class CandidateRepository {
    private List<Candidate> candidates;

    public CandidateRepository(){
        candidates = new ArrayList<>();
        // ตัวอย่าง >=10 ผู้สมัคร
        candidates.add(new Candidate("30000001","Alice","Smith","alice@email.com","STUDENT"));
        candidates.add(new Candidate("30000002","Bob","Johnson","bob@email.com","GRAD"));
        candidates.add(new Candidate("30000003","Charlie","Lee","charlie@email.com","STUDENT"));
        candidates.add(new Candidate("30000004","David","Kim","david@email.com","GRAD"));
        candidates.add(new Candidate("30000005","Eva","Wong","eva@email.com","STUDENT"));
        candidates.add(new Candidate("30000006","Frank","Brown","frank@email.com","GRAD"));
        candidates.add(new Candidate("30000007","Grace","Davis","grace@email.com","STUDENT"));
        candidates.add(new Candidate("30000008","Henry","Miller","henry@email.com","GRAD"));
        candidates.add(new Candidate("30000009","Ivy","Wilson","ivy@email.com","STUDENT"));
        candidates.add(new Candidate("30000010","Jack","Taylor","jack@email.com","GRAD"));
        candidates.add(new Candidate("99999999","Admin","Admin","admin@email.com","ADMIN"));
    }

    public List<Candidate> getAllCandidates(){ return new ArrayList<>(candidates); }

    public Candidate findById(String id){
        for(Candidate c : candidates){
            if(c.getId().equals(id)) return c;
        }
        return null;
    }

    public void addCandidate(Candidate c){ candidates.add(c); }
}
