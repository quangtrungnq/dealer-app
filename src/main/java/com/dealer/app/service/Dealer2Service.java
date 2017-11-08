package com.dealer.app.service;

import com.dealer.app.domain.Dealer2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Dealer2.
 */
public interface Dealer2Service {

    /**
     * Save a dealer2.
     *
     * @param dealer2 the entity to save
     * @return the persisted entity
     */
    Dealer2 save(Dealer2 dealer2);

    /**
     *  Get all the dealer2S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dealer2> findAll(Pageable pageable);

    /**
     *  Get the "id" dealer2.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Dealer2 findOne(Long id);

    /**
     *  Delete the "id" dealer2.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
