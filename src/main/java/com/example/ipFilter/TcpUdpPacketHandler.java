package com.example.ipFilter;

import io.pkts.PacketHandler;
import io.pkts.packet.*;
import io.pkts.protocol.Protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TcpUdpPacketHandler implements PacketHandler {
    List<String> ipFromDump = new ArrayList<>();

    @Override
    public boolean nextPacket(Packet packet) throws IOException {
        if (packet.hasProtocol(Protocol.IPv4)) {
            IPv4Packet iPv4Packet = (IPv4Packet) packet.getPacket(Protocol.IPv4);
            String bufferS = iPv4Packet.getSourceIP();
            ipFromDump.add(bufferS);
            String bufferD = iPv4Packet.getDestinationIP();
            ipFromDump.add(bufferD);
        }
        return true;
    }
}