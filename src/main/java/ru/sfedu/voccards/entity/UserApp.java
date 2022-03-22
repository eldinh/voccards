package ru.sfedu.voccards.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;


    private String password;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", fetch = FetchType.LAZY)
    private List<CardSet> ownSets;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE
            , CascadeType.REFRESH}, fetch = FetchType.LAZY
     )
    @JoinTable(
            name = "other_sets_customer",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "other_sets_id") }
    )
    private List<CardSet> otherSets;


    public void addOwnCardSet(CardSet cardSet){
        if (ownSets == null)
            ownSets = new ArrayList<>();

        ownSets.add(cardSet);
    }

    public void addOtherSets(CardSet cardSet){
        if (otherSets == null)
            otherSets = new ArrayList<>();

        otherSets.add(cardSet);
    }

}
