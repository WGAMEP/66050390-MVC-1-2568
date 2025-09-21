package Model;

public class Candidate {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String status; // STUDENT / GRAD / ADMIN
    private String grade;  // เก็บตัวอักษรเกรด เช่น "A", "B+", ...

    public Candidate(String id, String firstName, String lastName, String email, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.grade = null;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }

    // Grade as String (A, B+, ...)
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
