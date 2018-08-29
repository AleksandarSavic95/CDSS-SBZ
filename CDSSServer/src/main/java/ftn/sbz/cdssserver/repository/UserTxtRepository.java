package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.Role;
import ftn.sbz.cdssserver.model.User;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class UserTxtRepository {
    /** relative path to the .txt file with users **/ // try users.txt ONLY
    private static final String USERS_FILE = "CDSSServer/src/main/resources/users.txt"; // try users.txt ONLY

    private static final HashMap<String, User> usersMap;

    static {
        usersMap = new HashMap<>();

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(USERS_FILE))) {

            System.out.println("\nReading users...");

            stream.forEach(line -> {
                System.out.println(line);
                if (!line.startsWith("#")) {
                    String[] parsedLine = parseUsersLine(line);

                    User user = new User(parsedLine[0], parsedLine[0], parsedLine[1]);
                    user.setRole(Role.valueOf(parsedLine[2]));

                    usersMap.put(parsedLine[0], user);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nReading users finished! # = " + usersMap.size());
    }

    public User save(User user) {
        usersMap.put(user.getUsername(), user);
        return user;
    }

    public List<User> getAll() {
        return (List<User>) usersMap.values();
    }

    private static String[] parseUsersLine(String line) {
        return line.split(",");
    }

    public User findByUsername(String username) {
        return usersMap.get(username);
    }
}
