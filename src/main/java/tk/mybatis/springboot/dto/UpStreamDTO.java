package tk.mybatis.springboot.dto;

import tk.mybatis.springboot.model.BaseEntity;

public class UpStreamDTO extends BaseEntity implements java.io.Serializable {
    private String name;

    private String url;

    private String healthCheckUrl;


    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
