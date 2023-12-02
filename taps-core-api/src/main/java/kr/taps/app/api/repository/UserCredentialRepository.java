package kr.taps.app.api.repository;

import java.util.Optional;
import kr.taps.app.api.common.enums.ServiceType;
import kr.taps.app.api.entity.TblUserCredential;
import kr.taps.app.api.entity.TblUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<TblUserCredential, Long> {

  Optional<TblUserCredential> findByUserInfoAndServiceType(TblUserInfo userInfo, ServiceType serviceType);

}
