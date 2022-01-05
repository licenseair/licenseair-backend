package com.licenseair.backend.library;

import com.aliyuncs.AcsRequest;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.licenseair.backend.commons.model.ServerStatus;
import com.licenseair.backend.commons.util.ErrorHandler;
import com.licenseair.backend.domain.AppInstance;
import com.licenseair.backend.domain.InstanceImage;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 删除：设置为Stopping状态，停止实例运行
 * 保存：设置为Stopping状态，停止实例运行
 * 实例监控：
 * 检查所有Stopping状态实例，并且deleted=false/auto_save=false的记录，删除实例，修改deleted=true
 * 检查所有Running状态实例，如果超过设定运行时间，根据auto_save值，保存或删除记录
 */

public class AliyunInstances {
  protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

  private final Gson JSON = new Gson();
  private InstanceImage instanceImage;

  private String instancePassword;

  private final String accessKeyId = "LTAIi0pGaRgKSB3s";
  private final String accessSecret = "RAWjeZR32CBPVO1TkMTVEip3MajxyA";

  private static final String INSTANCE_STATUS_RUNNING = "Running";
  private static final int INSTANCE_STATUS_CHECK_INTERVAL_MILLISECOND = 3000;
  private static final long INSTANCE_STATUS_TOTAL_CHECK_TIME_ELAPSE_MILLISECOND = 60000 * 5;

  /**
   * 是否只预检此次请求。true：发送检查请求，不会创建实例，也不会产生费用；false：发送正常请求，通过检查后直接创建实例，并直接产生费用
   */
  private static final boolean dryRun = false;
  /**
   * 实例所属的地域ID
   */
  private final String regionId = "cn-zhangjiakou";
  /**
   * 实例的资源规格
   */
  private String instanceType;
  /**
   * 实例的计费方式
   */
  private final String instanceChargeType = "PostPaid";
  /**
   * 镜像ID
   */
  private String imageId;
  /**
   * 指定新创建实例所属于的安全组ID
   */
  private final String securityGroupId = "sg-8vbazwei4j05uzmqbkhn";
  /**
   * 购买资源的时长
   */
  private Integer period = 1;
  /**
   * 购买资源的时长单位
   */
  private final String periodUnit = "Hourly";
  /**
   * 实例所属的可用区编号
   */
  private final String zoneId = "cn-zhangjiakou-a";
  /**
   * 网络计费类型
   */
  private final String internetChargeType = "PayByTraffic";
  /**
   * 虚拟交换机ID
   */
  private final String vSwitchId = "vsw-8vbe6szd6laub18u6yxsn";
  /**
   * 实例名称
   */
  private final String instanceName = "app-instance";
  /**
   * 指定创建ECS实例的数量
   */
  private Integer amount = 1;
  /**
   * 公网出带宽最大值
   */
  private Integer internetMaxBandwidthOut = 100;
  /**
   * 是否为I/O优化实例
   */
  private final String ioOptimized = "optimized";
  /**
   * 是否开启安全加固
   */
  private final String securityEnhancementStrategy = "Active";
  /**
   * 系统盘大小
   */
  private final String systemDiskSize = "40";

  /**
   * 系统盘的磁盘种类
   */
  private final String systemDiskCategory = "cloud_essd";

  /**
   * 性能级别
   */
  private String systemDiskPerformanceLevel = "PL0";

  /**
   * 需要填充账号的AccessKey ID，以及账号的Access Key Secret
   */
  private IAcsClient client = new DefaultAcsClient(
    DefaultProfile.getProfile(regionId, accessKeyId, accessSecret)
  );

  /**
   * 调用创建实例的API，得到实例ID后继续查询实例状态
   */
  public Map<String, String> callToRunInstances(InstanceImage instanceImage, AppInstance appInstance) {
    this.instanceImage = instanceImage;
    this.instanceType = appInstance.instance_type;
    RunInstancesResponse response = callOpenApi(composeRunInstancesRequest());
    if(response == null) {
      return Map.of();
    }
    // logger.info(String.format("Success. Instance creation succeed. InstanceIds: %s",
    //   JSON.toJson(((RunInstancesResponse)response).getInstanceIdSets())));

    new Thread(() -> {
      AppInstance appIn = AppInstance.find.byId(appInstance.id);
      if(appIn != null) {
        appIn.setInstance_id(response.getInstanceIdSets().get(0));
        appIn.setPassword(this.instancePassword);
        System.out.println("instance_id");
        System.out.println(appIn.instance_id);
        System.out.println(response.getInstanceIdSets());
        appIn.save();
        callToDescribeInstances(response.getInstanceIdSets(), appIn);
      }
    }).start();

    return Map.of(
      "requestId", response.getRequestId(),
      "instanceId", response.getInstanceIdSets().get(0)
    );
  }

  private RunInstancesRequest composeRunInstancesRequest() {
    if (instanceImage != null) {
      this.imageId = instanceImage.image_id;
    } else {
      // 无可用镜像 发送短信
    }

    RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
    runInstancesRequest.setDryRun(dryRun);
    runInstancesRequest.setSysRegionId(regionId);
    runInstancesRequest.setInstanceType(instanceType);
    runInstancesRequest.setInstanceChargeType(instanceChargeType);
    runInstancesRequest.setImageId(imageId);
    runInstancesRequest.setSecurityGroupId(securityGroupId);
    runInstancesRequest.setPeriod(period);
    runInstancesRequest.setPeriodUnit(periodUnit);
    runInstancesRequest.setZoneId(zoneId);
    runInstancesRequest.setInternetChargeType(internetChargeType);
    runInstancesRequest.setVSwitchId(vSwitchId);
    runInstancesRequest.setInstanceName(instanceName);
    runInstancesRequest.setAmount(amount);
    runInstancesRequest.setInternetMaxBandwidthOut(internetMaxBandwidthOut);
    runInstancesRequest.setIoOptimized(ioOptimized);
    runInstancesRequest.setSecurityEnhancementStrategy(securityEnhancementStrategy);
    runInstancesRequest.setSystemDiskSize(systemDiskSize);
    runInstancesRequest.setSystemDiskCategory(systemDiskCategory);
    runInstancesRequest.setSystemDiskPerformanceLevel(systemDiskPerformanceLevel);
    // default username administrator

    String password = this.generatePassword();
    System.out.println(password);
    this.instancePassword = password;
    runInstancesRequest.setPassword(password);

    return runInstancesRequest;
  }

  // 生成9为密码
  private String generatePassword() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String oriPass = encoder.encode("administrator");
    String[] pass = oriPass.split("");
    ArrayList<String> winPass = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      winPass.add(pass[i+2]);
      System.out.println(pass[i+2]);
    }
    return String.join("", winPass);
  }

  public void callToDeleteInstances(String instanceId) throws ClientException {
    DeleteInstanceRequest request = new DeleteInstanceRequest();
    request.setSysRegionId(regionId);
    request.setInstanceId(instanceId);

    DeleteInstanceResponse response = client.getAcsResponse(request);
  }

  public void callToStopInstances(String instanceId) {
    StopInstanceRequest request = new StopInstanceRequest();
    request.setSysRegionId(regionId);
    request.setInstanceId(instanceId);
    request.setForceStop(true);

    try {
      StopInstanceResponse response = client.getAcsResponse(request);
      logger.info(new Gson().toJson(response));
    } catch (ServerException e) {
      e.printStackTrace();
    } catch (ClientException e) {
      System.out.println("ErrCode:" + e.getErrCode());
      System.out.println("ErrMsg:" + e.getErrMsg());
      System.out.println("RequestId:" + e.getRequestId());
    }
  }

  public void callToRestartInstances(String instanceId) {
    StartInstanceRequest request = new StartInstanceRequest();
    request.setSysRegionId(regionId);
    request.setInstanceId(instanceId);

    try {
      StartInstanceResponse response = client.getAcsResponse(request);
      logger.info(new Gson().toJson(response));
    } catch (ServerException e) {
      e.printStackTrace();
    } catch (ClientException e) {
      System.out.println("ErrCode:" + e.getErrCode());
      System.out.println("ErrMsg:" + e.getErrMsg());
      System.out.println("RequestId:" + e.getRequestId());
      System.out.println("五秒后重试");
      sleepSomeTime(1000 * 5);
      this.callToRestartInstances(instanceId);
    }
  }

  /**
   * 每3秒中检查一次实例的状态，超时时间设为5分钟。
   * @param instanceIds 需要检查的实例ID
   */
  private void callToDescribeInstances(List<String> instanceIds, AppInstance appInstance) {
    Long startTime = System.currentTimeMillis();
    for(;;) {
      sleepSomeTime(INSTANCE_STATUS_CHECK_INTERVAL_MILLISECOND);
      DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
      describeInstancesRequest.setSysRegionId(regionId);
      describeInstancesRequest.setInstanceIds(JSON.toJson(instanceIds));
      DescribeInstancesResponse describeInstancesResponse = callOpenApi(describeInstancesRequest);
      Long timeStamp = System.currentTimeMillis();
      if(describeInstancesResponse == null) {
        continue;
      } else {
        for(DescribeInstancesResponse.Instance instance : describeInstancesResponse.getInstances()) {
          if (instance.getPublicIpAddress().size() > 0) {
            appInstance.setPublic_address(instance.getPublicIpAddress().get(0));
            appInstance.setPrivate_address(instance.getVpcAttributes().getPrivateIpAddress().get(0));
            appInstance.setStatus(instance.getStatus().trim());
            appInstance.save();
          }

          // System.out.println(nodeServer.status);
          // System.out.println(instance.getPublicIpAddress());
          // System.out.println(instance.getVpcAttributes().getPrivateIpAddress());
          logger.info(instance.getStatus());
          if(INSTANCE_STATUS_RUNNING.equals(instance.getStatus())) {
            instanceIds.remove(instance.getInstanceId());
            logger.info(String.format("Instance boot successfully: %s", instance.getInstanceId()));
          }
        }
      }
      if(instanceIds.size() == 0) {
        logger.info("Instances all boot successfully.");
        return;
      }
      if(timeStamp - startTime > INSTANCE_STATUS_TOTAL_CHECK_TIME_ELAPSE_MILLISECOND) {
        if(instanceIds.size() > 0) {
          logger.info(String.format("Instances boot failed within %s mins: %s",
            INSTANCE_STATUS_TOTAL_CHECK_TIME_ELAPSE_MILLISECOND /60000, JSON.toJson(instanceIds)));
        } else {
          logger.info("Instances all boot successfully.");
        }
        return;
      }
    }
  }

  /**
   * 调用OpenAPI的方法，这里进行了错误处理
   *
   * @param request AcsRequest, Open API的请求
   * @param <T> AcsResponse 请求所对应返回值
   * @return 返回OpenAPI的调用结果，如果调用失败，则会返回null
   */
  private <T extends AcsResponse> T  callOpenApi(AcsRequest<T> request) {
    try {
      T response = client.getAcsResponse(request, false, 0);
      System.out.println(String.format("Success. OpenAPI Action: %s call successfully.", request.getSysActionName()));
      return response;
    } catch (ServerException e) {
      System.out.println(String.format("Fail. Something with your connection with Aliyun go incorrect. ErrorCode: %s",
        e.getErrCode()));
    } catch (ClientException e) {
      System.out.println(String.format("Fail. Business error. ErrorCode: %s, RequestId: %s",
        e.getErrCode(), e.getRequestId()));
    }
    return null;
  }

  private static void sleepSomeTime(int sleepTime) {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
