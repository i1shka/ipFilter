package com.example.ipFilter;

import io.pkts.PacketHandler;
import io.pkts.packet.*;
import io.pkts.protocol.Protocol;

import java.io.IOException;
import java.util.ArrayList;

public class TcpUdpPacketHandler implements PacketHandler {
    ArrayList<String> ipFromDump =new ArrayList<>();

    @Override
    public boolean nextPacket(Packet packet) throws IOException {
        // Check the packet protocol
        if (packet.hasProtocol(Protocol.TCP)) {
            // Cast the packet to subclass
            TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
            String bufferSTcp = tcpPacket.getParentPacket().getSourceIP();
            ipFromDump.add(bufferSTcp);
            String bufferDTcp=tcpPacket.getParentPacket().getDestinationIP();
            ipFromDump.add(bufferDTcp);
            // Explore the available methods.
            // This sample code prints the payload, but you can get other attributes as well
//            Buffer buffer = tcpPacket.getPayload();

//            String b=tcpPacket.getParentPacket().getSourceIP();
//            sourceIP.add(b);
//            sourceIP.stream().distinct();
            /*if (buffer != null) {


//                System.out.println("TCP: "  + buffer);
            }*/
        } else if (packet.hasProtocol(Protocol.UDP)) {
            // Cast the packet to subclass
            UDPPacket udpPacket = (UDPPacket) packet.getPacket(Protocol.UDP);
            String bufferSUdp = udpPacket.getParentPacket().getSourceIP();
            ipFromDump.add(bufferSUdp);
            String bufferDUdp=udpPacket.getParentPacket().getDestinationIP();
            ipFromDump.add(bufferDUdp);
            // Explore the available methods.
            // This sample code prints the payload, but you can get other attributes as well
            /*Buffer buffer = udpPacket.getPayload();
            if (buffer != null) {
//                System.out.println("UDP: " +  buffer);
            }*/
        }
        // Return true if you want to keep receiving next packet.
        // Return false if you want to stop traversal
        return true;
    }

}