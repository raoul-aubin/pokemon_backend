package com.pokemon.pokemon_backend.resource;

import com.pokemon.pokemon_backend.model.Pokemon;
import com.pokemon.pokemon_backend.service.PokemonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pokemons")
@Produces(MediaType.APPLICATION_JSON)
public class PokemonResource {

    @Inject
    private PokemonService pokemonService;

    @GET
    public List<Pokemon> getAllPokemons() {
        return pokemonService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getPokemonById(@PathParam("id") Long id) {

        return pokemonService.findById(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response createPokemon(Pokemon pokemon) {
        pokemonService.create(pokemon);
        return Response.status(Response.Status.CREATED)
                .entity(pokemon)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePokemon(@PathParam("id") Long id, Pokemon pokemon) {
        return pokemonService.update(id, pokemon)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePokemon(@PathParam("id") Long id) {
        boolean deleted = pokemonService.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build(); // 204
    }

}

