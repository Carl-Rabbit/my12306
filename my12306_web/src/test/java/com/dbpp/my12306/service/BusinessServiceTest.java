package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@RunWith(SpringRunner.class)
class BusinessServiceTest {

	@Autowired
	private BusinessService businessService;
	@Autowired
	private UserService userService;
	@Autowired
	private PassengerService passengerService;

	@Test
	@Transactional
	void buyTickets() {
		final int n = 100;
		final int[] cntSuccess = {0};
		final int[] cntPrefer = {0};
		final long[] time = {0};

		class Client extends Thread {
			int id;
			boolean finished = false;
			Integer userId;
			Integer[] psgIdArr;
			String[] trainCodeArr;
			Integer[] fromIndexArr;
			Integer[] toIndexArr;
			String[] departDateArr;
			String[] seatClassArr;
			String[] seatTypeArr;
			String[] preferArr;

			String msg;

			public Client(int id,
			              Integer userId,
			              Integer[] psgIdArr,
			              String[] trainCodeArr,
			              Integer[] fromIndexArr,
			              Integer[] toIndexArr,
			              String[] departDateArr,
			              String[] seatClassArr,
			              String[] seatTypeArr,
			              String[] preferArr) {
				this.id = id;
				this.userId = userId;
				this.psgIdArr = psgIdArr;
				this.trainCodeArr = trainCodeArr;
				this.fromIndexArr = fromIndexArr;
				this.toIndexArr = toIndexArr;
				this.departDateArr = departDateArr;
				this.seatClassArr = seatClassArr;
				this.seatTypeArr = seatTypeArr;
				this.preferArr = preferArr;
			}

			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				long time1 = 0, time2 = 0;
				Object[] ret;
				int tId = -1;
				List<Ticket> tickets;
				try {
					time1 = System.currentTimeMillis();
					ret = businessService.buyTickets(userId,
							psgIdArr,
							trainCodeArr,
							fromIndexArr,
							toIndexArr,
							departDateArr,
							seatClassArr,
							seatTypeArr,
							preferArr);
					time2 = System.currentTimeMillis();
					// analyze ret
					var ids = (List<Integer>) ret[1];
					tickets = (List<Ticket>) ret[2];

					cntSuccess[0] += (ids.get(0) > 0) ? 1 : 0;
					tId = ids.get(0);
					if (tId > 0) {
//						String locate = tickets.get(0).getSeatInfo().split(" ")[1];
//						cntPrefer[0] += (preferArr[0].equals(locate.substring(locate.length() - 1))) ? 1 : 0;
					}
					time[0] += time2 - time1;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("error id: " + id);
					System.exit(-1);
				} finally {
					System.out.println(id + ": finished, cutSuccess: " + cntSuccess[0]
							+ " tId: " + tId + " Time: " + (time2 - time1)
							+ " tot: " + time[0]
//							+ " cntPrefer: " + cntPrefer[0]
					);

//					System.out.println(Arrays.toString(ret));
					finished = true;
				}

			}
		}

		ArrayList<Client> list = new ArrayList<>();

		for (int i = 0; i < n; i++) {
//			var ret = userService.add("TestUser" + i, "123456", "13511112222", "A", "Y");
//			if (ret.getStatus() != ResultCode.CREATED.getCode()) {
//				throw new RuntimeException("User not created: " + i + "\n" + ret);
//			}
//			Integer userId = ret.getData();
//			var ret2 = passengerService.add(userId, "TestPsg", "TestPsg", "A", "M", "150223200002292137");
//			if (ret2.getStatus() != ResultCode.CREATED.getCode()) {
//				throw new RuntimeException("Psg not created: " + i + "\n" + ret2);
//			}
//			Integer psgId = ret2.getData();

			Integer userId = 92 + i;
			int psgId = 76 + i;
			Client c = new Client(i, userId,
					new Integer[]{psgId},
					new String[]{"G410"},
					new Integer[]{2},
					new Integer[]{3},
					new String[]{"2020-05-29"},
					new String[]{"B"},
					new String[]{"Z"},
//					new String[]{i < n / 2 ? "F" : "A"});
					new String[]{null});
			list.add(c);
		}

//		System.exit(0);

		ExecutorService threadPool = Executors.newFixedThreadPool(n + 1);

		for (Client c : list) {
			threadPool.submit(c);
		}

		while (true) {

		}
	}
}