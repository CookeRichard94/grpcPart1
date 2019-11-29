/*
Richard Cooke
G00331787@gmit.ie
 */

package ie.gmit.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ie.gmit.ds.User;

public class UserDB {

    public static HashMap<Integer, User> users = new HashMap<>();
    static{
        users.put(1, new User(1, "User1", "password1", "user@gmail.com"));
        users.put(2, new User(2, "User2", "password2", "user2@gmail.com"));
        users.put(3, new User(3, "User3", "password3", "user3@gmail.com"));
    }

    public static List<User> getUsers(){
        return new ArrayList<User>(users.values());
    }

    public static User getUser(Integer id){
        return users.get(id);
    }

    public static void createUser(User user) {users.put(user.getId(), user);}

    public static void updateUser(Integer id, User user){
        users.put(id, user);
    }

    public static void deleteUser(Integer id){
        users.remove(id);
    }

    public static User getUsername(String username){
        for(User user: users.values())
        {
            if(user.getUserName().equals(username)){
                return user;
            }
        }
        return null;
    }
}
