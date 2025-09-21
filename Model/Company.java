package Model;

public class Company {
    private String id;
    private String name;
    private String email;
    private String location;

    public Company(String id, String name, String email, String location){
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
    }

    public String getId(){ return id; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getLocation(){ return location; }
}
