package com.example.ipFilter;

import io.pkts.Pcap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class IpFilterService {
    List<String> resultIpList = new ArrayList<>();

    public void getDataFromForm(IpFilter filter, MultipartFile file_dump) throws Exception {
        Pcap pcap = Pcap.openStream(file_dump.getInputStream());
        TcpUdpPacketHandler tup = new TcpUdpPacketHandler();
        pcap.loop(tup);
        pcap.close();
        checkIp(tup, filter);
    }

    public void checkIp(TcpUdpPacketHandler tup, IpFilter filter) throws Exception {
        List<String> distinctIp =(tup.ipFromDump).stream().distinct().toList();
        resultIpList = filter.hostComparing(distinctIp);
        if (resultIpList.isEmpty()) throw new Exception("ip не найдены");
        else IpDBLoader.loadToDB(resultIpList);
    }
}