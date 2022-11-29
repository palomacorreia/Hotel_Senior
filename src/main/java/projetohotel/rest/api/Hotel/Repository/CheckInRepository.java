package projetohotel.rest.api.Hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetohotel.rest.api.Hotel.domain.Entities.CheckIn;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
