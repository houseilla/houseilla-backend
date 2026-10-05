package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> {

}