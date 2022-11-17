package modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import modules.common.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("ipfs")
public class WebController {

    @Autowired
    private IpfsService ipfsService;

    @ResponseBody
    @RequestMapping(value = "/{hash}", method = RequestMethod.GET)
    public SelectResult selectRecode(@PathVariable("hash") String hash) {
        try {
            String value = ipfsService.selectRecode(hash);
            return new SelectResult(200, value);
        } catch (Exception e) {
            return new SelectResult(500, "error:" + e.getMessage());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/addString", method = RequestMethod.POST)
    public AddResult addRecode(@RequestBody Request request) {
        try {
            String value = ipfsService.addRecode(request.getDateString());

            return new AddResult(200, value);
        } catch (Exception e) {
            return new AddResult(500, "error:" + e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public AddResult uploadFile(@RequestParam("file") MultipartFile file) {
        // 上传
        try {
            String value = ipfsService.uploadFile(file);
            if (StringUtils.isEmpty(value)) {
                return new AddResult(500, "上传文件失败！");
            }
            return new AddResult(200, value);
        } catch (Exception e) {
            return new AddResult(500, "error:" + e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/downloadFile/{hash}", method = RequestMethod.GET)
    public byte[] downloadFile(@PathVariable("hash") String hash) {
        try {
            return ipfsService.downloadFile(hash);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }


    @Getter
    @Setter
    @AllArgsConstructor
    class AddResult {
        private Integer status;
        private String hash;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class SelectResult {
        private Integer status;
        private String result;
    }

}
