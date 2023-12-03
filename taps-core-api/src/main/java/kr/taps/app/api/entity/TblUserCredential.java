package kr.taps.app.api.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.taps.app.api.common.enums.ServiceType;
import kr.taps.app.api.common.enums.ServiceTypeConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EqualsAndHashCode // 필수
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_USER_CREDENTIAL", schema = "TAPS")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class TblUserCredential {

  // 테이블 시퀀스 아이디
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long Id;

  // 고객정보 테이블 : 고객번호
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "user_no", nullable = false)
  private TblUserInfo userInfo;

  // 서비스 타입
  @Convert(converter = ServiceTypeConverter.class)
  @Column(name = "SERVICE_TYPE", length = 30, nullable = false)
  private ServiceType serviceType;

  // Client ID
  @Column(name = "CLIENT_ID", length = 4096, nullable = false)
  private String clientId;

  // Client Secret
  @Column(name = "CLIENT_SECRET", length = 4096, nullable = false)
  private String clientSecret;

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

  @Builder
  public TblUserCredential(TblUserInfo userInfo, ServiceType serviceType, String clientId, String clientSecret) {
    this.userInfo = userInfo;
    this.serviceType = serviceType;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.insOprt = "taps-be";
    this.updOprt = "taps-be";
  }

  public void updateCredential(String clientId, String clientSecret){
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public boolean isSameClientInfo(String clientId, String clientSecret){
    return this.clientId.equals(clientId) && this.clientSecret.equals(clientSecret);
  }

}
