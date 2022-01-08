package com.licenseair.monitor;

// import com.licenseair.backend.commons.model.ServerStatus;
// import com.licenseair.backend.domain.AppInstance;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.licenseair.backend.commons.model.ServerStatus;
import com.licenseair.backend.domain.AppInstance;
import com.licenseair.backend.library.AliyunInstances;
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

  private static void monitor() {
    while (true) {
      sleepSomeTime(1000 * 10); // 10分钟检查一次

      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      Calendar cal = new GregorianCalendar();
      cal.setTime(timestamp);


      List<AppInstance> AppInstanceList = AppInstance.find.query().where()
        .order("id DESC")
        .eq("status", ServerStatus.Running)
        .eq("deleted", false)
        .findList();

      System.out.println(AppInstanceList.size());

      AppInstanceList.forEach(ai -> {
        cal.add(Calendar.MINUTE, -(ai.hours * 60)); // 超过预定时间自动保存/释放
        Timestamp fortyMinutesBefore = new Timestamp(cal.getTimeInMillis());
        System.out.printf("Check Time: %s%n", fortyMinutesBefore);
        // System.out.printf("Update Time: %s%n", ai.updated_at);
        if(ai.updated_at.before(fortyMinutesBefore)) {
          if (ai.auto_save) {
            // 保存
            new Thread(() -> {
              System.out.println("stop instance");
              System.out.println(ai.instance_id);
              stopInstances(ai.instance_id,5);
            }).start();
          } else {
            // 释放
            new Thread(() -> {
              System.out.println("free instance");
              System.out.println(ai.instance_id);
              deleteInstances(ai.instance_id,5);
            }).start();
          }
        }
      });
    }
  }

  /**
   * 释放实例
   * @param instance_id 实例id
   * @param count 重试次数
   */
  private static void deleteInstances(String instance_id, int count) {
    sleepSomeTime(1000 * 10); // 等待10秒钟
    if (count < 10) {
      AliyunInstances aliyunInstances = new AliyunInstances();
      try {
        System.out.println("delete instance");
        aliyunInstances.callToStopInstances(instance_id);
        sleepSomeTime(1000 * 20); // 等待10秒钟
        aliyunInstances.callToDeleteInstances(instance_id);
        AppInstance app = AppInstance.find.query().where()
          .eq("instance_id", instance_id)
          .findOne();
        if(app != null) {
          app.setStatus("Stopping");
          app.setDeleted(true);
          app.save();
        }
      } catch (ServerException e) {
        e.printStackTrace();
      } catch (ClientException e) {
        deleteInstances(instance_id, count + 1);
        System.out.println("重试:" + e.getErrCode());
        System.out.println("ErrCode:" + e.getErrCode());
        System.out.println("ErrMsg:" + e.getErrMsg());
        System.out.println("RequestId:" + e.getRequestId());
      }
    }
  }

  /**
   * 释放实例
   * @param instance_id 实例id
   * @param count 重试次数
   */
  private static void stopInstances(String instance_id, int count) {
    sleepSomeTime(1000 * 10); // 等待10秒钟
    System.out.println(count);
    if (count < 10) {
      AliyunInstances aliyunInstances = new AliyunInstances();
      try {
        aliyunInstances.callToStopInstances(instance_id);
        AppInstance app = AppInstance.find.query().where()
          .eq("instance_id", instance_id)
          .findOne();
        if(app != null) {
          app.setStatus("Stopping");
          app.save();
        }
      } catch (ServerException e) {
        e.printStackTrace();
      } catch (ClientException e) {
        stopInstances(instance_id, count + 1);
        System.out.println("重试:" + e.getErrCode());
        System.out.println("ErrCode:" + e.getErrCode());
        System.out.println("ErrMsg:" + e.getErrMsg());
        System.out.println("RequestId:" + e.getRequestId());
      }
    }
  }

  private static void sleepSomeTime(int sleepTime) {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
