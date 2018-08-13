package jettytest.dbservices;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Balances")
public class Balance implements Serializable {

    private int balanceID;
    private User user;
    private double balance;

    public Balance() {

    }

    @Id
    @Column(name = "balance_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getBalanceID() {
        return balanceID;
    }

    public void setBalanceID(int balanceID) {
        this.balanceID = balanceID;
    }



    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Column
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }





}
