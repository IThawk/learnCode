package com.ithawk.ipfs.demo.service.impl;

import com.ithawk.ipfs.core.IPFS;
import com.ithawk.ipfs.core.MerkleNode;
import com.ithawk.ipfs.core.NamedStreamable;
import com.ithawk.ipfs.demo.service.IpfsService;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class IpfsServiceImpl implements IpfsService {
    @Value("${ipfs.downloadBaseUrl}")
    private String downloadBaseUrl;

    @Value("${ipfs.ipfsIp}")
    private String ipfsIp;

    @Value("${ipfs.ipfsPort}")
    private String ipfsPort;
    //	static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");// ipfs的服务器地址和端口

    @Value("${system.localPath}")
    private String localPath;
    static IPFS ipfs;

    @PostConstruct
    void init() {
        if (ipfs == null) {
            ipfs = new IPFS(new MultiAddress("/ip4/" + ipfsIp + "/tcp/" + ipfsPort));
        }

    }

    @Override
    public String addRecode(String recode) throws IOException {
        NamedStreamable.ByteArrayWrapper byteArray = new NamedStreamable.ByteArrayWrapper(recode.getBytes());
        MerkleNode addResult = ipfs.add(byteArray).get(0);
        return addResult.hash.toString();

    }

    @Override
    public String selectRecode(String hash) throws IOException {
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = ipfs.cat(filePointer);
        String str = new String(data);
        return str;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String filePath = this.saveFile(file);

        if (StringUtils.hasLength(filePath)) {
            return null;
        }

        //保存上传文件
        NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(new File(filePath));
        //wrapper对象上传daoipfs
        MerkleNode result = ipfs.add(savefile).get(0);
        return downloadBaseUrl + result.hash.toString();
    }

    @Override
    public byte[] downloadFile(@RequestParam("hash") String hash) throws IOException {
        // 上传
        Multihash filePointer = Multihash.fromBase58(hash);//参数为文件 hash
        byte[] fileContents = ipfs.cat(filePointer);
        return fileContents;
    }

    public String saveFile(MultipartFile file) {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID() + suffixName;
        //指定本地文件夹存储图片
        String filePath = localPath + fileName;
        try {
            file.transferTo(new File(filePath));
            return filePath;
        } catch (IOException e) {
            return null;
        }

    }
}
