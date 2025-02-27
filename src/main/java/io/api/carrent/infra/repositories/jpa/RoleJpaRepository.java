package io.api.carrent.infra.repositories.jpa;

import io.api.carrent.domain.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends CrudRepository<Role, Integer> {
}
