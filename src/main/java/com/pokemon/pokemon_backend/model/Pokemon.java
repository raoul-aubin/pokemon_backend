package com.pokemon.pokemon_backend.model;
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


    public Pokemon() {
    }

    public Pokemon(Long id, String name, int hp, int cp) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.cp = cp;
    }

    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getCp() { return cp; }
    public void setCp(int cp) { this.cp = cp; }
}
