package com.skhu.job;

//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.StatefulJob;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//
//import com.skhu.task.DocumentTask;
//
//public class DocumentJobBean  extends QuartzJobBean implements StatefulJob {
//	public DocumentTask docTask;
//	
//	@Override
//	protected void executeInternal(JobExecutionContext arg0)
//			throws JobExecutionException {
//		for(;true;){
//			System.out.println("-------------- start update --------------------");
//			docTask.updateSkhuBrd();
//			try {
//				Thread.sleep(600000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			System.out.println("-------------- stop update --------------------");
//		}
//	
//	}
//
//	public void setDocTask(DocumentTask docTask) {
//		this.docTask = docTask;
//	}
//}
