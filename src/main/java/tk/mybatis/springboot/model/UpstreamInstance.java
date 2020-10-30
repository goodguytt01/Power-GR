package tk.mybatis.springboot.model;

public class UpstreamInstance extends BaseEntity {

    private Integer upStreamId;

    private String upStreamName;

    private String ipAddress;

    public Integer getUpStreamId() {
        return upStreamId;
    }

    public void setUpStreamId(Integer upStreamId) {
        this.upStreamId = upStreamId;
    }

    public String getUpStreamName() {
        return upStreamName;
    }

    public void setUpStreamName(String upStreamName) {
        this.upStreamName = upStreamName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
