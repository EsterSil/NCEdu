package jettytest.dbservices;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table( name = "USERS")
public class User implements Serializable {
    private String phoneNumber;
    private String encryptedPassword;
    private int userID;
    private Balance balance;

    public User(String phoneNumber, String encryptedPassword, Balance balance) {
        this.phoneNumber = phoneNumber;
        this.encryptedPassword = encryptedPassword;
        this.balance = balance;
    }


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public User(String phoneNumber, String encryptedPassword) {
        this.phoneNumber = phoneNumber;
        this.encryptedPassword = encryptedPassword;
    }

    public User() {

    }
    @Id
    @Column(name = "USER_ID", nullable = false, unique = true)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    @Column
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Column(name = "password")
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
