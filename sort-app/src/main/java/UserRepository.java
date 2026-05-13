import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
public List <User> readFromFile(String filePath){
    List<User> users = new ArrayList<>();

     try(BufferedReader br = new BufferedReader (new FileReader(filePath))){
         String line;
         while ((line = br.readLine()) != null){
             String[] data = line.split("\t");
                    if (data.length >= 3 ){
                        User user = new User (data[0], data[1], Integer.parseInt(data[2]));
                                users.add(user);
                    }
         }
     } catch (IOException e){
         System.err.println("Error reading file: " + e.getMessage());
     }
     return users;
}

}
