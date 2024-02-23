package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.controller.UsersController;
import com.kbtg.bootcamp.posttest.dto.TicketsDto;
import com.kbtg.bootcamp.posttest.entity.UserTicketsEntity;
import com.kbtg.bootcamp.posttest.entity.UsersEntity;
import com.kbtg.bootcamp.posttest.service.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class PosttestApplicationTests {

	@InjectMocks
	private UsersController usersController;

	@Mock
	private UsersService usersService;

	@Test
	void testGetAllLotteries() {
		TicketsDto ticketsDto = new TicketsDto();
		when(usersService.getAllLotteries()).thenReturn(ticketsDto);
		ResponseEntity<?> responseEntity = usersController.lotteries();
		assert responseEntity.getStatusCode() == HttpStatus.OK;
		assert responseEntity.getBody() != null && responseEntity.getBody() instanceof TicketsDto;
	}

	@Test
	void testGetListMyLotteries() {
		Long userId = 1L;
		TicketsDto expectedTicketsDto = new TicketsDto();
		when(usersService.getMyLotteries(userId)).thenReturn(expectedTicketsDto);
		TicketsDto actualTicketsDto = usersController.myLotteries(userId);
		assertEquals(expectedTicketsDto, actualTicketsDto);
	}

	@Test
	void testBuyLotteries() {
		Long userId = 1L;
		Integer ticketId = 112233;
		UserTicketsEntity expectedUserTicketsEntity = new UserTicketsEntity();
		when(usersService.buyLotteries(userId, ticketId)).thenReturn(expectedUserTicketsEntity);
		UserTicketsEntity responseEntity = usersController.buyLotteries(userId, ticketId);
		assertEquals(expectedUserTicketsEntity, responseEntity);
	}

	@Test
	void testUserRegister() {
		UsersEntity usersEntity = new UsersEntity();
		String expectedMessage = "Your userId is : " + usersEntity.getUserId();
		ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok(expectedMessage);
		when(usersService.usersRegister(usersEntity)).thenReturn(expectedResponseEntity);
		ResponseEntity<String> actualResponseEntity = usersController.userRegister(usersEntity);
		assertNotNull(actualResponseEntity);
		assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
		assertEquals(expectedMessage, actualResponseEntity.getBody());
	}

	@Test
	void testSellLotteries() {
		Long userId = 1L;
		Integer ticketId = 2;
		TicketsDto expectedTicketsDto = new TicketsDto();
		when(usersService.sellLotteries(userId, ticketId)).thenReturn(expectedTicketsDto);
		TicketsDto actualTicketsDto = usersController.sellLotteries(userId, ticketId);
		assertNotNull(actualTicketsDto);
		assertEquals(expectedTicketsDto, actualTicketsDto);
	}
}
