package Model;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private List<Company> companies;

    public CompanyRepository(){
        companies = new ArrayList<>();
        companies.add(new Company("10000001","TechCorp","hr@techcorp.com","Bangkok"));
        companies.add(new Company("10000002","WebSoft","contact@websoft.com","Chiang Mai"));
    }

    public List<Company> getAllCompanies(){ return new ArrayList<>(companies); }

    public Company findById(String id){
        for(Company c: companies){
            if(c.getId().equals(id)) return c;
        }
        return null;
    }
}
