package depositservice.controller;


import com.deposit.DepositApplication;
import com.deposit.controller.dto.DepositResponseDTO;
import com.deposit.entity.Deposit;
import com.deposit.repository.DepositRepository;
import com.deposit.rest.AccountResponseDTO;
import com.deposit.rest.AccountServiceClient;
import com.deposit.rest.BillResponseDTO;
import com.deposit.rest.BillServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import depositservice.config.SpringBootH2TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DepositApplication.class, SpringBootH2TestConfig.class})
public class DepositControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DepositRepository depositRepository;

    @MockBean
    private BillServiceClient billServiceClient;

    @MockBean
    private AccountServiceClient accountServiceClient;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private static final String REQUEST = "{\n" +
            "    \"billId\": 1,\n" +
            "    \"amount\": 3000\n" +
            "}";

    @Test
    public void createDeposit() throws Exception {
        BillResponseDTO billResponseDTO = createBillResponseDTO();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDTO);
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(createAccountResponseDTO());
        MvcResult mvcResult = mockMvc.perform(post("/deposits")
                .content(REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        List<Deposit> deposits = depositRepository.findDepositsByEmail("lori@cat.xyz");
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDTO depositResponseDTO = objectMapper.readValue(body, DepositResponseDTO.class);

        Assertions.assertThat(depositResponseDTO.getMail()).isEqualTo("Lori");
        Assertions.assertThat(depositResponseDTO.getAmount()).isEqualTo(BigDecimal.valueOf(3000));
    }

    private AccountResponseDTO createAccountResponseDTO() {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setAccountID(1L);
        accountResponseDTO.setBills(Arrays.asList(1L, 2L, 3L));
        accountResponseDTO.setCreationDate(OffsetDateTime.now());
        accountResponseDTO.setEmail("lori@cat.xyz");
        accountResponseDTO.setName("Lori");
        accountResponseDTO.setPhone("+1349831");
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
