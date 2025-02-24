package io.api.carrent.repositories;

import io.api.carrent.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends CrudRepository<Role, Integer> {
}
