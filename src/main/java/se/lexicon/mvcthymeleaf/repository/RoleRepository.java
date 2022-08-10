package se.lexicon.mvcthymeleaf.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.mvcthymeleaf.model.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>
{
}
