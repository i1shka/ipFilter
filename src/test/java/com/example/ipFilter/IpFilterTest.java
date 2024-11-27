package com.example.ipFilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class IpFilterTest {

    @Test
    void testConstructorOk1(){
        Assertions.assertDoesNotThrow(()->new IpFilter("192.168.0.1", "255.255.255.0"));
    }

    @Test
    void testConstructorOk2(){
        Assertions.assertDoesNotThrow(()->new IpFilter("255.255.255.255", "255.255.255.0"));
    }

    @Test
    void testConstructorOk3(){
        Assertions.assertDoesNotThrow(()->new IpFilter("1.0.0.1", "255.255.255.0"));
    }

    @Test
    void testConstructorNok1(){
        Assertions.assertThrows(WrongIpException.class, ()->new IpFilter("30.168.1.255.1", "255.255.255.0"));
    }

    @Test
    void testConstructorNok2(){
        Assertions.assertThrows(WrongIpException.class, ()->new IpFilter("3...3", "255.255.255.0"));
    }

    @Test
    void testConstructorNok3(){
        Assertions.assertThrows(WrongIpException.class, ()->new IpFilter("192,67.43.a", "255.256.255.0"));
    }

    @Test
    void testConstructorNok4(){
        Assertions.assertThrows(WrongIpException.class, ()->new IpFilter("127.1.", "255.256.255.0"));
    }

    @Test
    void testConstructorNok5(){
        Assertions.assertThrows(WrongIpException.class, ()->new IpFilter("-1.2.3.4", "255.256.255.0"));
    }

    @Test
    void testSplitToOctetsOk1() {
        String actualStr="192.168.0.1";
//        int[]actual=IpFilter.splitToOctets(actualStr);
        int[]expected={192,168,0,1};
        Assertions.assertArrayEquals(expected,IpFilter.splitToOctets(actualStr));
    }

    @Test
    void testSplitToOctetsOk2() {
        String actualStr="0.0.0.0";
//        int[]actual=IpFilter.splitToOctets(actualStr);
        int[]expected={0,0,0,0};
        Assertions.assertArrayEquals(expected,IpFilter.splitToOctets(actualStr));
    }

    @Test
    void testApplyMaskOk1() {
        int [] addrBit={192,168,0,10};
        int [] maskBit={255,0,0,0};
        String expected="192.0.0.0";
        Assertions.assertEquals(expected, IpFilter.applyMask(addrBit,maskBit));
    }

    @Test
    void testApplyMaskOk2() {
        int [] addrBit={192,168,0,10};
        int [] maskBit={0,0,0,0};
        String expected="0.0.0.0";
        Assertions.assertEquals(expected, IpFilter.applyMask(addrBit,maskBit));
    }

    @Test
    void testApplyMaskOk3() {
        int [] addrBit={192,168,0,10};
        int [] maskBit={255,255,255,255};
        String expected="192.168.0.10";
        Assertions.assertEquals(expected, IpFilter.applyMask(addrBit,maskBit));
    }

    @Test
    void testApplyMaskOk4() {
        int [] addrBit={10,0,60,127};
        int [] maskBit={255,255,255,192};
        String expected="10.0.60.64";
        Assertions.assertEquals(expected, IpFilter.applyMask(addrBit,maskBit));
    }

    @Test
    void testApplyMaskOk5() {
        int [] addrBit={172,12,63,27};
        int [] maskBit={255,255,252,0};
        String expected="172.12.60.0";
        Assertions.assertEquals(expected, IpFilter.applyMask(addrBit,maskBit));
    }

    @Test
    void testApplyMaskNok1() {
        int [] addrBit={0,0,0,0};
        int [] maskBit={255,255,255,255};
        String expected="0.0.0.0";
        Assertions.assertEquals(expected, IpFilter.applyMask(addrBit,maskBit));
    }

    @Test
    void testHostComparingOk1() throws WrongIpException {
        IpFilter filter=new IpFilter("192.168.8.106", "255.255.255.0");
        ArrayList<String>ipList= new ArrayList<>(Arrays.asList("192.168.8.106","192.168.8.1", "192.168.7.108", "191.168.8.106"));
        ArrayList<String>expected=new ArrayList<>(Arrays.asList("192.168.8.106", "192.168.8.1"));
        Assertions.assertIterableEquals(expected, filter.hostComparing(ipList));
    }

    @Test
    void testHostComparingOk2() throws WrongIpException {
        IpFilter filter=new IpFilter("192.168.8.106", "0.0.0.0");
        ArrayList<String>ipList= new ArrayList<>(Arrays.asList("192.168.8.106","192.168.8.1", "192.168.7.108", "191.168.8.106"));
        ArrayList<String>expected=new ArrayList<>(Arrays.asList("192.168.8.106","192.168.8.1", "192.168.7.108", "191.168.8.106"));
        Assertions.assertIterableEquals(expected, filter.hostComparing(ipList));
    }

    @Test
    void testHostComparingOk3() throws WrongIpException {
        IpFilter filter=new IpFilter("192.168.8.106", "255.255.255.255");
        ArrayList<String>ipList= new ArrayList<>(Arrays.asList("192.168.8.106","192.168.8.1", "192.168.7.108", "191.168.8.106"));
        ArrayList<String>expected=new ArrayList<>(List.of("192.168.8.106"));
        Assertions.assertIterableEquals(expected, filter.hostComparing(ipList));
    }

    @Test
    void testHostComparingNok1() throws WrongIpException {
        IpFilter filter=new IpFilter("172.168.8.106", "255.255.255.0");
        ArrayList<String>ipList= new ArrayList<>(Arrays.asList("192.168.8.106","192.168.8.1", "192.168.7.108", "191.168.8.106"));
        ArrayList<String>expected=new ArrayList<>(Arrays.asList());
        Assertions.assertIterableEquals(expected, filter.hostComparing(ipList));
    }

    @Test
    void testHostComparingNok2() throws WrongIpException {
        IpFilter filter=new IpFilter("172.168.8.106", "255.255.255.0");
        ArrayList<String>ipList= new ArrayList<>(Arrays.asList());
        ArrayList<String>expected=new ArrayList<>(Arrays.asList());
        Assertions.assertIterableEquals(expected, filter.hostComparing(ipList));
    }
}