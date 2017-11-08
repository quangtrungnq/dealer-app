package com.dealer.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dealer.app.domain.Dealer2;
import com.dealer.app.service.Dealer2Service;
import com.dealer.app.web.rest.errors.BadRequestAlertException;
import com.dealer.app.web.rest.util.HeaderUtil;
import com.dealer.app.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dealer2.
 */
@RestController
@RequestMapping("/api")
public class Dealer2Resource {

    private final Logger log = LoggerFactory.getLogger(Dealer2Resource.class);

    private static final String ENTITY_NAME = "dealer2";

    private final Dealer2Service dealer2Service;

    public Dealer2Resource(Dealer2Service dealer2Service) {
        this.dealer2Service = dealer2Service;
    }

    /**
     * POST  /dealer-2-s : Create a new dealer2.
     *
     * @param dealer2 the dealer2 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dealer2, or with status 400 (Bad Request) if the dealer2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dealer-2-s")
    @Timed
    public ResponseEntity<Dealer2> createDealer2(@RequestBody Dealer2 dealer2) throws URISyntaxException {
        log.debug("REST request to save Dealer2 : {}", dealer2);
        if (dealer2.getId() != null) {
            throw new BadRequestAlertException("A new dealer2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dealer2 result = dealer2Service.save(dealer2);
        return ResponseEntity.created(new URI("/api/dealer-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dealer-2-s : Updates an existing dealer2.
     *
     * @param dealer2 the dealer2 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dealer2,
     * or with status 400 (Bad Request) if the dealer2 is not valid,
     * or with status 500 (Internal Server Error) if the dealer2 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dealer-2-s")
    @Timed
    public ResponseEntity<Dealer2> updateDealer2(@RequestBody Dealer2 dealer2) throws URISyntaxException {
        log.debug("REST request to update Dealer2 : {}", dealer2);
        if (dealer2.getId() == null) {
            return createDealer2(dealer2);
        }
        Dealer2 result = dealer2Service.save(dealer2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealer2.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dealer-2-s : get all the dealer2S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dealer2S in body
     */
    @GetMapping("/dealer-2-s")
    @Timed
    public ResponseEntity<List<Dealer2>> getAllDealer2S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dealer2S");
        Page<Dealer2> page = dealer2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dealer-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dealer-2-s/:id : get the "id" dealer2.
     *
     * @param id the id of the dealer2 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dealer2, or with status 404 (Not Found)
     */
    @GetMapping("/dealer-2-s/{id}")
    @Timed
    public ResponseEntity<Dealer2> getDealer2(@PathVariable Long id) {
        log.debug("REST request to get Dealer2 : {}", id);
        Dealer2 dealer2 = dealer2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dealer2));
    }

    /**
     * DELETE  /dealer-2-s/:id : delete the "id" dealer2.
     *
     * @param id the id of the dealer2 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dealer-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteDealer2(@PathVariable Long id) {
        log.debug("REST request to delete Dealer2 : {}", id);
        dealer2Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
