package tk.mybatis.springboot.service;


import tk.mybatis.springboot.dto.DubboInterfaceDTO;
import tk.mybatis.springboot.dto.Result;

import java.util.List;

public interface DubboInterfaceService {
    Result insert(DubboInterfaceDTO dubboInterface);
    Result getList();
    Result getUrl(DubboInterfaceDTO dubboInterface);
    Result updateInterface(DubboInterfaceDTO dubboInterface);
    Result deleteInterface(DubboInterfaceDTO dubboInterface);
    Result enableOrDisable(DubboInterfaceDTO dubboInterface);
    Result getById(Long id);

}
