package com.excise.base;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.net.InetAddress;

public class AddDoc {
    public static void main(String[] args) throws IOException {
        /**
         * 增加文档，json格式并不是按照索引定义的，可以添加其他格式的json
         * http://weekend01:9200/secisland/secilog/1   PUT
         * {"computer":"huipu","message":"hello world!"}
         */
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","my-application")//集群名字，必须
                .put("client.transport.sniff", true)//自动嗅探整个集群，不用配置所有ip
                .build();
        Client client = TransportClient.builder()
                .settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("weekend01"), 9300));
        IndexResponse response = client
                .prepareIndex("secisland", "secilog", "2")//在插入文档时，类型可以随便填写，并不一定只能在创建索引时定义的
                .setSource(
                        XContentFactory.jsonBuilder().startObject()
                            .field("computer", "daier")
                            .field("message", "hello world!")
                .endObject()).get();
        System.out.println("index:" + response.getIndex() + " insert doc id:" + response.getId() + " result:" + response.isCreated());
    }
}
