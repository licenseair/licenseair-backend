package com.licenseair.backend.library;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.licenseair.backend.commons.model.ServerStatus;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.backend.domain.AppInstance;
import com.licenseair.backend.domain.InstanceImage;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class AppManager {
  public boolean create(InstanceImage instanceImage, AppInstance appInstance) {
    AliyunInstances instances = new AliyunInstances();
    Map<String, String> runRes = instances.callToRunInstances(instanceImage, appInstance);

    return runRes.get("instanceId") != null;
  }

  public void update(AppInstance appInstance) throws ClientException {
    AliyunInstances instances = new AliyunInstances();
    if(appInstance.status.trim().equals(ServerStatus.Stopping)) {
      try {
        instances.callToStopInstances(appInstance.instance_id);
      } catch (ServerException e) {
        e.printStackTrace();
      } catch (ClientException e) {
        instances.callToStopInstances(appInstance.instance_id);
        System.out.println("重试:" + e.getErrCode());
        System.out.println("ErrCode:" + e.getErrCode());
        System.out.println("ErrMsg:" + e.getErrMsg());
        System.out.println("RequestId:" + e.getRequestId());
      }
    } else if(appInstance.status.trim().equals(ServerStatus.Running)) {
      instances.callToRestartInstances(appInstance.instance_id);
    }
  }
}
