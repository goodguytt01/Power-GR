package tk.mybatis.springboot.mapper;


import tk.mybatis.springboot.model.Upstream;
import tk.mybatis.springboot.model.UpstreamInstance;

import java.util.List;


public interface UpStreamInstanceMapper {
    int insert(UpstreamInstance upstream);
    List<UpstreamInstance> getList(UpstreamInstance instance);
    UpstreamInstance getUrl(UpstreamInstance upstream);
    int updateUpStream(UpstreamInstance upstream);
    int deleteUpStream(UpstreamInstance upstream);
    int enableOrDisable(UpstreamInstance upstream);
    Upstream getById(Long id);
}
