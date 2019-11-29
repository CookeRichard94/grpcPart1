/*
Richard Cooke
G00331787@gmit.ie
 */

package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UserLogin {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    // Constructor for userLogin
    public UserLogin(){

    }


    // Getters and setters
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
}
