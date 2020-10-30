package tk.mybatis.springboot.service;

import tk.mybatis.springboot.dto.Result;
import tk.mybatis.springboot.dto.UpStreamDTO;
import tk.mybatis.springboot.dto.UpstreamInstanceDTO;
import tk.mybatis.springboot.mapper.UpStreamInstanceMapper;
import tk.mybatis.springboot.model.UpstreamInstance;

public interface UpStreamInstanceService {
    Result insert(UpstreamInstanceDTO upStreamDTO);
    Result getList(UpstreamInstanceDTO upstreamInstanceDTO);
    Result getUrl(UpstreamInstanceDTO upstreamInstanceDTO);
    Result updateStream(UpstreamInstanceDTO upstreamInstanceDTO);
    Result deleteStream(UpstreamInstanceDTO upstreamInstanceDTO);
    Result enableOrDisable(UpstreamInstanceDTO upstreamInstanceDTO);
    Result getById(Long id);
}
