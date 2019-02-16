package com.excise.base;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DeleteIndex {
    public static void main(String[] args) throws UnknownHostException {
        /**
         * 删除索引
         * http://weekend01:9200/   DELETE
         */
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","my-application")//集群名字，必须
                .put("client.transport.sniff", true)//自动嗅探整个集群，不用配置所有ip
                .build();
        Client client = TransportClient.builder()
                .settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("weekend01"), 9300));
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("secisland");
        client.admin().indices().delete(deleteIndexRequest);
    }
}
