package edu.ucmo.kcfedapp.controller;

import edu.ucmo.kcfedapp.model.Business;
import edu.ucmo.kcfedapp.model.User;
import edu.ucmo.kcfedapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserRepository userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        List<User> allUser = userRepo.findAll().stream()
                .filter(isNotEmpty)
                .collect(Collectors.toList());
        logger.info("Returning all public messages: {}", allUser);
        return allUser;
    }


    @GetMapping("/bySkillset")
    public List<User> getUsersBySkillset(@Param("skillset") String skillset) {
        // this is a hack
        List<User> users = userRepo.findAll();
        List<User> matchingUsers = users.stream()
                .filter(user -> user.getSkillsets().contains(skillset))
                .filter(isNotEmpty)
                .collect(Collectors.toList());

        logger.info("Returning all public messages: {}", matchingUsers);
        return matchingUsers;
    }

    @GetMapping("/me")
    public User getAuthenticatedUser(Principal principal) {
        return userRepo.findByVerifiedUser(principal.getName()).orElse(new User());
    }

    @GetMapping("/byId/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userRepo.findById(id).orElse(null);
        logger.info("Returning user: {}", user);
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User user, Principal principal) {
        user.setVerifiedUser(principal.getName());
        return userRepo.save(user);
    }

    @PostMapping("/likeBusiness")
    public Business likeBusiness(@RequestBody Business business, Principal principal) {
        User currentUser = getUserFromPrincipal(principal);
        currentUser.addLikedBusiness(business);
        userRepo.saveAndFlush(currentUser);
        return business;
    }


    @PutMapping("/{id}/image")
    public User addUserImage(
            @PathVariable Long id,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "resume", required = false) MultipartFile resume
    ) {
        try {
            User user = userRepo.findById(id)
                    .orElse(null);

            if (user == null) {
                return null;
            }

            if (image != null) {
                Byte[] imageBytes = createBytesObject(image);
                user.setImage(imageBytes);
            }

            if (resume != null) {
                Byte[] resumeBytes = createBytesObject(resume);
                user.setResume(resumeBytes);
            }

            return userRepo.save(user);
        } catch (IOException e) {
            //todo handle better
            logger.error("Error occurred", e);
            e.printStackTrace();
        }
        return null;

    }

    private Byte[] createBytesObject(@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Byte[] byteObjects = new Byte[image.getBytes().length];
        int i = 0;
        for (byte b : image.getBytes()) {
            byteObjects[i++] = b;
        }
        return byteObjects;
    }

    private User getUserFromPrincipal(Principal principal) {
        String principalName = principal.getName();
        Optional<User> possibleUser = userRepo.findByVerifiedUser(principalName);
        return possibleUser.orElseGet(() ->
                createUserForPrincipal(principalName));
    }

    private User createUserForPrincipal(String authedName) {
        User newUser = new User();
        newUser.setVerifiedUser(authedName);
        return userRepo.save(newUser);
    }

    private Predicate<User> isNotEmpty = user -> !user.getName().isEmpty();


}
