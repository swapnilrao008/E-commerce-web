package in.linpaws.ecommerce.Repositories;

import in.linpaws.ecommerce.Models.Mobiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileRepository extends JpaRepository<Mobiles,Long> {
    Mobiles save(Mobiles mobiles);
    //List<Mobiles> findAll();
    Page<Mobiles> findAll(Pageable pageable);

}
