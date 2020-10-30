package tk.mybatis.springboot.mapper;


import tk.mybatis.springboot.model.DubboInterface;

import java.util.List;


public interface DubboInterfaceMapper {
    int insert(DubboInterface dubboInterface);
    List<DubboInterface> getList();
    DubboInterface getUrl(DubboInterface dubboInterface);
    int updateInterface(DubboInterface dubboInterface);
    int deleteInterface(DubboInterface dubboInterface);
    int enableOrDisable(DubboInterface dubboInterface);
    DubboInterface getById(Long id);
}
