package kr.taps.app.api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EqualsAndHashCode // 필수
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_USER_INFO", schema = "TAPS")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class TblUserInfo {

  // 고객번호 (모든 테이블의 PK라고 보면 됌)
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "USER_NO", nullable = false)
  private Long userNo;

  // 사용자 이메일
  @Column(name = "USER_EMAIL", nullable = false)
  private String userEmail;

  // 고객의 서비스 정보
  @OneToMany(mappedBy = "userInfo", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  @Fetch(FetchMode.JOIN)
  private List<TblUserCredential> userCredentials = new ArrayList<>();

  // 추가된 날짜
  @CreatedDate
  @Column(name = "INS_DATE", updatable = false, nullable = false)
  private LocalDateTime insDate;

  // 생성자
  @Column(name = "INS_OPRT", updatable = false, nullable = false)
  private String insOprt;

  // 수정된 날짜
  @LastModifiedDate
  @Column(name = "UPD_DATE", updatable = true, nullable = false)
  private LocalDateTime updDate;

  // 수정자
  @Column(name = "UPD_OPRT", updatable = true, nullable = false)
  private String updOprt;

  public void addUserCredential(TblUserCredential userCredential){
    if(this.userCredentials == null){
      this.userCredentials = List.of(userCredential);
    }
    else{
      this.userCredentials.add(userCredential);
    }
  }

  @Builder
  public TblUserInfo(String userEmail, List<TblUserCredential> userCredentials) {
    this.userEmail = userEmail;
    this.userCredentials = userCredentials;
    this.insDate = LocalDateTime.now();
    this.insOprt = "taps-be";
    this.updDate = LocalDateTime.now();
    this.updOprt = "taps-be";
  }

}
