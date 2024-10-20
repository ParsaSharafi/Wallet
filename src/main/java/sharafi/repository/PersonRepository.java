package sharafi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharafi.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	boolean existsByNationalId(String nationalId);
	
	Person findByNationalId(String nationalId);
	
	boolean existsByPhoneNumber(String PhoneNumber);
	
	Person findByPhoneNumber(String PhoneNumber);
	
	boolean existsByEmail(String email);
	
	Person findByEmail(String email);
	
	@Query(value = "SELECT p FROM Person p WHERE p.user.username = :username")
	Person findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT p.id FROM Person p WHERE p.user.username = :username")
	Integer findIdByUsername(@Param("username") String username);
}