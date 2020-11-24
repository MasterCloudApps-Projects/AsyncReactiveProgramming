package es.codeurjc.arpj.worker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eureka.instance")
public class EurekaProperties {

    private String appname;

    private String instanceId;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String toString() {
        return "EurekaProperties{" +
                "appname='" + appname + '\'' +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
