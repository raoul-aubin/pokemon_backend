package com.pokemon.pokemon_backend.resource;

import com.pokemon.pokemon_backend.model.Pokemon;
import com.pokemon.pokemon_backend.model.User;
import com.pokemon.pokemon_backend.service.PokemonService;
import com.pokemon.pokemon_backend.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/pokemons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonResource {

    @Inject
    private PokemonService pokemonService;

    @Inject
    private UserService userService;

    // Public: list all Pokémons
    @GET
    public List<Pokemon> getAllPokemons() {
        return pokemonService.findAll();
    }

    // Public: get one Pokémon by id
    @GET
    @Path("/{id}")
    public Response getPokemonById(@PathParam("id") Long id) {
        return pokemonService.findById(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    // Protected: create a Pokémon owned by the authenticated user
    @POST
    public Response createPokemon(Pokemon pokemon, @Context SecurityContext sc) {

        if (sc.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String username = sc.getUserPrincipal().getName();
        User owner = userService.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));

        // Never trust client input for id/owner
        pokemon.setId(null);     // Requires Pokemon.setId(Long)
        pokemon.setOwner(owner);

        pokemonService.create(pokemon);

        return Response.status(Response.Status.CREATED)
                .entity(pokemon)
                .build();
    }

    // Protected: only owner can update
    @PUT
    @Path("/{id}")
    public Response updatePokemon(@PathParam("id") Long id, Pokemon updated, @Context SecurityContext sc) {

        if (sc.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String username = sc.getUserPrincipal().getName();
        User requester = userService.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));

        try {
            Pokemon result = pokemonService.update(id, requester.getId(), updated);

            if (result == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(result).build();

        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    // Protected: only owner can delete
    @DELETE
    @Path("/{id}")
    public Response deletePokemon(@PathParam("id") Long id, @Context SecurityContext sc) {

        if (sc.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String username = sc.getUserPrincipal().getName();
        User requester = userService.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));

        try {
            boolean deleted = pokemonService.delete(id, requester.getId());

            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.noContent().build();

        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
