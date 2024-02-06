public class User {

    private String email;
    private String username;
    private String city;

    public User(String email, String username, String city) {
        this.email = email;
        this.username = username;
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return null;
    }
}