public class Appointment {
    private String title;
    private String name;
    private String email;
    private String phone;
    private String courseType;
    private String location;
    private String hours;
    private boolean agreedToTerms;

    public Appointment(String title, String name, String email, String phone, String courseType,
                       String location, String hours, boolean agreedToTerms) {
        this.title = title;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.courseType = courseType;
        this.location = location;
        this.hours = hours;
        this.agreedToTerms = agreedToTerms;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public boolean isAgreedToTerms() {
        return agreedToTerms;
    }

    public void setAgreedToTerms(boolean agreedToTerms) {
        this.agreedToTerms = agreedToTerms;
    }

    public String toCSVString() {
        return String.join(",", title, name, email, phone, courseType, location, hours, String.valueOf(agreedToTerms));
    }
}