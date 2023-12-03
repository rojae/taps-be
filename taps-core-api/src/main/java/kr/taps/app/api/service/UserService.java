package kr.taps.app.api.service;

import java.util.List;
import java.util.Optional;
import kr.taps.app.api.common.enums.ServiceType;
import kr.taps.app.api.common.exception.ApiRuntimeException;
import kr.taps.app.api.dto.ProfileResponse;
import kr.taps.app.api.dto.SignupRequest;
import kr.taps.app.api.dto.SignupResponse;
import kr.taps.app.api.entity.TblUserCredential;
import kr.taps.app.api.entity.TblUserCredentialHistory;
import kr.taps.app.api.entity.TblUserInfo;
import kr.taps.app.api.repository.UserCredentialHistoryRepository;
import kr.taps.app.api.repository.UserCredentialRepository;
import kr.taps.app.api.repository.UserInfoRepository;
import kr.taps.app.base.ErrorCode;
import kr.taps.app.base.TapsApiResponse;
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
  private final UserCredentialHistoryRepository userCredentialHistoryRepository;


  //////////////////////////////////////////////////////////////////////
  /////////////////////////// READ SERVICE /////////////////////////////
  //////////////////////////////////////////////////////////////////////

  public TapsApiResponse<ProfileResponse> profile(Long userNo) {
    Optional<TblUserInfo> selectedUserInfo = userInfoRepository.findById(userNo);
    if(selectedUserInfo.isEmpty()){
      throw new ApiRuntimeException(ErrorCode.DATA_NOT_FOUND);
    }
    else{
      List<TblUserCredential> selectedUserCredentials = userCredentialRepository.findByUserInfo(selectedUserInfo.get());
      return new TapsApiResponse<ProfileResponse>().ok(ProfileResponse.from(selectedUserInfo.get(), selectedUserCredentials));
    }
  }

  //////////////////////////////////////////////////////////////////////
  /////////////////////////// WRITE SERVICE ////////////////////////////
  //////////////////////////////////////////////////////////////////////

  @Transactional(readOnly = false)
  public SignupResponse save(SignupRequest request){
    Optional<TblUserInfo> selectedUserInfo = userInfoRepository.findByUserEmail(request.getEmail());

    // 가입이력이 있다면, 서비스이용 테이블에 추가
    if(selectedUserInfo.isPresent()){
      // 서비스 테이블과의 연관관계 추가
      mappingUserCredential(request, selectedUserInfo.get());
      return new SignupResponse(selectedUserInfo.get().getUserNo(), selectedUserInfo.get().getUserEmail());
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
      return new SignupResponse(saveUserInfo.getUserNo(), saveUserInfo.getUserEmail());
    }
  }

  // 사용자정보 테이블과 서비스 테이블 연관관계 연결
  @Transactional(readOnly = false)
  public void mappingUserCredential(SignupRequest request, TblUserInfo saveUserInfo) {
    Optional<TblUserCredential> selectedUserCredential = userCredentialRepository.findByUserInfoAndServiceType(
        saveUserInfo, ServiceType.ofCode(request.getServiceType())
    );

    // 요청된 서비스와 동일한, 사용자 서비스가 존재한다면, 업데이트 처리
    if(selectedUserCredential.isPresent()){
      // 요청 데이터가 기존 데이터와 동일하다면, 예외처리
      if(selectedUserCredential.get().isSameClientInfo(request.getClientId(), request.getClientSecret())){
        throw new ApiRuntimeException(ErrorCode.ALREADY_SAME_DATA);
      }
      else{
        updateCredential(selectedUserCredential.get(), request.getClientId(), request.getClientSecret());
      }
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

  //////////////////////////////// SAVE ////////////////////////////////

  @Transactional(readOnly = false)
  public TblUserInfo saveUserInfo(TblUserInfo tblUserInfo){
    return userInfoRepository.save(tblUserInfo);
  }

  @Transactional(readOnly = false)
  public TblUserCredential saveUserCredential(TblUserCredential tblUserCredential){
    return userCredentialRepository.save(tblUserCredential);
  }

  //////////////////////////////// UPDATE ////////////////////////////////

  @Transactional(readOnly = false)
  public void updateCredential(TblUserCredential userCredential, String clientId, String clientSecret){
    userCredentialHistoryRepository.save(TblUserCredentialHistory.from(userCredential));    // SAVE HISTORY
    userCredential.updateCredential(clientId, clientSecret);                                // UPDATE
  }


}

