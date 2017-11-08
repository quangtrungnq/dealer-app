package com.dealer.app.service.impl;

import com.dealer.app.service.Dealer2Service;
import com.dealer.app.domain.Dealer2;
import com.dealer.app.repository.Dealer2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Dealer2.
 */
@Service
@Transactional
public class Dealer2ServiceImpl implements Dealer2Service{

    private final Logger log = LoggerFactory.getLogger(Dealer2ServiceImpl.class);

    private final Dealer2Repository dealer2Repository;

    public Dealer2ServiceImpl(Dealer2Repository dealer2Repository) {
        this.dealer2Repository = dealer2Repository;
    }

    /**
     * Save a dealer2.
     *
     * @param dealer2 the entity to save
     * @return the persisted entity
     */
    @Override
    public Dealer2 save(Dealer2 dealer2) {
        log.debug("Request to save Dealer2 : {}", dealer2);
        return dealer2Repository.save(dealer2);
    }

    /**
     *  Get all the dealer2S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dealer2> findAll(Pageable pageable) {
        log.debug("Request to get all Dealer2S");
        return dealer2Repository.findAll(pageable);
    }

    /**
     *  Get one dealer2 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Dealer2 findOne(Long id) {
        log.debug("Request to get Dealer2 : {}", id);
        return dealer2Repository.findOne(id);
    }

    /**
     *  Delete the  dealer2 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dealer2 : {}", id);
        dealer2Repository.delete(id);
    }
}
