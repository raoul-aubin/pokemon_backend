package com.pokemon.pokemon_backend.service;
import com.pokemon.pokemon_backend.model.Pokemon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class PokemonService {

    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    public List<Pokemon> findAll() {
        return em.createQuery("SELECT p FROM Pokemon p", Pokemon.class)
                .getResultList();
    }

    public Optional<Pokemon> findById(Long id) {
        return Optional.ofNullable(em.find(Pokemon.class, id));
    }

    @Transactional
    public Pokemon create(Pokemon pokemon) {
        em.persist(pokemon);
        return pokemon;
    }

    @Transactional
    public Optional<Pokemon> update(Long id, Pokemon updated) {
        Pokemon existing = em.find(Pokemon.class, id);
        if (existing == null) {
            return Optional.empty();
        }

        existing.setName(updated.getName());
        existing.setHp(updated.getHp());
        existing.setCp(updated.getCp());

        return Optional.of(existing);
    }

    @Transactional
    public boolean delete(Long id) {
        Pokemon pokemon = em.find(Pokemon.class, id);
        if (pokemon == null) {
            return false;
        }
        em.remove(pokemon);
        return true;
    }
}