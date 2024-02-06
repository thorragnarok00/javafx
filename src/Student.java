public class Student {

    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;

    public Student(String lastName, String firstName, String middleName, String gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getGender() {
        return gender;
    }
}