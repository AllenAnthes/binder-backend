package edu.ucmo.kcfedapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String verifiedUser = "";

    private String name = "";

    @Lob
    private String bio = "";

    @Lob
    private Byte[] image;

    @Lob
    private Byte[] resume;

    private HashSet<String> skillsets = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_liked_businesses")
    @JsonIgnoreProperties({"likedUsers", "user"})
    private Set<Business> likedBusinesses = new HashSet<>();

    @Lob
    private String imageUrl = "";

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Byte[] getResume() {
        return resume;
    }

    public void setResume(Byte[] resume) {
        this.resume = resume;
    }

    public HashSet<String> getSkillsets() {
        return skillsets;
    }

    public void setSkillsets(HashSet<String> skillsets) {
        this.skillsets = skillsets;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getVerifiedUser() {
        return verifiedUser;
    }

    public void setVerifiedUser(String verifiedUser) {
        this.verifiedUser = verifiedUser;
    }

    public Set<Business> getLikedBusinesses() {
        return likedBusinesses;
    }

    public void setLikedBusinesses(Set<Business> likedBusinesses) {
        this.likedBusinesses = likedBusinesses;
    }

    public void addLikedBusiness(Business business) {
        if (this.likedBusinesses == null) {
            this.likedBusinesses = new HashSet<>();
        }
        this.likedBusinesses.add(business);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
