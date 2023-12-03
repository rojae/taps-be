package kr.taps.app.api.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import kr.taps.app.api.common.enums.ServiceType;
import kr.taps.app.api.common.enums.ServiceTypeConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EqualsAndHashCode // 필수
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_USER_CREDENTIAL_HISTORY", schema = "TAPS")
@EntityListeners(AuditingEntityListener.class)
public class TblUserCredentialHistory {

  // 테이블 시퀀스 아이디
  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long Id;

  // 고객번호
  @Column(name = "USER_NO", nullable = false)
  private Long userNo;

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

  @Builder
  public TblUserCredentialHistory(Long userNo, ServiceType serviceType, String clientId,
      String clientSecret, LocalDateTime insDate, String insOprt) {
    this.userNo = userNo;
    this.serviceType = serviceType;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.insDate = LocalDateTime.now();
    this.insOprt = "taps-be";
  }

  public static TblUserCredentialHistory from(TblUserCredential userCredential) {
    return TblUserCredentialHistory.builder()
        .userNo(userCredential.getUserInfo().getUserNo())
        .serviceType(userCredential.getServiceType())
        .clientId(userCredential.getClientId())
        .clientSecret(userCredential.getClientSecret())
        .build();
  }

}
