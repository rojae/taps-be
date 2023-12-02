package kr.taps.app.api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EqualsAndHashCode // 필수
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_USER_INFO_HISTORY", schema = "TAPS")
@EntityListeners(AuditingEntityListener.class)
public class TblUserInfoHistory {

  // 고객번호 (모든 테이블의 PK라고 보면 됌)
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long Id;

  // 사용자 이메일
  @Column(name = "USER_EMAIL", nullable = false)
  private String userEmail;

  // 추가된 날짜
  @CreatedDate
  @Column(name = "INS_DATE", updatable = false, nullable = false)
  private LocalDateTime insDate;

  // 생성자
  @Column(name = "INS_OPRT", updatable = false, nullable = false)
  private String insOprt;


}
