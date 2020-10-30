package tk.mybatis.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.dto.JsonResVo;
import tk.mybatis.springboot.dto.UpstreamInstanceDTO;
import tk.mybatis.springboot.service.UpStreamInstanceService;


@RestController
@RequestMapping("/UpStreamInstance")
public class UpStreamInstanceController {
    @Autowired
    private UpStreamInstanceService upStreamService;


    @RequestMapping(method =  {RequestMethod.POST},value = "/Add")
    @ResponseBody
    public JsonResVo addPropertyKey(@RequestBody UpstreamInstanceDTO upStreamDTO) {
        upStreamService.insert(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "/List")
    @ResponseBody
    public JsonResVo addPropertyKey(Integer upStreamId) {
        UpstreamInstanceDTO dto = new UpstreamInstanceDTO();
        dto.setUpStreamId(upStreamId);
        return JsonResVo.buildSuccess(upStreamService.getList(dto));
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Delete")
    @ResponseBody
    public JsonResVo delete(@RequestBody UpstreamInstanceDTO upStreamDTO) {
        upStreamDTO.setDeleteFlag(true);
        upStreamService.deleteStream(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Modify")
    @ResponseBody
    public JsonResVo update(@RequestBody UpstreamInstanceDTO upStreamDTO) {
        upStreamService.updateStream(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Status")
    @ResponseBody
    public JsonResVo updateStatus(@RequestBody UpstreamInstanceDTO upStreamDTO) {
        upStreamService.enableOrDisable(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "")
    @ResponseBody
    public JsonResVo getById(Long id) {
        return JsonResVo.buildSuccess(upStreamService.getById(id));
    }
}
