package com.example.ipFilter;

import io.pkts.Pcap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class IpFilterService {
    ArrayList<String>resultIpList=new ArrayList<>();;

    public void getDataFromForm(IpFilter filter, String file_dump) throws Exception {
        Pcap pcap = Pcap.openStream(file_dump);
        TcpUdpPacketHandler tup = new TcpUdpPacketHandler();
        pcap.loop(tup);
        pcap.close();
        checkIp(tup, filter);
    }

    public void checkIp(TcpUdpPacketHandler tup, IpFilter filter) throws Exception {
        ArrayList<String> distinctIp = new ArrayList<>((tup.ipFromDump).stream().distinct().toList());
        resultIpList=filter.hostComparing(distinctIp);
        if (resultIpList.isEmpty()) throw new Exception ("ip не найдены");
    }


}