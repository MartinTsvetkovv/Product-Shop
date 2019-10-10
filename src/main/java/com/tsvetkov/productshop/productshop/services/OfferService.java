package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.models.service.OfferServiceModel;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

public interface OfferService {
    List<OfferServiceModel> findAllOffers();

}
