package kr.taps.app.api.common.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

@Converter
public class ServiceTypeConverter implements AttributeConverter<ServiceType, String> {

  @Override
  public String convertToDatabaseColumn(ServiceType status) {
    if (status == null)
      return ServiceType.UNKNOWN.getCode();

    return status.getCode();
  }

  @Override
  public ServiceType convertToEntityAttribute(String code) {
    if (StringUtils.isEmpty(code))
      return ServiceType.UNKNOWN;

    return ServiceType.ofCode(code);
  }

}