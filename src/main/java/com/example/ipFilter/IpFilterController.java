package com.example.ipFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Controller
public class IpFilterController {

    @Autowired
    IpFilterService ipFilterService;

    @PostMapping("check_ip")
    public String checkIp(String ip, String mask, MultipartFile file_dump, Model model){
        try {
            byte[] bytes = file_dump.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(Objects.requireNonNull(file_dump.getOriginalFilename()))));
            stream.write(bytes);
            stream.close();
//            System.out.println("file_dump.get = " + file_dump.get);
            ipFilterService.checkIp(new IpFilter(ip, mask), file_dump.getOriginalFilename());
            model.addAttribute("list", ipFilterService.resultIpList.toString());
        } catch (IOException | WrongIpException e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMsg", e.getMessage());
        }
//        model.addAttribute("file_dump", file_dump.);
        return "index";
    }
}
