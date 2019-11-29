/*
Richard Cooke
G00331787@gmit.ie
 */

package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
// import javax.xml.bind.annotation.XmlAnyElement;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class User {

    @NotNull
    private Integer id;
    @NotBlank
    @Length(min=5, max=15)
    private String userName;
    @NotBlank @Length(min=5, max=20)
    private String password;
    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;
    private String hashedPassword;
    private String salt;

    public User(){
    }

    public User(Integer id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        String[] response = new UserClient("localhost", 50551).hash(id, password);

        this.hashedPassword = response[0];
        this.salt = response[1];
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public String getHashedPassword() {
        return hashedPassword;
    }

    @JsonProperty
    public String getSalt() {
        return salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
