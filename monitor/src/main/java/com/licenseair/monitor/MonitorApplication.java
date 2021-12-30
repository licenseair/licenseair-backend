package com.licenseair.monitor;

// import com.licenseair.backend.commons.model.ServerStatus;
// import com.licenseair.backend.domain.AppInstance;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@SpringBootApplication
public class MonitorApplication {
  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(MonitorApplication.class, args);
    monitor();
  }

  private static void monitor() throws InterruptedException {
    while (true) {
      sleepSomeTime(1000 * 10); // 10分钟检查一次

      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      Calendar cal = new GregorianCalendar();
      cal.setTime(timestamp);
      cal.add(Calendar.MINUTE, -45); // 超过45分钟自动保存
      Timestamp fortyMinutesBefore = new Timestamp(cal.getTimeInMillis());

      System.out.printf("Check Time: %s%n", fortyMinutesBefore);

      // List<AppInstance> AppInstanceList = AppInstance.find.query().where()
      //   .order("id DESC")
      //   .eq("status", ServerStatus.Running)
      //   .lt("updated_at", fortyMinutesBefore)
      //   .findList();

      // AppInstanceList.forEach(ai -> {
      //   // deleteInstances(ai.instance_id,5);
      // });
    }
  }

  // /**
  //  * 释放实例
  //  * @param instance_id
  //  * @param count
  //  */
  // private static void deleteInstances(String instance_id, int count) {
  //   sleepSomeTime(1000 * 10); // 等待10秒钟
  //   if (count < 10) {
  //     AliyunInstances aliyunInstances = new AliyunInstances();
  //     try {
  //       aliyunInstances.callToDeleteInstances(instance_id);
  //     } catch (ServerException e) {
  //       e.printStackTrace();
  //     } catch (ClientException e) {
  //       deleteInstances(instance_id, count + 1);
  //       System.out.println("重试:" + e.getErrCode());
  //       System.out.println("ErrCode:" + e.getErrCode());
  //       System.out.println("ErrMsg:" + e.getErrMsg());
  //       System.out.println("RequestId:" + e.getRequestId());
  //     }
  //   }
  // }

  private static void sleepSomeTime(int sleepTime) {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
