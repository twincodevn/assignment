package model;
import java.util.Date;
@lombok.Getter
@lombok.Setter 
@lombok.AllArgsConstructor 
@lombok.NoArgsConstructor
@lombok.Data 
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String passwordHash;
    private boolean isAdmin;
    private boolean isVendor;
    private Date registeredAt;
    private Date lastLogin;
    private String intro;
    private String profile;
}