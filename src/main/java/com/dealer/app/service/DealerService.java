package com.dealer.app.service;

import com.dealer.app.domain.Dealer;
import java.util.List;

/**
 * Service Interface for managing Dealer.
 */
public interface DealerService {

    /**
     * Save a dealer.
     *
     * @param dealer the entity to save
     * @return the persisted entity
     */
    Dealer save(Dealer dealer);

    /**
     *  Get all the dealers.
     *
     *  @return the list of entities
     */
    List<Dealer> findAll();

    /**
     *  Get the "id" dealer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Dealer findOne(Long id);

    /**
     *  Delete the "id" dealer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
