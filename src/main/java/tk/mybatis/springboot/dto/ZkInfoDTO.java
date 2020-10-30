package tk.mybatis.springboot.dto;

public class ZkInfoDTO {
    private String zkAddr;

    private String domain;

    private String interfaceAddr;

    public String getInterfaceAddr() {
        return interfaceAddr;
    }

    public void setInterfaceAddr(String interfaceAddr) {
        this.interfaceAddr = interfaceAddr;
    }

    public String getZkAddr() {
        return zkAddr;
    }

    public void setZkAddr(String zkAddr) {
        this.zkAddr = zkAddr;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
