package com.skhu.job;

import com.skhu.task.DocumentTask;

public class DocumentThread extends Thread {
	static {
		DocumentThread documentThread = new DocumentThread();
		documentThread.setDaemon(true);
		documentThread.start();
	}

	@Override
	public void run() {
		super.run();
//		DocumentTask docTask = new DocumentTask();
//
//		for (; true;) {
//			System.out
//					.println("-------------- start update --------------------");
//			try {
//				docTask.updateSkhuBrd();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out
//					.println("-------------- stop update --------------------");
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
}
