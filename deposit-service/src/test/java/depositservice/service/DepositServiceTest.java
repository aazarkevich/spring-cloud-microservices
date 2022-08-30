package depositservice.service;

import com.deposit.controller.dto.DepositResponseDTO;
import com.deposit.entity.Deposit;
import com.deposit.exception.DepositServiceException;
import com.deposit.repository.DepositRepository;
import com.deposit.rest.AccountResponseDTO;
import com.deposit.rest.AccountServiceClient;
import com.deposit.rest.BillResponseDTO;
import com.deposit.rest.BillServiceClient;
import com.deposit.service.DepositService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;
    @Mock
    private AccountServiceClient accountServiceClient;
    @Mock
    private BillServiceClient billServiceClient;
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DepositService depositService;

    @Test
    public void depositServiceTest_withBillId() {
        BillResponseDTO billResponseDTO = createBillResponseDTO();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDTO);
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(createAccountResponseDTO());
        DepositResponseDTO deposit = depositService.deposit(null, 1L, BigDecimal.valueOf(100L));
        Assertions.assertThat(deposit.getMail()).isEqualTo("Lory");
//        System.out.println(deposit.getMail());
//        System.out.println(deposit.);

    }

    @Test(expected = DepositServiceException.class)
    public void depositServiceTest_exception() {
        depositService.deposit(null,null,BigDecimal.valueOf(1000L));
    }

    private AccountResponseDTO createAccountResponseDTO() {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setAccountID(1l);
        accountResponseDTO.setBills(Arrays.asList(1l, 2l,3l));
        accountResponseDTO.setCreationDate(OffsetDateTime.now());
        accountResponseDTO.setEmail("lory@mail.ru");
        accountResponseDTO.setName("Lory");
        accountResponseDTO.setPhone("+798654321");
        return accountResponseDTO;
    }

    private BillResponseDTO createBillResponseDTO() {
        BillResponseDTO billResponseDTO = new BillResponseDTO();
        billResponseDTO.setAccountId(1L);
        billResponseDTO.setAmount(BigDecimal.valueOf(1000));
        billResponseDTO.setBillId(1L);
        billResponseDTO.setCreationDate(OffsetDateTime.now());
        billResponseDTO.setIsDefault(true);
        billResponseDTO.setOverdraftEnabled(true);
        return billResponseDTO;
    }
}
