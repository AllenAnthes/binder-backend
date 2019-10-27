package edu.ucmo.kcfedapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private HashSet<String> skillsets = new HashSet<>();

    @Lob
    @Column(name = "CONTENT")
    private String pitch;

    private String category;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
            })
    @JoinTable(name = "businesses_likes_users",
            joinColumns = {@JoinColumn(name = "business_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> likedUsers = new HashSet<>();

    private String logoUrl;

    @OneToOne
    private User user;

    public Business() {
    }

    public Business(User user) {
        this.user = user;
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

    public HashSet<String> getSkillsets() {
        return skillsets;
    }

    public void setSkillsets(HashSet<String> skillsets) {
        this.skillsets = skillsets;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public void addLikedUser(User user) {
        if (this.likedUsers == null) {
            this.likedUsers = new HashSet<>();
        }
        this.likedUsers.add(user);
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
