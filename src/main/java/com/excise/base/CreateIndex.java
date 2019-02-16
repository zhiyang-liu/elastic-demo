package com.excise.base;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import java.io.IOException;
import java.net.InetAddress;

public class CreateIndex {
    public static void main(String[] args) throws IOException {
        /**
         * 创建索引库
         * http://weekend01:9200/scisland/  PUT
         * {"mapping":{"scilog":{"properties":{"computer":{"type":"string"},"message":{"type":"string"}}}}}
         */
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","my-application")//集群名字，必须
                .put("client.transport.sniff", true)//自动嗅探整个集群，不用配置所有ip
                .build();
        Client client = TransportClient.builder()
                .settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("weekend01"), 9300));
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("settings")
                .field("number_of_shards", "1")//分片数量
                .field("number_of_replicas", "0")//副本数量
                .endObject()
                .endObject()
                .startObject()
                .startObject("secilog")//类型
                .startObject("properties")//下面是文档属性
                .startObject("computer").field("type", "string").field("store", "yes")
                .endObject()
                .startObject("message").field("type", "string").field("store", "yes")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        CreateIndexRequestBuilder cirb = client.admin().indices()
                .prepareCreate("secisland")//索引名字
                .setSource(mapping);
        CreateIndexResponse response = cirb.execute().actionGet();
        if(response.isAcknowledged()) {
            System.out.println("创建成功！");
        } else {
            System.out.println("创建失败！");
        }
    }
}
