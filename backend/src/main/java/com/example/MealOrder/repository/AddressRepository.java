package com.example.MealOrder.repository;

import com.example.MealOrder.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

    AddressEntity findByStreetNameAndStreetNumber(String streetName, String streetNumber);
}
