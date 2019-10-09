package com.tsvetkov.productshop.productshop.repository;

import com.tsvetkov.productshop.productshop.domain.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.openmbean.OpenDataException;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    Optional<Offer> findByProduct_Id(String id);
}
