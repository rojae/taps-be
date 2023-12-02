package kr.taps.app.api.repository;

import kr.taps.app.api.entity.TblUserInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoHistoryRepository extends JpaRepository<TblUserInfoHistory, Long> {

}
