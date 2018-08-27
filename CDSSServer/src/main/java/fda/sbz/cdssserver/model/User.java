package fda.sbz.cdssserver.model;

//@Document(collection = "user")
public class User {
    //@Id
//    private String id;

    //@Indexed(direction = IndexDirection.ASCENDING)
    private String username;

    private String password;
    private Role role;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
