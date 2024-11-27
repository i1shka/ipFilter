package com.example.ipFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IpFilter {
    String ip;
    String  mask;

    public IpFilter(String ip, String mask) throws WrongIpException {
        if (!ip.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")) throw new WrongIpException(ip, "Неверный ip " + ip);
        this.ip = ip;
        this.mask = mask;
    }

    public static int[] splitToOctets(String addrDec) {
        int[]octet= Stream.of(addrDec.split("\\.")).mapToInt(Integer::parseInt).toArray();
        return octet;
    }

    public static String applyMask(int[] addrBit, int[] maskBit) {
        int [] addrHost=new int[4];
        for (int i=0; i< addrHost.length; i++){
            addrHost[i]=addrBit[i]&maskBit[i];
        }
        return Arrays.stream(addrHost).mapToObj(String::valueOf).collect(Collectors.joining("."));
    }

    public ArrayList<String> hostComparing(ArrayList<String>ipFromDump) {
        ArrayList<String>filteredIp=new ArrayList<>();
        String expectedHost=applyMask(splitToOctets(ip), splitToOctets(mask));
        for (String ipDump:ipFromDump) {
            String ipStr=applyMask(splitToOctets(ipDump),splitToOctets(mask));
            if (ipStr.equals(expectedHost)) {
                filteredIp.add(ipDump);
            }
        }
        return filteredIp;
    }

}

