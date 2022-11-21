package com.ithawk.ipfs.demo.service;


import java.io.IOException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public interface IpfsService {

     String addRecode(String recode) throws IOException ;

     String selectRecode(String hash) throws IOException;

     String uploadFile(MultipartFile file) throws IOException ;

     byte[] downloadFile(@RequestParam("hash") String hash) throws IOException;
}
