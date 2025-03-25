package com.trimble.trimblecars.repositories;

import com.trimble.trimblecars.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,String> {
    Optional<Owner> findByOwnerEmail(String ownerEmail);

}
