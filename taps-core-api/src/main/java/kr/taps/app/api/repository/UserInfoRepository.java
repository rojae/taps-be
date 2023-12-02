package kr.taps.app.api.repository;

import java.util.Optional;
import kr.taps.app.api.entity.TblUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<TblUserInfo, Long> {

  Optional<TblUserInfo> findByUserEmail(String email);

}