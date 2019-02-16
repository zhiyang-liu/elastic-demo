package com.excise.base;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReadDoc {
    public static void main(String[] args) throws UnknownHostException {
        /**
         * 从索引secisland中查询类型secilog且id为1的文档
         * http://weekend01:9200/secisland/secilog/1/   GET
         */
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","my-application")//集群名字，必须
                .put("client.transport.sniff", true)//自动嗅探整个集群，不用配置所有ip
                .build();
        Client client = TransportClient.builder()
                .settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("weekend01"), 9300));
        GetResponse response = client.prepareGet("secisland", "secilog1", "1").get();
        String source = response.getSource().toString();
        long version = response.getVersion();
        String indexName = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        System.out.println("source:" + source);
        System.out.println("version:" + version);
        System.out.println("indexName:" + indexName);
        System.out.println("type::" + type);
        System.out.println("id:" + id);
    }
}
