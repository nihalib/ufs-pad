package com.bluemoonllc.ufs.api.controller;

import com.bluemoonllc.ufs.api.service.bi.RegisterBI;
import com.bluemoonllc.ufs.model.CiamUser;
import com.bluemoonllc.ufs.model.UfsResponse;
import com.bluemoonllc.ufs.model.common.UfsResponseStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {
  @Mock private RegisterBI service;
  @InjectMocks private RegistrationController controller;

  @Test
  public void testRegisterCiam() {
    //Given
    CiamUser user = CiamUser.builder().ciamId(BigInteger.ONE).location("Test").currencyCode("EUR").build();
    UfsResponseStatus status = UfsResponseStatus.DATA_UPDATED;
    UfsResponse<CiamUser> response = new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", user);
    //When
    Mockito.when(service.addCiam(user, true)).thenReturn(response);
    ResponseEntity<UfsResponse> result = controller.registerCiam(user, true);
    //Then
    assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
  }
}