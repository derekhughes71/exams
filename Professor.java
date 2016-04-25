package cis498;

public class Professor {
    int professorId;
    String fullName;
    String department;
    String availability;

    public Professor(String fullNameIn, String departmentIn, String availabilityIn) {
        fullName = fullNameIn;
        department = departmentIn;
        availability = availabilityIn;
    }
    
    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorIdIn) {
        professorId = professorIdIn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullNameIn) {
        fullName = fullNameIn;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String departmentIn) {
        department = departmentIn;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availabilityIn) {
        availability = availabilityIn;
    }
}
