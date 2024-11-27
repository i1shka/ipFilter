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
        if (packet.hasProtocol(Protocol.IPv4)) {
            IPv4Packet iPv4Packet = (IPv4Packet) packet.getPacket(Protocol.IPv4);
            String bufferSTcp = iPv4Packet.getSourceIP();
            ipFromDump.add(bufferSTcp);
            String bufferDTcp=iPv4Packet.getDestinationIP();
            ipFromDump.add(bufferDTcp);
        }
        return true;
    }

}