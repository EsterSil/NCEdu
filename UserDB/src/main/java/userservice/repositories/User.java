package userservice.repositories;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity

public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userID;
    @Column
    @NotNull
    private String login;
    @Column
    @NotNull
    private String hashPassword;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private Set<Coordinate> coordinates;

    public User(String login, String hashPassword) {
        this.login = login;
        this.hashPassword = hashPassword;
    }

    public User() {

    }
    @Override
    public String toString(){
        return String.format(" User: id = %d, login = '%s', password = '%s'", userID, login, hashPassword);
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

}

