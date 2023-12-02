package kr.taps.app.api.repository;

import kr.taps.app.api.entity.TblUserCredentialHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialHistoryRepository extends JpaRepository<TblUserCredentialHistory, Long> {

}
