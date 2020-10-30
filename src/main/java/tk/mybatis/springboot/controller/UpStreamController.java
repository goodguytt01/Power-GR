package tk.mybatis.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.dto.JsonResVo;
import tk.mybatis.springboot.dto.UpStreamDTO;
import tk.mybatis.springboot.service.UpStreamService;


@RestController
@RequestMapping("/UpStream")
public class UpStreamController {

    @Autowired
    private UpStreamService upStreamService;


    @RequestMapping(method =  {RequestMethod.POST},value = "/Add")
    @ResponseBody
    public JsonResVo addPropertyKey(@RequestBody UpStreamDTO upStreamDTO) {
        upStreamService.insert(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "/List")
    @ResponseBody
    public JsonResVo addPropertyKey() {
        return JsonResVo.buildSuccess(upStreamService.getList());
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Delete")
    @ResponseBody
    public JsonResVo delete(@RequestBody UpStreamDTO upStreamDTO) {
        upStreamDTO.setDeleteFlag(true);
        upStreamService.deleteStream(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Modify")
    @ResponseBody
    public JsonResVo update(@RequestBody UpStreamDTO upStreamDTO) {
        upStreamService.updateStream(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.POST},value = "/Status")
    @ResponseBody
    public JsonResVo updateStatus(@RequestBody UpStreamDTO upStreamDTO) {
        upStreamService.enableOrDisable(upStreamDTO);
        return JsonResVo.buildSuccess();
    }

    @RequestMapping(method =  {RequestMethod.GET},value = "")
    @ResponseBody
    public JsonResVo getById(Long id) {
        return JsonResVo.buildSuccess(upStreamService.getById(id));
    }
}
