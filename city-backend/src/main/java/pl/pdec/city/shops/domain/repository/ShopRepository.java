package pl.pdec.city.shops.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pdec.city.shops.domain.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
