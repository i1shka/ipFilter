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

//    private static final String SAMPLE_FILE = Objects.requireNonNull(IpFilterService.class.getResource("/dumps/sip_NA_incoming.pcap")).getFile();

    public void checkIp(IpFilter filter, String file_dump) throws IOException, WrongIpException {
        // TODO: Replace SAMPLE_FILE by the actual file you want to parse
        String sample=Objects.requireNonNull(IpFilterService.class.getResource(file_dump)).getFile();
        Pcap pcap = Pcap.openStream(sample);
//        pcap.loop(new TcpUdpPacketHandler());
        TcpUdpPacketHandler tup=new TcpUdpPacketHandler();
        pcap.loop(tup);
        pcap.close();
        ArrayList<String> ipFromDump = new ArrayList<String> ((tup.ipFromDump).stream().distinct().toList());
        System.out.println("ipFromDump = " + ipFromDump);


//        IpFilter filter=new IpFilter("192.168.8.106", "255.255.255.0");
        resultIpList=filter.hostComparing(ipFromDump);
        if (resultIpList.isEmpty()) System.out.println("ip не найдены");
        else System.out.println("resultIpList = " + resultIpList);
//        return resultIpList;
    }

    /*public static void main(String[] args) throws IOException, WrongIpException {
        // TODO: Replace SAMPLE_FILE by the actual file you want to parse
        Pcap pcap = Pcap.openStream(SAMPLE_FILE);
//        pcap.loop(new TcpUdpPacketHandler());
            TcpUdpPacketHandler tup=new TcpUdpPacketHandler();
            pcap.loop(tup);
            pcap.close();
        ArrayList<String> ipFromDump = new ArrayList<String> ((tup.ipFromDump).stream().distinct().toList());
        System.out.println("ipFromDump = " + ipFromDump);


        IpFilter filter=new IpFilter("192.168.8.106", "255.255.255.0");
        ArrayList<String>resultIpList=filter.hostComparing(ipFromDump);
        if (resultIpList.isEmpty()) System.out.println("ip не найдены");
        else System.out.println("resultIpList = " + resultIpList);
    }*/

}