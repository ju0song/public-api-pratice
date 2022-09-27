package pratice.publicapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pratice.publicapi.entity.Api;

public interface ApiRepository extends JpaRepository<Api, Long> {
}