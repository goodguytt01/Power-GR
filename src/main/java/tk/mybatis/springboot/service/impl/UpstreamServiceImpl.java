package tk.mybatis.springboot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.dto.Result;
import tk.mybatis.springboot.dto.UpStreamDTO;
import tk.mybatis.springboot.mapper.UpStreamMapper;
import tk.mybatis.springboot.model.Upstream;
import tk.mybatis.springboot.service.UpStreamService;

import java.util.ArrayList;
import java.util.List;

@Service("upStreamService")
public class UpstreamServiceImpl implements UpStreamService {

    @Autowired
    private UpStreamMapper upStreamMapper;

    @Override
    public Result insert(UpStreamDTO upStreamDTO) {
        if(upStreamDTO!=null) {
            Upstream upstream = new Upstream();
            BeanUtils.copyProperties(upStreamDTO,upstream);
            upStreamMapper.insert(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result getList() {
        List<Upstream> list = upStreamMapper.getList();
        List<UpStreamDTO> upStreamDTOS = new ArrayList<>();
        for(Upstream method:list){
            UpStreamDTO dto = new UpStreamDTO();
            BeanUtils.copyProperties(method,dto);
            upStreamDTOS.add(dto);
        }
        return Result.success(upStreamDTOS);
    }

    @Override
    public Result getUrl(UpStreamDTO upStreamDTO) {
        if(upStreamDTO!=null) {
            Upstream upstream = new Upstream();
            BeanUtils.copyProperties(upStreamDTO,upstream);
            upstream = upStreamMapper.getUrl(upstream);
            BeanUtils.copyProperties(upstream,upStreamDTO);
            return Result.success(upStreamDTO);
        }
        return null;
    }

    @Override
    public Result updateStream(UpStreamDTO upStreamDTO) {
        Upstream upstream = new Upstream();
        if(upStreamDTO!=null){
            BeanUtils.copyProperties(upStreamDTO,upstream);
            upStreamMapper.updateUpStream(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result deleteStream(UpStreamDTO upStreamDTO) {
        Upstream upstream = new Upstream();
        if(upStreamDTO!=null){
            BeanUtils.copyProperties(upStreamDTO,upstream);
            upStreamMapper.deleteUpStream(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result enableOrDisable(UpStreamDTO upStreamDTO) {
        Upstream upstream = new Upstream();
        if(upStreamDTO!=null){
            BeanUtils.copyProperties(upStreamDTO,upstream);
            upStreamMapper.enableOrDisable(upstream);
            return Result.success(null);
        }
        return null;
    }

    @Override
    public Result getById(Long id) {
        return Result.success(upStreamMapper.getById(id));
    }
}
