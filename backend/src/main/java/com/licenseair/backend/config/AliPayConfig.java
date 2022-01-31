package com.licenseair.backend.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class AliPayConfig {

  @Value("${site.payNotify}")
  private String notifyUrl;

  @Value("${site.rootPath}")
  private String rootPath;

  private final String certsPath = Paths.get("").toAbsolutePath().toString();

  /**
   * 配置支付宝
   *
   * @return
   */
  @Bean
  public void SetConfig() {
    // 1. 设置参数（全局只需设置一次）
    Factory.setOptions(getOptions());
  }

  private Config getOptions() {
    Config config = new Config();
    config.protocol = "https";
    config.gatewayHost = "openapi.alipay.com";
    config.signType = "RSA2";
    config.appId = "2019010362746842";

    String privateKey = "";
    try {
      String privateKeyPath = this.getCrt("appPrivate.key");
      BufferedReader fileReader = new BufferedReader(new FileReader(privateKeyPath));
      privateKey = fileReader.readLine();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
    config.merchantPrivateKey = privateKey;

    //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
    config.merchantCertPath = this.getCrt("appCertPublicKey_2019010362746842.crt");
    config.alipayCertPath = this.getCrt("alipayCertPublicKey_RSA2.crt");
    config.alipayRootCertPath = this.getCrt("alipayRootCert.crt");

    //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
    // config.alipayPublicKey = "<-- 请填写您的支付宝公钥，例如：MIIBIjANBg... -->";

    //可设置异步通知接收服务地址（可选）
    config.notifyUrl = this.notifyUrl;

    //可设置AES密钥，调用AES加解密相关接口时需要（可选）
    // config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";

    return config;
  }

  private String getCrt(String file) {
    if (rootPath.length() > 0) {
      return String.format("%s/%s/certs/%s", certsPath, rootPath, file);
    } else {
      return String.format("%s/certs/%s", certsPath, file);
    }
  }
}
