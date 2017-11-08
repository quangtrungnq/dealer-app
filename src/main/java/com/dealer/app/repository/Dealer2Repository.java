package com.dealer.app.repository;

import com.dealer.app.domain.Dealer2;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dealer2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Dealer2Repository extends JpaRepository<Dealer2, Long> {

}
