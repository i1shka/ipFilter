package com.example.ipFilter;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class IpFilterController {

    @Autowired
    IpFilterService ipFilterService;

    @PostMapping("check_ip")
    public String checkIp(String ip, String mask, MultipartFile file_dump, Model model) {
        Path path = Path.of("src/main/resources/dumps/" + file_dump.getOriginalFilename());
        try {
            file_dump.transferTo(path);
            ipFilterService.getDataFromForm(new IpFilter(ip, mask), file_dump);
            model.addAttribute("inputIp", ip);
            model.addAttribute("inputMask", mask);
            model.addAttribute("filename", file_dump.getOriginalFilename());
            model.addAttribute("list", ipFilterService.resultIpList);
//            FileUtils.cleanDirectory(new File("src/main/resources/dumps/"));
            Files.delete(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("inputIp", ip);
            model.addAttribute("inputMask", mask);
            model.addAttribute("filename", file_dump.getOriginalFilename());
            model.addAttribute("errorMsg", e.getMessage());
        }
        return "index";
    }
}
