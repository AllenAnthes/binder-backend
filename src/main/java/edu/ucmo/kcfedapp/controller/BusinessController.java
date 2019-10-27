package edu.ucmo.kcfedapp.controller;

import edu.ucmo.kcfedapp.model.Business;
import edu.ucmo.kcfedapp.model.User;
import edu.ucmo.kcfedapp.repository.BusinessRepository;
import edu.ucmo.kcfedapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/business")
public class BusinessController {

    private final BusinessRepository businessRepo;
    private final UserRepository userRepo;

    @Autowired
    public BusinessController(BusinessRepository businessRepo, UserRepository userRepo) {
        this.businessRepo = businessRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Business> getAllBusinesses() {
        return businessRepo.findAll()
                .stream()
                .filter(business -> !business.getPitch().isEmpty())
                .collect(Collectors.toList());
    }

    @GetMapping("/mine")
    public Business getBusinessById(Principal principal) {
        return getBusinessForPrincipal(principal);
    }


    @GetMapping("/byId/{id}")
    public Business getBusinessById(@PathVariable Long id) {
        return businessRepo.findById(id).orElse(null);
    }

    @PostMapping
    public Business createOrUpdateBusiness(@RequestBody Business business, Principal principal) {
        User user = getUserFromPrincipal(principal);
        business.setUser(user);
        return businessRepo.save(business);
    }

    @PutMapping
    public Business updateBusiness(@RequestBody Business business) {
        return businessRepo.save(business);
    }

    @PostMapping("/likeUser")
    public User likeUser(@RequestBody User user, Principal principal) {
        Business business = getBusinessForPrincipal(principal);
        business.addLikedUser(user);
        businessRepo.save(business);
        return user;
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

    private Business getBusinessForPrincipal(Principal principal) {
        User user = getUserFromPrincipal(principal);
        return businessRepo
                .findByUser(user)
                .orElseGet(() ->
                        createBusinessForUser(user));
    }

    private Business createBusinessForUser(User user) {
        Business business = new Business(user);
        return businessRepo.save(business);
    }
}
