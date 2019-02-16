package com.excise.base;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DeleteDoc {
    public static void main(String[] args) throws UnknownHostException {
        /**
         * 删除文档
         * http://weekend01:9200/secisland/secilog/1/   DELETE
         */
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","my-application")//集群名字，必须
                .put("client.transport.sniff", true)//自动嗅探整个集群，不用配置所有ip
                .build();
        Client client = TransportClient.builder()
                .settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("weekend01"), 9300));
        DeleteResponse deleteResponse = client.prepareDelete("secisland", "secilog", "2").get();
        boolean found = deleteResponse.isFound();
        if(found){
            System.out.println("删除成功!");
        } else {
            System.out.println("没有发现文档！!");
        }
    }
}
