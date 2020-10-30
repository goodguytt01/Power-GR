package tk.mybatis.springboot.mapper;


import tk.mybatis.springboot.model.DubboInterface;
import tk.mybatis.springboot.model.Upstream;

import java.util.List;


public interface UpStreamMapper {
    int insert(Upstream upstream);
    List<Upstream> getList();
    Upstream getUrl(Upstream upstream);
    int updateUpStream(Upstream upstream);
    int deleteUpStream(Upstream upstream);
    int enableOrDisable(Upstream upstream);
    Upstream getById(Long id);
}
