package com.pokemon.pokemon_backend.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int hp;
    private int cp;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonbTransient // Prevent infinite JSON recursion and avoid exposing owner data
    private User owner;

    public Pokemon() {
    }

    public Pokemon(Long id, String name, int hp, int cp, User owner) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.cp = cp;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; } // Use Long for consistency

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getCp() { return cp; }
    public void setCp(int cp) { this.cp = cp; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}

