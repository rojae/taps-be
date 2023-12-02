package kr.taps.app.api.service;

import java.util.Optional;
import kr.taps.app.api.common.enums.ServiceType;
import kr.taps.app.api.common.exception.ApiRuntimeException;
import kr.taps.app.api.dto.ClientInfoRequest;
import kr.taps.app.api.entity.TblUserCredential;
import kr.taps.app.api.entity.TblUserInfo;
import kr.taps.app.api.repository.UserCredentialRepository;
import kr.taps.app.api.repository.UserInfoRepository;
import kr.taps.app.base.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserInfoRepository userInfoRepository;
  private final UserCredentialRepository userCredentialRepository;

  @Transactional(readOnly = false)
  public Long save(ClientInfoRequest request){
    Optional<TblUserInfo> selectedUserInfo = userInfoRepository.findByUserEmail(request.getEmail());

    // 가입이력이 있다면, 서비스이용 테이블에 추가
    if(selectedUserInfo.isPresent()){
      // 서비스 테이블과의 연관관계 추가
      mappingUserCredential(request, selectedUserInfo.get());
      return selectedUserInfo.get().getUserNo();
    }
    // 최초로 가입이라면, 사용자 테이블에 추가
    else{
      // 사용자 정보 테이블에 추가
      TblUserInfo userInfo = TblUserInfo.builder()
          .userEmail(request.getEmail())
          .build();
      TblUserInfo saveUserInfo = saveUserInfo(userInfo);

      // 사용자정보 테이블과 서비스 테이블 연관관계 연결
      mappingUserCredential(request, saveUserInfo);
      return saveUserInfo.getUserNo();
    }
  }

  // 사용자정보 테이블과 서비스 테이블 연관관계 연결
  @Transactional(readOnly = false)
  public void mappingUserCredential(ClientInfoRequest request, TblUserInfo saveUserInfo) {
    Optional<TblUserCredential> selectedUserCredential = userCredentialRepository.findByUserInfoAndServiceType(
        saveUserInfo, ServiceType.ofCode(request.getServiceType())
    );

    // 요청된 서비스와 동일한, 사용자 서비스가 존재한다면, 업데이트 처리
    if(selectedUserCredential.isPresent()){
      // 요청 데이터가 기존 데이터와 동일하다면, 예외처리
      if(selectedUserCredential.get().sameClientInfo(request.getClientId(), request.getClientSecret())){
        throw new ApiRuntimeException(ErrorCode.ALREADY_SAME_DATA);
      }
      selectedUserCredential.get().setClientInfo(request.getClientId(), request.getClientSecret());   // UPDATE
    }
    else{
      // 사용자 서비스 테이블에 추가
      TblUserCredential userCredential = TblUserCredential.builder()
          .userInfo(saveUserInfo)
          .serviceType(ServiceType.ofCode(request.getServiceType()))
          .clientId(request.getClientId())
          .clientSecret(request.getClientSecret())
          .build();
      TblUserCredential saveUserCredential = saveUserCredential(userCredential);

      saveUserInfo.addUserCredential(saveUserCredential);     // 사용자정보 테이블과 서비스 테이블 연관관계 연결
    }
  }

  @Transactional(readOnly = false)
  public TblUserInfo saveUserInfo(TblUserInfo tblUserInfo){
    return userInfoRepository.save(tblUserInfo);
  }

  @Transactional(readOnly = false)
  public TblUserCredential saveUserCredential(TblUserCredential tblUserCredential){
    return userCredentialRepository.save(tblUserCredential);
  }


}

