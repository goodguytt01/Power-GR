package tk.mybatis.springboot.service;

import tk.mybatis.springboot.dto.DubboInterfaceDTO;
import tk.mybatis.springboot.dto.Result;
import tk.mybatis.springboot.dto.UpStreamDTO;

public interface UpStreamService {
    Result insert(UpStreamDTO upStreamDTO);
    Result getList();
    Result getUrl(UpStreamDTO upStreamDTO);
    Result updateStream(UpStreamDTO upStreamDTO);
    Result deleteStream(UpStreamDTO upStreamDTO);
    Result enableOrDisable(UpStreamDTO upStreamDTO);
    Result getById(Long id);
}
