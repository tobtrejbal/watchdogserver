package watchdog.server.core.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by admin on 23.09.2016.
 */

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private long user_id;

    @Column(name = "user_login", nullable = false, unique = true)
    private String login;

    @Column(name = "user_password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = true, mappedBy = "user")
    private List<Sample> samples;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
